<template>
  <main :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
        class="page sidebar-single-enter-animate" id="article-page">
    <slot name="top" />
    <div id="theme-default-content" :class="!isHideH1 ? 'hide-h1-tag' : 'show-h1-tag'" class="theme-default-content pageContent medium-zoom-content">
      <div class="page-top-share">
        <div class="page-top-share-next">
          <poster :title="originPageData.title" :content="posterContent"/>
        </div>
      </div>
      <Content />
    </div>
    <PageMeta v-if="showPageMeta" />
    <slot name="bottom" />
  </main>
  <b-center v-if="themeProperty.articlePagination === undefined ? true : themeProperty.articlePagination">
    <template #page-center1>
      <page-next/>
    </template>
  </b-center>
  <div>
    <donate v-if="showDonate" />
  </div>
  <div class="recommend-page">
    <RecommendPage :theme-property="themeProperty"/>
  </div>
  <comment :path-name="pathName"/>
</template>

<script>
import Mood from "./Mood.vue";
import PageMeta from './PageMeta.vue'
import PageNav from './PageNav.vue'
import RecommendPage from "./RecommendPage.vue";
import PageNext from "./child/page/PageNext.vue";

import { defineComponent } from 'vue'
import {usePageData} from "@vuepress/client";
import $ from 'jquery'

