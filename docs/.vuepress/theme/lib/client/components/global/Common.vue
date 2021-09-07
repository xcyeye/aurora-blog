<template>
  <div class="poster-append" id="poster-append">
    <poster-img></poster-img>
  </div>
  <div
      @click="cancelWelcome"
      class="theme-container"
      :class="containerClass"
      @touchstart="onTouchStart"
      @touchend="onTouchEnd"
      :style="colorFontStyle"
  >
    <Navbar :style="$store.state.opacityStyle" v-if="shouldShowNavbar" @toggle-sidebar="toggleSidebar">
      <template #before>
        <slot name="navbar-before" />
      </template>
      <template #after>
        <slot name="navbar-after" />
      </template>
    </Navbar>

    <!--<div class="sidebar-mask" @click="toggleSidebar(false)" />-->
    <div class="page-sidebar" @wheel="handleScroll">
      <top-image :is-show-top-img="isShowTopImg"
                 :is-show-head-line="isShowHeadLine"
                 :head-line="headLine">
        <!--<div class="top-show">
          <poster/>
        </div>-->
      </top-image>
      <div class="common-test" id="content">
        <Sidebar>
          <template #top>
            <slot name="sidebar-top" />
          </template>
          <template #bottom>
            <slot name="sidebar-bottom" />
          </template>
        </Sidebar>
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
    </div>
    <!--@setBodyStyle="getBodyStyle"-->
    <home-welcome
        :theme-property="themeProperty"
        @setIsFitter="setIsFitter"
        @setBodyStyle="getBodyStyle"
        @setBodyWallpaper="setBodyWallpaper"
        :is-show-ico="true"
        custom-class="custom-about"/>

      <slot name="bottom1"></slot>
      <slot name="bottom2"></slot>
      <slot name="bottom3"></slot>
      <slot name="bottom4"></slot>

      <!--<comment/>-->
      <Footer :theme-property="themeProperty"
              :is-home="frontmatter.home"
              :is-show-footer="isShowFooter">
      </Footer>
    </div>
  <!--:style="{'--backgroundImageUrl':setBackgroundImg}"-->
  <!--style="--backgroundImageUrl:url(https://api.iro.tw/webp_pc.php);--fitter-blue: 29px;"-->
  <!--:style="{'&#45;&#45;backgroundImageUrl':setBackgroundImg}"-->
  <div id="set-bg"
       :data="$store.state.isFitter"
       :class="{'set-bg-fitter': $store.state.isFitter}"
       :style="setVarCommonStyle"
  ></div>
  <!--$store.state.showPosterShadow-->
  <div :class="{posterShade: $store.state.showPosterShadow}">
    <span :class="{iconSpinner6: $store.state.showShadeLoad}"></span>
  </div>

  <!--$store.state.showPosterShadow-->
  <!--<div :class="{posterShade: true}">
    <span :class="{iconSpinner6: true}"></span>
  </div>-->


</template>
<script lang="ts">

import {computed, defineComponent, onMounted, onUnmounted, ref, Transition,} from 'vue'
import {useRouter} from 'vue-router'
import {usePageData, usePageFrontmatter} from '@vuepress/client'
import type {DefaultThemePageFrontmatter} from '../../../shared'
import Navbar from '../../components/Navbar.vue'
import Home from '../Home'
import {useScrollPromise, useSidebarItems, useThemeLocaleData,} from '../../composables'
import HomeWelcome from '../../components/child/HomeWelcome'
import EasyTyper from "easy-typer-js";
import $ from 'jquery'
import Sidebar from '../Sidebar'
import PosterImg from '../child/PosterImg'
// import filterPostsByType from '../../public/js/post'
//导入配置属性
let themeProperty = null
const network = require('../../public/js/network.js')
const tag = require('../../public/js/tag')
// some-client-component.vue
import myData from '@temp/my-data'

