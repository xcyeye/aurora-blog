<template>
  <div class="c-page-parent">
    <main :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="page" id="c-page">
      <slot name="top" />

      <!--:adsense-script="adsenseArr[0].script"-->
      <div class="theme-default-content pageContent">
        <AdSense adsense-position="right"
                 :adsense-background-img="adsenseArr[0].adsenseBackgroundImg"
                 :adsense-message="adsenseArr[0].adsenseMessage"
        >
          <div v-html="adsenseArr[0].script">

          </div>
        </AdSense>
        <Content />
      </div>

      <PageMeta />
      <PageNav />
      <slot name="bottom" />
    </main>
  </div>
  <div>
    <donate v-if="themeProperty.donate.articlePage" />
  </div>
  <div class="recommend-page">
    <RecommendPage :theme-property="themeProperty"/>
  </div>

  <comment></comment>

</template>

<script>
import Mood from "./Mood";
import { defineComponent } from 'vue'
import PageMeta from './PageMeta.vue'
import PageNav from './PageNav.vue'
import AdSense from './AdSense'
import RecommendPage from "./RecommendPage";
import {usePageData, useRouteLocale, useSiteData} from "@vuepress/client";
import $ from 'jquery'

export default defineComponent({
  name: 'Page',
  components: {
    PageMeta,
    PageNav,
    AdSense,
    RecommendPage,
    Mood
  },
  data() {
    return {
      adsenseArr: null,
      insertAdsenseRule: '',
      adsenseLength: 3,
      lazyLoadingImg: null
    }
  },
  props: {
    themeProperty: null
  },
  created() {
    const page = usePageData()
    this.$emit('getHeadLine',page.value.title)
    this.adsenseArr = this.themeProperty.adsenseArr
    this.insertAdsenseRule = this.themeProperty.insertAdsenseRule
    this.adsenseLength = this.themeProperty.adsenseLength
    let lazyLoadingImg = this.themeProperty.lazyLoadingImg
    this.lazyLoadingImg = lazyLoadingImg === undefined ? "https://ooszy.cco.vin/img/blog-public/ljz.gif" : lazyLoadingImg

    $(window).on("scroll",() => {
      //console.log("scroll")
      this.start()
    })
  },

  methods: {
    start() {
      let imgs = $(".pageContent img")
      for (let i = 0; i < imgs.length; i++) {
        let img = imgs[i]
        let top = img.offsetTop
        let scrollTop = $(window).scrollTop();
        let scrollCz = top - (scrollTop + 100)

        if (scrollCz < 0) {
          this.loadImg(img)
        }
      }
    },
    loadImg(img) {
      //console.log(img)
      let originSrc = img.getAttribute("data-origin")
      //console.log("data-origin: " + originSrc)
      img.setAttribute("src",originSrc)
    }
  },
  mounted() {

    let imgs = $(".pageContent img")
    console.log(imgs)
    for (let i = 0; i < imgs.length; i++) {
      let originSrc = imgs[i].src
      imgs[i].setAttribute("data-origin",originSrc)
      imgs[i].src = this.lazyLoadingImg
    }

    //设置meta标签的keyword和description
    //获取tip类名的内容 theme-default-content
    let tips = $(".tip")
    let pContent = ''
    let keyContent = ""
    if (tips.length === 0) {
      //没有设置tip，则描述选取第一二个p标签内容
      let pContents = $(".pageContent h2")
      if (pContents.length < 2) {
        //截取全文80个字符作为描述
        pContent = $(".pageContent").get(0)
            .innerText.substr(0,80)
      }else {
        //使用所有的h2标签作为描述
        for (let i = 0; i < pContents.length; i++) {
          let originText =  pContents[i].innerText
          .replace("#","")
          .replace("\n","")
          let h2Text = (i+1) + "、" + pContents[i].innerText
          .replace("#","")
          .replace("\n","") + "\t"
          pContent = pContent + h2Text

          keyContent = keyContent + "," + originText
        }
      }
    }else {
      let childrenArr = tips.get(0).children
      for (let i = 1; i < childrenArr.length; i++) {
        pContent = pContent + childrenArr[i].innerText
      }
    }

    let childmete = $("head").get(0).children;
    for (let i = 0; i < childmete.length; i++) {
      let name = childmete[i].getAttribute("name");

      if (name === "description") {
        childmete[i].setAttribute("content",pContent);
      }
    }

    //设置关键词keyword
    let keys = $(".key")
    let kContent = ''
    if (keys.length === 0) {
      let keyRuleNum = this.themeProperty.keyRule

      kContent = keyContent.substr(1, keyRuleNum)
    }else {
      kContent = keys.get(0).innerText
    }
    let metaKey = $('<meta name="keyword" content="'+kContent+'">')
    $("head").get(0).appendChild(metaKey.get(0))

    let h1s = $("#c-page h1")
    if (h1s.length > 0) {
      $(h1s[0]).css('display','none')
    }

    //下面就是自动添加广告

    let allContents = $(".pageContent p")

    new Promise((resolve,reject) => {
      let adsenseNodes = []
      for (let i = 0; i < this.adsenseArr.length; i++) {

        if (this.adsenseArr[i].position === 'center') {
          let node = $("<AdSense adsense-position=\""+this.adsenseArr[i].position+"\"\n" +
              "                 :adsense-background-img=\""+this.adsenseArr[i].adsenseBackgroundImg+"\"\n" +
              "                 :adsense-message=\""+this.adsenseArr[i].adsenseMessage+"\"\n" +
              "\n" +
              "        >\n" +
              "          <div v-html=\""+this.adsenseArr[i].script+"\">\n" +
              "\n" +
              "          </div>\n" +
              "        </AdSense>").get(0)

          adsenseNodes.push(node)
        }
      }
      resolve(adsenseNodes)
    }).then((adsenseNodes) => {
      /*console.log(adsenseNodes)
      console.log("allContents.length: "+ allContents.length)
      console.log("allContents.length / this.insertAdsenseRule: "+ allContents.length / this.insertAdsenseRule)

      let div = $("<h2>插入测试</h2>").get(0)
      $(".theme-default-content").get(0).insertBefore(div,allContents[6])
      if (allContents.length < this.insertAdsenseRule) {
        //所有p标签的长度不够，则直接插入在最后
        console.log(allContents[allContents.length -5])
        $(".theme-default-content").get(0).insertBefore(adsenseNodes[0],allContents[allContents.length -5])
      }

      if (allContents.length > this.insertAdsenseRule *2) {
        for (let i = 1; i < allContents.length / this.insertAdsenseRule; i++) {
          if (adsenseNodes.length >= allContents.length / this.insertAdsenseRule) {
            //文章中间的长度大于所有p标签除以广告规则的长度
            if (this.adsenseLength >= allContents.length / this.insertAdsenseRule) {
              for (let j = 0; j < allContents.length / this.insertAdsenseRule; j++) {
                $(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
              }
            }else {
              //小于广告规则长度
              for (let j = 0; j < this.adsenseLength; j++) {
                $(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
              }
            }
          }else {
            //所有符合的广告小于
            for (let j = 0; j < adsenseNodes.length; j++) {
              $(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
            }
          }
          //$(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
        }
      }else {
        console.log(allContents[this.insertAdsenseRule])
        $(".theme-default-content").get(0).insertBefore(adsenseNodes[0],allContents[this.insertAdsenseRule])
      }*/
    })
  }
})
</script>

