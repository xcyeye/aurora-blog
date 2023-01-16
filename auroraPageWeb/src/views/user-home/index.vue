<template>
	<navbar :user-uid="userUid"/>
	<home-content :user-uid="userUid" :is-home="true"/>
	<home-bottom :condition="condition"
							 :query-article-data-method="queryDataMethod"
							 :user-uid="userUid"/>
	<Footer :current-site-info="siteSettingInfo"
					:is-home="true"
					:is-show-footer="true"/>
	<set-bg/>
</template>

<script lang="ts" setup>
import {onBeforeMount, ref,} from 'vue';
import {useRouter} from 'vue-router';
import {blogPageData} from "@/assets/config";
import {StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";
import {defaultSiteSettingInfo} from "@/field";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {articleApi, userApi} from "@/service";
import {useCurrentUser, useSiteInfo, useUserInfo} from "@/stores";
import {isNotEmptyObject} from "@/utils/business";
import {Condition, PageData} from "@/bean/core/bean";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import RequestResult = Service.RequestResult;

const themeProperty = ref(blogPageData)
const router = useRouter()
const routerPush = useRouterPush()
const userUid = ref<string>('')
const userInfo = ref<UserVo>({})
const siteSettingInfo = ref<SiteSettingInfo>({})
const useSite = useSiteInfo()
const useUser = useUserInfo()
const condition = ref<Condition>({
	delete: false,
	status: true,
	pageSize: 10,
	pageNum: 1,
	orderBy: 'create_time desc, update_time desc'
})

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<ArticleVo>>> => {
	return articleApi.queryListDataByCondition(condition);
}

const setProperties = () => {
	Object.keys(defaultSiteSettingInfo).forEach(v => {
		if (siteSettingInfo.value[v as keyof SiteSettingInfo] === null || siteSettingInfo.value[v as keyof SiteSettingInfo] === undefined) {
			// @ts-ignore
			siteSettingInfo.value[v as keyof SiteSettingInfo] = defaultSiteSettingInfo[v as keyof SiteSettingInfo]
		}
	})
}

onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.userUid as string
	condition.value.otherUid = userUid.value
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		})
	}
	
	if (!isNotEmptyObject(useUser.getUserInfo(userUid.value))) {
		userApi.queryOneDataByUid({uid: userUid.value}).then(result => {
			if (result.data) {
				userInfo.value = result.data
				useUser.setUserInfo(userUid.value, result.data)
			}
		})
	}
	
	useCurrentUser().setCurrentUserInfo({uid: userUid.value})
	
	if (!isNotEmptyObject(useSite.getSiteInfo(userUid.value))) {
		useSite.setSiteInfo(userUid.value, defaultSiteSettingInfo)
		siteSettingInfo.value = defaultSiteSettingInfo
		// siteSettingApi.queryOneDataByUserUid({userUid: userUid.value}).then(result => {
		// 	if (result.data) {
		// 		if (result.data.paramValue) {
		// 			// TODO 临时解决
		// 			// siteSettingInfo.value = JSON.parse(result.data.paramValue)
		// 			siteSettingInfo.value = JSON.parse(result.data.paramValue)
		// 			useSite.setSiteInfo(userUid.value, defaultSiteSettingInfo)
		// 		}
		// 	}
		// })
	}else {
		siteSettingInfo.value = useSite.getSiteInfo(userUid.value)
		console.log(siteSettingInfo.value);
	}
})
</script>