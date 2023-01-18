<template>
	<aurora-common :is-sticky-sidebar="true" :is-show-side-bar="true"
								 :show-sidebar-link="true" :user-uid="userUid"
								 :article-info="articleInfo"
								 :show-navbar="true"
								 :is-article-page="true"
								 :is-home-page="false"
								 :is-show-top-img="true"
								 :is-show-head-line="true">
		<template #topImageSlot>
			<div ref="articleTopScroll" style="width: 0; height: 0"></div>
		</template>
		<template #center1>
			<main :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
						class="page sidebar-single-enter-animate blog-article" id="article-page">
				
				<div id="theme-default-content" class="hide-h1-tag theme-default-content pageContent">
					<!--TODO 分享这块暂时不做-->
					<!--<div class="page-top-share">-->
					<!--	<div class="page-top-share-next">-->
					<!--		&lt;!&ndash;<poster :title="originPageData.title" :content="posterContent"/>&ndash;&gt;-->
					<!--	</div>-->
					<!--</div>-->
					<div v-html="articleContent"></div>
					<!--<render-markdown :markdown-content="articleInfo.content"/>-->
				</div>
				
				<!--<div class="aurora-article-like">-->
				<!--	<div class="aurora-article-like-heart" @click="handleClickArticleLike">-->
				<!--		<div class="aurora-article-like-heart-svg" :class="isClickLikeBut ? 'aurora-article-like-heart-active' : ''">-->
				<!--			<svg-icon icon="bi:suit-heart-fill"/>-->
				<!--		</div>-->
				<!--	</div>-->
				<!--	<div class="aurora-article-like-heart-num">{{articleInfo.likeNumber}}</div>-->
				<!--</div>-->
				<div class="aurora-article-like">
					<give-like :like-number="articleInfo.likeNumber"
										 :control-like-number="false"
										 @finishGiveLikeAction="finishGiveLikeAction"
										 :give-like-info="articleInfo"
										 cookie-name="article_give_like"
										 :show-like-num="true"
										 :update-like-num-request-method="updateLikeNumMethod"
										 :multi-click-give-like="false"/>
				</div>
				
				<div class="aurora-article-tag" v-if="getArticleTag">
					<svg-icon icon="bi:tag-fill"/> <n-tag class="aurora-article-tag-single" v-for="(item, index) in getArticleTag" :type="getRandomTagType()" :bordered="false" style="border-radius: 16px" :key="index">{{item}}</n-tag>
				</div>
				
				<div class="aurora-article-tag" v-if="getArticleCategory">
					<svg-icon icon="material-symbols:content-copy-rounded"/> <n-tag class="aurora-article-tag-single" v-for="(item, index) in getArticleCategory" :type="getRandomTagType()" :bordered="false" style="border-radius: 16px" :key="index">{{item}}</n-tag>
				</div>
				
				<div class="aurora-article-next">
					<div class="aurora-article-next-previous aurora-article-next-common">
						<span @click="handleGoArticlePreviousAndNext(true)">前一篇</span>
					</div>
					<div class="aurora-article-next-next aurora-article-next-common">
						<span @click="handleGoArticlePreviousAndNext(false)">下一篇</span>
					</div>
				</div>
				
				<div class="aurora-article-info">
					<div class="aurora-article-info-author">
						<svg-icon icon="material-symbols:copyright"/>
						<span>版权归属: {{useUserInfo().getUserInfo(userUid).username}}</span>
					</div>
					<div class="aurora-article-info-author">
						<svg-icon icon="ri:link-m"/>
						<span>本文链接: </span>
						<span @click="copyContent(`${getHost()}/article/${articleInfo.userUid}/${articleInfo.uid}`, false)">{{`${getHost()}/article/${articleInfo.userUid}/${articleInfo.uid}`}}</span>
					</div>
					<div class="aurora-article-info-author">
						<svg-icon icon="ri:time-fill"/>
						<span>发布时间: {{articleInfo.createTime}}</span>
					</div>
					<div class="aurora-article-info-author">
						<svg-icon icon="mingcute:time-line"/>
						<span>更新时间: {{articleInfo.updateTime}}</span>
					</div>
					<div class="aurora-article-info-author">
						<svg-icon icon="bi:share"/>
						<span>许可协议: 本文使用《署名-非商业性使用-相同方式共享4.0国际(CC BY-NC-SA 4.0)》协议授权</span>
					</div>
				</div>
			</main>
			
			<blog-comment
				:user-uid="userUid"
				:page-uid="articleUid"
				:page-path="`/article/${articleUid}`"
				reply-page-type="ARTICLE"/>
		</template>
	</aurora-common>
