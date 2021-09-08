<template>
  <div>
    <div :data="height" class="poster-img" style="display: none" id="poster-img">
      <div class="poster-cancel">
        <span @click="cancelShade" class="icon-close"></span>
      </div>
      <div class="share-div" :style="getHeight">
        <img id="share-img" class="medium-zoom-image"  @click="openImg" :src="app.$store.state.postImgHref" alt="">
      </div>
      <div class="share-bottom" id="share-bottom">
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
  </div>
</template>

<script>
import mediumZoom from "medium-zoom";
import $ from "jquery";

export default {
  name: "PosterShare",
  data() {
    return {

    }
  },
  props: {
    app: ''
  },
  computed: {
    getHeight() {
      setTimeout(() => {

      },5000)

      console.log("------------get cc--------------")
      console.log(this.app.$store.state.posterImgHeight)
      console.log("--------------get cc---------------")
      let style = "height: " + this.app.$store.state.posterImgHeight + "px;"
      console.log(style)
      return style
    },
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
}
</script>

<style scoped>

</style>