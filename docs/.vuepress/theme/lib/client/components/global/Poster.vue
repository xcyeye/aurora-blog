<template>

  <!--<PosterImg/>-->
  <!--点击生成海报-->
  <div class="poster-share" style="z-index: 99">
    <div class="poster-button" id="poster-button">
      <span class="icon-share" @click="createPoster">生成海报</span>
    </div>
  </div>

  <!--<div class="hide-poster-top" id="hide-poster-top">
    &lt;!&ndash;模板&ndash;&gt;
    <div id="hide-poster" class="hide-poster">
      <div  class="poster" id="poster">
        <div class="poster-top">
          <div class="poster-time">
            <div class="poster-time-day">{{day}}</div>
            <div class="poster-time-year">{{month}}/{{year}}</div>
          </div>
        </div>
        <div class="poster-center">
          <div class="poster-title">
            <span>{{title}}</span>
          </div>
          <div class="poster-content">
            <span>{{content}}</span>
          </div>
        </div>
        <div class="poster-bottom">

          <div class="poster-say">
            <div class="transform">
              <div class="poster-author">
                <span>{{author}}</span>
              </div>
              <div class="poster-author-say">
                <span>{{sayContent}}</span>
              </div>
            </div>
          </div>

          <div class="poster-social-qr">
            &lt;!&ndash;<div class="poster-social-desc">
            </div>&ndash;&gt;
            <div class="poster-qr">
              &lt;!&ndash;https://ooszy.cco.vin/img/blog-public/wxpay.png&ndash;&gt;
              <img id="poster-qrimg" :src="qrImgHref" alt="">
            </div>
          </div>
        </div>
      </div>
    </div>

    &lt;!&ndash;生成的截图&ndash;&gt;
    &lt;!&ndash;<div class="poster-qrimg-center" style="display: none">
      <div class="poster-img" id="poster-img">
        <div class="poster-cancel">
          <span @click="cancelShade" class="icon-close"></span>
        </div>
        <img :src="href" alt="">
        <div class="poster-share-desc">
          <span>分享到</span>
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
    </div>&ndash;&gt;
  </div>-->
</template>

<script>
const network = require('../../public/js/network.js')
import PosterImg from "../child/PosterImg";
import $ from 'jquery'
import html2canvas from 'html2canvas'
var QRCode = require('qrcode')
export default {
  name: "Poster",
  components: {
    PosterImg
  },
  data() {
    return {
      showPoster: false,
      href: '',
      clickCreateNum: 0
    }
  },
  props: {
    title: {
      type: String,
      default() {
        return '青衫烟雨客'
      }
    },
    content: {
      type: String,
      default() {
        return "...";
      }
    },
    author: {
      type: String,
      default() {
        return 'qsyyke'
      }
    },
    sayContent: {
      type: String,
      default() {
        return '记录成长|分享快乐'
      }
    },
    qrHref: {
      type: String,
      default() {
        return ''
      }
    },
    backgroundImgHref: {
      type: String,
      default() {
        return 'https://api.paugram.com/bing/'
      }
    }
  },
  computed: {
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
      let href = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" +
          "url="+this.qrHref+"" +
          "&sharesource=qzone" +
          "&title="+this.title+"" +
          "&pics=sdf" +
          "&summary="+this.content+""
      return href
    },
    weiboShare() {
      let href = "http://service.weibo.com/share/share.php?url=你的分享网址&sharesource=weibo&title=你的分享标题&pic=你的分享图片&appkey=你的key，需要在新浪微博开放平台中申请"
    },
    getPosterStyle() {
      return "--poster-back-img: url(" + this.backgroundImgHref + ");"
    }
  },

  methods: {
    cancelShade() {

      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })

      $(".poster-qrimg-center").slideUp(500)
    },
    async createPoster() {
      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: true
      })

      this.$store.commit("setAuthor", {
        author: this.author
      })

      this.$store.commit("setShowShadeLoad",{
        showShadeLoad: true
      })

      this.$store.commit("setDownloadImgTitle",{
        downloadImgTitle: this.title
      })

      this.$store.commit("setPosterContent",{
        posterContent: this.content
      })

      /*let hide = document.querySelector("#hide-poster-top")
      hide.style.display = "block"*/
      let qrHref = this.qrHref
      if (qrHref === undefined || qrHref === "") {
        qrHref = window.location.href
      }

      this.$store.commit("setPosterShareSite",{
        posterShareSite: qrHref
      })
      //console.log("次数: " + this.clickCreateNum)
      if (this.clickCreateNum !== 0) {
        //第二次点击
        $(".poster-append").css("z-index",21)
        $(".poster-qrimg-center").slideDown(500)
        this.$store.commit("setShowPosterShadow", {
          showPosterShadow: true
        })

        this.$store.commit("setShowShadeLoad",{
          showShadeLoad: false
        })
        return;
      }
      QRCode.toDataURL(qrHref, (err,url) => {
        this.saveQrimg(url).then(async () => {
          console.log("-----二维码转换完成------")

          await html2canvas(document.querySelector("#poster"), {
            onclone: () => {

              $(".poster-append").css("z-index",21)
              console.log("-------clone完成-------")
              this.$store.commit("setShowPostImg",{
                showPostImg: true
              })

              setTimeout(() => {
                //$(".poster-qrimg-center").slideDown(500)
                $(".poster-qrimg-center").slideDown(400)
              },200)

              this.$store.commit("setShowShadeLoad",{
                showShadeLoad: false
              })

            },
            logging: false,
            removeContainer: false,
            allowTaint: true,
            /*width: 400,
            height: 400,
            backgroundColor: 'red',*/
            //proxy: this._proxyURL,
            useCORS: true,
          }).then(canvas => {
            console.log("----------最终完成---------")

            //document.body.appendChild(canvas)
            this.href = this.convertCanvasToImage(canvas).src

            this.$store.commit("setPostImgHref",{
              postImgHref: this.href
            })

            this.$store.commit("setShowShadeLoad",{
              showShadeLoad: false
            })



            //console.log("------poster------------")
            //console.log(this.href)
           /* setTimeout(() => {
                //$(".poster-qrimg-center").slideDown(500)
                $(".poster-qrimg-center").slideDown(500)
              },100)*/

            this.clickCreateNum = this.clickCreateNum + 1
          });
        })
      })
    },
    convertCanvasToImage(canvas) {
      var image = new Image();
      image.setAttribute("crossOrigin","anonymous")
      //image.crossOrigin="anonymous"
      image.src = canvas.toDataURL("image/png");
      return image;
    },
    saveImg() {
      var a = document.createElement('a');
      var event = new MouseEvent('click')
      a.download = this.title
      a.href = this.href;
      a.dispatchEvent(event);
    },
    saveQrimg(url) {
      this.$store.commit("setQrImgHref",{
        qrImgHref: url
      })
      return new Promise((resolve,reject) => {
        resolve()
      })
    }
  }
}
</script>

<style scoped>

</style>