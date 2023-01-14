<template>
	<div>
		<show-table-data
			:data-table-info="{title: '邮箱发送日志', rowKey: 'uid', striped: true, scrollX: 1800}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:page-sizes="[10, 20, 30]">
			<template #cardHeader1>
				<n-space v-if="batchDeleteEmailLogInfoUidArr.length !== 0">
					<n-button strong secondary tertiary round type="error" @click="handleBatchDeleteEmailLogInfo">删除</n-button>
				</n-space>
			</template>
		</show-table-data>
		<n-drawer v-model:show="showDrawer" width="calc(35vw)" placement="right">
			<n-drawer-content :title="currentEmailLogInfo.subject">
				<n-card :bordered="false">
					<n-space vertical>
						<div v-html="currentEmailLogInfo.content"></div>
					</n-space>
				</n-card>

				<template #footer>
					<n-space>
						<n-button type="primary" ghost @click="handleResendMail">重新发送</n-button>
					</n-space>
				</template>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import {emailLogApi, sendMailApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NTag, useLoadingBar} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {LoginInfoVo} from "@/theme/vo/auth/LoginInfoVo";
import {emitter} from "@/utils";
import {EmailLogVo} from "@/theme/vo/message/EmailLogVo";
import {EmailLog} from "@/theme/pojo/message/EmailLog";
import RequestResult = Service.RequestResult;
import {useLoading} from "@/hooks";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const batchDeleteEmailLogInfoUidArr = ref<Array<string>>([])
const showDrawer = ref(false)
const currentEmailLogInfo = ref<EmailLogVo>({})
const loadingBar = useLoadingBar()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<EmailLogVo>>> => {
	return emailLogApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: EmailLogVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.subject} ◔ ‸◔?`,
		content: '确定的话，就点删除',
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			emailLogApi.physicalDeleteData(data as EmailLog).then(result => {
				if (result.data === 1) {
					condition.value.delete = false
					window.$message?.error(`删除 ${data.subject} 邮件记录成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleShowEmailLogInfoAction = (data: LoginInfoVo) => {
	currentEmailLogInfo.value = data
	showDrawer.value = true
}

const handleResendMail = () => {
	window.$dialog?.warning({
		title: `重新发送 ${currentEmailLogInfo.value.subject} ◔ ‸◔?`,
		content: '部分包含连接的邮件内容，点击可能无效',
		positiveText: 'o(￣▽￣)ｄ ',
		negativeText: '(ノへ￣、)',
		onPositiveClick: () => {
			loadingBar.start()
			sendMailApi.resendCustomMail({emailLogUid: currentEmailLogInfo.value.uid}).then(result => {
				if (result.error) {
					loadingBar.error()
				}else {
					window.$message?.success(`已重新发送 记得提醒他接收 o(￣▽￣)ｄ`);
					emitter.emit(EnumMittEventName.reloadData)
					loadingBar.finish()
					showDrawer.value = false
				}
			})
		},
	})
}

const createColumns = (): Array<DataTableColumn> => {
	return [
		{
			type: 'selection'
		},
		{
			title: '发件人',
			key: 'sender',
			titleColSpan: 1,
			width: 250,
			sortOrder: false,
			sorter: 'default',
			fixed: 'left'
		},
		{
			title: '收件人',
			key: 'receiver',
			titleColSpan: 1,
			width: 250,
			ellipsis: true
		},
		{
			title: '状态',
			key: 'send',
			titleColSpan: 1,
			width: 100,
			render(row: EmailLogVo) {
				return h(
					NTag, {
						bordered: false,
						type: row.send ? 'success' : 'warning'
					},
					{
						default: () => row.send ? '发送成功' : '发送失败'
					}
				)
			}
		},
		{
			title: '标题',
			key: 'subject',
			width: 300,
			titleColSpan: 1,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '内容',
			key: 'content',
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
			render(row: EmailLogVo) {
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
									onClick: () => handleShowEmailLogInfoAction(row)
								},
								{ default: () => '详情' }
							)
						)
					}
				);
			}
		}
	]
}

const columns = ref<Array<DataTableColumn>>(createColumns())

const handleCheckedRowKeys = (keys: Array<string>) => {
	batchDeleteEmailLogInfoUidArr.value = keys
}

const handleBatchDeleteEmailLogInfo = () => {

}

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
