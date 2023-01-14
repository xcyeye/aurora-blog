<template>
	<div>
		<show-table-data
			:data-table-info="{title: '白名单', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			:page-sizes="[10, 20, 30]"
			:query-data-method="queryDataMethod">
			<template #cardHeader1>
				<n-button strong secondary tertiary round type="success" @click="handleAddSysSettingAction">添加</n-button>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import {DataTableColumn, NButton, NSpace} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter} from "@/utils";
import {useRouterPush} from "@/composables";
import {sysSettingApi} from "@/service/api/admin/sysSettingApi";
import {SysSettingVo} from "@/theme/vo/admin/SysSettingVo";
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<SysSettingVo>>> => {
	return sysSettingApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: SysSettingVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.paramName} ◔ ‸◔?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			sysSettingApi.physicalDeleteData(data as SysSetting).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.paramName} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleModifyAction = (data: SysSettingVo) => {
	emitter.emit('sysSettingManageModifySysSettingAction', data)
}

const handleAddSysSettingAction = () => {
	emitter.emit('sysSettingManageAddSysSettingAction')
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '参数编码',
			key: 'paramCode',
			titleColSpan: 1,
			width: 120,
			sorter: 'default',
			sortOrder: false,
		},
		{
			title: '参数名称',
			key: 'paramName',
			titleColSpan: 1,
			width: 150,
			sorter: 'default',
			sortOrder: false,
		},
		{
			title: '参数值',
			key: 'paramValue',
			titleColSpan: 1,
			width: 350,
			ellipsis: {
				tooltip: true
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
