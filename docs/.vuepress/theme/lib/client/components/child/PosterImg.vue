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
          <span>{{app.$store.state.posterCon}}</span>
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

  <div class="poster-img" style="display: block" id="poster-img">
    <div class="poster-img-child" style="display: block">
      <div class="poster-cancel">
        <span @click="cancelShade" class="icon-close"></span>
      </div>
      <div class="share-div">
        <img id="share-img" class="medium-zoom-image"  @click="openImg" :src="app.$store.state.postImgHref" alt="">
      </div>
      <div class="share-bottom" id="share-bottom">

        <div class="poster-social">
          <a :href="qqHref">
            <span class="qq">好友</span>
          </a>
          <a :href="qzoneHref">
            <span class="qzone">空间</span>
          </a>
          <a :href="weiboHref">
            <span class="weibo">微博</span>
          </a>

          <a :href="href">
            <span class="save" @click="saveImg">保存</span>
          </a>
        </div>
      </div>
    </div>
  </div>
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
    getHeight() {
      let style = "height: " + this.app.$store.state.posterImgHeight + "px;"
      //console.log(style)
      return style
    },
    getHei() {
      return this.app.$store.posterImgHeight
    },
    getScale() {
      return "--scale-transform: " + this.app.$store.state.scaleTransform + ";"
    },
    getLastUpdate() {
      const page = usePageData()
      console.log(page)
      let time = 0
      //let time = page.value.git.updatedTime
      let git = page.value.git
      if (git === undefined || git == null) {
        git = + new Date()
        return this.getLocalTime(time)
      }

      let update = page.value.git.updatedTime

      if (update === undefined) {
        update = +new Date()
      }

      return this.getLocalTime(update)
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
      //console.log(myData)
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
      //console.log(scale)
    },
    cancelShade() {
      this.app.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })

      $(".poster-img").slideUp(500)
      $(".poster-append").css("z-index",1)
    },
  },
  created() {
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

    setTimeout(() => {
      let content = $(".poster-content").get(0).innerText
      if (content.length > 120) {
        content = content.substr(1,120)
      }
      let title = $(".poster-title").get(0).innerText
      let href = window.location.href
      this.qzoneHref = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+href+"&title="+title+"&desc="+content+"&pics=https://api.paugram.com/bing"

      this.qqHref ="http://connect.qq.com/widget/shareqq/index.html?url=https://blog.cco.vin&sharesource=qzone&title=你的分享标题&pics=你的分享图片地址&summary=你的分享描述&desc=你的分享简述"
      this.weiboHref = "https://service.weibo.com/share/share.php?url=https://blog.cco.vin&pichttps://ooszy.cco.vin/img/blog-note/image-20210904175030428.png?x-oss-process=style/pictureProcess1=&appkey="
    },1000)
  },

}
</script>

<style scoped>

</style>

