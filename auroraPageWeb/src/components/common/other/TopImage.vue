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
      <div class="page-top-record" id="page-top-record" v-if="articleInfo">
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
						<span class="waline-comment-count" >{{articleCommentTotal}}</span>
            <!--<span>{{$store.state.commentCount}}</span>-->
          </div>
          <div class="page-record-center-right page-record-single-common">
            <span class="aurora-iconfont-common aurora-page-read"></span>&nbsp;
            <span class="page-record-single-desc">总阅读数</span>
						<span class="waline-visitor-count" >{{articleInfo.readNumber ? articleInfo.readNumber : '0'}}</span>
            <!--<span>{{$store.state.readCount}}</span>-->
          </div>
        </div>
        <!--<div v-if="tagArr.length !== 0" class="page-record-bot-common page-record-bot">-->
        <!--  <div class="page-record-bot-tag" id="page-record-bot-tag">-->
        <!--    <span class="aurora-iconfont-common aurora-page-tag"></span>&nbsp;-->
        <!--    <router-link v-for="(item,index) in tagArr" :to="goTag(item.uid)">-->
        <!--      &lt;!&ndash;<span class="home-page-tag-span page-record-tag-span">{{item.title}}</span>&ndash;&gt;-->
				<!--			<n-tag :bordered="false" style="border-radius: 16px" :type="getRandomTagType()">{{item.title}}</n-tag>-->
        <!--    </router-link>-->
        <!--  </div>-->
        <!--</div>-->
      </div>
    </div>
    <div class="top-image" id="top-image" v-if="isShowHeadLine">
      <h1>{{getHeadLine}}</h1>
    </div>
  </div>
</template>

<script lang="ts">
import {readingTime} from 'reading-time-estimator';
import blogConfig from '@/config/blogConfig.json';
import gsap from "gsap";
import {TagVo} from "@/bean/vo/article/TagVo";
import {PropType} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {getRandomTagType, StringUtil} from "@/utils";
import {commentApi} from "@/service";
import {CategoryVo} from "@/bean/vo/article/CategoryVo";

const tagArr:Array<TagVo> = []
export default {
  name: "TopImage",
  data() {
    return {
			articleCommentTotal: 0,
      totalWordObj: {
        minutes: '',
        text: '',
        words: 0
      },
      animeImg: '',
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
		backgroundImage: {
			type: String
		},
		tagOrCategory: {
			type: Object as PropType<TagVo> | Object as PropType<CategoryVo>
		},
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
      type: String
    },
		articleInfo: {
			type: Object as PropType<ArticleVo>
		},
		currentSiteInfo: {
			type: Object as PropType<SiteSettingInfo>
		}
  },
  created() {
		
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
		getHeadLine() {
			if (StringUtil.haveLength(this.headLine)) {
				return this.headLine
			}else if (this.articleInfo) {
				return this.articleInfo.title
			}else if (this.tagOrCategory) {
				return this.tagOrCategory.title
			}
		},
    animatedContentLength() {
      return this.contentLengthTemp.toFixed(0)
    },
    goTag() {
      return (item: string) => {
        return '/tag?tag=' + item
      }
    },
    setBackgroundUrl() {
			if (StringUtil.haveLength(this.backgroundImage)) {
				return `background-image: url(${this.backgroundImage});`
			}else if (this.articleInfo && StringUtil.haveLength(this.articleInfo.coverPictureUrl)) {
				return `background-image: url(${this.articleInfo.coverPictureUrl});`
			}else if (this.tagOrCategory && StringUtil.haveLength(this.tagOrCategory.coverUrl)) {
				return `background-image: url(${this.tagOrCategory.coverUrl});`
			}else if (StringUtil.haveLength(this.currentSiteInfo.randomPictureInterface)) {
				if (/.*&.*=.*/.test(this.currentSiteInfo.randomPictureInterface)) {
					return `background-image: url(${this.currentSiteInfo.randomPictureInterface}&aurora_time=${new Date().getTime()});`
				}else if (/.*?.*=.*/.test(this.currentSiteInfo.randomPictureInterface)) {
					return `background-image: url(${this.currentSiteInfo.randomPictureInterface}&aurora_time=${new Date().getTime()});`
				}
				return `background-image: url(${this.currentSiteInfo.randomPictureInterface}?aurora_time=${new Date().getTime()});`
			}
      return `background-image: url(https://pic-tool.xcye.xyz/pic/rmimg?aurora_time=${new Date().getTime()});`
    },
    getSugTime() {
      return this.sugReadTimeTemp.toFixed(0)
    },
  },
  methods: {
		getRandomTagType
		
  },
  watch: {
		articleInfo(nv: ArticleVo, ov: ArticleVo) {
			this.tagArr = nv.tagNames?.split(",").map(v => {
				const tag: TagVo = {
					title: v
				}
				return tag
			}).concat()
			const totalWordObj = readingTime(nv.content!, this.sugCountPerMin);
			this.totalWordObj = totalWordObj
			this.contentLength = totalWordObj.words
			gsap.to(this.$data, { duration: 0.5, contentLengthTemp: totalWordObj.words })
			let sugReadTime = Math.floor(this.contentLength / this.sugCountPerMin) === 0 ? 1 : Math.ceil(this.contentLength / this.sugCountPerMin)
			gsap.to(this.$data, { duration: 0.5, sugReadTimeTemp: sugReadTime })
			if (this.articleInfo) {
				// 查询总评论数
				commentApi.queryListDataByCondition({keyword:`/article/${this.articleInfo.uid}`}).then(result => {
					if (result.data) {
						this.articleCommentTotal = result.data.total
					}
				})
			}
		}
  }
}
</script>

<style scoped>

</style>