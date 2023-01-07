<template>
	<div class="aurora-coze-slide-body" :style="slideBodyBg">
		<div class="coze-image-container" id="coze-image-container"></div>
		
		<div class="aurora-coze-slide-shade aurora-coze-slide-radius">
			<div class="aurora-coze-slide-box-style-box">
				<div class="aurora-coze-slide-box-style"></div>
			</div>
			<swiper
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
				<swiper-slide v-slot="{ isActive }" v-for="(item,index) in talkArr" :key="index" :style="setSlideItemStyle(index)" class="aurora-coze-slide-item aurora-coze-slide-radius">
					<slide-coze-mood-item @mood-comment="moodComment" @mood-love="moodLove" @mood-poster="moodPoster"
																@mood-edit="moodEdit" @set-slide-bodyBg="setSlideBodyBg" :user-uid="userUid" :data="updateBgStyle(isActive,item)" :mood-item="item" :is-active="isActive" />
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

const talkArr: Array<TalkVo> = []
const routerPush = useRouterPush()

export default defineComponent({
	name: "AuroraCozeMood",
	data() {
		return {
			userUid: '',
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
		if (!StringUtil.haveLength(this.userUid)) {
			routerPush.routerPush({
				name: 'home'
			})
		}
		this.loadTalkData()
	},
	methods: {
		loadTalkData() {
			talkApi.queryListDataByCondition({delete: false, show: true, otherUid: this.userUid,pageSize: 300}).then(result => {
				if (result.data && result.data.result) {
					this.talkArr = result.data.result
				}
			})
		},
		setSlideBodyBg(photoUrl: string) {
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
				if(StringUtil.haveLength(moodItem.pictureUids)) {
					const pictureUidArr: Array<string> = moodItem.pictureUids?.split(",")!
					for (let i = 0; i < pictureUidArr.length; i++) {
						fileApi.queryOneDataByUid({uid: pictureUidArr[i]}).then(result => {
							if (result.data && StringUtil.haveLength(result.data.path)) {
								//将图片设置为背景图片
								this.slideBodyBg = "--aurora-coze-slide-bgImg: url(" + result.data.path + ");"
								return
							}
						})
					}
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
	},
	mounted() {

	}
	
});
</script>