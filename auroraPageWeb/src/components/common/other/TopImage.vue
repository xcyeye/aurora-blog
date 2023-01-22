<template>
  <div class="sidebar-single-enter-animate page-top" v-if="isShowTopImg" id="page-top">
    <div class="top-mask" :style="setBackgroundUrl"></div>

    <div v-if="showBubble" class="aurora-top-bubble">
      <!--<div class="aurora-top-bubble-par">-->
      <!--  <div class="aurora-top-bubble-box" id="aurora-top-bubble-box"></div>-->
      <!--  <canvas class="aurora-top-bubble-canvas" id="aurora-top-bubble-canvas"></canvas>-->
      <!--</div>-->
			<aurora-bubble/>
    </div>
		
		<div class="aurora-top-image-public-time" v-if="isShowHeadLine">
			<span>{{getPublicTime}}</span>
			<svg-icon icon="bi:hourglass-split"/>
		</div>
		
		<div class="aurora-top-image-article-info" v-if="isShowHeadLine">
			<div class="aurora-top-image-article-title">
				<h1>{{getHeadLine}}</h1>
			</div>
			<div class="aurora-top-image-article-summary">{{getSummary}}</div>
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
import {getRandomTagType, parseTime, StringUtil} from "@/utils";
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
		getPublicTime() {
			let createTime = this.articleInfo ? this.articleInfo.createTime : this.tagOrCategory.createTime
			const createData: Date = parseTime(createTime)
			const timeNumber = new Date().getTime() - createData.getTime()
			let day: number = Number.parseInt((timeNumber / 1000 / 60 / 60 / 24).toFixed(0))
			if (day < 1) {
				return 'today'
			}
			return day + ' days ago'
		},
		getHeadLine() {
			if (StringUtil.haveLength(this.headLine)) {
				return this.headLine
			}else if (this.articleInfo) {
				return this.articleInfo.title
			}else if (this.tagOrCategory) {
				return this.tagOrCategory.title
			}
		},
		getSummary() {
			if (this.articleInfo) {
				return this.articleInfo.summary
			}else if (this.tagOrCategory) {
				return this.tagOrCategory.summary
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