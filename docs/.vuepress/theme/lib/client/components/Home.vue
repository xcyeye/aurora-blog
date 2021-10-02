<template>
  <div :class="{heroHome: isHome}">
    <main :style="navbarStyle" class="home" id="home" :aria-labelledby="heroText ? 'main-title' : undefined">
      <header class="hero" >
        <div class="hero-div">
          <img id="hero-img" :src="getHeroImage" :alt="heroAlt" />
        </div>

        <div :data="themeProperty.showHomeMood" v-if="themeProperty.showHomeMood">
          <div v-if="isHome" class="mood" >
            <span>{{themeProperty.mood}}</span>
          </div>
        </div>

        <div v-if="randomSawRes" class="random-saw">
          <div v-if="isHome"><span>「 {{obj.output}}」</span></div>
        </div>

        <home-social :theme-property="themeProperty" :is-home-page="isHome"/>
      </header>


      <div v-if="isHome" class="theme-default-content custom">
        <Content />
      </div>
    </main>
  </div>
</template>

<script lang="ts">

import { computed, defineComponent } from 'vue'
import {
  usePageData,
  usePageFrontmatter, useRouteLocale, useSiteData,
  useSiteLocaleData,
  withBase,
} from '@vuepress/client'
import { isArray } from '@vuepress/shared'
import type { DefaultThemeHomePageFrontmatter } from '../../shared'
import NavLink from './NavLink.vue'
import HomeSocial from './child/HomeSocial'
import $ from 'jquery'
import EasyTyper from "easy-typer-js";
// some-client-component.vue

//导入配置属性

const network = require('../public/js/network.js')

export default defineComponent({
  name: 'Home',
  components: {
    NavLink,
    HomeSocial,
  },
  data() {
    return {
      networkOption: {
        baseURL: '',
        timeout: 5000,
        method: 'get',
        query: ''
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
    }
  },
  props: {
    navbarStyle: {
      type: String,
      default() {
        return ""
      }
    },
    globalColor: {
      type: String,
      default() {
        return ''
      },
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
        return null
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

    const randomSaw = computed(() => {
      if (frontmatter.value.randomSaw === null) {
        return null
      }
      return frontmatter.value.randomSaw
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
      randomSaw,
      tagline,
      actions,
      features,
      footer,
      footerHtml,
    }
  },
  created() {
    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }

    if (this.themeProperty.randomSaw !== undefined) {
      this.networkOption.baseURL = this.themeProperty.randomSaw
    }
    if (this.themeProperty.randomSawQuery !== undefined) {
      this.networkOption.query = this.themeProperty.randomSawQuery
    }
    if (this.themeProperty.method !== undefined) {
      this.networkOption.method = this.themeProperty.method
    }
    this.fetchData()
    this.$store.commit('setHeroImg',{
      heroImg: this.heroImage
    })
    // console.log(this.themeProperty)
  },
  mounted() {
    if (document.body.clientWidth > 500) {
      $("#c-sidebar").css("display","none")
    }

  },
  methods: {
    fetchData() {
      if (this.themeProperty.customRandomSay) {
        this.randomSawRes = this.themeProperty.customRandomValue
        this.initTyped(this.randomSawRes,() => {
          setTimeout(() => {
            this.fetchData()
          },this.intervalTime)
        })
      }else {
        network.req(this.networkOption).then(res => {
          const dataQuery = this.networkOption.query
          this.randomSawRes = res[dataQuery];
          this.initTyped(this.randomSawRes,() => {
            setTimeout(() => {
              this.fetchData()
            },this.intervalTime)
          })
        })
      }
    },
    initTyped(input, fn, hooks) {
      const obj = this.obj
      const typed = new EasyTyper(obj, input, fn, hooks)
    }
  },
  computed: {
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
