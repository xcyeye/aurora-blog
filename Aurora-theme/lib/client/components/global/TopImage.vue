<template>
  <div class="sidebar-single-enter-animate page-top" v-if="isShowTopImg" id="page-top">
    <div class="top-mask" :style="setBackgroundUrl"></div>

    <div v-if="showBubble" class="aurora-top-bubble">
      <div class="aurora-top-bubble-par">
        <div class="aurora-top-bubble-box" id="aurora-top-bubble-box"></div>
        <canvas class="aurora-top-bubble-canvas" id="aurora-top-bubble-canvas"></canvas>
      </div>
    </div>
    <slot name="top1"></slot>
    <slot name="top2"></slot>
    <slot name="top3"></slot>
    <slot name="top4"></slot>
    <slot name="top5"></slot>
    <div class="page-record-control" v-if="isShowHeadLine">
      <div class="page-top-record" id="page-top-record">
        <div class="page-record-bot-common page-record-top">
          <div class="page-record-top-left page-record-single-common">
            <span class="aurora-iconfont-common aurora-page-word"></span>&nbsp;
            <span class="page-record-single-desc">总字数</span>
            <span>{{animatedContentLength}}</span>
          </div>
          <div class="page-record-top-right page-record-single-common">
            <span class="aurora-iconfont-common aurora-page-time"></span>&nbsp;
            <span class="page-record-single-desc">时长</span>
            <span>{{getSugTime}}</span>
          </div>
        </div>
        <div class="page-record-bot-common page-record-center">
          <div class="page-record-center-left page-record-single-common">
            <span class="aurora-iconfont-common aurora-page-comment"></span>&nbsp;
            <span class="page-record-single-desc">评论数</span>
            <span :id="pathName" class="waline-comment-count" />
            <!--<span>{{$store.state.commentCount}}</span>-->
          </div>
          <div class="page-record-center-right page-record-single-common">
            <span class="aurora-iconfont-common aurora-page-read"></span>&nbsp;
            <span class="page-record-single-desc">总阅读数</span>
            <span :id="pathName" class="waline-visitor-count" />
            <!--<span>{{$store.state.readCount}}</span>-->
          </div>
        </div>
        <div v-if="tagArr.length !== 0" class="page-record-bot-common page-record-bot">
          <div class="page-record-bot-tag" id="page-record-bot-tag">
            <span class="aurora-iconfont-common aurora-page-tag"></span>&nbsp;
            <router-link v-for="(item,index) in tagArr" :to="goTag(item)">
              <span class="home-page-tag-span page-record-tag-span">{{item}}</span>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="top-image" id="top-image" v-if="isShowHeadLine">
      <h1>{{headLine}}</h1>
    </div>
  </div>
</template>

