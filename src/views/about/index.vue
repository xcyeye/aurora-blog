<template>
	<div class="aurora-slide-body" :style="slideBodyBg">
		<div class="aurora-slide-shade aurora-slide-radius">
			<div class="aurora-slide-box-style-box">
				<div class="aurora-slide-box-style"></div>
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
				class="aurora-slide-box aurora-slide-radius"
			>
				<AuroraBubble v-if="showAboutPageBubble"/>
				<swiper-slide v-slot="{ isActive }" v-for="(item,index) in abouts" :key="index" :style="setSlideItemStyle(index)" class="aurora-slide-item aurora-slide-radius">
					<div :data="updateBgStyle(isActive,item.bgImg,index)" class="aurora-slide-item-son">
						<div class="aurora-slide-item-top aurora-slide-radius">
							<div class="aurora-slide-item-top-left aurora-slide-item-top-avatar">
								<img :src="useUserInfo().getUserInfo(userUid).avatar" @click="goBackPage" alt="">
							</div>
							<div class="aurora-slide-item-top-right">
								<div class="aurora-slide-item-top-title aurora-slide-item-top-common">
									<span>{{item.title}}</span>
								</div>
								<div v-if="item.tag" class="aurora-slide-item-top-tag aurora-slide-item-top-common">
									<span v-for="(tagItem,index) in item.tag" :key="index">{{tagItem}}</span>
								</div>
							</div>
						</div>
						
						<div class="aurora-slide-item-bottom">
							<!--              <div class="aurora-slide-item-bottom-box">-->
							<!--                -->
							<!--              </div>-->
							<div v-if="item.bar" class="aurora-slide-item-desc-common aurora-slide-item-desc-bar">
								<li class="aurora-slide-item-font-common aurora-slide-item-bar-li" v-for="(descItem,index) in item.describe" :key="index">
									<span class="about-bar-title aurora-slide-item-bar-title">{{descItem.name}}</span>
									<span class="aurora-slide-item-bar-score" :style="setBarStyle(descItem.score)">{{descItem.score}}%</span>
								</li>
							</div>
							<div v-else class="aurora-slide-item-desc-text aurora-slide-item-desc-common">
								<li class="aurora-slide-item-font-common" v-for="(descItem,index) in item.describe" :key="index" v-html="descItem"></li>
							</div>
						</div>
					</div>
				</swiper-slide>
			</swiper>
		</div>
	</div>
</template>
<script lang="ts">
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";

// Import Swiper styles
import "swiper/css";

import "swiper/css/effect-creative";

// import required modules
import { EffectCreative } from "swiper";
import {getRandomNum, StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";
import blogConfig from '@/config/blogConfig.json'
import {useSiteInfo, useUserInfo} from "@/stores";

const routerPush = useRouterPush()
const currentSiteInfo: SiteSettingInfo = {}
export default {
	name: "About",
	data() {
		return {
			userUid: '',
			currentSiteInfo,
			abouts: blogConfig.about,
			hexRgb: '',
			slideBodyBg: '',
			showAboutPageBubble: true
		}
	},
	components: {
		Swiper,
		SwiperSlide,
	},
	setup() {
		return {
			modules: [EffectCreative],
		};
	},
	computed: {
		setSlideBodyBg() {
		
		},
		setSlideItemStyle() {
			//background-image: linear-gradient(to right top, #fff1eb 0%, #ace0f9 100%);
			return (index: number) => {
			
			}
		},
		setBarStyle() {
			return (score: number) => {
				return "background-color:" + blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)] + "; width: " + score * 0.85 + "%"
			}
		}
	},
	created() {
		this.userUid = this.$route.params.userUid as string
		if (!StringUtil.haveLength(this.userUid)) {
			routerPush.routerPush({
				name: 'home'
			})
		}
		this.currentSiteInfo = useSiteInfo().getSiteInfo(this.userUid)
		this.showAboutPageBubble = this.currentSiteInfo.showAboutPageBubble
		
	},
	methods: {
		useUserInfo,
		//点击头像回到上一步网页
		goBackPage() {
			this.$router.go(-1)
		},
		getInearGradientStyle() {
			//let hexRgb1 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
			//let hexRgb2 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
			return "--aurora-slide-bgImg: linear-gradient(to right top, " + blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)] + " 0%, "+ blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)] +" 100%);"
		},
		updateBgStyle(isActive: boolean, bgImg: string , index: number) {
			if(isActive) {
				if(bgImg !== undefined) {
					//将图片设置为背景图片
					this.slideBodyBg = "--aurora-slide-bgImg: url(" + bgImg + ");"
				}else {
					//如果没有图片，那么则使用渐变颜色作为背景颜色
					this.slideBodyBg = this.getInearGradientStyle()
				}
			}
			
		}
	},
};
</script>