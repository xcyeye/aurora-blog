<template>
	<div class="home" :style="getHomeHeight">
		<div class="home-hero-img" :class="{'home-hero-img-custom ': isHome}" id="home-hero-img">
			<img :src="useUserInfo().getUserInfo(userUid).avatar"/>
		</div>
		<slot name="home1"></slot>
		<div v-if="randomSawRes" :class="{'home-random-say-custom': isHome}" class="home-random-say">
			<div v-if="isHome">
				<span>「 {{obj.output}}」</span>
			</div>
		</div>
		<slot name="home2"></slot>
		<div :class="{'home-social-custom': isHome}" class="home-social">
			<div :class="{'home-social-custom-single': isHome}">
				<HomeSidebarSocialItem :sidebar-row-pc-var="socialsIsHomeNum + 1"
															 :sidebar-width-pc-var="sidebarWidthPcVar"
															 :sidebar-row-var="socialsIsHomeNum + 1"
															 :show-social-img="true"
															 :sidebar-cut-width="1"
															 :current-site-info="currentSiteInfo"
															 :sidebar-width-var="sidebarWidthVar"
															 :social-item="item"
															 v-for="(item, index) in socialsArrTemp"
															 :key="index"/>
			</div>
		</div>
		<div v-if="isHome" class="home-click-down">
			<span @click="clickDown">
				<svg-icon icon="entypo:chevron-down"/>
			</span>
		</div>
		<div v-if="currentSiteInfo.showWave && isHome" :class="showHomeWaveStyle ? 'home-wave-show': 'home-wave-hide'" class="home-wave-box" ref="home-wave">
			<div class="home-wave">
				<canvas class="home-wave-canvas1" id="home-wave-canvas1"></canvas>
				<canvas class="home-wave-canvas2" id="home-wave-canvas2"></canvas>
			</div>
		</div>
		<slot name="home3"></slot>
	</div>
</template>
<script lang="ts">
import {defineComponent} from 'vue';
import EasyTyper from "easy-typer-js";
import smoothscroll from 'smoothscroll-polyfill';

import {req} from "@/assets/network";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {useSiteInfo, useUserInfo} from "@/stores";

const siteSettingInfoTemp: SiteSettingInfo = {}
const currentUserInfo: UserVo = {}
const currentSiteInfo: SiteSettingInfo = {}
const socialsArrTemp: Array<SocialInfo> = []
const useUser = useUserInfo()
const useSite = useSiteInfo()

