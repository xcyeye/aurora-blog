<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifySiteSettingInfo.paramName}` : '新增站点设置'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" title="参数名称" size="small">
						<n-input
							:round="true"
							v-model:value="modifySiteSettingInfo.paramName"
							type="text"
							@keydown.enter.prevent
						/>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-space vertical>
								<n-text>logo</n-text>
								<n-input size="small" round v-model:value="model.logo" placeholder="输入姓名" />
							</n-space>
							<n-space vertical>
								<n-text>name</n-text>
								<n-input size="small" round v-model:value="model.name" placeholder="输入姓名" />
							</n-space>
							<n-row :gutter="[0, 24]">
								<n-col :span="24">
									<div style="display: flex; justify-content: flex-end">
										<n-button round type="primary" @click="handleClickModifyAction">
											{{ !addStatus ? '更新' : '添加' }}
										</n-button>
									</div>
								</n-col>
							</n-row>
						</n-space>
					</n-card>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import {useAuthStore} from "@/store";

defineComponent({name: 'index'});

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
