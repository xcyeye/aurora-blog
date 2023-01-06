<template>
	<div class="common"
			 :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle">
		<!--<mobile-sidebar/>-->
		<!--<social-spin/>-->
		<slot name="top1"></slot>
		<slot name="top2"></slot>
		<slot name="top3"></slot>
		<slot name="top4"></slot>
		<slot name="top5"></slot>
		<slot name="top6"></slot>
		<div
			class="theme-container"
			@touchstart="onTouchStart"
			@touchend="onTouchEnd"
			:style="colorFontStyle"
			:class="{'sidebar-single-enter-animate': showSidebarAnimateClass}"
		>
			<div class="page-sidebar">
				<top-image :is-show-top-img="isShowTopImg"
									 :is-show-head-line="isShowHeadLine"
									 :show-mood-edit="showMoodEdit"
									 :head-line="headLine">
				</top-image>
				
				<div :class="{'content': !isHomePage}">
					<div :id="getArticleId" :class="{'article-page-parent-pro': !isHomePage}" class="article-page-parent">
						<div :class="{noShowSidebar: getNoShowSidebar}" id="page-sidebar-left"
								 class="page-sidebar-left">
							<slot name="center1"></slot>
							<slot name="center2"></slot>
							<slot name="center3"></slot>
							<slot name="center4"></slot>
							<slot name="center5"></slot>
							<slot name="center6"></slot>
							<slot name="center7"></slot>
							<slot name="center8"></slot>
							<slot name="center9"></slot>
						</div>
						<div id="page-sidebar-right" v-show="showSidebar" class="page-sidebar-right">
							<div class="stickSidebar" v-if="mobilePageSidebar">
								<HomeSidebar :show-navbar="false"
														 :sidebar-width-var="0.92"
														 :show-sidebar-social="true"
														 :show-sidebar-link="showSidebarLink"
														 :sidebar-row-var="sidebarRowVar"
														 :is-sticky-sidebar="isStickySidebar"
														 :show-tag-cloud="showTagCloud"
														 :is-show-catalog="isShowCatalog">
								</HomeSidebar>
							</div>
						</div>
					</div>
				</div>
			</div>
			<slot name="bottom1"></slot>
			<slot name="bottom2"></slot>
			<slot name="bottom3"></slot>
			<slot name="bottom4"></slot>
			<Footer :theme-property="themeProperty"
							:is-show-footer="isShowFooter">
			</Footer>
		</div>
		<div id="set-bg" class="set-bg-fitter"
				 :style="'--opacity: ' + $store.state.varOpacity +
         '; --borderRadius: ' + $store.state.varBorderRadius +
         'px; --backgroundImageUrl: url(' + $store.state.homeWps + ')'"
		></div>
		<div id="posterShade" :class="{posterShade: $store.state.showPosterShadow}">
			<span :class="{iconSpinner6: $store.state.showShadeLoad}"></span>
		</div>
	</div>
</template>
<script lang="ts" setup>

//组件导入
//配置导入
import {computed, defineComponent, onBeforeMount, onMounted, reactive, ref, Transition,} from 'vue';
import {blogPageData} from "@/assets/config";
import {getRandomNum} from "@/utils";
import blogConfig from '@/config/blogConfig.json'
import {useSiteInfo} from "@/stores";

interface Props {
	showSidebarAnimateClass?: boolean,
	isHomePage?: boolean,
	showSidebarLink?: boolean,
	isStickySidebar?: boolean,
	isShowCatalog?: boolean,
	showTagCloud?: boolean,
	showSidebar?: boolean,
	showMoodEdit?: boolean,
	isShowTopImg?: boolean,
	isShowHeadLine?: boolean,
	headLine?: string,
	isShowSideBar?: boolean,
	isShowFooter?: boolean,
	userUid: string
}

const props = withDefaults(defineProps<Props>(), {
	showSidebarAnimateClass: true,
	isHomePage: false,
	showSidebarLink: true,
	isStickySidebar: false,
	isShowCatalog: false,
	showTagCloud: true,
	showSidebar: true,
	showMoodEdit: false,
	isShowTopImg: false,
	isShowHeadLine: false,
	headLine: '',
	isShowSideBar: true,
	isShowFooter: true
})

const showHeaderBg = ref(true)
const sidebarRowVar = ref(5)
const obj = ref({
	output: '',
	isEnd: false,
	speed: 80,
	singleBack: false,
	sleep: 1700,
	type: 'normal',
	backSpeed: 70,
	sentencePause: false
})
const colorStyle = ref('')
const fontStyle = ref('')
const colorFontStyle = ref('')
const isFitter = ref(false)
const mobilePageSidebar = ref(true)
const pageYOffset = ref(0)
const width = ref(0)
const currentSiteInfo = ref<SiteSettingInfo>({})
const useSite = useSiteInfo()

const setHomeBg = () => {
	// TODO 后期再做
}

const handleScroll = () => {
	showHeaderBg.value = window.pageYOffset <= pageYOffset.value;
	pageYOffset.value = window.pageYOffset
}

const setBodyWallpaper = () => {
	// TODO 后期再做
}

const getArticleId = computed(() => {
	if (props.isHomePage) {
		return 'article-page-parent'
	}else {
		//如果是首页，并且不是手机端
		if (width.value > 719) {
			return 'article-page-parent'
		}else {
			return 'article-page-parent-mobile'
		}
	}
})

const getNoShowSidebar = computed(() => {
	// showSidebar
	if (props.isHomePage) {
		return false
	}else {
		return props.showSidebar
	}
})

const getIndex = computed(() => {
	return (index: number,length: number)=> {
		if (index === 0 && length === 1) {
			return ""
		}
		return index+1 + ". "
	}
})

const setSpanStyle = computed(() => {
	return (score: number) => {
		let newScore = score * 0.8
		let background_color = blogConfig.randomColor[
			getRandomNum(0,blogConfig.randomColor.length -1)]
		return 'width: '+ newScore + "%;" + "background-color: "+background_color + ";"
	}
})

onBeforeMount(() => {
	currentSiteInfo.value = useSite.getSiteInfo(props.userUid)
})

onMounted(() =>{
	width.value = document.body.clientWidth
	
	if (document.documentElement.clientWidth < 719) {
		sidebarRowVar.value = 6
	}
	
	// 滚动条的获取
	window.addEventListener('scroll', handleScroll, true)
	
	//手机端壁纸
	let screen = document.body.clientWidth
	if (screen < 500) {
		// TODO 这里要设置手机端壁纸
		// setHomeBg()
	}
	
	if (document.body.clientWidth < 550 && currentSiteInfo.value.mobilePageSidebar !== undefined) {
		mobilePageSidebar.value = currentSiteInfo.value.mobilePageSidebar
	}
})
</script>