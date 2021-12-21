<template>
  <!--点击生成海报-->
  <div class="poster-share" style="z-index: 99">
    <div class="poster-button" id="poster-button">
      <span class="aurora-iconfont-common aurora-poster-ico" :data="getRandomInt(0,10)" @click="createPoster">生成海报{{clickSecond}}</span>&nbsp;
    </div>
  </div>
</template>

<script>
import {useThemeData} from "../../composables";
import $ from 'jquery'
import html2canvas from 'html2canvas'
import qrcode from 'qrcode'
import {createApp} from 'vue'
import storeIndex from '../../public/js/store'

import PosterImg from "../child/PosterImg.vue";
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
      clickStatus: false,
      spanData: -2,
      themeConfig: '',
      postImgApi: 'https://unsplash.it/1600/900?random'
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
    }
  },
  created() {
    this.themeConfig = useThemeData().value
    if (this.themeConfig.postImgApi !== undefined) {
      this.postImgApi = this.themeConfig.postImgApi
    }else {
      this.postImgApi = this.$store.state.defaultPostImgApi
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    cancelShade() {
      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: false
      })
      this.$store.commit("setShowShadeLoad",{
        showShadeLoad: false
      })
      $(".poster-img").slideUp(500)
    },
    async createPoster(e) {

      if (!this.clickStatus) {
        return
      }
      this.$store.commit("setShowPosterShadow", {
        showPosterShadow: true
      })

      this.$store.commit("setShowShadeLoad",{
        showShadeLoad: true
      })

      this.$store.commit("setPicture", {
        picture: 'https://unsplash.it/1600/900?random'
      })
      this.setTopBackStyle = "--poster-back-img: url(" + this.postImgApi + ")"
      if (this.clickStatus === false) {
        return
      }

      let spanData = e.target.getAttribute("data")

      if (this.$store.state.posterData !== spanData) {
        //第一次
        setTimeout(() => {
          this.loadPosterImg(spanData)
          setTimeout(() =>{
            this.handlePoster(spanData)
          },500)
        },500)
      }else {
        this.handlePoster(spanData)
      }
    },
    loadPosterImg(spanData) {
      if (this.$store.state.posterData !== spanData) {
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
    handlePoster(spanData) {

      $(".poster-append").css("z-index",21)
      let qrHref = this.qrHref
      if (qrHref === undefined || qrHref === "") {
        qrHref = window.location.href
      }

      if (this.$store.state.posterData === spanData) {
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
      //第一次点击

      this.$store.commit("setPosterData",{
        posterData: spanData
      })

      qrcode.toDataURL(qrHref, (err,url) => {
        this.saveQrimg(url).then(async () => {
          await html2canvas(document.querySelector("#create-poster"), {
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
            // document.body.appendChild(canvas)
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
      // console.log("最终使用高度: " + height)
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
    /*setTimeout(() => {
      this.picture = this.$store.state.picture
      this.setTopBackStyle = "--poster-back-img: url(" + this.picture + ")"
    },50)*/

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