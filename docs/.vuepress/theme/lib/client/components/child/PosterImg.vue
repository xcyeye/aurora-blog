<template>
  <!--模板-->
  <div style="display: block" class="poster" id="poster">
    <div class="poster-top" id="poster-top">
      <div class="poster-time">
        <div class="poster-time-day" id="poster-time-day">{{day}}</div>
        <div class="poster-time-year" id="poster-time-year">{{month}}/{{year}}</div>
      </div>
      <!--<img src="http://h2.ioliu.cn/bing/HowgillFells_ZH-CN1134328886_640x480.jpg?imageslim" id="poster-top-img" alt="">-->
    </div>
    <div class="poster-center" id="poster-center">
      <div class="poster-title" id="poster-title">
        <span>{{$store.state.downloadImgTitle}}</span>
      </div>
      <div class="poster-content" id="poster-content">
            <span>{{$store.state.posterContent}}</span>
      </div>
      <div class="poster-page" id="poster-page">
        <div class="poster-page-left">
          <div class="poster-page-avatar">
            <img id="poster-page-avatar" src="/avatar.jpg" alt="">
          </div>
          <div class="poster-page-desc" id="poster-page-desc">
            <div class="poster-desc-top">
              <span>@{{$store.state.author}}</span>
            </div>
            <div class="poster-desc-bottom">
              <span>Time {{getLastUpdate}}</span>
            </div>
          </div>
        </div>
        <div class="poster-page-right" id="poster-page-right"></div>
      </div>
    </div>
    <div class="poster-bottom" id="poster-bottom">

      <div class="poster-say" id="poster-say">
        <div class="blog-name" id="blog-name">
          <span>{{getLogoTitle}}</span>
          <span>&nbsp;  Blog</span>
        </div>
        <div class="blog-desc" id="blog-desc">
          <!--技术记录生活|分享技术记录生活|分享技术记录生活|分享技术记录生活|分享技术-->
          <span>{{getBlogDesc}}</span>
        </div>
      </div>

      <div class="poster-social-qr">
        <!--<div class="poster-social-desc">
        </div>-->
        <div class="poster-qr">
          <!--https://ooszy.cco.vin/img/blog-public/wxpay.png-->
          <img id="poster-qrimg" :src="$store.state.qrImgHref" alt="">
        </div>
      </div>
    </div>
  </div>
<!--
  &lt;!&ndash;生成的截图&ndash;&gt;
  <div id="poster-qrimg-center"
       style="display: block"
       :style="getScale" class="poster-qrimg-center">

  </div>-->

  <div class="poster-img" style="display: none" id="poster-img">
    <div class="poster-cancel">
      <span @click="cancelShade" class="icon-close"></span>
    </div>
    <div class="share-div" :style="$store.state.posterStyle">
      <img id="share-img" :src="$store.state.postImgHref" alt="">
    </div>
    <div class="share-bottom" id="share-bottom">
      <div class="poster-share-desc">
        <span>分享到</span>
        <!--<div class="poster-scale">
          <i>缩放&nbsp;</i>
          <span @click="scaleAdd">+</span>
          <span @click="scaleDown">-</span>
        </div>-->
      </div>
      <div class="poster-social">
        <a :href="qqShare">
          <span class="qq">好友</span>
        </a>
        <a :href="qZone">
          <span class="qzone">空间</span>
        </a>
        <a :href="weiboShare">
          <span class="weibo">微博</span>
        </a>

        <a :href="href">
          <span class="save" @click="saveImg">保存</span>
        </a>
      </div>
    </div>
  </div>

  <!--生成的截图-->
  <!--<div id="poster-qrimg-center" v-if="$store.state.showPostImg" class="poster-qrimg-center" style="display: none">
    <div class="poster-img" id="poster-img">
      <div class="poster-cancel">
        <span @click="cancelShade" class="icon-close"></span>
      </div>
      <img :src="$store.state.postImgHref" alt="">
      <div class="poster-share-desc">
        <span>分享到</span>
      </div>
      <div class="poster-social">
        <a :href="qqShare">
          <span class="qq">好友</span>
        </a>
        &lt;!&ndash;<a :href="qZone">
          <span class="qzone">空间</span>
        </a>&ndash;&gt;
        <a href="javascript:;">
          <span @click="qZone" class="qzone">空间</span>
        </a>
        <a :href="weiboShare">
          <span class="weibo">微博</span>
        </a>

        <a :href="href">
          <span class="save" @click="saveImg">保存</span>
        </a>
      </div>
    </div>
  </div>-->
</template>

<script>

import $ from "jquery";
import {usePageData, useSiteLocaleData} from "@vuepress/client";
import myData from '@temp/my-data'
import {useThemeLocaleData} from "../../composables";

