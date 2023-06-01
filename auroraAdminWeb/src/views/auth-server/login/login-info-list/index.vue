<template>
	<div>
		<show-table-data
			:data-table-info="{title: '登录日志', rowKey: 'uid', striped: true, scrollX: 1500}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:page-sizes="[10, 20, 30]">
			<template #cardHeader1>
				<n-space v-if="batchDeleteLoginInfoUidArr.length !== 0">
					<n-button strong secondary tertiary round type="error" @click="handleBatchDeleteLoginInfo">删除</n-button>
				</n-space>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {Condition, PageData} from "@/bean/core/bean";
import {loginInfoApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NTag} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import RequestResult = Service.RequestResult;
import {LoginInfoVo} from "@/bean/vo/auth/LoginInfoVo";
import {emitter} from "@/utils";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const batchDeleteLoginInfoUidArr = ref<Array<string>>([])

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<LoginInfoVo>>> => {
	return loginInfoApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: LoginInfoVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.username} ◔ ‸◔?`,
		content: '永久删除该登录信息?',
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			loginInfoApi.physicalDeleteData(data as LoginInfoVo).then(result => {
				if (result.data === 1) {
					condition.value.delete = false
					window.$message?.error(`删除 ${data.username} 登录信息成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleShowLoginInfoAction = (data: LoginInfoVo) => {
	emitter.emit('authServerLoginInfoShowLogAction', data)
}

const createColumns = ({
												 getTagInfo
											 }: {
	getTagInfo: (row: UserVo) => string
}): Array<DataTableColumn> => {
	return [
		{
			type: 'selection'
		},
		{
			title: '用户名',
			key: 'username',
			titleColSpan: 1,
			width: 120,
			sortOrder: false,
			sorter: 'default',
			fixed: 'left'
		},
		{
			title: 'IP',
			key: 'loginIp',
			titleColSpan: 1,
			width: 150,
			ellipsis: true
		},
		{
			title: '登录地',
			key: 'loginLocation',
			titleColSpan: 1,
			minWidth: 150,
			maxWidth: 170,
			ellipsis: true
		},
		{
			title: '状态',
			key: 'status',
			titleColSpan: 1,
			width: 100,
			render(row: LoginInfoVo) {
				return h(
					NTag, {
						bordered: false,
						type: row.status ? 'success' : 'warning'
					},
					{
						default: () => row.status ? '成功' : '失败'
					}
				)
			}
		},
		{
			title: 'Message',
			key: 'message',
			width: 180,
			titleColSpan: 1
		},
		{
			title: '系统',
			key: 'operationSystemInfo',
			titleColSpan: 1,
			width: 300,
			ellipsis: true
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
									onClick: () => handleShowLoginInfoAction(row)
								},
								{ default: () => '日志' }
							)
						)
					}
				);
			}
		}
	]
}

const columns = ref<Array<DataTableColumn>>(createColumns({
	getTagInfo(row: UserVo) {
		if (row.gender === 'MALE') return 'success'
		if (row.gender === 'FEMALE') return 'warning'
		return 'error'
	}
}))

const handleCheckedRowKeys = (keys: Array<string>) => {
	batchDeleteLoginInfoUidArr.value = keys
}

const handleBatchDeleteLoginInfo = () => {
	window.$dialog?.info({
		title: `删除 ${batchDeleteLoginInfoUidArr.value.length}条登录信息?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			loginInfoApi.batchDeleteLoginInfoByUid({uids: batchDeleteLoginInfoUidArr.value}).then(result => {
				if (result.data) {
					window.$message?.success(`成功删除 ${result.data}条登录信息`)
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
