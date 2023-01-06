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
		<!--TODO 这一块需要重新做一下-->
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
						<span class="waline-comment-count" >199</span>
            <!--<span>{{$store.state.commentCount}}</span>-->
          </div>
          <div class="page-record-center-right page-record-single-common">
            <span class="aurora-iconfont-common aurora-page-read"></span>&nbsp;
            <span class="page-record-single-desc">总阅读数</span>
						<span class="waline-visitor-count" >3485</span>
            <!--<span>{{$store.state.readCount}}</span>-->
          </div>
        </div>
        <div v-if="tagArr.length !== 0" class="page-record-bot-common page-record-bot">
          <div class="page-record-bot-tag" id="page-record-bot-tag">
            <span class="aurora-iconfont-common aurora-page-tag"></span>&nbsp;
            <router-link v-for="(item,index) in tagArr" :to="goTag(item.uid)">
              <span class="home-page-tag-span page-record-tag-span">{{item.title}}</span>
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

<script lang="ts">
import {readingTime} from 'reading-time-estimator';
import blogConfig from '@/config/blogConfig.json';
import gsap from "gsap";
import {TagVo} from "@/bean/vo/article/TagVo";

const tagArr:Array<TagVo> = []
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
      tagArr,
      topBackgroundUrl: 'https://picture.xcye.xyz/pic/rmimg',
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
    }
  },
  created() {
    // this.$router.beforeEach((to,from,next) => {
    //   next()
    // })
  },
  mounted() {
    let bubbleNumber = 0.15
    let bubbleAlpha = 0.7
    let alphaChangeSpeed = 0.0005
    let size = 0.5
    let sizeChangeSpeed = 0.002
    let riseSpeed = 0.9
    let color = '255,255,255'
    if (blogConfig.bubble !== undefined) {
      if (blogConfig.bubble.show !== undefined) {
      
      }

      if (blogConfig.bubble.bubbleNumber !== undefined) {
        bubbleNumber = blogConfig.bubble.bubbleNumber
      }

      if (blogConfig.bubble.bubbleAlpha !== undefined) {
        bubbleAlpha = blogConfig.bubble.bubbleAlpha
      }

      if (blogConfig.bubble.alphaChangeSpeed !== undefined) {
        alphaChangeSpeed = blogConfig.bubble.alphaChangeSpeed
      }

      if (blogConfig.bubble.size !== undefined) {
        size = blogConfig.bubble.size
      }

      if (blogConfig.bubble.sizeChangeSpeed !== undefined) {
        sizeChangeSpeed = blogConfig.bubble.sizeChangeSpeed
      }

      if (blogConfig.bubble.riseSpeed !== undefined) {
        riseSpeed = blogConfig.bubble.riseSpeed
      }

      if (blogConfig.bubble.color !== undefined) {
        color = blogConfig.bubble.color
      }
    }

    this.$nextTick(() =>{
      if (this.showBubble && this.isShowTopImg) {
        import("@/assets/bubble").then(module => {
          module.bubble(bubbleNumber,bubbleAlpha,alphaChangeSpeed,size,sizeChangeSpeed,riseSpeed,color)
        })
      }
    })
    this.document = document
  },
  computed: {
    animatedContentLength() {
      return this.contentLengthTemp.toFixed(0)
    },
    goTag() {
      return (item: string) => {
        return '/tag?tag=' + item
      }
    },
    setBackgroundUrl() {
      return "background-image: url(https://pic-tool.xcye.xyz/pic/rmimg);"
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
  },
  methods: {
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