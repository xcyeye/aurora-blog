<template>
	<div class="wh-full">
		<interface-modify/>
		<n-card class="h-full shadow-sm rounded-16px">
			<n-tabs type="line" animated v-model:value="tabValue">
				<n-tab-pane name="list" tab="列表">
					<interface-list/>
				</n-tab-pane>
				<n-tab-pane name="scan" tab="扫描">
					<n-space vertical>
						<n-transfer
							ref="transfer"
							v-model:value="transferInterfaceArr"
							:options="transferInterfaceOptions"
							:render-source-list="renderSourceList"
							source-filterable
						/>
					</n-space>
				</n-tab-pane>
				<template #suffix>
					<n-space justify="end">
						<n-button v-if="batchDeletePermissionUidArr.length !== 0" strong secondary tertiary round type="error" @click="handleBatchDeletePermission">删除</n-button>
						<n-button v-if="tabValue !== 'list'" strong secondary tertiary round type="info" @click="handleScanAllInterfaceAction">扫描</n-button>
						<n-button v-if="tabValue !== 'list'" strong secondary tertiary round type="success" @click="handleAddSelectInterfaceAction">添加选中</n-button>
						<n-button v-if="tabValue === 'list'" strong secondary tertiary round type="success" @click="handleScanAddInterfaceAction">添加</n-button>
					</n-space>
				</template>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref, watch} from "vue";
import {NSpace, NTag, NTree, TransferRenderSourceList} from "naive-ui";
import {interfaceInfoApi} from "@/service/api/auth/interfaceInfoApi";
import {permissionApi} from "@/service/api/admin/permissionApi";
import InterfaceList from './interface-list/index.vue';
import InterfaceModify from './interface-modify/index.vue';
import {emitter} from "@/utils";
import {Permission} from "@/theme/pojo/admin/Permission";
import {EnumMittEventName} from "@/enum";

defineComponent({name: 'index'});

interface InterfaceTreeOption {
	label?: string,
	value?: string,
	children?: InterfaceTreeOption[],
	isInterfacePath?: boolean,
	requestMethod?: string,
	realLabel?: string,
	requestPath?: string
}

interface ModuleServiceInterfaceProperties {
	moduleTagName?: string | null,
	interfaceArr?: {
		summary?: string | null,
		requestPath?: string | null,
		requestMethod?: string | null
	}[]
}

interface InterfaceProperties {
	serviceTagName: string | null,
	servicePathArr: Array<ModuleServiceInterfaceProperties> | []
}

const transferInterfaceArr = ref<Array<string>>([])
const transferInterfaceOptions = ref<Array<InterfaceTreeOption>>([])
const transferTreeOptions = ref<Array<InterfaceTreeOption>>([])
const tabValue = ref<string>('list')
const batchDeletePermissionUidArr = ref<Array<string>>([])

const renderSourceList: TransferRenderSourceList = function ({
																															 onCheck,
																															 pattern
																														 }) {
	return h(
		// @ts-ignore
		NTree,
		{
			checkboxPlacement: 'right',
			style: 'margin: 0 4px;',
			keyField: 'value',
			checkable: true,
			cascade: true,
			selectable: false,
			blockLine: true,
			checkOnClick: true,
			renderLabel: handleRenderLabel,
			data: transferTreeOptions.value,
			pattern,
			checkedKeys: transferInterfaceArr.value,
			onUpdateCheckedKeys: (checkedKeys: Array<string | number>) => {
				onCheck(checkedKeys)
			}
		})
}

const handleBatchDeletePermission = () => {
  window.$dialog?.success({
		title: '批量删除',
		content: `你确定要删除 ${batchDeletePermissionUidArr.value.length}条接口信息么`,
		positiveText: '确定',
		negativeText: '取消',
		onPositiveClick() {
			let permissions = batchDeletePermissionUidArr.value.map(v => {
				const permissionInfo: Permission = {
					uid: v
				}
				return permissionInfo
			}).concat();
			permissionApi.batchPhysicalDeletePermission({permissionList: permissions}).then(result => {
				if (!result.error) {
					window.$message?.success('操作成功')
					emitter.emit(EnumMittEventName.reloadData)
					tabValue.value = 'list'
					batchDeletePermissionUidArr.value = []
				}
			})
		}
	})
}

