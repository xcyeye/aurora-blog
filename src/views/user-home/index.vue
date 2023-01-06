<template>
	<home-content :user-uid="userUid" :is-home="true"/>
	<home-bottom :user-uid="userUid"/>
</template>

<script lang="ts" setup>
import {onBeforeMount, ref,} from 'vue';
import {useRouter} from 'vue-router';
import {blogPageData} from "@/assets/config";
import {StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import {defaultSiteSettingInfo} from "@/field";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {userApi} from "@/service";
import {useCurrentUser, useSiteInfo, useUserInfo} from "@/stores";
import {setDefaultProperties} from "@/utils/business";

const themeProperty = ref(blogPageData)
const router = useRouter()
const routerPush = useRouterPush()
const userUid = ref<string>('')
const userInfo = ref<UserVo>({})
const siteSettingInfo = ref<SiteSettingInfo>({})
const useSite = useSiteInfo()
const useUser = useUserInfo()

const setProperties = () => {
	Object.keys(defaultSiteSettingInfo).forEach(v => {
		if (siteSettingInfo.value[v as keyof SiteSettingInfo] === null || siteSettingInfo.value[v as keyof SiteSettingInfo] === undefined) {
			// @ts-ignore
			siteSettingInfo.value[v as keyof SiteSettingInfo] = defaultSiteSettingInfo[v as keyof SiteSettingInfo]
		}
	})
}

onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.uid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		})
	}
	
	// if (!useUser.getUserInfo(userUid.value) || !StringUtil.haveLength(useUser.getUserInfo(userUid.value).username)) {
	// 	userApi.queryOneDataByUid({uid: userUid.value}).then(result => {
	// 		if (result.data) {
	// 			userInfo.value = result.data
	// 			useUser.setUserInfo(userUid.value, result.data)
	// 		}
	// 	})
	// }
	
	useCurrentUser().setCurrentUserInfo({uid: userUid.value})
	
	
	if (!useSite.getSiteInfo(userUid.value)) {
		siteSettingApi.queryOneDataByUserUid({userUid: userUid.value}).then(result => {
			if (result.data) {
				if (result.data.paramValue) {
					siteSettingInfo.value = JSON.parse(result.data.paramValue)
					useSite.setSiteInfo(userUid.value, siteSettingInfo.value)
				}
			}
		})
	}
})
</script>