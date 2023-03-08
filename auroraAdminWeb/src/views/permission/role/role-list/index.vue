<template>
	<div>
		<show-table-data
			:data-table-info="{title: '用户管理', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod">
			<template #cardHeader1>
				<n-button strong secondary tertiary round type="success" @click="handleAddRoleAction">添加</n-button>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {DataTableColumn, NButton, NSpace, NSwitch, NTag, NText} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getRandomTagType, StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";
import RequestResult = Service.RequestResult;
import {bulletinApi, roleApi} from "@/service";
import {RoleVo} from "@/bean/vo/admin/RoleVo";
import {BulletinVo} from "@/bean/vo/article/BulletinVo";
import {Role} from "@/bean/pojo/admin/Role";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<RoleVo>>> => {
	return roleApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: RoleVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.name} ◔ ‸◔?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			roleApi.physicalDeleteData(data as Role).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.name} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleModifyAction = (data: RoleVo) => {
	emitter.emit('roleManageModifyRoleAction', data)
}

const handleAddRoleAction = () => {
	emitter.emit('roleManageAddRoleAction')
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '角色名称',
			key: 'name',
			titleColSpan: 1,
			width: 170,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '状态',
			key: 'status',
			titleColSpan: 1,
			width: 100,
			render(row: RoleVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.status,
						onUpdateValue(value: boolean) {
							window.$dialog?.success({
								title: value ? `启用${row.name} 角色?` : `禁用${row.name} 角色?`,
								content: `启用或者禁用 ${row.name}后，拥有此角色的用户都将受影响`,
								positiveText: '确定',
								negativeText: '取消',
								onPositiveClick() {
									roleApi.updateData({uid: row.uid, status: value}).then(result => {
										if (result.data && result.data === 1) {
											window.$message?.success('修改成功')
											emitter.emit(EnumMittEventName.reloadData)
											row.status = value
										}
									})
								}
							})
						}
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