export default defineComponent({
	name: 'index',
	data() {
		return {
			currentUserInfo: currentUserInfo,
			currentSiteInfo: currentSiteInfo,
			siteSettingInfoTemp,
			socialsArrTemp: socialsArrTemp,
			networkOption: {
				baseURL: 'https://v1.hitokoto.cn/?encode=text&c=j',
				timeout: 5000,
				method: 'GET',
				query: '',
			},
			randomSawRes: '',
			obj: {
				output: '',
				isEnd: false,
				speed: 80,
				singleBack: false,
				sleep: 1700,
				type: 'rollback',
				backSpeed: 70,
				sentencePause: false
			},
			intervalTime: 1500,
			socialsIsHomeNum: 0,
			sidebarWidthVar: 0.92,
			sidebarRowVar: 0,
			showHomeWaveStyle: false,
		}
	},
	props: {
		sidebarWidthPcVar: {
			type: String,
			default() {
				return "88vw"
			}
		},
		homeHeightVar: {
			type: String,
			default() {
				return "100vh"
			}
		},
		navbarStyle: {
			type: String,
			default() {
				return ""
			}
		},
		isHome: {
			type: Boolean,
			default() {
				return false
			}
		},
		userUid: {
			type: String
		}
	},
	created() {
		//如果手机端侧边栏打开的，那么就关闭
		if (this.$store.state.openMobileSidebar) {
			this.$store.commit("setOpenMobileSidebar",{
				openMobileSidebar: false
			})
		}
	},
	mounted() {
		window.addEventListener('scroll', this.handleScroll, true)
		
		this.currentUserInfo = useUser.getUserInfo(this.userUid)
		this.currentSiteInfo = useSite.getSiteInfo(this.userUid)
		
		if (this.currentSiteInfo.randomSayApi !== undefined) {
			this.networkOption.baseURL = this.currentSiteInfo.randomSayApi.urlApi
			
			if (this.currentSiteInfo.randomSayApi.method !== undefined) {
				this.networkOption.method = this.currentSiteInfo.randomSayApi.method
			}
		}
		this.loadSocialInfo()
		this.fetchRandomSayData()
		
		this.showHomeWaveStyle = window.pageYOffset <= 0;
		this.$nextTick(() => {
			if (this.currentSiteInfo.showWave && this.isHome) {
				import("@/assets/wave").then(module => {
					module.wave()
				})
				
			}
		})
		
		// this.socialsArrTemp = []
		// new Promise((resolve,reject) => {
		// 	if (document.body.clientWidth < 719) {
		// 		this.sidebarWidthVar = 0.92
		// 		this.sidebarRowVar = this.socialsIsHomeNum + 1
		//
		// 		if (this.socialsArr.length > 7) {
		// 			for (let i = 0; i < 7; i++) {
		// 				this.socialsArrTemp.push(this.socialsArr[i])
		// 			}
		// 		}else {
		// 			this.socialsArrTemp = this.socialsArr
		// 		}
		// 	}else {
		// 		//可视区域宽度大于719
		// 		if (this.siteSettingInfo.socialsArr && this.siteSettingInfo.socialsArr.length > 19) {
		// 			for (let i = 0; i < 19; i++) {
		// 				this.socialsArrTemp.push(this.socialsArr[i])
		// 			}
		// 		}else {
		// 			if (this.siteSettingInfo.socialsArr) {
		// 				this.socialsArrTemp = this.siteSettingInfo.socialsArr
		// 			}
		// 		}
		// 	}
		// 	resolve(null)
		// }).then(() => {
		// 	this.socialsIsHomeNum = this.socialsArrTemp.length
		// })
	},
	methods: {
		useUserInfo,
		loadSocialInfo(): void {
			this.socialsArrTemp = []
			if (this.currentSiteInfo.socialsArr) {
				const socials = this.currentSiteInfo.socialsArr
				for (let i = 0; i < socials.length; i++) {
					if (socials[i].isHome && socials[i].show) {
						this.socialsArrTemp.push(socials[i])
						// this.socialsIsHomeNum ++
					}
				}
				this.socialsIsHomeNum = this.currentSiteInfo.socialsArr.length
			}
		},
		clickDown() {
			smoothscroll.polyfill();
			document.getElementById('home-bottom')!.scrollIntoView({behavior: "smooth", block: 'start'})
		},
		
		handleScroll() {
			this.showHomeWaveStyle = window.pageYOffset <= 0;
		},
		getRandomInt(min: number, max: number) {
			min = Math.ceil(min);
			max = Math.floor(max);
			return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
		},
		fetchRandomSayData() {
			if (!this.isHome) {
				if (!this.showRandomSay) {
					return
				}
			}
			
			if (this.currentSiteInfo.customRandomSay && this.currentSiteInfo.customRandomSay.show) {
				this.randomSawRes = this.currentSiteInfo.customRandomSay.value === undefined ||
				this.currentSiteInfo.customRandomSay.value == null ? "Aurora blog System" : this.siteSettingInfo.customRandomSay.value
				this.initTyped(this.randomSawRes,() => {
					setTimeout(() => {
						this.fetchRandomSayData()
					},this.intervalTime)
				})
			}else {
				req(this.networkOption).then(res => {
					try {
						this.randomSawRes = res;
						this.initTyped(this.randomSawRes,() => {
							setTimeout(() => {
								this.fetchRandomSayData()
							},this.intervalTime)
						})
					}catch (e) {
						this.initTyped('Aurora',() => {
							setTimeout(() => {
								this.fetchRandomSayData()
							},this.intervalTime)
						})
					}
				})
			}
		},
		initTyped(input: string, fn: Function, hooks: Function) {
			const obj = this.obj
			return new EasyTyper(obj, input, fn, hooks)
		}
	},
	computed: {
		getHomeSocial() {
			if (this.isHome) {
				return 'home-social-custom'
			}
		},
		getHomeHeight() {
			return '--homeHeight: ' + this.homeHeightVar + ";"
		}
	},
	watch: {
		siteSettingInfo() {
			this.loadSocialInfo()
		}
	}
})
</script>
