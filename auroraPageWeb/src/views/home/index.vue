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
										<n-gradient-text :type="getRandomTagType()">
											{{item.username}}
										</n-gradient-text>
									</div>
									<div class="home-main-top-right-user-single-right-userSummary">
										<n-gradient-text :type="getRandomTagType()">
											{{item.userSummary}}
										</n-gradient-text>
									</div>
								</div>
							</div>
						</n-scrollbar>
					</div>
					<div class="home-main-top-right-info">
						<n-row>
							<n-col :span="12">
								<n-statistic label="活跃用户">
									<template #prefix>
										<svg-icon icon="ic:round-hourglass-bottom"/>
									</template>
									<template #suffix>
										{{userArr.length}}
									</template>
								</n-statistic>
							</n-col>
							<n-col :span="12">
								<n-statistic label="总文章">
									<template #prefix>
										<svg-icon icon="ion:planet-sharp"/>
									</template>
									<template #suffix>
										{{articleArr.length}}
									</template>
								</n-statistic>
							</n-col>
						</n-row>
						<n-row>
							<n-col :span="12">
								<n-statistic label="总评论数">
									<template #prefix>
										<svg-icon icon="ion:ios-cube"/>
									</template>
									<template #suffix>
										{{userArr.length}}
									</template>
								</n-statistic>
							</n-col>
							<n-col :span="12">
								<n-statistic label="总文章">
									<template #prefix>
										<svg-icon icon="ion:planet-sharp"/>
									</template>
									<template #suffix>
										{{articleArr.length}}
									</template>
								</n-statistic>
							</n-col>
						</n-row>
					</div>
				</div>
			</div>
			<div class="home-main-bottom">
				<home-bottom :show-home-sidebar="false" :condition="{delete: false, status: true}" :query-article-data-method="queryDataMethod"/>
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
					 style="background-image: url(https://pic-tool.xcye.xyz/pic/rmimg)"
			></div>
			<div id="posterShade" :class="{posterShade: $store.state.showPosterShadow}">
				<span :class="{iconSpinner6: $store.state.showShadeLoad}"></span>
			</div>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onBeforeMount, ref} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, userApi} from "@/service";
import {Condition, PageData} from "@/bean/core/bean";
import RequestResult = Service.RequestResult;
import {useRouterPush} from "@/composables";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {getRandomTagType} from "@/utils";
import {useSysSettingStore} from "@/stores";
import {sysSettingApi} from "@/service/api/admin/sysSettingApi";

defineComponent({name: 'index'});

const articleArr = ref<Array<ArticleVo>>([])

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
const sysSettingStore = useSysSettingStore()

const getArticleCover = computed(() => {
	return (article: ArticleVo) => {
		return `background-image: url("${article.coverPictureUrl}");`
	}
})

const goRead = (article: ArticleVo) => {
	routerPush.routerPush({
		path: `/article/${article.uid}`
	})
}

const goUserPage = (userInfo: UserVo) => {
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

const loadSysSetting = () => {
	// 获取系统配置
	if (!sysSettingStore.sysSettingMap || sysSettingStore.sysSettingMap.size === 0) {
		sysSettingApi.queryListDataByCondition({pageSize: 999}).then(result => {
			if (result.data && result.data.result) {
				sysSettingStore.setSysSetting(result.data.result)
			}else {
				sysSettingStore.setSysSetting([])
			}
		})
	}
}

onBeforeMount(() => {
	loadAllArticle()
	loadAllUser()
	loadSysSetting()
	
})
</script>

<style scoped>

</style>