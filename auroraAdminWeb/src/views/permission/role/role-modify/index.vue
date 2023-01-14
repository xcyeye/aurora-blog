<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyRoleInfo.name}` : '新增邮箱'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-form ref="addUserFormRef" :model="modifyRoleInfo">
							<n-form-item path="password" label="角色名称">
								<n-input
									:round="true"
									v-model:value="modifyRoleInfo.name"
									type="text"
									@keydown.enter.prevent
								/>
							</n-form-item>

							<n-form-item path="nickname" label="状态">
								<n-switch v-model:value="modifyRoleInfo.status" :disabled="addStatus" />
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
					<n-card hoverable v-if="rolePermissionDtoArr.length > 0" class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-tag v-for="(item, index) in rolePermissionDtoArr" :bordered="false" :key="index" :type="getRandomTagType()">
								{{item.permissionName}} {{item.path}}
							</n-tag>
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
import {emitter, getRandomTagType, StringUtil} from "@/utils";
import {Role} from "@/theme/pojo/admin/Role";
import {roleApi} from "@/service";
import {rolePermissionRelApi} from "@/service/api/admin/RolePermissionRelApi";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifyRoleInfo = ref<Role>({})
const addStatus = ref(false)
const rolePermissionDtoArr = ref<Array<RolePermissionDto>>([])

// 定义方法
const handleClickModifyAction = () => {
	if (!StringUtil.haveLength(modifyRoleInfo.value.name)) {
		window.$message?.error('角色名不能为空')
		return
	}
	if (addStatus.value) {
		modifyRoleInfo.value.status = true
		roleApi.insertData(modifyRoleInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入角色成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		roleApi.updateData(modifyRoleInfo.value).then(result => {
			if (result.data) {
				window.$message?.success('操作成功')
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

const loadRolePermissionInfo = () => {
	rolePermissionRelApi.loadPermissionByRoleName({roleNameArr: [modifyRoleInfo.value.name!]}).then(result => {
		if (result.data) {
			rolePermissionDtoArr.value = result.data
		}
	})
}

// 监听mitt
onMounted(() => {
	emitter.on('roleManageAddRoleAction', e => {
		modifyRoleInfo.value = {}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('roleManageModifyRoleAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifyRoleInfo.value = e as Role
			loadRolePermissionInfo()
		}
	})
})

</script>

<style scoped>

</style>