</template>

<script lang="ts" setup>
import {computed, onBeforeMount, onMounted, ref} from 'vue';
import {ArticleStoreBean, useArticleStore, useSiteInfo, useUserInfo} from "@/stores";
import {getHost, getLocalTime, getRandomTagType, StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {isNotEmptyObject} from "@/utils/business";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, userApi} from "@/service";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import {copyContent} from "@/plugins";
import {Condition} from "@/bean/core/bean";
import smoothscroll from 'smoothscroll-polyfill';
import RequestResult = Service.RequestResult;
import RenderMarkdown from "@/components/common/content/RenderMarkdown.vue";

const currentSiteInfo = ref<SiteSettingInfo>({})
const useSite = useSiteInfo()
const useUser = useUserInfo()
const router = useRouter()
const routerPush = useRouterPush()
const userUid = ref<string>('')
const articleUid = ref<string>('')
const friendLinkSiteInformation = ref<FriendLinkSiteInformation>({})
const articleInfo = ref<ArticleVo>({})
const articleContent = ref<string>('')
const isClickLikeBut = ref(false)
const articleStore = useArticleStore()
const articleStoreInfo = ref<ArticleStoreBean>({})
const articleTopScroll = ref<Element>()

const getArticleTag = computed((): Array<string> => {
	if (!StringUtil.haveLength(articleInfo.value.tagNames)) return []
	return articleInfo.value.tagNames!.split(",")
})

const getArticleCategory = computed((): Array<string> => {
	if (!StringUtil.haveLength(articleInfo.value.categoryNames)) return []
	return articleInfo.value.categoryNames!.split(",")
})

const updateLikeNumMethod = (article: ArticleVo): Promise<RequestResult<void>> => {
	return articleApi.updateArticleLikeNum(article);
}

const wrap = (wrapped) => (...args) => {
	const [tokens, idx] = args
	const token = tokens[idx]
	const rawCode = wrapped(...args)
	return `<!--beforebegin--><div class="language-${token.info.trim()} extra-class">`
		+ `<!--afterbegin-->${rawCode}<!--beforeend--></div><!--afterend-->`
}

const updateRouterParam = () => {
  // router.currentRoute.value.params.pageUid = articleInfo.value.uid!
	router.push({
		path: `/article/${userUid.value}/${articleInfo.value.uid}`
	})
}

const renderMarkdownContent = (article: ArticleVo) => {
	// 渲染markdown内容
	const markdown = new MarkdownIt()
	markdown.set({
		highlight: function (str, lang) {
			if (lang && hljs.getLanguage(lang)) {
				try {
					return hljs.highlight(lang, str, true).value;
				} catch (__) {}
			}
			return hljs.highlightAuto(str).value; // 使用额外的默认转义
		}
	})
	
	// 代码块增强
	const { fence, code_block: codeBlock } = markdown.renderer.rules
	markdown.renderer.rules.fence = wrap(fence)
	markdown.renderer.rules.code_block = wrap(codeBlock)
	
	
	let defaultRender = markdown.renderer.rules.image!
	markdown.renderer.rules.image = function (tokens, idx, options, env, self) {
		let token = tokens[idx]
		let picSrcIndex = token.attrIndex('src')
		let picSrc = token.attrs![picSrcIndex][1]
		let picAltIndex = token.attrIndex('alt')
		let picAlt = token.attrs![picAltIndex]
		if (/\.(png|jpg|gif|jpeg|webp)$/.test(picSrc)) {
			return `<div role="none" class="n-image n-image--preview-disabled"><img src="${picSrc}" loading="eager" data-error="true" data-preview-src="${picSrc}" style="object-fit: fill;" data-group-id=""></div>`;
		}
		return defaultRender(tokens, idx, options, env, self)
	}
	articleContent.value = markdown.render(article.content!)
	
	smoothscroll.polyfill();
	if (articleTopScroll.value) {
		articleTopScroll.value.scrollIntoView({behavior: "smooth"})
	}
}

