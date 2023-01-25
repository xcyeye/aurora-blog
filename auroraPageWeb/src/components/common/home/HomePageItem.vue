<template>
  <aurora-card ref="pageItemTop" class="aurora-card-bottom-page home-page-scroll home-page-tag-item sidebar-single-enter-animate" id="home-page-tag-item">
		<div @click="goRead($event,pageItem)" class="home-page-tag-img">
			<div class="home-page-img-gradual global-common-animate">
				<div class="home-page-gradual-title-par">
					<div class="home-page-top" v-if="true">
						<div>
							<span>置顶</span>
						</div>
					</div>
					<div :class="getGradualClass" class="home-page-gradual-title-item-common">
						<div class="home-page-gradual-item">
							<div class="home-page-gradual-title home-page-gradual-common">
								<router-link :to="`/article/${pageItem.userUid}/${pageItem.uid}`">
									<span>{{getPageItemTitle}}</span>
								</router-link>
							</div>
							<div class="home-page-gradual-other-info home-page-gradual-common">
								<div class="home-page-gradual-other-info-common home-page-gradual-other-time">
									<span>{{pageItem.createTime}}&nbsp;&nbsp;</span>
								</div>
								<div class="home-page-gradual-other-info-common home-page-gradual-other-tag">
									<span :key="index" v-for="(item,index) in getPageTag(pageItem)">{{item}}{{index !== getPageTag(pageItem).length -1? '、': ''}}</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<img id="home-page-img" class="home-page-img" ref="home-page-img"
					 :data-src="getCoverImg"
					 :src="currentSiteInfo.homePageLazyLoadingImg" alt="">
		</div>
	
		<div class="home-page-tag-con global-common-animate">
			<div ref="homePageDom" :class="getPageClass" class="home-page-tag-content">
				<div class="home-page-tag-content-rendered" style="display: block" v-html="pageItem.content"></div>
			</div>
			<!--<div class="home-page-tag-bottom"></div>-->
		</div>
	</aurora-card>
</template>

<script lang="ts" setup>

import {computed, onMounted, ref} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {StringUtil} from "@/utils";
import {useSiteInfo} from "@/stores";
import {useRouterPush} from "@/composables";

interface Props {
	pageItem: ArticleVo,
	showHomePageImg?: boolean,
	index: number,
	userUid: string
}

const props = withDefaults(defineProps<Props>(), {
	showHomePageImg: true
})

const currentSiteInfo = ref<SiteSettingInfo>({})
const useSite = useSiteInfo()
const routerPush = useRouterPush()

const getGradualClass = computed(() => {
	let num = props.index % 2
	if (num === 0) {
		return 'home-page-gradual-title-item-left'
	}else {
		return 'home-page-gradual-title-item-right'
	}
})

const getCoverImg = computed(() => {
	if (StringUtil.haveLength(props.pageItem.coverPictureUrl)) {
		return props.pageItem.coverPictureUrl
	}else if (StringUtil.haveLength(currentSiteInfo.value.defaultCoverRequestInterface)) {
		return  currentSiteInfo.value.defaultCoverRequestInterface
	}else {
		return currentSiteInfo.value.homePageLazyLoadingImg
	}
})

const getPageItemTitle = computed(() => {
	let title = props.pageItem.title
	if (!StringUtil.haveLength(title)) {
		return '暂时还没有标题'
	}
	return title
})

const getPageClass = computed(() => {
	return 'homePageConImg' + props.index
})

const getTime = computed(() => {

})

const handleScroll = () => {
	let clientHeight = document.documentElement.clientHeight
	let homePageScrolls = document.querySelectorAll(".home-page-scroll")
	for (let i = 0; i < homePageScrolls.length; i++) {
		let distance_top = homePageScrolls[i].getBoundingClientRect().top
		if (distance_top < clientHeight) {
			//加载图片
			let elementsByTagName = homePageScrolls[i].getElementsByTagName("img");
			let dataSrc = elementsByTagName[0].getAttribute("data-src") as string;
			elementsByTagName[0].setAttribute("src",dataSrc)
		}
	}
}

const goRead = (e: any, articleInfo: ArticleVo) => {
	routerPush.routerPush({
		path: `/article/${articleInfo.userUid}/${articleInfo.uid}`
	})
}

const getPageTag = computed(() => {
	return (item: ArticleVo) => {
		if (!StringUtil.haveLength(item.tagNames)) {
			return item.categoryNames?.split(",")
		}else {
			return item.tagNames?.split(",")
		}
	}
})

onMounted(() => {
	currentSiteInfo.value = useSite.getSiteInfo(props.userUid)
	
	let h1s = document.querySelectorAll(".home-page-tag-content h1");
	let h2s = document.querySelectorAll(".home-page-tag-content h2");
	let h3s = document.querySelectorAll(".home-page-tag-content h3");
	let h4s = document.querySelectorAll(".home-page-tag-content h4");
	let h5s = document.querySelectorAll(".home-page-tag-content h5");
	
	for (let i = 0; i < 5; i++) {
		let hTag = document.querySelectorAll(".home-page-tag-content h"+ (i + 1));
		for (let j = 0; j < hTag.length; j++) {
			let aElement = hTag[j].getElementsByTagName("a");
			aElement[0].innerText = ""
		}
	}
	
	let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
	new Promise((resolve,rejcet) => {
		for (let i = 0; i < contentHtmlImg.length; i++) {
			contentHtmlImg[i].setAttribute("src","")
		}
		resolve(null)
	}).then(() => {
		let contentHtmlImgs = document.querySelectorAll(".home-page-tag-content img")
		for (let i = 0; i < contentHtmlImgs.length; i++) {
			let nodeParent = contentHtmlImgs[i].parentNode as ParentNode
			nodeParent.removeChild(contentHtmlImgs[i])
		}
	})
	window.addEventListener('scroll', handleScroll, true)
})
</script>