export default defineComponent({
  name: 'Common',

  components: {
    Navbar,
    Transition,
    HomeWelcome,
    Home,
    Sidebar,
    PosterImg
  },
  data() {
    return {
      windowHeight:0,
      aboutOption: null,
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
      backgroundStyle: 'background-image: url(https://api.iro.tw/webp_pc.php);',
      fontStyle: '',
      isShowFooter: '',
      colorFontStyle: '',
      isFitter: false,
      backgroundUrl: 'url(https://api.iro.tw/webp_pc.php)',
      themeProperty: null
    }
  },
  props: {
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
    getDesc() {
      return (index,desc) => {
        if (index === 0) {
          //是需要打印的页面
          return this.obj.output
          // return "sdfsdffdgsfg"
        }
        return desc
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
        let background_color = themeProperty.randomColor[
            this.getRandomInt(0,themeProperty.randomColor.length -1)]
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
    },
    setBackgroundImg() {
      return this.backgroundUrl
    },
    setVarCommonStyle() {
      return "--opacity: " + this.$store.state.varOpacity +
          "; --fitter-blue: " + this.$store.state.varFilterBlur +
          "px; --borderRadius: " + this.$store.state.varBorderRadius + "px;" +
          " --backgroundImageUrl: " + this.backgroundUrl + ";"
    }
  },
  methods: {
    initTyped(input, fn, hooks) {
      const obj = this.obj
      return new EasyTyper(obj, input, fn, hooks)
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    getHeadLine(title) {
      this.headLine = title
    },
    handleScroll(e) {
      if(this.isShowSideBar) {
        let scrollTop = $(document).scrollTop()
        if (document.body.clientWidth < 500) {
          return
        }
        if (scrollTop >= 300 && !this.isShow) {
          $("#c-sidebar").show(500)
          $(".adsense-right").css("display","block")
          this.isShow = true
        }else {
          if (scrollTop < 300) {
            //小于300，隐藏
            $("#c-sidebar").hide(500)
            $(".adsense-right").css("display","none")
            this.isShow = false
          }
        }
      }
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
    setBodyWallpaper(url) {
      this.backgroundUrl = url
    },
    setIsFitter(isFitter) {
      this.isFitter = isFitter
    },
    cancelWelcome() {
      /*let time = +new Date()
      this.$store.commit("setWelcomeOpenTime",{
        time: time
      })*/
    }
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

    // sidebar
    const sidebarItems = useSidebarItems()
    const isSidebarOpen = ref(false)
    const toggleSidebar = (to?: boolean): void => {
      isSidebarOpen.value = typeof to === 'boolean' ? to : !isSidebarOpen.value
      if (isSidebarOpen.value) {
        $("#c-sidebar").css("display","block")
      }
    }
    const touchStart = { x: 0, y: 0 }
    const onTouchStart = (e): void => {
      touchStart.x = e.changedTouches[0].clientX
      touchStart.y = e.changedTouches[0].clientY
    }
    const onTouchEnd = (e): void => {
      const dx = e.changedTouches[0].clientX - touchStart.x
      const dy = e.changedTouches[0].clientY - touchStart.y
      if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > 40) {
        if (dx > 0 && touchStart.x <= 80) {
          toggleSidebar(true)
        } else {
          toggleSidebar(false)
        }
      }
    }

    // classes
    const containerClass = computed(() => [
      {
        'no-navbar': !shouldShowNavbar.value,
        'no-sidebar': !sidebarItems.value.length,
        'sidebar-open': isSidebarOpen.value,
      },
      frontmatter.value.pageClass,
    ])

    // close sidebar after navigation
    let unregisterRouterHook
    onMounted(() => {
      const router = useRouter()
      unregisterRouterHook = router.afterEach(() => {
        toggleSidebar(false)
      })
    })
    onUnmounted(() => {
      unregisterRouterHook()
    })

    // handle scrollBehavior with transition
    const scrollPromise = useScrollPromise()
    const onBeforeEnter = scrollPromise.resolve
    const onBeforeLeave = scrollPromise.pending

    return {
      frontmatter,
      page,
      containerClass,
      shouldShowNavbar,
      toggleSidebar,
      onTouchStart,
      onTouchEnd,
      onBeforeEnter,
      onBeforeLeave,
    }
  },
  created() {
    //控制台打印
    console.log("%c vuepress-theme-ccds %c by qsyyke","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;")
    console.log("%c Version %c "+this.$store.state.version+"","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;")
    // console.log(myData)
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
      resolve()
    }).then(() => {
      let metaKey = $('<link rel="shortcut icon" href=\"'+this.themeProperty.faviconIco+'\">')
      $("head").get(0).appendChild(metaKey.get(0))
    })
    // console.log(filterPostsByType)
    this.aboutOption = this.themeProperty.about
    this.ico = this.themeProperty.ico.aboutIco
    this.$store.commit("setIsFitter",{
      isFitter: this.themeProperty.isFitter
    })
    tag.setTag(this).then(() => {
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

    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    if (frontmatter.value.home) {
      //如果是首页的话，就将key和description设置为配置中的
      let heads = $("head").get(0).children
      new Promise((resolve,reject) => {
        let isDescription = false
        let isKeyword = false
        for (let i = 0; i < heads.length; i++) {
          let name = heads[i].getAttribute("name")
          if (name === 'description') {
            heads[i].setAttribute("content",this.themeProperty.description)
            isDescription = true
          }

          if (name === 'keyword') {
            heads[i].setAttribute("content",this.themeProperty.keyword)
            isKeyword = true
          }
        }
        resolve({
          isKeyword,isDescription
        })
      }).then((option) => {
        if (!option.isKeyword) {
          //不存在keyword
          let metaKey = $('<meta name="keyword" content="'+this.themeProperty.keyword+'">')
          $("head").get(0).appendChild(metaKey.get(0))
        }

        if (!option.isDescription) {
          //不存在description
          let metaKey = $('<meta name="description" content="'+this.themeProperty.description+'">')
          $("head").get(0).appendChild(metaKey.get(0))
        }
      })
    }
  },
  mounted() {
    this.initTyped(this.aboutOption[0].describe[0])
  },
})
</script>
<style>
/*@import "../../styles/theme.style.css";*/
</style>