const updateReadNum = (article: ArticleVo) => {
	// 修改文章的阅读数
	articleApi.updateArticleReadNum({uid: article.uid}).then(result => {
		if (!result.error) {
			articleInfo.value.readNumber = (articleInfo.value.readNumber ? articleInfo.value.readNumber : 0)  + 1
		}
	})
}

const loadArticleInfo = () => {
	articleApi.queryOneDataByUid({uid: articleUid.value}).then(result => {
		if (result.data) {
			articleInfo.value = result.data
			renderMarkdownContent(result.data)
			// 获取此用户的信息，如果没有，则加载
			if (!isNotEmptyObject(useUser.getUserInfo(articleInfo.value.userUid!))) {
				userApi.queryOneDataByUid({uid: result.data.userUid}).then(result => {
					if (result.data) {
						useUser.setUserInfo(result.data.uid!, result.data)
					}
				})
			}
			
			if (!isNotEmptyObject(useSite.getSiteInfo(articleInfo.value.userUid!))) {
				siteSettingApi.queryOneDataByUserUid({userUid: result.data.userUid}).then(result => {
					if (result.data) {
						if (result.data.paramValue) {
							currentSiteInfo.value = JSON.parse(result.data.paramValue)
							useSite.setSiteInfo(userUid.value, currentSiteInfo.value)
						}
					}
				})
			}
		}
		
		updateReadNum(result.data!)
	})
}

const setLikeStatus = () => {
	let cookie = document.cookie;
	new Promise((resolve,reject) => {
		const cookieList = cookie.split(';')
		for(let i = 0; i < cookieList.length; i++) {
			const arr = cookieList[i].split('=')
			let cookieName =  'article_like_status_' + articleUid.value
			let cookieOriginName = arr[0].replace(" ","")
			if (cookieName === cookieOriginName) {
				if (arr[1] === '1') {
					isClickLikeBut.value = true
				}
			}
		}
		resolve(null)
	})
}

const handleClickArticleLike = () => {
	let cookie = document.cookie;
	let article_like_status = false
	new Promise((resolve,reject) => {
		const cookieList = cookie.split(';')
		for(let i = 0; i < cookieList.length; i++) {
			const arr = cookieList[i].split('=')
			let cookieName =  'article_like_status_' + articleInfo.value.uid
			let cookieOriginName = arr[0].replace(" ","")
			if (cookieName === cookieOriginName) {
				if (arr[1] === '1') {
					article_like_status = true
					resolve(null)
				}
			}
			if (i === cookieList.length -1) {
				resolve(null)
			}
		}
	}).then(() => {
		if (!article_like_status) {
			//没有点赞
			// TODO
			articleApi.updateArticleLikeNum({uid: articleInfo.value.uid}).then(result => {
				if (!result.error) {
					articleInfo.value.likeNumber = (articleInfo.value.likeNumber ? articleInfo.value.likeNumber : 0) + 1
					let expiresTime = new Date().getTime() + 864000000;
					let expires = new Date(expiresTime);
					document.cookie = "article_like_status_" + articleInfo.value.uid + "=1;expires=" + expires + ";";
					isClickLikeBut.value = true
				}
			})
		}else {
			//减赞
			// TODO
			// let expiresTime = new Date().getTime() + 864000000;
			// let expires = new Date(expiresTime);
			// document.cookie = "mood_like_status_" + this.moodItem.id + "=0;expires=" + expires + ";";
			// this.cozeLikeTemp = mood_like - 1
			// this.moodLikeStatus = false
			// this.setLikeSuccess = true
		}
	})
}

const finishGiveLikeAction = () => {
	articleInfo.value.likeNumber = articleInfo.value.likeNumber ? (articleInfo.value.likeNumber + 1) : 1
}