export default defineComponent({
  name: 'Page',
  components: {
    PageMeta,
    PageNav,
    RecommendPage,
    Mood,
    PageNext
  },
  data() {
    return {
      lazyLoadingImg: 'https://ooszy.cco.vin/img/blog-public/ljz.gif',
      originPageData: '',
      posterContent: '',
      title: '',
      showMobileCatalog: true,
      showPageMeta: true,
      isHideH1: true
    }
  },
  props: {
    pathName: '',
    themeProperty: ''
  },
  computed: {
    showDonate() {
      let showArticleDonate = true
      try {
        showArticleDonate = this.themeProperty.donate.articlePage
      }catch (e) {

      }
      return showArticleDonate
    }
  },
  emits: ['getHeadLine'],
  created() {
    if (this.$route.hash !== "") {
      this.$router.push(this.$route.hash)
    }

    this.showPageMeta = this.themeProperty.showPageMeta

    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }
    //设置sidebar的class
    const page = usePageData()
    this.originPageData = page

    if (page.value.frontmatter.title === undefined) {
      this.isHideH1 = false
    }

    this.$emit('getHeadLine',page.value.title)
    let lazyLoadingImg = this.themeProperty.lazyLoadingImg
    this.lazyLoadingImg = lazyLoadingImg === undefined ? "https://ooszy.cco.vin/img/blog-public/ljz.gif" : lazyLoadingImg
  },
  methods: {
    getPosterText() {
      return new Promise((resolve,reject) => {
        let allP = $(".pageContent p")
        let content = ''
        if (allP.length > 30) {
          for (let i = 0; i < 30; i++) {
            content = content + allP[i].innerText
          }
        }else {
          for (let i = 0; i < allP.length; i++) {
            content = content + allP[i].innerText
          }
        }
        content = content.replace(/\r\n/g,"")
        content = content.replace(/\n/g,"");
        content = content.replace(/\s/g,"")
        content = content.replace("#","")
        content = content.replace("#\n","")
        content = content.replace("##\n","")
        content = content.replace("##","")
        resolve(content)
      })
    },
    start() {
      let imgs = $(".pageContent img")
      for (let i = 0; i < imgs.length; i++) {
        let clientWidth = document.body.clientWidth
        let img = imgs[i]
        let top = img.offsetTop
        let scrollTop = $(window).scrollTop();
        let scrollCz = ''
        if (clientWidth < 600) {
          scrollCz = top - scrollTop - 350
        }else {
          scrollCz = top - (scrollTop + 50)
        }
        if (scrollCz < 0) {
          this.loadImg(img)
        }
      }
    },
    loadImg(img) {
      let originSrc = img.getAttribute("originSrc")
      if (originSrc === null) {
        return;
      }
      img.setAttribute("src",originSrc)
    },
    setOtherProperty(keyword,description) {
      /*const page = usePageData()
      let title = document.querySelector('meta[property="og:title"]')
      let descriptionDom = document.querySelector('meta[property="og:description"]')
      if (descriptionDom === null) {
        //head中没有keyword 添加一个
        let keywordMeta = $('<meta property="og:description" content="'+description+'">').get(0)
        document.querySelector("head").appendChild(keywordMeta)
      }else {
        //已经存在keyword属性的dom 设置其content
        descriptionDom.setAttribute("content",description)
      }

      if (title === null) {
        //head中没有keyword 添加一个
        let keywordMeta = $('<meta property="og:title" content="'+page.value.title+'">').get(0)
        document.querySelector("head").appendChild(keywordMeta)
      }else {
        //已经存在keyword属性的dom 设置其content
        descriptionDom.setAttribute("content",page.value.title)
      }*/

    },
    setDesc(description) {
      let descriptionDom = document.querySelectorAll('meta[name="description"]')
      if (descriptionDom.length !== 0) {
        new Promise((resolve,reject) => {
          for (let i = 0; i < descriptionDom.length; i++) {
            document.querySelector("head").removeChild(descriptionDom[i])
          }
          resolve()
        }).then(() => {
          let descriptionMeta = $('<meta name="description" content="'+ description +'">').get(0)
          document.querySelector("head").appendChild(descriptionMeta)
        })
      }else {
        let descriptionMeta = $('<meta name="description" content="'+ description +'">').get(0)
        document.querySelector("head").appendChild(descriptionMeta)
      }
    },
    setKeyword(keyword) {
      let keywordDom= document.querySelector('meta[name="keyword"]')
      if (keywordDom === null) {
        //head中没有keyword 添加一个
        let keywordMeta = $('<meta name="keyword" content="'+keyword+'">').get(0)
        document.querySelector("head").appendChild(keywordMeta)
      }else {
        //已经存在keyword属性的dom 设置其content
        keywordDom.setAttribute("content",keyword)
      }
    },
    setMeta() {
      //设置meta标签的keyword和description
      let frontmatterDesc = this.originPageData.frontmatter.description
      let frontmatterKeyword = this.originPageData.frontmatter.keyword

      let keyword = ''
      let description = ''

      let content = document.querySelector("#theme-default-content").innerText
      //截取220个字符作为描述和关键字信息
      content = content.replace(/[\r\n]/g,"");
      content = content.replace(" ","")

      if (content.length > 220) {
        content = content.substr(0,220)
      }

      if (frontmatterDesc === undefined || frontmatterDesc == null) {
        description = content
      }else {
        description = frontmatterDesc
      }

      if (frontmatterKeyword === undefined || frontmatterKeyword == null) {
        if (content.length > 90) {
          keyword = content.substr(0,90)
        }else {
          keyword = content
        }
      }else {
        keyword = frontmatterKeyword
      }

      this.setKeyword(keyword)
      this.setDesc(description)
      this.setOtherProperty(keyword,description)
    }
  },
  mounted() {
    $(window).on("scroll",() => {
      this.start()
    })

    this.$nextTick(() => {
      this.getPosterText().then((res) => {
        this.posterContent = res
      })
      this.setMeta()
      let h1s = $("#c-page h1")
      if (h1s.length > 0) {
        $(h1s[0]).css('display','none')
      }


      let medium_zoom_content_h1Tag = document.querySelectorAll(".medium-zoom-content h1 a");
      let medium_zoom_content_h2Tag = document.querySelectorAll(".medium-zoom-content h2 a");
      let medium_zoom_content_h3Tag = document.querySelectorAll(".medium-zoom-content h3 a");
      let medium_zoom_content_h4Tag = document.querySelectorAll(".medium-zoom-content h4 a,.medium-zoom-content h5 a,.medium-zoom-content h6 a");

      for (let i = 0; i < medium_zoom_content_h1Tag.length; i++) {
        medium_zoom_content_h1Tag[i].innerText = ''
      }

      for (let i = 0; i < medium_zoom_content_h2Tag.length; i++) {
        if (this.themeProperty.articleH2Icon !== undefined) {
          medium_zoom_content_h2Tag[i].innerText = this.themeProperty.articleH2Icon
        }else {
          medium_zoom_content_h2Tag[i].innerText = "🌸"
        }
      }

      for (let i = 0; i < medium_zoom_content_h3Tag.length; i++) {
        if (this.themeProperty.articleH3Icon !== undefined) {
          medium_zoom_content_h3Tag[i].innerText = this.themeProperty.articleH3Icon
        }else {
          medium_zoom_content_h3Tag[i].innerText = "🐳"
        }
      }

      for (let i = 0; i < medium_zoom_content_h4Tag.length; i++) {
        if (this.themeProperty.articleH4Icon !== undefined) {
          medium_zoom_content_h4Tag[i].innerText = this.themeProperty.articleH4Icon
        }else {
          medium_zoom_content_h4Tag[i].innerText = "⛄"
        }
      }
    })
  }
})
</script>

