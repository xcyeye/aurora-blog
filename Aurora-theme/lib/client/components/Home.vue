<template>
  <div class="home" :style="getHomeHeight">
    <div class="home-hero-img" :class="{'home-hero-img-custom ': isHome}" id="home-hero-img">
      <img :src="getHeroImg">
    </div>
    <slot name="home1"></slot>
    <div v-if="randomSawRes" :class="{'home-random-say-custom': isHome}" class="home-random-say">
      <div v-if="isHome">
        <span>「 {{obj.output}}」</span>
      </div>
    </div>
    <slot name="home2"></slot>
    <div :class="{'home-social-custom': isHome}" class="home-social">
      <div :class="{'home-social-custom-single': isHome}">
        <HomeSidebarSocialItem :sidebar-row-pc-var="socialsIsHomeNum + 1"
                               :sidebar-width-pc-var="sidebarWidthPcVar"
                               :sidebar-row-var="socialsIsHomeNum + 1"
                               :show-social-img="true"
                               :sidebar-cut-width="1"
                               :sidebar-width-var="sidebarWidthVar"
                               :social-item="item" v-for="item in socialsArrTemp"/>
      </div>
    </div>
    <div v-if="isHome" class="home-click-down">
      <span @click="clickDown" class="aurora-iconfont-common home-click-down-icon"></span>
    </div>
    <div v-if="showWave && isHome" :class="showHomeWaveStyle ? 'home-wave-show': 'home-wave-hide'" class="home-wave-box" ref="home-wave">
      <div class="home-wave">
        <canvas class="home-wave-canvas1" id="home-wave-canvas1"></canvas>
        <canvas class="home-wave-canvas2" id="home-wave-canvas2"></canvas>
      </div>
    </div>
    <slot name="home3"></slot>
  </div>
  <div v-if="isHome" class="theme-default-content custom">
    <Content />
  </div>
</template>
<script lang="ts">
import { computed, defineComponent } from 'vue'
import { usePageFrontmatter, useSiteLocaleData, withBase} from '@vuepress/client'
import { isArray } from '@vuepress/shared'
import type { DefaultThemeHomePageFrontmatter } from '../../shared'
import EasyTyper from "easy-typer-js";
import {useThemeLocaleData} from "../composables";

import { req,cors } from "../public/js/network.js";

import NavLink from './NavLink.vue'
import HomeSocial from './child/home/HomeSocial.vue'
import HomeSidebarSocialItem from './child/side/HomeSidebarSocialItem.vue'

