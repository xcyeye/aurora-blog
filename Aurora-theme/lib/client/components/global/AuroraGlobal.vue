<template>
  <div class="common"
       :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle">
    <Navbar :show-header-bg="showHeaderBg" class="sidebar-single-enter-animate" :style="$store.state.opacityStyle" v-if="showNavbar">
      <template #before>
        <slot name="navbar-before" />
      </template>
      <template #after>
        <slot name="navbar-after" />
      </template>
    </Navbar>
    <mobile-sidebar v-if="showMobileSidebar"/>
    <social-spin v-if="showStyleMenu"/>
    <style-menu
        v-if="showStyleMenu"
        :theme-property="themeProperty"
        @setIsFitter="setIsFitter"
        @setBodyStyle="getBodyStyle"
        @setBodyWallpaper="setBodyWallpaper"
        :is-show-ico="true"
        custom-class="custom-about"/>
    <slot name="top"></slot>
    <slot name="center"></slot>
    <slot name="bottom"></slot>
    <div id="set-bg"
         v-if="showBg"
         :class="{'set-bg-fitter': $store.state.isFitter}"
         :style="'--opacity: ' + $store.state.varOpacity +
         '; --fitter-blue: ' + $store.state.varFilterBlur +
         'px; --borderRadius: ' + $store.state.varBorderRadius +
         'px; --backgroundImageUrl: url(' + $store.state.homeWps + ')'"
    ></div>
  </div>

</template>
<script lang="ts">

//组件导入
import StyleMenu from '../child/home/StyleMenu.vue'
import Navbar from '../../components/Navbar.vue'
import Home from '../Home.vue'
import MobileSidebar from "../child/side/MobileSidebar.vue";
import SocialSpin from '../SocialSpin.vue'

//配置导入
import {computed, defineComponent, Transition,} from 'vue'
import {usePageData, usePageFrontmatter} from '@vuepress/client'
import type {DefaultThemePageFrontmatter} from '../../../shared'
import {useThemeData, useThemeLocaleData} from '../../composables'
import $ from 'jquery'

