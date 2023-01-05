<template>
	<home-content :theme-property="themeProperty" :show-random-say="true" :is-home="true"/>
	<!--<home-bottom/>-->
</template>

<script lang="ts" setup>
import {
	computed,
	defineComponent,
	onMounted,
	onUnmounted,
	ref,
	Transition,
} from 'vue'
import { useRouter } from 'vue-router'
import $ from 'jquery'
import {blogPageData} from "@/assets/config";
import {StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";

const themeProperty = ref(blogPageData)
const router = useRouter()
const routerPush = useRouterPush()
const userUid = ref<string>('')

onMounted(() => {
	userUid.value = router.currentRoute.value.params.uid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		})
	}
	
	siteSettingApi.queryOneDataByUserUid({userUid: '1522074993315815424'}).then(result => {
		console.log(result);
	})
})
</script>