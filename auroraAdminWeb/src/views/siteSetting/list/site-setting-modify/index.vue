<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="right">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifySiteSettingInfo.paramName}` : '新增站点设置'">
				<n-code
					:hljs="hljs"
					:word-wrap="true"
					:code="JSON.stringify(JSON.parse(modifySiteSettingInfo.paramValue), null, 4)"
					language="json"
				/>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import {EnumMittEventName} from "@/enum";
import {emitter} from "@/utils";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import {useAuthStore} from "@/store";
import hljs from 'highlight.js/lib/core';
import json from 'highlight.js/lib/languages/json';

defineComponent({name: 'index'});
hljs.registerLanguage('json', json);

const model = ref<SiteSettingInfo>({})
const authStore = useAuthStore()

// 定义data
const showDrawer = ref<boolean>(false)
const modifySiteSettingInfo = ref<SiteSetting>({})
const addStatus = ref(false)

// 定义方法
const handleClickModifyAction = () => {

	modifySiteSettingInfo.value.paramValue = JSON.stringify(model.value, null, 2)

	if (addStatus.value) {
		modifySiteSettingInfo.value.userUid = authStore.userInfo.user_uid
		siteSettingApi.insertData(modifySiteSettingInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		siteSettingApi.updateData(modifySiteSettingInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

// 监听mitt
onMounted(() => {
	emitter.on('siteSettingManageAddSiteSettingAction', e => {
		modifySiteSettingInfo.value = {}
		model.value = {}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('siteSettingManageModifySiteSettingAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		model.value = {}
		if (e) {
			modifySiteSettingInfo.value = e as SiteSetting
			model.value = JSON.parse(modifySiteSettingInfo.value.paramValue!)
		}
	})
})

</script>

<style scoped>

</style>
