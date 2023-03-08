<template>
	<div>
		<show-table-data
			:data-table-info="{title: '白名单', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:page-sizes="[10, 20, 30]"
			:query-data-method="queryDataMethod">
			<template #cardHeader1>
				<n-button strong secondary tertiary round type="success" @click="handleAddWhiteUrlAction">添加</n-button>
				<n-space v-if="batchDeleteWhiteUrlInfoUidArr.length !== 0">
					<n-button strong secondary tertiary round type="error" @click="handleBatchDeleteWhiteUrlLogInfo">删除</n-button>
				</n-space>
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
import {bulletinApi, roleApi, whiteUrlApi} from "@/service";
import {RoleVo} from "@/bean/vo/admin/RoleVo";
import {BulletinVo} from "@/bean/vo/article/BulletinVo";
import {Role} from "@/bean/pojo/admin/Role";
import {WhiteUrlVo} from "@/bean/vo/admin/WhiteUrlVo";
import {WhiteUrl} from "@/bean/pojo/admin/WhiteUrl";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const batchDeleteWhiteUrlInfoUidArr = ref<Array<string>>([])
const router = useRouterPush()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<WhiteUrlVo>>> => {
	return whiteUrlApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: WhiteUrlVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.url} ◔ ‸◔?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			whiteUrlApi.physicalDeleteData(data as WhiteUrl).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.url} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleCheckedRowKeys = (keys: Array<string>) => {
	batchDeleteWhiteUrlInfoUidArr.value = keys
}

const handleModifyAction = (data: WhiteUrlVo) => {
	emitter.emit('whiteUrlManageModifyWhiteUrlAction', data)
}

const handleAddWhiteUrlAction = () => {
	emitter.emit('whiteUrlManageAddWhiteUrlAction')
}

const handleBatchDeleteWhiteUrlLogInfo = () => {
  if (batchDeleteWhiteUrlInfoUidArr.value.length > 0) {
		window.$dialog?.success({
			title: '批量删除白名单',
			content: `删除 ${batchDeleteWhiteUrlInfoUidArr.value.length} 条白名单么`,
			positiveText: '确定',
			negativeText: '取消',
			onPositiveClick() {
				whiteUrlApi.batchDeleteWhiteUrl({uidList: batchDeleteWhiteUrlInfoUidArr.value}).then(result => {
					if (!result.error) {
						window.$message?.success('操作成功')
						emitter.emit(EnumMittEventName.reloadData)
						batchDeleteWhiteUrlInfoUidArr.value = []
					}
				})
			}
		})
	}
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: 'uid',
			key: 'uid',
			titleColSpan: 1,
			width: 100,
			sorter: 'default',
			sortOrder: false,
		},
		{
			title: 'url',
			key: 'url',
			titleColSpan: 1,
			width: 350,
			render(row: WhiteUrlVo) {
				return h(
					// @ts-ignore
					NTag,
					{
						bordered: false,
						type: getRandomTagType()
					},
					{
						default: () => row.url
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