function handleRenderLabel ({ option }: { option: InterfaceTreeOption }) {
	if (!option.isInterfacePath) return option.label
	return h(
		NSpace,
		{
			justify: 'start'
		},
		{
			default: () => Array.of(
				h(
					NTag,
					{
						type: 'success',
						bordered: false
					},
					{
						default: () => option.requestMethod
					}
				),
				h(
					NTag,
					{
						type: 'info',
						bordered: false
					},
					{
						default: () => option.realLabel
					}
				),
				h(
					NTag,
					{
						type: 'error',
						bordered: false
					},
					{
						default: () => option.requestPath
					}
				),
			)
		}
	)
}

const handleScanAllInterfaceAction = () => {
	transferInterfaceOptions.value = []
	transferInterfaceArr.value = []
	transferTreeOptions.value = []
	interfaceInfoApi.queryListSwaggerConfig().then(result => {
		if (result.data) {
			result.data.urls?.forEach((v, index) => {
				const parentOneTreeOption: InterfaceTreeOption = {
					label: v.name!,
					value: v.name!,
					children: new Array<InterfaceTreeOption>(),
					isInterfacePath: false
				}
				interfaceInfoApi.querySingleSwaggerInterfaceInfo(v.name!).then(result1 => {
					if (!result1.tags) return
					const interfaceProperties :InterfaceProperties = {
						serviceTagName: v.name!,
						servicePathArr: []
					}
					result1.tags.forEach((tag, tagIndex) => {
						const moduleServiceInterfaceProperties :ModuleServiceInterfaceProperties = {
							moduleTagName: tag.name,
							interfaceArr: []
						}
						const parentTwoTreeOption: InterfaceTreeOption = {
							label: tag.name!,
							value: tag.name!,
							children: new Array<InterfaceTreeOption>(),
							isInterfacePath: false
						}
						moduleServiceInterfaceProperties.moduleTagName = tag.name
						result1.requestPaths?.forEach(pathInfo => {
							// @ts-ignore
							if (pathInfo.tags!.indexOf(tag.name) !== -1) {
								const parentThreeTreeOption: InterfaceTreeOption = {
									label: `${pathInfo.requestMethod!.toUpperCase()}-${pathInfo.summary}-${pathInfo.requestPath}`,
									value: `${pathInfo.requestMethod!.toUpperCase()}:${pathInfo.requestPath!}`,
									isInterfacePath: true,
									requestMethod: pathInfo.requestMethod!.toUpperCase(),
									realLabel: pathInfo.summary!,
									requestPath: pathInfo.requestPath!
								}
								transferInterfaceOptions.value.push(parentThreeTreeOption)
								parentTwoTreeOption.children?.push(parentThreeTreeOption)
								moduleServiceInterfaceProperties.interfaceArr?.push({
									summary: pathInfo.summary,
									requestMethod: pathInfo.requestMethod,
									requestPath: pathInfo.requestPath
								})
							}
						})
						// @ts-ignore
						interfaceProperties.servicePathArr.push(moduleServiceInterfaceProperties)
						parentOneTreeOption.children?.push(parentTwoTreeOption)
					})
					transferTreeOptions.value.push(parentOneTreeOption)
				})
			})
		}
	})

	// 查询数据库中已经存在的接口
	permissionApi.queryListDataByCondition({pageSize: 10000}).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v =>{
				transferInterfaceArr.value.push(v.path!)
			})
		}
	})
}

const handleScanAddInterfaceAction = () => {
	emitter.emit('permissionManageAddPermissionAction')
}

const handleAddSelectInterfaceAction = () => {
	const permissionArr: Array<Permission> = []
	new Promise((resolve, reject) => {
		let filter = transferInterfaceArr.value.filter(v => /^(DELETE|POST|GET|PUT):\/?/.test(v));
		filter.forEach((v, index) => {
			transferInterfaceOptions.value.forEach(v1 => {
				if (v1.value === v) {
					permissionArr.push({
						name: v1.realLabel,
						path: v
					})
				}
				if (index === filter.length -1) {
					resolve(null)
				}
			})
		})
	}).then(result => {
		window.$dialog?.success({
			title: '批量插入接口',
			content: `你确定要插入 ${permissionArr.length}条接口么`,
			positiveText: '确定',
			negativeText: '取消',
			onPositiveClick() {
				permissionApi.batchInsertPermission({permissionList: permissionArr}).then(result => {
					if (!result.error) {
						window.$message?.success('批量插入成功')
						emitter.emit(EnumMittEventName.reloadData)
						tabValue.value = 'list'
					}
				})
			}
		})
	})
}

onMounted(() => {
	emitter.on('permissionManageBatchDeletePermissionAction', e => {
		if (e) {
			batchDeletePermissionUidArr.value = e as Array<string>
		}
	})
})
</script>

<style scoped>

</style>
