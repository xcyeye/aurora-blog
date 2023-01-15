<template>
	<aurora-common :is-sticky-sidebar="true" :is-show-side-bar="true"
								 :show-sidebar-link="true" :user-uid="userUid"
								 :article-info="articleInfo"
								 :show-navbar="true"
								 :is-article-page="true"
								 :is-home-page="false"
								 :is-show-top-img="true"
								 :is-show-head-line="true">
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
				</div>
				
				<div class="aurora-article-like">
					<div class="aurora-article-like-heart" @click="handleClickArticleLike">
						<div class="aurora-article-like-heart-svg" :class="isClickLikeBut ? 'aurora-article-like-heart-active' : ''">
							<svg-icon icon="bi:suit-heart-fill"/>
						</div>
					</div>
					<div class="aurora-article-like-heart-num">{{articleInfo.likeNumber}}</div>
				</div>
				
				<div class="aurora-article-tag" v-if="getArticleTag">
					<svg-icon icon="bi:tag-fill"/> <n-tag class="aurora-article-tag-single" v-for="(item, index) in getArticleTag" :type="getRandomTagType()" :bordered="false" style="border-radius: 16px" :key="index">{{item}}</n-tag>
				</div>
				
				<div class="aurora-article-tag" v-if="getArticleCategory">
					<svg-icon icon="material-symbols:content-copy-rounded"/> <n-tag class="aurora-article-tag-single" v-for="(item, index) in getArticleCategory" :type="getRandomTagType()" :bordered="false" style="border-radius: 16px" :key="index">{{item}}</n-tag>
				</div>
				
				<div class="aurora-article-info">
					<div class="aurora-article-info-author">
						<svg-icon icon="material-symbols:copyright"/>
						<span>版权归属: {{useUserInfo().getUserInfo(userUid).username}}</span>
					</div>
					<div class="aurora-article-info-author">
						<svg-icon icon="ri:link-m"/>
						<span>本文链接: </span>
						<span @click="copyContent(`/article/${articleInfo.userUid}/${articleInfo.uid}`)">{{`/article/${articleInfo.userUid}/${articleInfo.uid}`}}</span>
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
import {useSiteInfo, useUserInfo} from "@/stores";
import {getRandomTagType, StringUtil} from "@/utils";
import {useRouter, onBeforeRouteUpdate} from "vue-router";
import {useRouterPush} from "@/composables";
import {isNotEmptyObject} from "@/utils/business";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, userApi} from "@/service";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js'
import Token from "markdown-it/lib/token";
import Renderer from "markdown-it/lib/renderer";
import Vditor from 'vditor'
import {copyContent} from "@/plugins";

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

const getArticleTag = computed((): Array<string> => {
	if (!StringUtil.haveLength(articleInfo.value.tagNames)) return []
	return articleInfo.value.tagNames!.split(",")
})

const getArticleCategory = computed((): Array<string> => {
	if (!StringUtil.haveLength(articleInfo.value.categoryNames)) return []
	return articleInfo.value.categoryNames!.split(",")
})

onMounted(() => {
	//如果手机端侧边栏打开的，那么就关闭
	// if (this.$store.state.openMobileSidebar) {
	// 	this.$store.commit("setOpenMobileSidebar",{
	// 		openMobileSidebar: false
	// 	})
	// }
	
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
})

const wrap = (wrapped) => (...args) => {
	const [tokens, idx] = args
	const token = tokens[idx]
	const rawCode = wrapped(...args)
	return `<!--beforebegin--><div class="language-${token.info.trim()} extra-class">`
		+ `<!--afterbegin-->${rawCode}<!--beforeend--></div><!--afterend-->`
}

const loadArticleInfo = () => {
	articleApi.queryOneDataByUid({uid: articleUid.value}).then(result => {
		if (result.data) {
			articleInfo.value = result.data
			
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
			articleContent.value = markdown.render(result.data.content!)
			
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
		
		// 修改文章的阅读数
		articleApi.updateArticleReadNum({uid: result.data?.uid}).then(result => {
			if (!result.error) {
				articleInfo.value.readNumber = (articleInfo.value.readNumber ? articleInfo.value.readNumber : 0)  + 1
			}
		})
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
})

</script>
<style lang="css">
@import "@/styles/dyzj/dyzj-dark.css";
/*@import "@/styles/dyzj/darkcode.css";*/
/*@import "@/styles/dyzj/dyzj.css";*/
</style>
