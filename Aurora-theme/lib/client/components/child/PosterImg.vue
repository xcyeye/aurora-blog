<template>
  <!--模板-->
  <div style="display: block" class="poster" id="create-poster">
    <div class="poster-top" :style="setTopBackStyle" id="poster-top">
<!--/*    <div class="poster-top" style="&#45;&#45;poster-back-img: url(https://unsplash.it/1600/900?random);" id="poster-top">*/-->
      <div class="poster-time">
        <div class="poster-time-day" id="poster-time-day">{{day}}</div>
        <div class="poster-time-year" id="poster-time-year">{{month}}/{{year}}</div>
      </div>
      <!--<img src="http://h2.ioliu.cn/bing/HowgillFells_ZH-CN1134328886_640x480.jpg?imageslim" id="poster-top-img" alt="">-->
    </div>
    <div class="poster-center" id="poster-center">

      <div class="poster-title" id="poster-title">
        <span>{{title}}</span>
      </div>
      <div class="poster-content" id="poster-content">
          <span>{{content}}</span>
      </div>
      <div class="poster-page" id="poster-page">
        <div class="poster-page-left">
          <div class="poster-page-avatar">
            <img id="poster-page-avatar" :src="getAvatar" alt="">
          </div>
          <div class="poster-page-desc" id="poster-page-desc">
            <div class="poster-desc-top">
              <span>@{{getAuthor}}</span>
            </div>
            <div class="poster-desc-bottom">
              <span>{{getLastUpdate}}</span>
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

  <div :sd="getPicture" class="poster-img" style="display: block" id="poster-img">
    <div class="poster-img-child" style="display: block">
      <div class="poster-cancel">
        <span class="aurora-iconfont-common aurora-poster-cancel" style="cursor: pointer" @click="cancelShade"></span>&nbsp;
      </div>
      <div class="share-div">
        <img id="share-img" class="medium-zoom-image"  @click="openImg" :src="app.$store.state.postImgHref" alt="">
      </div>
      <div class="share-bottom" id="share-bottom">

        <div class="poster-social">
          <a target="_blank" :href="qqHref">
            <span class="qq">好友</span>
          </a>
          <a target="_blank" :href="qzoneHref">
            <span class="qzone">空间</span>
          </a>
          <a target="_blank" :href="weiboHref">
            <span class="weibo">微博</span>
          </a>

          <a>
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
import mediumZoom from "medium-zoom";
import {useThemeData} from "../../composables";
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
      imgHeight: 'height: 90%;',
      picture: "",
      pictureSrc: 'https://h2.ioliu.cn/bing/Knuthojdsmossen_EN-CA12064544039_640x480.jpg?imageslim',
      postImgApi: 'https://unsplash.it/1600/900?random'
    }
  },
  props: {
    content: {
      type: String,
      default() {
        return "ccds";
      }
    },
    title: {
      type: String,
      default() {
        return "ccds";
      }
    },
    app: '',
    height: '',
    setTopBackStyle: {
      type: String,
      default() {
        return '--poster-back-img: url(https://h2.ioliu.cn/bing/Knuthojdsmossen_EN-CA12064544039_640x480.jpg?imageslim);'
      }
    }
  },
  computed: {
    getAvatar() {
      let poster = this.themeConfig.poster
      if (poster === undefined) {
        return "请设置海报avatar项"
      }
      let avatar = poster.avatar

      if (avatar === undefined || avatar == null) {
        return "请设置海报avatar项"
      }
      return avatar
    },
    getPicture() {
      this.picture = this.app.$store.state.picture
      return this.app.$store.state.picture.src
    },
    getTopBackStyle() {
      console.log("---------getTop-----------")
      if (this.app.$store.state.picture === "") {
        return "--poster-back-img: url( " + this.pictureSrc + ");"
      }else {
        return "--poster-back-img: url( " + this.app.$store.state.picture.src + ");"
      }
      // return '--poster-back-img: url(https://picture.cco.vin/pic/rmimg?type=bing);'
      // return '--poster-back-img: url(https://h2.ioliu.cn/bing/GiantManta_ZH-CN0594951444_640x480.jpg?imageslim);'

    },
    getLastUpdate() {
      const page = usePageData()
      let git = page.value.git
      let createDate = page.value.frontmatter.date

      if (createDate !== undefined) {
        return this.getLocalTime(createDate)
      }else {
        if (git === undefined || git == null) {
          git = + new Date()
          return this.getLocalTime(git)
        }

        let update = page.value.git.updatedTime

        if (update === undefined) {
          update = +new Date()
        }
        return this.getLocalTime(update)
      }
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
        return 'のblog'
      }else {
        if(this.poster.suffixBlog === undefined || this.poster.suffixBlog == null) {
          return 'のblog'
        }else {
          return this.poster.suffixBlog
        }
      }
    },
    getAuthor() {
      if(this.poster === undefined) {
        return 'Aurora'
      }else {
        if(this.poster.author === undefined) {
          return 'Aurora'
        }else {
          return this.poster.author
        }
      }
    },
    getBlogDesc() {
      if (this.themeConfig.poster === undefined) {
        return "从别以后,几回梦缥缈,执手若无,泪溅花上"
      }else {
        if (this.themeConfig.poster.description === undefined
            || this.themeConfig.poster.description === "" ) {
          return "从别以后,几回梦缥缈,执手若无,泪溅花上"
        }else {
          return this.themeConfig.poster.description
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
    cancelShade() {
      let stopStatus = 0
      $(".poster-img").slideUp(500,function () {
        stopStatus = 1
      })

      let stop = setInterval(() => {
        if (stopStatus === 1) {
          this.$store.commit("setShowPosterShadow", {
            showPosterShadow: false
          })
          clearInterval(stop)
          $(".poster-append").css("z-index",-1)
        }
      },70)
    },
    saveImg() {
      var a = document.createElement('a');
      var event = new MouseEvent('click')
      a.download = this.app.$store.state.downloadImgTitle + (+new Date())
      a.href = this.app.$store.state.postImgHref;
      a.dispatchEvent(event);
    }
  },
  created() {
    let date = new Date()
    this.day = date.getDate()
    this.month = date.getMonth() + 1
    this.year = date.getFullYear()

    this.themeConfig = useThemeData().value
    this.poster = this.themeConfig.poster
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
        content = content.substr(0,120)
      }
      let title = $(".poster-title").get(0).innerText
      let href = window.location.href
      this.qzoneHref = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+href+"&title="+title+"&desc="+content+"&pics=https://api.paugram.com/bing"

      this.qqHref ="http://connect.qq.com/widget/shareqq/index.html?url="+href+"&sharesource=qzone&title="+title+"&pics="+content+"&summary=你的分享描述&desc=你的分享简述"
      this.weiboHref = "https://service.weibo.com/share/share.php?url"+href+"&pichttps://ooszy.cco.vin/img/blog-note/image-20210904175030428.png?x-oss-process=style/pictureProcess1=&appkey="
    },1000)
  },
  watch: {
    getPicture(nV,oV) {
      //this.picture = nV
    }
  }
}
</script>

<style scoped>

</style>

