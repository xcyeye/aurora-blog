<template>
	<div>
		<n-space justify="end">
			<n-button strong secondary tertiary round type="success" @click="handleUpdateReadmeAction">{{addInfoStatus ? '添加': '更新'}}</n-button>
		</n-space>
		<markdown-editor :render-md-content="siteInfo.readme" :storage-mode="0" @vditorInput="vditorInput"/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";
import {useAuthStore} from "@/store";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";

defineComponent({name: 'index'});

const siteInfo = ref<SiteSettingInfo>({})
const siteSetting = ref<SiteSetting>({})
const autoStore = useAuthStore()
const addInfoStatus = ref(true)

const loadSiteInfo = () => {
	siteSettingApi.queryListDataByCondition({otherUid: autoStore.userInfo.user_uid, keyword: `${autoStore.userInfo.user_uid}SiteInfo`}).then(result => {
		if (result.data && result.data.result && result.data.result.length > 0) {
			siteSetting.value = result.data.result[0]
			siteInfo.value = JSON.parse(result.data.result[0].paramValue!);
			addInfoStatus.value = false
		}else {
			addInfoStatus.value = true
		}
	})
}
loadSiteInfo()

const vditorInput = (input: string) => {
  siteInfo.value.readme = input
}

const handleUpdateReadmeAction = () => {
	siteSetting.value.paramValue = JSON.stringify(siteInfo.value, null, 2)
	if (addInfoStatus.value) {
		siteSetting.value.paramName = `${autoStore.userInfo.user_uid}SiteInfo`
		siteSetting.value.userUid = autoStore.userInfo.user_uid
		siteSettingApi.insertData(siteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('添加成功')
			}
		})
	}else {
		// g更新
		siteSettingApi.updateData(siteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
			}
		})
	}
}
</script>

<style scoped>

</style>
