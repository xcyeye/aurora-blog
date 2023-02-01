<template>
	<div>
		<div class="home">
			<div class="home-main-top">
				<div class="home-main-article-latest">
					<n-carousel show-arrow autoplay>
						<div @click="goRead(item)" :style="getArticleCover(item)" class="home-main-article-latest-article" v-for="(item, index) in articleLatestArr">
							<div class="home-main-article-latest-article-info">
								<div>
									<span>{{item.title}}</span>
								</div>
							</div>
						</div>
					</n-carousel>
				</div>
				<div class="home-main-top-right">
					<div class="home-main-top-right-user">
						<n-scrollbar>
							<div class="home-main-top-right-user-single" @click="goUserPage(item)" v-for="(item, index) in userArr">
								<div class="home-main-top-right-user-single-left">
									<div class="home-main-top-right-user-single-left-avatar">
										<n-avatar :src="item.avatar" round/>
									</div>
								</div>
								<div class="home-main-top-right-user-single-right">
									<div class="home-main-top-right-user-single-right-username">
										<span>{{item.username}}</span>
									</div>
									<div class="home-main-top-right-user-single-right-userSummary">
										<span>{{item.userSummary}}</span>
									</div>
								</div>
							</div>
						</n-scrollbar>
					</div>
					<div class="home-main-top-right-info">
						<n-row>
							<n-col :span="12">
								<n-statistic >
									<template #label>
										<span class="home-main-top-right-info-title">活跃用户</span>
									</template>
									<template #prefix>
										<span class="home-main-top-right-info-color-common">
											<svg-icon icon="fa:user"/>
										</span>
									</template>
									<template #suffix>
										<span class="home-main-top-right-info-color-common">{{userArr.length}}</span>
									</template>
								</n-statistic>
							</n-col>
							<n-col :span="12">
								<n-statistic>
									<template #label>
										<span class="home-main-top-right-info-title">总文章</span>
									</template>
									<template #prefix>
										<span class="home-main-top-right-info-color-common">
											<svg-icon icon="fa:envira"/>
										</span>
									</template>
									<template #suffix>
										<span class="home-main-top-right-info-color-common">{{articleArr.length}}</span>
									</template>
								</n-statistic>
							</n-col>
						</n-row>
						<n-row>
							<n-col :span="12">
								<n-statistic>
									<template #label>
										<span class="home-main-top-right-info-title">总评论</span>
									</template>
									<template #prefix>
										<span class="home-main-top-right-info-color-common"><svg-icon icon="fa:commenting"/></span>
									</template>
									<template #suffix>
										<span class="home-main-top-right-info-color-common">{{totalCommentNumber}}</span>
									</template>
								</n-statistic>
							</n-col>
							<n-col :span="12">
								<n-statistic>
									<template #label>
										<span class="home-main-top-right-info-title">总说说</span>
									</template>
									<template #prefix>
										<span class="home-main-top-right-info-color-common"><svg-icon icon="fa:comments"/></span>
									</template>
									<template #suffix>
										<span class="home-main-top-right-info-color-common">{{totalTalkNumber}}</span>
									</template>
								</n-statistic>
							</n-col>
						</n-row>
					</div>
				</div>
			</div>
			<div class="home-main-bottom">
				<home-bottom :page-cover-picture="blogConfig.defaultRandomPicture" :show-home-sidebar="false" :condition="{delete: false, status: true}" :query-article-data-method="queryDataMethod"/>
			</div>
			<div class="home-main-footer">
				<Footer :current-site-info="footerSiteInfo"
								:is-home="false"
								:is-show-footer="true">
				</Footer>
			</div>
		</div>
		<div>
			<div id="set-bg" class="set-bg-fitter"
					 :style="`background-image: url(${blogConfig.bingPictureInterface})`"
			></div>
			<!--TODO 临时改的-->
			<!--<div id="posterShade" :class="{posterShade: true}">-->
			<!--	<span :class="{iconSpinner6: true}"></span>-->
			<!--</div>-->
			<div id="posterShade" :class="{posterShade: false}">
				<span :class="{iconSpinner6: false}"></span>
			</div>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onBeforeMount, ref} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, commentApi, talkApi, userApi} from "@/service";
import {Condition, PageData} from "@/bean/core/bean";
import {useRouterPush} from "@/composables";
import {UserVo} from "@/bean/vo/admin/UserVo";
import blogConfig from '@/config/blogConfig.json';
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

const articleArr = ref<Array<ArticleVo>>([])

const totalCommentNumber = ref(0)
const totalTalkNumber = ref(0)
const userArr = ref<Array<UserVo>>([])
const articleLatestArr = ref<Array<ArticleVo>>([])
const routerPush = useRouterPush()
const footerSiteInfo: SiteSettingInfo = {
	footerInfo: {
		enable: true,
		isShowRunTime: false,
		footInfo: [
			'Copyright © by xcye All Rights Reserved'
		]
	}
}

const getArticleCover = computed(() => {
	return (article: ArticleVo) => {
		return `background-image: url("${article.coverPictureUrl}");`
	}
})

const goRead = (article: ArticleVo) => {
	routerPush.routerPush({
		path: `/article/${article.userUid}/${article.uid}`
	})
}

const goUserPage = (userInfo: UserVo) => {
	console.log(userInfo);
	routerPush.routerPush({
		path: `/user/${userInfo.uid}`
	})
}

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<ArticleVo>>> => {
	return articleApi.queryListDataByCondition(condition);
}

const loadAllUser = () => {
  userApi.queryListDataByCondition({delete: false, pageSize: 5000}).then(result => {
		if (result.data && result.data.result) {
			userArr.value = result.data.result
		}
	})
}

const loadAllArticle = () => {
  articleApi.queryListDataByCondition({delete: false, status: true}).then(result => {
		if (result.data && result.data.result) {
			articleArr.value = result.data.result
			if (result.data.result.length > 5) {
				articleLatestArr.value = result.data.result.splice(0, 5)
			}else {
				articleLatestArr.value = result.data.result
			}
		}
	})
}

const loadAllComment = () => {
  commentApi.queryTotalCount({}).then(result => {
		if (result.data) {
			totalCommentNumber.value = result.data
		}
	})
}

const loadAllTalk = () => {
	talkApi.queryTotalCount({}).then(result => {
		if (result.data) {
			totalTalkNumber.value = result.data
		}
	})
}

loadAllTalk()
loadAllComment()

onBeforeMount(() => {
	loadAllArticle()
	loadAllUser()
})
</script>

<style scoped>

</style>