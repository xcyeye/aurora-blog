<template>
  <!--点击生成海报-->
  <div class="poster-share" style="z-index: 99">
    <div class="poster-button" id="poster-button">
      <span class="icon-share" @click="createPoster">生成海报 {{clickSecond}}</span>
    </div>
  </div>
</template>

<script>
const network = require('../../public/js/network.js')
import PosterImg from "../child/PosterImg";
import $ from 'jquery'
import html2canvas from 'html2canvas'
var QRCode = require('qrcode')
import {createApp} from 'vue'
import storeIndex from '../../public/js/store'
export default {
  name: "Poster",
  components: {
    PosterImg
  },
  data() {
    return {
      href: '',
      clickCreateNum: 0,
      imgHeight: '',
      posterImgHeight: 500,
      setTopBackStyle: '',
      picture: '',
      clickSecond: 3,
      clickStatus: false
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
        return '...'
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
  methods: {
    test() {
      $.get("https://pciture.cco.vin/pic/rp?appId=lnZxmObbJSp3o8Zea2KXxPwat&appKey=6TleVWdLeVwpOKv9eXtTQUam7",function () {

      })
    },
    cancelShade() {
      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })
      $(".poster-img").slideUp(500)
    },
    async createPoster() {

      if (this.clickStatus === false) {
        return
      }

      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: true
      })

      this.$store.commit("setShowShadeLoad",{
        showShadeLoad: true
      })

      if (this.clickCreateNum === 0) {
        //第一次
        setTimeout(() => {
          this.loadPosterImg()
          this.handlePoster()
        },500)
      }else {
        this.handlePoster()
      }
    },
    loadPosterImg() {
      if (this.clickCreateNum === 0) {
        let append = document.querySelector("#poster-append")
        if (append === null) {
          let posterAppend = $("<div class=\"poster-append\" id=\"poster-append\">").get(0)
          $("#app").get(0).appendChild(posterAppend)
          const app = createApp(PosterImg,{
            app: this,
            content: this.content,
            height: this.posterImgHeight,
            title: this.title,
            setTopBackStyle: this.setTopBackStyle
          }).use(storeIndex).mount("#poster-append")
        }else {
          append.remove()
          let posterAppend = $("<div class=\"poster-append\" id=\"poster-append\">").get(0)
          $("#app").get(0).appendChild(posterAppend)
          const app = createApp(PosterImg,{
            app: this,
            content: this.content,
            height: this.posterImgHeight,
            title: this.title,
            setTopBackStyle: this.setTopBackStyle,
          }).use(storeIndex).mount("#poster-append")
        }
      }
    },
    handlePoster() {
      $(".poster-append").css("z-index",21)
      let qrHref = this.qrHref
      if (qrHref === undefined || qrHref === "") {
        qrHref = window.location.href
      }
      if (this.clickCreateNum !== 0) {
        //第二次点击
        $(".poster-append").css("z-index",21)
        $(".poster-img").slideDown(500)
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

          await html2canvas(document.querySelector("#poster"), {
            onclone: () => {
              this.$store.commit("setShowShadeLoad",{
                showShadeLoad: false
              })
            },
            logging: false,
            removeContainer: false,
            allowTaint: true,
            useCORS: true,
          }).then(canvas => {
            $(".poster-append").css("z-index",21)
            this.imgHeight = canvas.height
            this.href = this.convertCanvasToImage(canvas).src

            this.$store.commit("setPostImgHref",{
              postImgHref: this.href
            })

            this.$store.commit("setShowShadeLoad",{
              showShadeLoad: false
            })
            this.clickCreateNum = this.clickCreateNum + 1
            this.$store.commit("setShowPostImg",{
              showPostImg: true
            })

            let shareBottomHeight = document.querySelector(".share-bottom").offsetHeight
            let posterCancelHeight = document.querySelector(".poster-cancel").offsetHeight
            let viewHeight = document.documentElement.clientHeight

            if (this.imgHeight > viewHeight) {
              let czHeight = viewHeight - shareBottomHeight - posterCancelHeight - 48
              this.setHeight(czHeight)
              //分享图片的高度大于可见区域的高度，则使用计算之后的高度
            }else {
              //图片高度小于可视化高度，则图片高度，直接使用图片高度
              let czHeight = viewHeight - shareBottomHeight - posterCancelHeight - 48
              if (czHeight > this.imgHeight) {
                this.setHeight(this.imgHeight)
              }else {
                this.setHeight(czHeight)
              }
            }
          });
        })
      })
    },
    setHeight(height) {
      console.log("最终使用高度: " + height)
      height = height + "px"
      if (document.body.clientWidth < 600) {
        //是手机
        $(".share-div").css("maxHeight",height)
      }else {
        $(".share-div").animate({
        height: 'auto'
      },500)
      }
    },
    convertCanvasToImage(canvas) {
      var image = new Image();
      image.setAttribute("crossOrigin","anonymous")
      //image.crossOrigin="anonymous"
      image.src = canvas.toDataURL("image/png");
      return image;
    },
    saveQrimg(url) {
      this.$store.commit("setQrImgHref",{
        qrImgHref: url
      })
      return new Promise((resolve,reject) => {
        resolve()
      })
    }
  },
  mounted() {

    setTimeout(() => {
      this.picture = this.$store.state.picture
      this.setTopBackStyle = "--poster-back-img: url(" + this.picture.src + ")"
      console.log(this.setTopBackStyle)
    })

    let clickSecond = setInterval(() => {
      this.clickSecond = this.clickSecond - 1

      if (this.clickSecond === 0) {
        clearInterval(clickSecond)
        this.clickStatus = true
        this.clickSecond = ""
      }
    },1000)
  },

}
</script>

<style scoped>

</style>