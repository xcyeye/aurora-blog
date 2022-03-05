<template>
  <div class="common"
       :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle">
    <Navbar :show-header-bg="showHeaderBg" :style="$store.state.opacityStyle" v-if="shouldShowNavbar">
      <template #before>
        <slot name="navbar-before" />
      </template>
      <template #after>
        <slot name="navbar-after" />
      </template>
    </Navbar>
    <mobile-sidebar :show-navbar="frontmatter.home"/>
    <!--<social-spin/>-->
    <style-menu
        :theme-property="themeProperty"
        @setIsFitter="setIsFitter"
        @setBodyStyle="getBodyStyle"
        @setBodyWallpaper="setBodyWallpaper"
        :is-show-ico="true"
        custom-class="custom-about"/>
    <slot name="top1"></slot>
    <slot name="top2"></slot>
    <slot name="top3"></slot>
    <slot name="top4"></slot>
    <slot name="top5"></slot>
    <slot name="top6"></slot>
    <div
        class="theme-container"
        @touchstart="onTouchStart"
        @touchend="onTouchEnd"
        :style="colorFontStyle"
        :class="{'sidebar-single-enter-animate': showSidebarAnimateClass}"
    >
      <div class="page-sidebar">
        <top-image :is-show-top-img="isShowTopImg"
                   :theme-property="themeProperty"
                   :is-show-head-line="isShowHeadLine"
                   :show-mood-edit="showMoodEdit"
                   :head-line="headLine">
        </top-image>

        <div :class="{'content': !isHomePage}">
          <div :id="getArticleId" :class="{'article-page-parent-pro': !isHomePage}" class="article-page-parent">
            <div :class="{noShowSidebar: getNoShowSidebar}" id="page-sidebar-left"
                 class="page-sidebar-left">
              <slot name="center1"></slot>
              <slot name="center2"></slot>
              <slot name="center3"></slot>
              <slot name="center4"></slot>
              <slot name="center5"></slot>
              <slot name="center6"></slot>
              <slot name="center7"></slot>
              <slot name="center8"></slot>
              <slot name="center9"></slot>
            </div>
            <div id="page-sidebar-right" v-if="!frontmatter.home" v-show="showSidebar" class="page-sidebar-right">
              <div class="stickSidebar" v-if="mobilePageSidebar">
                <HomeSidebar :show-navbar="false"
                             :sidebar-width-var="0.92"
                             :show-sidebar-social="true"
                             :show-sidebar-link="showSidebarLink"
                             :sidebar-row-var="sidebarRowVar"
                             :is-sticky-sidebar="isStickySidebar"
                             :show-tag-cloud="showTagCloud"
                             :is-show-catalog="isShowCatalog">
                </HomeSidebar>
              </div>
            </div>
          </div>
        </div>
      </div>
      <slot name="bottom1"></slot>
      <slot name="bottom2"></slot>
      <slot name="bottom3"></slot>
      <slot name="bottom4"></slot>
      <Footer :theme-property="themeProperty"
              :is-home="frontmatter.home"
              :is-show-footer="isShowFooter">
      </Footer>
    </div>
    <div id="set-bg" class="set-bg-fitter"
         :style="'--opacity: ' + $store.state.varOpacity +
         '; --borderRadius: ' + $store.state.varBorderRadius +
         'px; --backgroundImageUrl: url(' + $store.state.homeWps + ')'"
    ></div>
    <div id="posterShade" :class="{posterShade: $store.state.showPosterShadow}">
      <span :class="{iconSpinner6: $store.state.showShadeLoad}"></span>
    </div>
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
import {setTag} from '../../public/js/tag'
import {computed, defineComponent, Transition,} from 'vue'
import {usePageData, usePageFrontmatter, withBase} from '@vuepress/client'
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
  data() {
    return {
      showHeaderBg: true,
      aboutOption: [],
      sidebarRowVar: 5,
      obj: {
        output: '',
        isEnd: false,
        speed: 80,
        singleBack: false,
        sleep: 1700,
        type: 'normal',
        backSpeed: 70,
        sentencePause: false,
      },
      ico: null,
      colorStyle: '',
      fontStyle: '',
      isShowFooter: '',
      colorFontStyle: '',
      isFitter: false,
      themeProperty: '',
      //首页壁纸数组
      homeWps: [],
      mobilePageSidebar: true,
      pageYOffset: 0,
      width: 0,
    }
  },
  props: {
    showSidebarAnimateClass: {
      type: Boolean,
      default() {
        return true
      }
    },
    isHomePage: {
      type: Boolean,
      default() {
        return false
      }
    },
    showSidebarLink: {
      type: Boolean,
      default() {
        return true
      }
    },
    isStickySidebar: {
      type: Boolean,
      default() {
        return false;
      }
    },
    isShowCatalog: {
      type: Boolean,
      default() {
        return false
      }
    },
    showTagCloud: {
      type: Boolean,
      default() {
        return true
      }
    },
    showSidebar: {
      type: Boolean,
      default() {
        return true
      }
    },
    showMoodEdit: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowTopImg: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowHeadLine: {
      type: Boolean,
      default() {
        return false
      }
    },
    headLine: {
      type: String,
      default() {
        return ""
      }
    },
    isShowSideBar: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  computed: {
    getArticleId() {
      if (!this.isHomePage) {
        return 'article-page-parent'
      }else {
        //如果是首页，并且不是手机端
        if (this.width > 719) {
          return 'article-page-parent'
        }else {
          return 'article-page-parent-mobile'
        }
      }
    },
    getNoShowSidebar() {
      // showSidebar
      if (this.isHomePage) {
        return false
      }else {
        return this.showSidebar
      }
    },
    getIndex() {
      return (index,length)=> {
        if (index === 0 && length === 1) {
          return ""
        }
        return index+1 + ". "
      }
    },
    setSpanStyle() {
      return (score) => {
        let newScore = score * 0.8
        let background_color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        return 'width: '+ newScore + "%;" + "background-color: "+background_color + ";"
      }
    },
    setIco() {
      return 'background-image: url('+this.ico+');'
    },
    setBodyStyle() {
      if (this.fontStyle === "") {
        return ""
      }
      return this.$store.state.fontColorStyle + ";"+ this.$store.state.fontFamilyStyle
    }
  },
  methods: {
    setHomeBg() {
      let base = ""
      if (this.$site.base !== "/") {
        base = this.$site.base
      }
      new Promise((resolve,reject) => {
        let homeWpsSet = new Set()
        for (let i = 0; i < this.homeWps.length; i++) {
          // homeWpsSet.add(withBase(this.homeWps[i]))
          homeWpsSet.add(base + this.homeWps[i])
        }
        resolve(homeWpsSet)
      }).then((homeWpsSet) => {
        this.homeWps = Array.from(homeWpsSet)
        let backgroundUrl = ''
        if (this.$store.state.homeWps === "") {
          //将首页壁纸设置为配置文件数组中的第一张图片
          // backgroundUrl = this.homeWps[0]
          backgroundUrl = this.homeWps[this.getRandomInt(0,this.homeWps.length -1)]
        }else {
          //将首页壁纸设置为配置文件数组中的第一张图片
          backgroundUrl = this.$store.state.homeWps
        }

        this.$store.commit("setHomeWps",{
          homeWps: backgroundUrl
        })
      })
    },
    handleScroll() {
      if (window.pageYOffset > this.pageYOffset) {
        this.showHeaderBg = false
      }else {
        this.showHeaderBg = true
      }
      this.pageYOffset = window.pageYOffset
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
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
    //在v1.3.2之后，就已经移除通过docs/readme.md中配置favicon，转为在config中进行配置
    // let metaKey = $('<link rel="shortcut icon" href=\"'+this.themeProperty.faviconIco+'\">')
    // $("head").get(0).appendChild(metaKey.get(0))

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

    if (this.aboutOption !== undefined) {
      this.aboutOption = this.themeProperty.about
    }

    try {
      this.ico = this.themeProperty.ico.aboutIco
    }catch (e) {
      this.ico = "https://ooszy.cco.vin/img/ico/cat.svg"
    }

    setTag(this,this.themeProperty).then(() => {
      this.$store.commit('setTagStatus',{
        isSuccess:  true
      })
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
    this.width = document.body.clientWidth

    if (document.documentElement.clientWidth < 719) {
      this.sidebarRowVar = 6
    }

    // 滚动条的获取
    window.addEventListener('scroll', this.handleScroll, true)

    //手机端壁纸
    let screen = document.body.clientWidth
    if (screen < 500) {
      if (this.themeProperty.homeWpsMobile !== undefined) {
        try {
          if (this.themeProperty.homeWpsMobile.length !== 0) {
            this.homeWps = this.themeProperty.homeWpsMobile
          }
        }catch (e) {
        }
      }
    }

    this.setHomeBg()


    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    let keywordValue = this.themeProperty.keyword
    let descriptionValue = this.themeProperty.description

    if (keywordValue === undefined) {
      keywordValue = "vuepress,vuepress-theme-aurora,blog-theme"
    }

    if (descriptionValue === undefined) {
      descriptionValue = "aurora blog"
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

