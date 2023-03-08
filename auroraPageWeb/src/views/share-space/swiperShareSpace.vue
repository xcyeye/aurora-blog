<template>
	<div class="aurora-coze-slide-body" :style="slideBodyBg">
		<aurora-bubble/>
		<div class="coze-image-container" id="coze-image-container"></div>
		
		<div class="aurora-coze-slide-shade aurora-coze-slide-radius">
			<div class="aurora-coze-slide-box-style-box">
				<div class="aurora-coze-slide-box-style"></div>
			</div>
			<swiper
				@swiper="onSwiper"
				:grabCursor="true"
				:effect="'creative'"
				:creativeEffect="{
      prev: {
        shadow: false,
        translate: ['-120%', 0, -500],
      },
      next: {
        shadow: false,
        translate: ['120%', 0, -500],
      },
    }"
				:modules="modules"
				class="aurora-coze-slide-box aurora-coze-slide-radius"
			>
				<swiper-slide v-slot="{ isActive }" v-for="(item,index) in talkArr" :key="index" :style="setSlideItemStyle(index)"
											class="aurora-coze-slide-item aurora-coze-slide-radius">
					<slide-coze-mood-item @mood-comment="moodComment" @mood-love="moodLove" @mood-poster="moodPoster"
																:talk-uid="talkUid" @mood-edit="moodEdit" @set-slide-bodyBg="setSlideBodyBg"
																:user-uid="userUid" :mood-item="item" :is-active="isActive" :dataBg="updateBgStyle(isActive,item)" />
				</swiper-slide>
			</swiper>
		</div>
	</div>
</template>
<script lang="ts">
// Import Swiper Vue.js components
import {Swiper, SwiperSlide} from "swiper/vue";

// Import Swiper styles
import "swiper/css";
import "swiper/css/effect-creative";

// import required modules
import {EffectCreative} from "swiper";

import blogConfig from '@/config/blogConfig.json';
import gsap from "gsap";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import {getRandomNum, StringUtil} from "~/src/utils";
import {useRouterPush} from "@/composables";
import {fileApi, talkApi} from "@/service";
import {defineComponent} from "vue";
import {Swiper as SwiperClass} from "swiper/types";

const talkArr: Array<TalkVo> = []
const routerPush = useRouterPush()
// @ts-ignore
const swiperInstance:SwiperClass = {}
export default defineComponent({
	name: "AuroraCozeMood",
	data() {
		return {
			swiperInstance,
			userUid: '',
			talkUid: '',
			talkArr,
			abouts: [],
			hexRgb: '',
			slideBodyBg: '',
			showAboutPageBubble: true,
			//下面的是原本的CozeMood组件数据
			//这是一个数组对象
			color: '',
			showMoodEdit: true,
			openEditStatus: false,
			num: 1,
			currentMoodObj: {},
			showMoodControl: false
		}
	},
	created() {
		this.userUid = this.$route.params.userUid as string
		this.talkUid = this.$route.params.talkUid as string
		if (!StringUtil.haveLength(this.userUid)) {
			routerPush.routerPush({
				name: 'home'
			})
		}
		this.loadTalkData()
	},
	methods: {
		onSwiper(swiper: SwiperClass) {
			this.swiperInstance = swiper
		},
		loadTalkData() {
			talkApi.queryListDataByCondition({delete: false, show: true, otherUid: this.userUid,pageSize: 300}).then(result => {
				if (result.data && result.data.result) {
					this.talkArr = result.data.result
					if (StringUtil.haveLength(this.talkUid)) {
						this.talkArr.forEach((v: TalkVo, index: number) => {
							if (v.uid === this.talkUid) {
								this.swiperInstance.slideTo(index)
							}
						})
					}
				}
			})
		},
		setSlideBodyBg(photoUrl: {photoUrl: string}) {
			this.slideBodyBg = "--aurora-coze-slide-bgImg: url(" + photoUrl.photoUrl + ");"
		},
		moodComment(moodItem) {
		
		},
		moodLove(moodItem) {
		
		},
		moodPoster(moodItem) {
		
		},
		moodEdit(moodItem) {
			this.currentMoodObj = moodItem
			this.openEditStatus = !this.openEditStatus
			if (this.verifyIdentifyStatus) {
				//密码验证成功，直接显示
				this.showMoodControl = true
			}else {
				this.showMoodControl = true
			}
			this.$emit("moodEdit",{
				openEditStatus: this.openEditStatus
			})
		},
		
		//点击头像回到上一步网页
		goBackPage() {
			this.$router.go(-1)
		},
		getInearGradientStyle() {
			//let hexRgb1 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
			//let hexRgb2 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
			return "--aurora-coze-slide-bgImg: linear-gradient(to right top, " + blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)] + " 0%, "+ blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)] +" 100%);"
		},
		updateBgStyle(isActive: boolean, moodItem: TalkVo) {
			if(isActive) {
				if(StringUtil.haveLength(moodItem.pictureSrcList)) {
					const pictureSrcArr: Array<string> = moodItem.pictureSrcList?.split(",")!
					this.slideBodyBg = "--aurora-coze-slide-bgImg: url(" + pictureSrcArr[0] + ");"
				}else {
					//如果没有图片，那么则使用渐变颜色作为背景颜色
					this.slideBodyBg = this.getInearGradientStyle()
				}
			}
		}
	},
	components: {
		Swiper,
		SwiperSlide,
	},
	setup() {
		return {
			modules: [EffectCreative]
		};
	},
	computed: {
		getBgStyle() {
			return (moodItem: TalkVo) => {
				console.log(moodItem);
			}
		},
		getUpdatedTime() {
			let updatedAt = this.moodItem.createdAt;
			let day = new Date(updatedAt).getDate();
			let month = new Date(updatedAt).getMonth() + 1;
			let year = new Date(updatedAt).getFullYear();
			gsap.to(this.$data, {duration: 1.1, cozeYearTemp:year, ease: 'sine'})
			gsap.to(this.$data, {duration: 2, cozeMonthTemp: month, ease: 'sine'})
			gsap.to(this.$data, {duration: 2, cozeDayTemp: day, ease: 'sine'})
		},
		
		setSlideItemStyle() {
			//background-image: linear-gradient(to right top, #fff1eb 0%, #ace0f9 100%);
			return (index) => {
			
			}
		},
		setBarStyle() {
			return (score) => {
				return "background-color:" + blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)] + "; width: " + score * 0.85 + "%"
			}
		}
	}
	
});
</script>