export default defineComponent({
  name: 'Common',
  components: {
    SocialSpin,
    Navbar,
    Transition,
    StyleMenu,
    Home,
    MobileSidebar,
  },
  props: {
    showBg: {
      type: Boolean,
      default() {
        return true
      }
    },
    showStyleMenu: {
      type: Boolean,
      default() {
        return true
      }
    },
    showNavbar: {
      type: Boolean,
      default() {
        return true
      }
    },
    showMobileSidebar: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  data() {
    return {
      showHeaderBg: true,
      sidebarRowVar: 5,
      colorStyle: '',
      fontStyle: '',
      isShowFooter: '',
      colorFontStyle: '',
      isFitter: false,
      themeProperty: '',
      //首页壁纸数组
      homeWps: [],
      mobilePageSidebar: true,
      pageYOffset: 0
    }
  },
  computed: {
    setBodyStyle() {
      if (this.fontStyle === "") {
        return ""
      }
      return this.$store.state.fontColorStyle + ";"+ this.$store.state.fontFamilyStyle
    }
  },
  methods: {
    handleScroll() {
      if (window.pageYOffset > this.pageYOffset) {
        this.showHeaderBg = false
      }else {
        this.showHeaderBg = true
      }
      this.pageYOffset = window.pageYOffset
    },
    getBodyStyle() {
      let fontColorStyle = this.$store.state.fontColorStyle
      let fontFamilyStyle = this.$store.state.fontFamilyStyle

      if (fontColorStyle === undefined) {
        this.colorStyle = '--fontColor: ""'
      }else {
        this.colorStyle = fontColorStyle
      }
      if (fontFamilyStyle === undefined) {
        this.fontStyle = '--fontFamily: ""'
      }else {
        this.fontStyle = fontFamilyStyle
      }

      this.colorFontStyle = this.colorStyle + " "+ this.fontStyle
    },
    setBodyWallpaper() {
      //切换首页壁纸
      if (this.homeWps.length === 1) {
        this.$store.commit("setHomeWps",{
          homeWps: this.homeWps[0]
        })
        return
      }

      for (let i = 0; i < this.homeWps.length; i++) {
        if (this.$store.state.homeWps.search(this.homeWps[i]) !== -1) {
          if (i === this.homeWps.length -1) {
            this.$store.commit("setHomeWps",{
              homeWps: this.homeWps[0]
            })
            return;
          }else {
            this.$store.commit("setHomeWps",{
              homeWps: this.homeWps[i + 1]
            })
            return;
          }
        }
      }
    },
    setIsFitter(isFitter) {
      this.isFitter = isFitter
    },
  },
  setup() {
    const page = usePageData()
    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    const themeLocale = useThemeLocaleData()

    // navbar
    const shouldShowNavbar = computed(
        () =>
            frontmatter.value.navbar !== false && themeLocale.value.navbar !== false
    )

    return {
      themeLocale,
      frontmatter,
      page,
      shouldShowNavbar,
    }
  },
  created() {
    if (this.$store.state.printRightIndex === 0) {
      console.log("%c vuepress-theme-Aurora %c by qsyyke","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;")
      console.log("%c Version %c "+ this.$store.state.latestVersion + "","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;")
    }

    this.$store.state.printRightIndex = 1
    this.themeProperty = useThemeData().value

    //从配置文件中，获取首页壁纸
    let homeWps = []
    if (this.themeProperty.homeWps === undefined || this.themeProperty.homeWps == null) {
      homeWps.push("https://picoss.cco.vin/animate/wall/404901.png")
    }else {
      homeWps = this.themeProperty.homeWps
    }

    if (homeWps.length === 0) {
      homeWps.push("https://picoss.cco.vin/animate/wall/404901.png")
    }

    this.homeWps = homeWps

    if (this.aboutOption !== undefined || this.aboutOption != null) {
      this.aboutOption = this.themeProperty.about
    }

    this.$store.commit("setIsFitter",{
      isFitter: this.themeProperty.isFitter
    })
    this.isShowFooter = this.themeProperty.isShowFooter

    let fontColorStyle = this.$store.state.fontColorStyle
    let fontFamilyStyle = this.$store.state.fontFamilyStyle
    if (fontColorStyle === undefined) {
      this.colorStyle = '--fontColor: ""'
    }else {
      this.colorStyle = fontColorStyle
    }
    if (fontFamilyStyle === undefined) {
      this.fontStyle = '-fontFamily: ""'
    }else {
      this.fontStyle = fontFamilyStyle
    }

    this.colorFontStyle = this.colorStyle + " "+ this.fontStyle
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll, true)
    if (document.documentElement.clientWidth < 719) {
      this.sidebarRowVar = 6
    }

    //手机端壁纸
    let screen = document.body.clientWidth
    if (screen < 500) {
      if (this.themeProperty.homeWpsMobile !== undefined &&
          this.themeProperty.homeWpsMobile != null) {
        try {
          if (this.themeProperty.homeWpsMobile.length !== 0) {
            this.homeWps = this.themeProperty.homeWpsMobile
          }
        }catch (e) {
        }
      }
    }

    let backgroundUrl = ''
    if (this.$store.state.homeWps === "") {
      //将首页壁纸设置为配置文件数组中的第一张图片
      backgroundUrl = this.homeWps[0]
    }else {
      //将首页壁纸设置为配置文件数组中的第一张图片
      backgroundUrl = this.$store.state.homeWps
    }
    this.$store.commit("setHomeWps",{
      homeWps: backgroundUrl
    })


    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    let keywordValue = this.themeProperty.keyword
    let descriptionValue = this.themeProperty.description

    if (keywordValue === undefined) {
      keywordValue = "vuepress,vuepress-theme-aurora,blog-theme"
    }

    if (descriptionValue === undefined) {
      descriptionValue = "个人博客"
    }

    if (frontmatter.value.home) {
      //如果是首页的话，就将key和description设置为配置中的
      let descriptionDom = document.querySelectorAll('meta[name="description"]')
      let keywordDom= document.querySelector('meta[name="keyword"]')

      //设置关键词
      if (keywordDom === null) {
        //head中没有keyword 添加一个
        let keywordMeta = $('<meta name="keyword" content="'+ keywordValue +'">').get(0)
        document.querySelector("head").appendChild(keywordMeta)
      }else {
        //已经存在keyword属性的dom 设置其content
        keywordDom.setAttribute("content",keywordValue)
      }

      if (descriptionDom.length !== 0) {
        new Promise((resolve,reject) => {
          for (let i = 0; i < descriptionDom.length; i++) {
            document.querySelector("head").removeChild(descriptionDom[i])
          }
          resolve()
        }).then(() => {
          let descriptionMeta = $('<meta name="description" content="'+ descriptionValue +'">').get(0)
          document.querySelector("head").appendChild(descriptionMeta)
        })
      }else {
        let descriptionMeta = $('<meta name="description" content="'+ descriptionValue +'">').get(0)
        document.querySelector("head").appendChild(descriptionMeta)
      }
    }

    if (document.body.clientWidth < 550 && this.themeProperty.mobilePageSidebar !== undefined) {
      this.mobilePageSidebar = this.themeProperty.mobilePageSidebar
    }
  }
})
</script>

<style lang="css">
/* 从调色板中引入变量 */
/*@import '@vuepress/plugin-palette/palette';*/
</style>

<!--<style lang="css" src="@vuepress/plugin-palette/style"></style>-->
