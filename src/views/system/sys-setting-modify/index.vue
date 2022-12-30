<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifySysSettingInfo.paramCode}` : '新增系统设置'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" title="参数编码" size="small">
						<n-input
							:round="true"
							v-model:value="modifySysSettingInfo.paramCode"
							type="text"
							@keydown.enter.prevent
						/>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" title="参数名称" size="small">
						<n-input
							:round="true"
							v-model:value="modifySysSettingInfo.paramName"
							type="text"
							@keydown.enter.prevent
						/>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" title="参数值" size="small">
						<n-space vertical>
							<n-input
								:round="true"
								v-model:value="modifySysSettingInfo.paramValue"
								type="text"
								@keydown.enter.prevent
							/>
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
import {sysSettingApi} from "@/service/api/admin/sysSettingApi";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifySysSettingInfo = ref<SysSetting>({})
const addStatus = ref(false)

// 定义方法
const handleClickModifyAction = () => {
	if (!StringUtil.haveLength(modifySysSettingInfo.value.paramCode)) {
		window.$message?.error('参数编码不能为空')
		return
	}

	if (!StringUtil.haveLength(modifySysSettingInfo.value.paramValue)) {
		window.$message?.error('参数值不能为空')
		return
	}

	if (addStatus.value) {
		sysSettingApi.insertData(modifySysSettingInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		sysSettingApi.updateData(modifySysSettingInfo.value).then(result => {
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
	emitter.on('sysSettingManageAddSysSettingAction', e => {
		modifySysSettingInfo.value = {}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('sysSettingManageModifySysSettingAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifySysSettingInfo.value = e as SysSetting
		}
	})
})

</script>

<style scoped>

</style>