<script>
import { readingTime } from 'reading-time-estimator';
import {useThemeLocaleData} from "../../composables";
import gsap from "gsap";
import {withBase} from "@vuepress/client";
export default {
  name: "TopImage",
  data() {
    return {
      totalWordObj: {
        minutes: '',
        text: '',
        words: 0
      },
      animeImg: '',
      pageMap: '',
      contentLength: 0,
      tagArr: [],
      topBackgroundUrl: 'https://picture.cco.vin/pic/rmimg',
      pathName: '',
      sugCountPerMin: 230,
      document: {},

      contentLengthTemp: 0,
      sugReadTimeTemp: 0,
      totalReadNumTemp: 0,
      showBubble: true
    }
  },
  props: {
    isShowTopImg: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowHeadLine: {
      type: Boolean,
      default() {
        return false
      }
    },
    headLine: {
      type: String,
      default() {
        return "aurora";
      }
    },
    themeProperty: {
      type: Object
    }
  },
  created() {
    if (this.themeProperty.sugCountPerMin !== undefined) {
      this.sugCountPerMin = this.themeProperty.sugCountPerMin
    }
    this.setPathName(this.$route.path)
    this.$router.beforeEach((to,from,next) => {
      this.setPathName(to.path)
      next()
    })
  },
  mounted() {
    let bubbleNumber = 0.15
    let bubbleAlpha = 0.7
    let alphaChangeSpeed = 0.0005
    let size = 0.5
    let sizeChangeSpeed = 0.002
    let riseSpeed = 0.9
    let color = '255,255,255'
    if (this.themeProperty.bubble !== undefined) {
      if (this.themeProperty.bubble.show !== undefined) {
        this.showBubble = this.themeProperty.bubble.show
      }

      if (this.themeProperty.bubble.bubbleNumber !== undefined) {
        bubbleNumber = this.themeProperty.bubble.bubbleNumber
      }

      if (this.themeProperty.bubble.bubbleAlpha !== undefined) {
        bubbleAlpha = this.themeProperty.bubble.bubbleAlpha
      }

      if (this.themeProperty.bubble.alphaChangeSpeed !== undefined) {
        alphaChangeSpeed = this.themeProperty.bubble.alphaChangeSpeed
      }

      if (this.themeProperty.bubble.size !== undefined) {
        size = this.themeProperty.bubble.size
      }

      if (this.themeProperty.bubble.sizeChangeSpeed !== undefined) {
        sizeChangeSpeed = this.themeProperty.bubble.sizeChangeSpeed
      }

      if (this.themeProperty.bubble.riseSpeed !== undefined) {
        riseSpeed = this.themeProperty.bubble.riseSpeed
      }

      if (this.themeProperty.bubble.color !== undefined) {
        color = this.themeProperty.bubble.color
      }
    }

    this.$nextTick(() =>{
      if (this.showBubble && this.isShowTopImg) {
        import("../../public/js/bubble").then(module => {
          module.bubble(bubbleNumber,bubbleAlpha,alphaChangeSpeed,size,sizeChangeSpeed,riseSpeed,color)
        })
      }
    })
    this.document = document
    this.pathName = window.location.pathname
    setTimeout(() =>{
      this.getPageMap()
    },500)
  },
  computed: {
    animatedContentLength() {
      return this.contentLengthTemp.toFixed(0)
    },
    goTag() {
      return (item) => {
        return '/tag?tag=' + item
      }
    },
    setBackgroundUrl() {
      let path = this.$route.path
      let customTop = this.themeProperty.customTopImg

      let imgPath = ""
      if (customTop === undefined) {
        return this.getRandomBg()
      }

      if (customTop.custom === undefined || !customTop.custom) {
        //用户自定义
        return this.getRandomBg()
      }

      //用户自定义 使用用户的图片
      if (path.search("link") !== -1) {
        let arr = this.themeProperty.customTopImg.friend
        imgPath = this.getCustomTopImgPath(arr)
      }else if (path.search("tag") !== -1) {
        let arr = this.themeProperty.customTopImg.tag
        imgPath = this.getCustomTopImgPath(arr)
      }
      else if (path.search("mood") !== -1) {

        let arr = this.themeProperty.customTopImg.mood
        imgPath = this.getCustomTopImgPath(arr)
      }else {
        let arr = this.themeProperty.customTopImg.page
        imgPath = this.getCustomTopImgPath(arr)
      }

      return "background-image: url(" + imgPath + ");"
    },
    getWordLength() {
      let content = this.pageMap.content + ""
      if (content.length === undefined || content.length === null) {
        this.length = 0
      }else {
        this.length = content.length
      }
      return this.length
    },
    getSugTime() {
      return this.sugReadTimeTemp.toFixed(0)
    },
    getRandom() {
      return this.getRandomInt(0,99999)
    }
  },
  methods: {
    setPathName(pathName) {
      this.pathName = pathName
    },
    getRandomBg() {
      if (this.pageMap !== "" && this.pageMap.data.frontmatter.coverUrl !== undefined) {
        //return "background-image: url(" + this.pageMap.data.frontmatter.coverUrl + ");"
        return "background-image: url(" + this.pageMap.data.frontmatter.coverUrl + ");"
      }
      //用户没有自定义图片，使用随机图片
      let num1 = this.getRandomInt(-9999,999)
      let num2 = this.getRandomInt(0,300)
      let num3 = this.getRandomInt(0,30)
      let num = num2 / num3 * num1 + num2

      const themeLocale = useThemeLocaleData()

      let homePageImgApi = themeLocale.value.homePageImgApi

      if (homePageImgApi === undefined) {
        homePageImgApi = this.$store.state.defaultHomePageImgApi
      }

      let path = homePageImgApi + "?time=" + num
      return "background-image: url(" + path + ");"
    },
    getCustomTopImgPath(pathArr) {
      if (pathArr.length === 0) {
        try {
          let path = pathArr[0]
          return path
        }catch (e) {
          return pathArr
        }
      }
      //随机选择一个
      let path = pathArr[this.getRandomInt(0,pathArr.length - 1)]
      return path
    },
    getPageMap() {
      let allPageMap = this.$store.state.allPageMap
      for (let i = 0; i < allPageMap.length; i++) {
        if (this.pathName === withBase(allPageMap[i].articleUrl)) {
          this.pageMap = allPageMap[i]
          if (this.pageMap.categories.length === 0) {
            this.tagArr = this.pageMap.tag
          }else {
            this.tagArr = this.pageMap.categories
          }
        }
      }
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    countNum() {
      new Promise((resolve,reject) => {
        let allContent = ''
        try {
          allContent = this.document.querySelector(".medium-zoom-content").innerText;
        }catch (e) {
          return
        }
        let allCodeElement = this.document.querySelectorAll(".medium-zoom-content div[class*=language-]");
        for (let i = 0; i < allCodeElement.length; i++) {
          let codeContent = allCodeElement[i].innerText;
          let indexOf = allContent.indexOf(codeContent);
          if (indexOf === -1) {
            continue
          }
          let startContent = allContent.substr(0,indexOf)
          let endContent = allContent.substr((indexOf + codeContent.length), allContent.length)
          startContent = startContent.replace("\n","")
          endContent = endContent.replace("\n","")
          allContent = startContent + endContent
          allContent = allContent.replace("\n","")
        }

        allContent = allContent.substr(document.querySelector(".page-top-share").innerText.length,allContent.length)
        resolve(allContent)
      }).then((allContent) => {
        const totalWordObj = readingTime(allContent, this.sugCountPerMin);
        this.totalWordObj = totalWordObj
        this.contentLength = totalWordObj.words
      })
    }
  },
  watch: {
    contentLength(nv) {
      gsap.to(this.$data, { duration: 0.5, contentLengthTemp: nv })
      let sugReadTime = Math.floor(this.contentLength / this.sugCountPerMin) === 0 ? 1 : Math.ceil(this.contentLength / this.sugCountPerMin)
      gsap.to(this.$data, { duration: 0.5, sugReadTimeTemp: this.totalWordObj.minutes })
    },
    headLine(newValue,oldValue) {
      setTimeout(() => {
        this.pathName = window.location.pathname
        this.getPageMap()
      },500)
      if (this.isShowHeadLine) {
        setTimeout(() => {
          this.countNum()
        },1000)
      }
    },
    pathName() {
      if (this.isShowHeadLine) {
        setTimeout(() => {
          this.countNum()
        },1000)
      }
    }
  }
}
</script>

<style scoped>

</style>