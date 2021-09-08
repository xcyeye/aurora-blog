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
        <span>{{app.$store.state.downloadImgTitle}}</span>
      </div>
      <div class="poster-content" id="poster-content">
            <span>{{app.$store.state.posterContent}}</span>
      </div>
      <div class="poster-page" id="poster-page">
        <div class="poster-page-left">
          <div class="poster-page-avatar">
            <img id="poster-page-avatar" src="/avatar.jpg" alt="">
          </div>
          <div class="poster-page-desc" id="poster-page-desc">
            <div class="poster-desc-top">
              <span>@{{getAuthor}}</span>
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
          <span>&nbsp;  {{getLogoSuffixTitle}}</span>
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
          <img id="poster-qrimg" :src="app.$store.state.qrImgHref" alt="">
        </div>
      </div>
    </div>
  </div>

  <!--<div :data="height" class="poster-img" style="display: none" id="poster-img">
    <div class="poster-cancel">
      <span @click="cancelShade" class="icon-close"></span>
    </div>
    <div class="share-div" :style="getHeight">
      <img id="share-img" class="medium-zoom-image"  @click="openImg" :src="app.$store.state.postImgHref" alt="">
    </div>
    <div class="share-bottom" id="share-bottom">
      &lt;!&ndash;<div class="poster-share-desc">
        <span>分享到</span>
        &lt;!&ndash;<div class="poster-scale">
          <i>缩放&nbsp;</i>
          <span @click="scaleAdd">+</span>
          <span @click="scaleDown">-</span>
        </div>&ndash;&gt;
      </div>&ndash;&gt;
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
  </div>-->
</template>

<script>
import $ from "jquery";
import {usePageData} from "@vuepress/client";
import myData from '@temp/my-data'
import {useThemeLocaleData} from "../../composables";
import mediumZoom from "medium-zoom";
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
      poster: '',
      imgHeight: 'height: 90%;'
    }
  },
  props: {
    title: {
      type: String,
      default() {
        return "ccds";
      }
    },
    app: '',
    height: ''
  },
  computed: {

    getHei() {
      return this.app.$store.posterImgHeight
    },
    getScale() {
      return "--scale-transform: " + this.app.$store.state.scaleTransform + ";"
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
      if(this.poster === undefined || this.poster == null) {
        return 'qsyyke'
      }else {
        if(this.poster.preBlog === undefined || this.poster.preBlog == null) {
          return 'qsyyke'
        }else {
          return this.poster.preBlog
        }
      }
    },
    getLogoSuffixTitle() {
      if(this.poster === undefined || this.poster == null) {
        return 'blog'
      }else {
        if(this.poster.suffixBlog === undefined || this.poster.suffixBlog == null) {
          return 'blog'
        }else {
          return this.poster.suffixBlog
        }
      }
    },
    getAuthor() {
      if(this.poster === undefined || this.poster == null) {
        return 'qsyyke'
      }else {
        if(this.poster.author === undefined || this.poster.author == null) {
          return 'qsyyke'
        }else {
          return this.poster.author
        }
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
    openImg(e) {
      const zoom = mediumZoom(e.target)
      zoom.open()
    },
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
      let scale = this.app.$store.state.scaleTransform + 0.1
      this.app.$store.commit("setScaleTransform",{
        scaleTransform: scale
      })
    },
    scaleDown() {
      let scale = this.app.$store.state.scaleTransform - 0.1
      this.app.$store.commit("setScaleTransform",{
        scaleTransform: scale
      })
      console.log(scale)
    },
    cancelShade() {
      this.app.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })

      $(".poster-img").slideUp(500)
      $(".poster-append").css("z-index",1)
    },
    saveImg() {
      console.log("--------save-----------")
      console.log(this.app.$store.state.postImgHref)
      var a = document.createElement('a');
      var event = new MouseEvent('click')
      console.log(this.app.$store.state.downloadImgTitle)
      a.download = this.app.$store.state.downloadImgTitle
      a.href = this.app.$store.state.postImgHref;
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
          "url="+this.app.$store.state.posterShareSite +
          "&title="+this.app.$store.state.downloadImgTitle+
          "&desc="+this.app.$store.state.posterContent.substr(1,this.app.$store.state.posterContent.length /4) +
          "&summary="+this.app.$store.state.posterContent.substr(1,this.app.$store.state.posterContent.length /4) +
          "&site="+this.app.$store.state.posterShareSite+"&pics=https://api.paugram.com/bing"
      console.log(href)
      return "javascript:;"
      window.location.href = href
    },
    weiboShare() {
      let href = "http://service.weibo.com/share/share.php?url=你的分享网址&sharesource=weibo&title=你的分享标题&pic=你的分享图片&appkey=你的key，需要在新浪微博开放平台中申请"
    }
  },
  created() {
    console.log("-----------create执行-----------")
    let date = new Date()
    this.day = date.getDate()
    this.month = date.getMonth() + 1
    this.year = date.getFullYear()

    for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === "/") {
          this.themeConfig = myData[i].frontmatter
          this.poster = this.themeConfig.poster
        }
      }

  },
  mounted() {
    if (document.body.clientWidth < 600) {
      this.app.$store.commit("setPosterImgHeight",{
        posterImgHeight: "max-height: 26.6rem;height: max-content;"
      })
    }
  },
   /*watch: {
     getHei(newValue,oleValue) {
       console.log("高度变化了")
       console.log(newValue)
       /!*let shareBottomHeight = document.querySelector(".share-bottom").offsetHeight
       let posterCancelHeight = document.querySelector(".poster-cancel").offsetHeight
       let posterImgHeight = document.querySelector(".poster-img").offsetHeight
       let czHeight = posterImgHeight - shareBottomHeight - posterCancelHeight - 30
       let height = czHeight + "px"
       $(".share-div").css("height",height)*!/
       //this.imgHeight = "height: " + newValue + "px;"
       console.log("新数据: " + newValue)
       console.log("旧数据: " + oleValue)
       console.log(this)
       console.log("-------watch-------")
     }
   }*/
}
</script>

<style scoped>

</style>