const handleGoArticlePreviousAndNext = (isPreviousArticle: boolean) => {
	// 如果articleStore中没有存储，则前一篇和下一篇便是该文章的前一篇或者是下一篇
	if (!isNotEmptyObject(articleStoreInfo.value)) {
		const conditionTemp: Condition = {
			otherUid: userUid.value,
			delete: false,
			status: true
		}
		if (isPreviousArticle) {
			conditionTemp.startTime = '1997-12-31 23:59:59'
			conditionTemp.endTime = articleInfo.value.createTime
			conditionTemp.orderBy = 'create_time asc'
		}else {
			conditionTemp.startTime = articleInfo.value.createTime
			conditionTemp.endTime = getLocalTime(new Date(), false)
			conditionTemp.orderBy = 'create_time desc'
		}
		articleApi.queryListDataByCondition(conditionTemp).then(result => {
			if (result.data && result.data.result) {
				articleStore.setArticleInfo({
					condition: conditionTemp,
					articleArr: result.data.result,
					currentPage: result.data.pageNum!,
					currentPageSize: result.data.pageSize!,
					queryArticleDataMethod: articleApi.queryListDataByCondition
				})
				articleStoreInfo.value = articleStore.getArticleInfo()
				articleInfo.value = result.data.result[0]
				renderMarkdownContent(articleInfo.value)
				setLikeStatus()
				updateReadNum(articleInfo.value)
				updateRouterParam()
			}else {
				window.$message?.success('没有更多文章啦┭┮﹏┭┮')
			}
		})
	}else {
		const index = articleStoreInfo.value.articleArr?.map(v => v.uid).indexOf(articleInfo.value.uid)
		if (isPreviousArticle) {
			// 如果是第一篇不执行操作，如果是最后一篇，则重新请求文章
			if (index === 0 && articleStoreInfo.value.condition?.pageNum === 1) {
				window.$message?.success('没有更多文章啦┭┮﹏┭┮')
				return
			}
		}
		// 如果是该数组中的最后一篇，则查询
		if (index === articleStoreInfo.value.articleArr!.length - 1 && !isPreviousArticle) {
			articleStoreInfo.value.condition!.pageNum = articleStoreInfo.value.currentPage! + 1
			articleStoreInfo.value.queryArticleDataMethod!(articleStoreInfo.value.condition as Condition).then(result => {
				if (result.data && result.data.result) {
					const articleStoreInfoTemp = articleStore.getArticleInfo()
					articleStoreInfoTemp.articleArr = result.data.result
					articleStoreInfoTemp.currentPage = result.data.pageNum!
					articleStoreInfoTemp.currentPageSize = result.data.pageSize!
					articleStoreInfoTemp.articleArr = result.data.result
					articleStore.setArticleInfo(articleStoreInfoTemp)
					articleInfo.value = result.data.result[0]
					articleStoreInfo.value = articleStore.getArticleInfo()
					renderMarkdownContent(articleInfo.value)
					setLikeStatus()
					updateReadNum(articleInfo.value)
					updateRouterParam()
				}
			})
		}else {
			// 前一篇和后一篇都在数组中
			if (isPreviousArticle) {
				articleInfo.value = articleStoreInfo.value.articleArr![index! - 1]
				renderMarkdownContent(articleInfo.value)
				setLikeStatus()
				updateReadNum(articleInfo.value)
				updateRouterParam()
			}else {
				articleInfo.value = articleStoreInfo.value.articleArr![index! + 1]
				renderMarkdownContent(articleInfo.value)
				setLikeStatus()
				updateReadNum(articleInfo.value)
				updateRouterParam()
			}
		}
	}
}

onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.userUid as string
	articleUid.value = router.currentRoute.value.params.pageUid as string
	if (!StringUtil.haveLength(articleUid.value)) {
		routerPush.routerBack()
	}
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerBack()
	}
	
	loadArticleInfo()
	
	articleStoreInfo.value = articleStore.getArticleInfo()
})

onMounted(() => {
	//如果手机端侧边栏打开的，那么就关闭
	// if (this.$store.state.openMobileSidebar) {
	// 	this.$store.commit("setOpenMobileSidebar",{
	// 		openMobileSidebar: false
	// 	})
	// }
	setLikeStatus()
})

</script>
<style lang="css">
@import "@/styles/dyzj/dyzj-dark.css";
/*@import "@/styles/dyzj/darkcode.css";*/
/*@import "@/styles/dyzj/dyzj.css";*/
</style>
