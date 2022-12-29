<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyPermissionInfo.name}` : '新增邮箱'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-form ref="addUserFormRef" :model="modifyPermissionInfo">
							<n-form-item path="password" label="接口名称">
								<n-input
									:round="true"
									v-model:value="modifyPermissionInfo.name"
									type="text"
									@keydown.enter.prevent
								/>
							</n-form-item>

							<n-form-item path="nickname" label="接口路径">
								<n-input :round="true" type="text" v-model:value="interfacePath" />
							</n-form-item>

							<n-form-item path="method" label="请求方法">
								<n-radio-group v-model:value="selectRequestMethodName" name="radiogroup">
									<n-space>
										<n-radio v-for="(method, index) in requestMethod" :key="index" :value="method">
											{{ method }}
										</n-radio>
									</n-space>
								</n-radio-group>
							</n-form-item>
						</n-form>
						<n-row :gutter="[0, 24]">
							<n-col :span="24">
								<div style="display: flex; justify-content: flex-end">
									<n-button round type="primary" @click="handleClickModifyAction">
										{{ !addStatus ? '更新' : '添加' }}
									</n-button>
								</div>
							</n-col>
						</n-row>
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
import {Permission} from "@/theme/pojo/admin/Permission";
import {permissionApi} from "@/service";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifyPermissionInfo = ref<Permission>({})
const addStatus = ref(false)
const requestMethod = ref([
	'POST',
	'DELETE',
	'GET',
	'PUT'
])
const selectRequestMethodName = ref<string>('')
const interfacePath = ref<string>('')

// 定义方法
const handleClickModifyAction = () => {
	if (!StringUtil.haveLength(modifyPermissionInfo.value.name)) {
		window.$message?.error('名称不能为空')
		return
	}
	if (!StringUtil.haveLength(interfacePath.value)) {
		window.$message?.error('路径不能为空')
		return
	}
	if (!StringUtil.haveLength(selectRequestMethodName.value)) {
		selectRequestMethodName.value = 'POST'
	}
	modifyPermissionInfo.value.path = selectRequestMethodName.value + ":" + interfacePath.value
	if (addStatus.value) {
		permissionApi.insertData(modifyPermissionInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入接口成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		permissionApi.updateData(modifyPermissionInfo.value).then(result => {
			if (result.data) {
				window.$message?.success('操作成功')
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

// 监听mitt
onMounted(() => {
	emitter.on('permissionManageAddPermissionAction', e => {
		modifyPermissionInfo.value = {}
		selectRequestMethodName.value = 'POST'
		interfacePath.value = ''
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('permissionManageModifyPermissionAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifyPermissionInfo.value = e as Permission
			selectRequestMethodName.value = modifyPermissionInfo.value.path!.split(":")[0]
			interfacePath.value = modifyPermissionInfo.value.path!.split(":")[1]
		}
	})
})

</script>

<style scoped>

</style>
