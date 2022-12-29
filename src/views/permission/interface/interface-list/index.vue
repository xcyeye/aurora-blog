<template>
	<div>
		<show-table-data
			:data-table-info="{title: '用户管理', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:query-data-method="queryDataMethod">
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import {permissionApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NTag, NText} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getRandomTagType} from "@/utils";
import {useRouterPush} from "@/composables";
import {PermissionVo} from "@/theme/vo/admin/PermissionVo";
import {Permission} from "@/theme/pojo/admin/Permission";
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<PermissionVo>>> => {
	return permissionApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: PermissionVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.name} ◔ ‸◔?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			permissionApi.physicalDeleteData(data as Permission).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.name} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleModifyAction = (data: Permission) => {
	emitter.emit('permissionManageModifyPermissionAction', data)
}

const handleCheckedRowKeys = (keys: Array<string>) => {
	emitter.emit('permissionManageBatchDeletePermissionAction', keys)
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '接口名称',
			key: 'name',
			titleColSpan: 1,
			width: 170,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '路径',
			key: 'path',
			titleColSpan: 1,
			width: 300,
			render(row: PermissionVo) {
				const path: string[] = row.path!.split(":")
				return h(
					NText,
					{

					},
					{
						default: () => path[1]
					}
				)
			}
		},
		{
			title: '请求方法',
			key: 'path',
			titleColSpan: 1,
			width: 100,
			render(row: PermissionVo) {
				const path: string[] = row.path!.split(":")
				return h(
					// @ts-ignore
					NTag,
					{
						bordered: false,
						type: getRandomTagType()
					},
					{
						default: () => path[0]
					}
				)
			}
		},
		{
			title: '创建时间',
			key: 'createTime',
			titleColSpan: 1,
			sorter: 'default',
			sortOrder: false,
			width: 170
		},
		{
			title: '操作',
			key: 'actions',
			fixed: 'right',
			width: 200,
			render(row) {
				return h(
					NSpace,
					{
						justify: 'center'
					},
					{
						default:() => Array.of(
							h(
								NButton,
								{
									size: 'small',
									type: 'warning',
									ghost: true,
									onClick: () => handleDeleteAction(row)
								},
								{ default: () => '删除' }
							),
							h(
								NButton,
								{
									size: 'small',
									type: 'primary',
									ghost: true,
									onClick: () => handleModifyAction(row)
								},
								{ default: () => '编辑' }
							)
						)
					}
				);
			}
		}
	]
}

const columns = ref<Array<DataTableColumn>>(createColumns())

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