export default defineComponent({
  name: 'Home',
  components: {
    NavLink,
    HomeSocial,
    HomeSidebarSocialItem
  },
  data() {
    return {
      socialsArrTemp: [],
      networkOption: {
        baseURL: 'https://v1.hitokoto.cn/?encode=text&c=j',
        timeout: 5000,
        method: 'GET',
        query: '',
      },
      randomSawRes: '',
      obj: {
        output: '',
        isEnd: false,
        speed: 80,
        singleBack: false,
        sleep: 1700,
        type: 'rollback',
        backSpeed: 70,
        sentencePause: false
      },
      intervalTime: 1500,
      socialsIsHomeNum: 0,
      sidebarWidthVar: 0,
      sidebarRowVar: 0,
      showHomeWaveStyle: false,
      //是否显示波浪效果
      showWave: true
    }
  },
  props: {
    showRandomSay:{
      type: Boolean,
      default() {
        return false
      }
    },
    showPrintText: {
      type: Boolean,
      default() {
        return false
      }
    },
    //电脑端最大社交长度为19 手机端首页最大为7个
    socialsArr: {
      type: Array,
      default() {
        return []
      }
    },
    sidebarWidthPcVar: {
      type: String,
      default() {
        return "88vw"
      }
    },
    homeHeightVar: {
      type: String,
      default() {
        return "100vh"
      }
    },
    navbarStyle: {
      type: String,
      default() {
        return ""
      }
    },
    isHome: {
      type: Boolean,
      default() {
        return false
      }
    },
    themeProperty: {
      type: Object,
      default() {
        return ''
      }
    },
  },
  setup() {
    const frontmatter = usePageFrontmatter<DefaultThemeHomePageFrontmatter>()
    const siteLocale = useSiteLocaleData()

    const heroImage = computed(() => {
      if (!frontmatter.value.heroImage) {
        return null
      }

      return withBase(frontmatter.value.heroImage)
    })

    const heroText = computed(() => {
      if (frontmatter.value.heroText === null) {
        return null
      }
      return frontmatter.value.heroText || siteLocale.value.title || 'Hello'
    })
    const heroAlt = computed(
        () => frontmatter.value.heroAlt || heroText.value || 'hero'
    )

    const tagline = computed(() => {
      if (frontmatter.value.tagline === null) {
        return null
      }
      return (
          frontmatter.value.tagline ||
          siteLocale.value.description ||
          'Welcome to your VuePress site'
      )
    })

    const actions = computed(() => {
      if (!isArray(frontmatter.value.actions)) {
        return []
      }

      return frontmatter.value.actions.map(
          ({ text, link, type = 'primary' }) => ({
            text,
            link,
            type,
          })
      )
    })

    const features = computed(() => {
      if (isArray(frontmatter.value.features)) {
        return frontmatter.value.features
      }
      return []
    })

    const footer = computed(() => frontmatter.value.footer)

    const footerHtml = computed(() => frontmatter.value.footerHtml)

    return {
      heroImage,
      heroAlt,
      heroText,
      tagline,
      actions,
      features,
      footer,
      footerHtml,
    }
  },
  created() {
    // 如果没有传入，或者传入的是一个空数组，那么就会使用新数组进行显示
    if (this.socialsArr.length === 0) {
      let socials = this.themeProperty.socials
      if (socials !== undefined) {
        new Promise((resolve,reject) => {
          for (let i = 0; i < socials.length; i++) {
            if (socials[i].isHome && socials[i].show) {
              this.socialsArr.push(socials[i])
              // this.socialsIsHomeNum ++
            }
          }
          resolve()
        }).then(() => {

        })
      }
    }else {
      //传入的是一个数组
      this.socialsIsHomeNum = this.socialsArr.length
    }

    if (this.themeProperty.wave !== undefined) {
      this.showWave = this.themeProperty.wave.showWave
    }

    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }

    if (this.themeProperty.randomSayApi !== undefined) {
      this.networkOption.baseURL = this.themeProperty.randomSayApi.urlApi

      if (this.themeProperty.randomSayApi.method !== undefined) {
        this.networkOption.method = this.themeProperty.randomSayApi.method
      }
    }

    this.fetchData()
    this.$store.commit('setHeroImg',{
      heroImg: this.heroImage
    })
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll, true)

    if (window.pageYOffset > 0) {
      this.showHomeWaveStyle = false
    }else {
      this.showHomeWaveStyle = true
    }

    this.$nextTick(() => {
      if (this.showWave && this.isHome) {
        import("../public/js/wave").then(module => {
          module.wave()
        })

      }
    })

    this.socialsArrTemp = []
    new Promise((resolve,reject) => {
      if (document.body.clientWidth < 719) {
        this.sidebarWidthVar = 0.92
        this.sidebarRowVar = this.socialsIsHomeNum + 1

        if (this.socialsArr.length > 7) {
          for (let i = 0; i < 7; i++) {
            this.socialsArrTemp.push(this.socialsArr[i])
          }
        }else {
          this.socialsArrTemp = this.socialsArr
        }
      }else {
        //可视区域宽度大于719
        if (this.socialsArr.length > 19) {
          for (let i = 0; i < 19; i++) {
            this.socialsArrTemp.push(this.socialsArr[i])
          }
        }else {
          this.socialsArrTemp = this.socialsArr
        }
      }
      resolve()
    }).then(() => {
      this.socialsIsHomeNum = this.socialsArrTemp.length
    })
  },
  methods: {
    clickDown() {
      import("smoothscroll-polyfill").then(module => {
        module.polyfill()
      })
      document.querySelector(".home-bottom").scrollIntoView({behavior: "smooth"})
    },

    handleScroll() {
      if (window.pageYOffset > 0) {
        this.showHomeWaveStyle = false
      }else {
        this.showHomeWaveStyle = true
      }
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    fetchData() {
      if (!this.isHome) {
        if (!this.showRandomSay) {
          return
        }
      }

      if (this.themeProperty.customRandomSay) {
        this.randomSawRes = this.themeProperty.customRandomValue === undefined ||
        this.themeProperty.customRandomValue == null ? "Aurora theme" : this.themeProperty.customRandomValue
        this.initTyped(this.randomSawRes,() => {
          setTimeout(() => {
            this.fetchData()
          },this.intervalTime)
        })
      }else {
        req(this.networkOption).then(res => {
          try {
            this.randomSawRes = res;
            const typed = this.initTyped(this.randomSawRes,() => {
              setTimeout(() => {
                this.fetchData()
              },this.intervalTime)
            })
          }catch (e) {
            this.initTyped('Aurora',() => {
              setTimeout(() => {
                this.fetchData()
              },this.intervalTime)
            })
          }
        })
      }
    },
    initTyped(input, fn, hooks) {
      const obj = this.obj
      const typed = new EasyTyper(obj, input, fn, hooks)
      return typed
    }
  },
  computed: {
    getHomeSocial() {
      if (this.isHome) {
        return 'home-social-custom'
      }
    },
    getHeroImg() {
      const themeLocale = useThemeLocaleData()
      let src = themeLocale.value.heroImg
      if (src === undefined) {
        console.warn("%c you need to set the heroImg field value,the default is: https://ooszy.cco.vin/img/blog-public/avatar.jpg","color: pink;")
        return "https://ooszy.cco.vin/img/blog-public/avatar.jpg"
      }else {
        return  withBase(src)
      }
    },
    getHomeHeight() {
      return '--homeHeight: ' + this.homeHeightVar + ";"
    },
    getHeroImage() {
      let src = this.themeProperty.heroLogo
      if (src === undefined) {
        console.log("you need to set the logo field value,the default is: \nhttps://ooszy.cco.vin/img/blog-public/avatar.jpg")
        return "https://ooszy.cco.vin/img/blog-public/avatar.jpg"
      }else {
        return src
      }
    }
  },
})
</script>