export default {
  name: "PosterImg",
  data() {
    return {
      qqHref: '',
      qzoneHref: '',
      weiboHref: '',
      day: '',
      month: '',
      year: '',
      themeConfig: '',
    }
  },
  props: {
    /*href: {
      type: String,
      default() {
        return 'https://i.pinimg.com/564x/56/a9/ad/56a9ad70fb92a77b8eed47ced71a495e.jpg'
      }
    },*/
    title: {
      type: String,
      default() {
        return "ccds";
      }
    },
  },
  computed: {
    getHeight() {
      return this.$store.state.scaleTransform
    },
    getScale() {
      return "--scale-transform: " + this.$store.state.scaleTransform + ";"
    },
    getLastUpdate() {
      const page = usePageData()
      let time = page.value.git.updatedTime;
      if (time === undefined) {
        time = +new Date()
      }
      return this.getLocalTime(time)
    },
    getLogoTitle() {
      return useSiteLocaleData().value.title
    },
    getLogoImg() {
      const themeLocale = useThemeLocaleData()
      let src = themeLocale.value.logo
      if (src === undefined || src === null) {
        console.log("you need to set the logo field value,the default is: \nhttps://ooszy.cco.vin/img/blog-public/ccds_64.ico")
        return "https://ooszy.cco.vin/img/blog-public/ccds_64.ico"
      }else {
        return  src
      }
    },
    getBlogDesc() {
      console.log(myData)
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === "/") {
          this.themeConfig = myData[i].frontmatter
          if (this.themeConfig.poster === undefined
              || this.themeConfig.poster == null
              ) {
            return "Theme by ccds"
          }else {
            if (this.themeConfig.poster.description === undefined
                || this.themeConfig.poster.description == null
                || this.themeConfig.poster.description === "" ) {
              return "Theme by ccds"
            }else {
              return this.themeConfig.poster.description
            }
          }
        }
      }
    }
  },
  methods: {
    getLocalTime(time) {
      let date = new Date(time);
      let day = date.getDate()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let hours = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
      return year + "-" + month + "-" + day + " "
    },
    scaleAdd() {
      let scale = this.$store.state.scaleTransform + 0.1
      this.$store.commit("setScaleTransform",{
        scaleTransform: scale
      })
    },
    scaleDown() {
      let scale = this.$store.state.scaleTransform - 0.1
      this.$store.commit("setScaleTransform",{
        scaleTransform: scale
      })
      console.log(scale)
    },
    cancelShade() {
      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })

      $(".poster-img").slideUp(500)
      $(".poster-append").css("z-index",1)
    },
    saveImg() {
      console.log("--------save-----------")
      console.log(this.$store.state.postImgHref)
      var a = document.createElement('a');
      var event = new MouseEvent('click')
      console.log(this.$store.state.downloadImgTitle)
      a.download = this.$store.state.downloadImgTitle
      a.href = this.$store.state.postImgHref;
      a.dispatchEvent(event);
    },
    qqShare() {
      let href = "http://connect.qq.com/widget/shareqq/index.html?" +
          "url=https://blog.cco.vin" +
          "&sharesource=qzone" +
          "&title=你的分享标题" +
          "&pics=https://ooszy.cco.vin/img/blog-note/image-20210831130544863.png?x-oss-process=style/pictureProcess1" +
          "&summary=你的分享描述&desc=你的分享简述"
      return href
    },
    qZone() {
      /*let href = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" +
          "url="+this.qrHref+"" +
          "&sharesource=qzone" +
          "&title="+this.title+"" +
          "&pics=https://api.paugram.com/bing" +
          "&summary="+this.content+""*/

      /*let href = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" +
          "url=https//blog.cco.vin " +
          "&sharesource=qzone" +
          "&title=标题" +
          "&pics=https://api.paugram.com/bing" +
          "&summary=内容"*/
      let href = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" +
          "url="+this.$store.state.posterShareSite +
          "&title="+this.$store.state.downloadImgTitle+
          "&desc="+this.$store.state.posterContent.substr(1,this.$store.state.posterContent.length /4) +
          "&summary="+this.$store.state.posterContent.substr(1,this.$store.state.posterContent.length /4) +
          "&site="+this.$store.state.posterShareSite+"&pics=https://api.paugram.com/bing"
      console.log(href)
      return "javascript:;"
      window.location.href = href
    },
    weiboShare() {
      let href = "http://service.weibo.com/share/share.php?url=你的分享网址&sharesource=weibo&title=你的分享标题&pic=你的分享图片&appkey=你的key，需要在新浪微博开放平台中申请"
    }
  },
  created() {
    let date = new Date()
    this.day = date.getDate()
    this.month = date.getMonth() + 1
    this.year = date.getFullYear()

  },
  mounted() {
    if (document.body.clientWidth < 600) {
      this.$store.commit("setPosterStyle",{
        posterStyle: "max-height: 26.6rem;height: max-content;"
      })
    }else {
      let shareBottomHeight = document.querySelector(".share-bottom").offsetHeight
      let posterCancelHeight = document.querySelector(".poster-cancel").offsetHeight
      let posterImgHeight = document.querySelector(".poster-img").offsetHeight
      console.log(posterCancelHeight)
      console.log(shareBottomHeight)
      console.log(posterImgHeight)
      let czHeight = posterImgHeight - shareBottomHeight - posterCancelHeight - 30
      let height = czHeight + "px"
      console.log(height)
      this.$store.commit("setPosterStyle",{
        posterStyle: "height: " + height
      })
    }

  },
  watch: {
    getHeight(newValue,oleValue) {
      let shareBottomHeight = document.querySelector(".share-bottom").offsetHeight
      let posterCancelHeight = document.querySelector(".poster-cancel").offsetHeight
      let posterImgHeight = document.querySelector(".poster-img").offsetHeight
      console.log(posterCancelHeight)
      console.log(shareBottomHeight)
      console.log(posterImgHeight)
      let czHeight = posterImgHeight - shareBottomHeight - posterCancelHeight - 30
      let height = czHeight + "px"
      console.log(height)

      $(".share-div").css("height",height)
    }
  }
}
</script>

<style scoped>

</style>
