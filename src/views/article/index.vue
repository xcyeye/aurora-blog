<template>
	<aurora-common :is-sticky-sidebar="true" :is-show-side-bar="false"
								 :show-sidebar-link="false" :user-uid="userUid"
								 :is-show-top-img="true" :is-show-head-line="false">
		<template #center1>
			<main :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
						class="page sidebar-single-enter-animate blog-article" id="article-page">
				
				<div id="theme-default-content" :class="true ? 'hide-h1-tag' : 'show-h1-tag'" class="theme-default-content pageContent medium-zoom-content">
					<div class="page-top-share">
						<div class="page-top-share-next">
							<!--<poster :title="originPageData.title" :content="posterContent"/>-->
						</div>
					</div>
					<div v-html="articleContent"></div>
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
import {onBeforeMount, onMounted, ref} from 'vue';
import {useSiteInfo, useUserInfo} from "@/stores";
import {StringUtil} from "@/utils";
import {useRouter, onBeforeRouteUpdate} from "vue-router";
import {useRouterPush} from "@/composables";
import {isNotEmptyObject} from "@/utils/business";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, userApi} from "@/service";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import MarkdownIt from 'markdown-it';

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

onMounted(() => {
	//如果手机端侧边栏打开的，那么就关闭
	// if (this.$store.state.openMobileSidebar) {
	// 	this.$store.commit("setOpenMobileSidebar",{
	// 		openMobileSidebar: false
	// 	})
	// }
})

const loadArticleInfo = () => {
	articleApi.queryOneDataByUid({uid: articleUid.value}).then(result => {
		if (result.data) {
			articleInfo.value = result.data
			userUid.value = result.data.userUid!
			
			// 渲染markdown内容
			const markdown = new MarkdownIt()
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
	})
}

// onBeforeRouteUpdate(to => {
// 	console.log(to);
// })

onBeforeMount(() => {
	articleUid.value = router.currentRoute.value.params.uid as string
	if (!StringUtil.haveLength(articleUid.value)) {
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
