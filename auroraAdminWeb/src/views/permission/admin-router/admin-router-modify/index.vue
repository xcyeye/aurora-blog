<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyAdminRouterInfo.name}` : '新增路由'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" title="路由信息" size="small">
						<n-form-item path="age" label="路由名称">
							<n-select @update:value="handleCalculateRouterPath"
												:default-value="modifyAdminRouterInfo.name"
												:options="adminRouterNameOption" />
						</n-form-item>
						<n-form-item path="age" label="组件布局">
							<n-select :default-value="modifyAdminRouterInfo.name"
												v-model:value="modifyAdminRouterInfo.component"
												:options="adminRouterComponentTypeArr" />
						</n-form-item>
						<n-form-item path="age" label="一级路由">
							<n-switch v-model:value="isFirstLevelStatus" />
						</n-form-item>
						<n-space v-if="!isFirstLevelStatus" vertical>
							<n-form-item path="age" label="选择父路由">
								<n-tree-select
									:options="adminRouterTreeOption"
									:default-value="defaultParentRouterPath"
									@update:value="handleSelectParentRouterAction"
								/>
							</n-form-item>
							<n-form-item path="age" label="选择子路由">
								<n-tree-select
									:default-value="defaultSonRouterPathArr"
									:options="adminRouterTreeOption"
									multiple
									@update:value="handleSelectSonRouterAction"
								/>
							</n-form-item>
						</n-space>
						<n-form-item path="age" label="排序">
							<n-input round type="number" v-model:value="modifyAdminRouterInfo.order"/>
						</n-form-item>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" title="Meta信息" size="small">
						<n-form-item path="age" label="站点标题">
							<n-input round type="text" v-model:value="modifyAdminRouterInfo.title"/>
						</n-form-item>
						<n-form-item path="age" label="图标">
							<n-input round type="text" v-model:value="modifyAdminRouterInfo.icon"/>
						</n-form-item>
						<n-form-item path="age" label="外链">
							<n-input round type="text" v-model:value="modifyAdminRouterInfo.href"/>
						</n-form-item>
						<n-form-item path="age" label="需要登录权限">
							<n-switch v-model:value="modifyAdminRouterInfo.requiresAuth" />
						</n-form-item>
						<n-form-item path="age" label="页面缓存">
							<n-switch v-model:value="modifyAdminRouterInfo.keepalive" />
						</n-form-item>
						<n-form-item path="age" label="菜单栏隐藏">
							<n-switch v-model:value="modifyAdminRouterInfo.hide" />
						</n-form-item>
						<n-form-item path="age" label="Tab不可关闭">
							<n-switch v-model:value="modifyAdminRouterInfo.affix" />
						</n-form-item>
						<n-space justify="end">
							<n-button strong secondary tertiary round type="success" @click="handleClickModifyAction">
								{{ !addStatus ? '更新' : '添加' }}
							</n-button>
						</n-space>
					</n-card>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, onMounted, ref} from "vue";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil, transformRouteNameToRoutePath, transformRoutePathToRouteName} from "@/utils";
import {AdminRouter} from "@/bean/pojo/admin/AdminRouter";
import {adminRouterApi} from "@/service/api/admin/adminRouterApi";
import {TreeSelectOption} from "naive-ui/es/tree-select/src/interface";
import {SelectMixedOption} from "naive-ui/es/select/src/interface";
import {routerNameArr} from "@/views/permission/admin-router/routerNameArr";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifyAdminRouterInfo = ref<AdminRouter>({})
const addStatus = ref(false)
const adminRouterNameOption = ref<Array<SelectMixedOption>>([])
const adminRouterComponentTypeArr = ref<Array<SelectMixedOption>>([
	{
		label: '基础布局',
		value: 'basic'
	},
	{
		label: '空白布局',
		value: 'blank'
	},
	{
		label: '多级路由布局',
		value: 'multi'
	},
	{
		label: '作为子路由',
		value: 'self'
	}
])
const isFirstLevelStatus = ref<boolean>(true)
const adminRouterTreeOption = ref<Array<TreeSelectOption>>([])
const parentRouterPath = ref<string>('')
const sonRouterPathArr = ref<Array<string>>([])
const defaultParentRouterPath = ref<string>('')
const defaultSonRouterPathArr = ref<Array<string>>([])

// 定义方法
const handleClickModifyAction = () => {
	if (!StringUtil.haveLength(modifyAdminRouterInfo.value.name)) {
		window.$message?.error('必须选择一个路由名称')
		return
	}
	if (!isFirstLevelStatus.value) {
		if (!StringUtil.haveLength(parentRouterPath.value)) {
			window.$message?.error('不是一级路由，必须要选择一个父级路由')
			return
		}
	}
	if (sonRouterPathArr.value.length !== 0) {
		modifyAdminRouterInfo.value.sonRouterPathList = sonRouterPathArr.value
	}
	if (parentRouterPath.value.length !== 0) {
		modifyAdminRouterInfo.value.parentRouterPathList = [parentRouterPath.value]
	}
	if (addStatus.value) {
		if (sonRouterPathArr.value.length !== 0) {
			modifyAdminRouterInfo.value.sonRouterPathList = sonRouterPathArr.value
		}
		adminRouterApi.insertData(modifyAdminRouterInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入路由成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		adminRouterApi.updateData(modifyAdminRouterInfo.value).then(result => {
			if (result.data) {
				window.$message?.success('操作成功')
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

const handleSelectParentRouterAction = (value: string) => {
	// @ts-ignore
	parentRouterPath.value = transformRouteNameToRoutePath(value)
}

const handleSelectSonRouterAction = (value: Array<string>) => {
	sonRouterPathArr.value = []
	value.forEach(v => {
		// @ts-ignore
		sonRouterPathArr.value.push(transformRouteNameToRoutePath(v))
	})
}

const handleCalculateRouterPath = (value: string) => {
	// @ts-ignore
	modifyAdminRouterInfo.value.path = transformRouteNameToRoutePath(value)
	modifyAdminRouterInfo.value.name = value
}

onBeforeMount(() => {
	adminRouterNameOption.value = []
	routerNameArr.forEach(name => {
		adminRouterNameOption.value.push({
			label: name,
			value: name
		})
	})
	routerNameArr
		.filter(v => v.indexOf("_") === -1)
		.forEach(routerName => {
			const routerTreeOption: TreeSelectOption = {
				label: routerName,
				key: routerName,
				children: []
			}
			routerNameArr.forEach(sonRouterName => {
				if (sonRouterName.startsWith(routerName)) {
					routerTreeOption.children?.push({
						label: sonRouterName,
						key: sonRouterName
					})
				}
			})
			adminRouterTreeOption.value.push(routerTreeOption)
		})
})

// 监听mitt
onMounted(() => {
	emitter.on('adminRouterManageAddAdminRouterAction', e => {
		modifyAdminRouterInfo.value = {
			requiresAuth: true,
			hide: false,
			keepalive: true,
			affix: true
		}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('adminRouterManageModifyAdminRouterAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		defaultParentRouterPath.value = ''
		defaultSonRouterPathArr.value = []
		if (e) {
			modifyAdminRouterInfo.value = e as AdminRouter
			if (!StringUtil.haveLength(modifyAdminRouterInfo.value.parentRouterUid)) {
				isFirstLevelStatus.value = true
			}else {
				isFirstLevelStatus.value = false
				adminRouterApi.queryOneDataByUid({uid: modifyAdminRouterInfo.value.parentRouterUid}).then(result => {
					if (!result.error && result.data) {
						// @ts-ignore
						defaultParentRouterPath.value = transformRoutePathToRouteName(result.data.path!)
					}
				})
			}
			if (StringUtil.haveLength(modifyAdminRouterInfo.value.sonRouterUids)) {
				modifyAdminRouterInfo.value.sonRouterUids!.split(",").forEach(v => {
					adminRouterApi.queryOneDataByUid({uid: v}).then(result => {
						if (!result.error && result.data) {
							// @ts-ignore
							defaultSonRouterPathArr.value.push(transformRoutePathToRouteName(result.data.path!))
							console.log(defaultSonRouterPathArr.value);
						}
					})
				})
			}
		}
	})
})

</script>

<style scoped>

</style>
