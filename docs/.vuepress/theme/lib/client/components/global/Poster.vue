<template>
  <!--点击生成海报-->
  <div class="poster-share" style="z-index: 99">
    <div class="poster-button" id="poster-button">
      <span class="icon-share" @click="createPoster">生成海报</span>
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
      showPoster: false,
      href: '',
      clickCreateNum: 0,
      imgHeight: '',
      posterImgHeight: 500,
      //content: ''
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
    getPosterStyle() {
      return "--poster-back-img: url(" + this.backgroundImgHref + ");"
    }
  },

  methods: {
    cancelShade() {

      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })

      $(".poster-img").slideUp(500)
    },
    async createPoster() {
      console.log(this.title)
      console.log(this.content)
      let status = this.$store.state.posterStatus
      console.log(status)
      if (status !== 1) {
        let getContentStatus = setInterval(() => {
          status = this.$store.state.posterStatus
          if (status === 1) {
            //console.log("加载完成")
            clearInterval(getContentStatus)
            this.loadPosterImg()
            //setTimeout(() => {
              this.handlePoster()
            //},300)
          }
        },200)
      }else {
        //setTimeout(() => {
          //this.handlePoster()
          this.loadPosterImg()
        //},200)
      }
      this.handlePoster()
    },
    loadPosterImg() {
      console.log("----click: " + this.clickCreateNum)
      if (this.clickCreateNum === 0) {
        //document.body.remo
        let append = document.querySelector("#poster-append")
        if (append === null) {
          let posterAppend = $("<div class=\"poster-append\" id=\"poster-append\">").get(0)
          $("#app").get(0).appendChild(posterAppend)
          const app = createApp(PosterImg,{
            app: this,
            content: this.content,
            height: this.posterImgHeight,
            title: this.title
          }).use(storeIndex).mount("#poster-append")
        }else {
          append.remove()
          let posterAppend = $("<div class=\"poster-append\" id=\"poster-append\">").get(0)
          $("#app").get(0).appendChild(posterAppend)
          const app = createApp(PosterImg,{
            app: this,
            content: this.content,
            height: this.posterImgHeight,
            title: this.title
          }).use(storeIndex).mount("#poster-append")
        }

        //return
      }
    },
    handlePoster() {
      $(".poster-append").css("z-index",21)
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

      /*this.$store.commit("setPosterContent",{
        posterContent: this.content
      })*/
      let qrHref = this.qrHref
      if (qrHref === undefined || qrHref === "") {
        qrHref = window.location.href
      }

      this.$store.commit("setPosterShareSite",{
        posterShareSite: qrHref
      })
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
            console.log("--------开始执行then--------------")
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

            console.log("view: " + viewHeight)
            console.log("bottom: " + shareBottomHeight)
            console.log("cancel: " + posterCancelHeight)
            console.log("img: " + this.imgHeight)

            if (this.imgHeight > viewHeight) {
              console.log("图片大于视图")
              let czHeight = viewHeight - shareBottomHeight - posterCancelHeight - 48
              this.setHeight(czHeight)
              //分享图片的高度大于可见区域的高度，则使用计算之后的高度
            }else {
              console.log("图片小于视图")
              //图片高度小于可视化高度，则图片高度，直接使用图片高度
              let czHeight = viewHeight - shareBottomHeight - posterCancelHeight - 48
              if (czHeight > this.imgHeight) {
                console.log("图片小于视图，并且差值大于图片，使用图片高度")
                //czHeight = this.imgHeight
                this.setHeight(this.imgHeight)
              }else {
                console.log("图片小于视图，并且差值小于图片，使用擦差值高度")
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