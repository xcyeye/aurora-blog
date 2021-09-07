<template>
  <!--生成的截图-->
  <div :data="$store.state.showPostImg"></div>
  <div id="poster-qrimg-center" v-if="$store.state.showPostImg" class="poster-qrimg-center" style="display: none">
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
        <!--<a :href="qZone">
          <span class="qzone">空间</span>
        </a>-->
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
  </div>
</template>

<script>
import $ from "jquery";

export default {
  name: "PosterImg",
  data() {
    return {
      qqHref: '',
      qzoneHref: '',
      weiboHref: ''
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
    }
  },
  computed: {

  },
  methods: {
    cancelShade() {
      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })

      $(".poster-qrimg-center").slideUp(500)
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
  }
}
</script>

<style scoped>

</style>