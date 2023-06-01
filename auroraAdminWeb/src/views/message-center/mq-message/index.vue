<template>
	<div>
		<show-table-data
			:data-table-info="{title: 'RabbitMQ投递记录', rowKey: 'uid', striped: true, scrollX: 1800}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:page-sizes="[10, 20, 30]">
			<template #cardHeader1>
				<n-space v-if="batchDeleteMessageLogInfoUidArr.length !== 0">
					<n-button strong secondary tertiary round type="error" @click="handleBatchDeleteEmailLogInfo">删除</n-button>
				</n-space>
			</template>
		</show-table-data>
		<n-drawer v-model:show="showDrawer" width="calc(35vw)" placement="right">
			<n-drawer-content title="RabbitMQ">
				<n-card :bordered="false">
					<n-code
						:hljs="hljs"
						:word-wrap="true"
						:code="JSON.stringify(JSON.parse(currentMessageLogInfo.message), null, 4)"
						language="json"
					/>
				</n-card>

				<template #footer>
					<n-space :size="30">
						<n-button type="primary" ghost @click="handleResendMqMessage">重新投递</n-button>
						<n-button type="primary" :disabled="currentMessageLogInfo.ackStatus" ghost @click="handleAckMessage"
						>应答</n-button
						>
						<n-button type="primary" ghost :disabled="currentMessageLogInfo.consumeStatus" @click="handleConsumerMessage"
						>消费</n-button
						>
					</n-space>
				</template>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {messageLogApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NTag, useLoadingBar} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getRandomTagType} from "@/utils";
import {EmailLogVo} from "@/bean/vo/message/EmailLogVo";
import {MessageLogVo} from "@/bean/vo/message/MessageLogVo";
import {MessageLog} from "@/bean/pojo/message/MessageLog";
import hljs from 'highlight.js/lib/core';
import json from 'highlight.js/lib/languages/json';
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});
hljs.registerLanguage('json', json);

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const batchDeleteMessageLogInfoUidArr = ref<Array<string>>([])
const showDrawer = ref(false)
const currentMessageLogInfo = ref<MessageLog>({})
const loadingBar = useLoadingBar()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<MessageLogVo>>> => {
	return messageLogApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: MessageLogVo) => {
	window.$dialog?.warning({
		title: '删除RabbitMQ投递记录',
		content: `删除 ${data.exchange}中的此条投递记录?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			messageLogApi.physicalDeleteData(data as MessageLog).then(result => {
				if (result.data === 1) {
					condition.value.delete = false
					window.$message?.success(`删除 ${data.exchange} 中间件消息成功 `);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleShowEmailLogInfoAction = (data: MessageLogVo) => {
	currentMessageLogInfo.value = data
	showDrawer.value = true
}

const handleResendMqMessage = () => {
	window.$dialog?.warning({
		title: `重新投递`,
		content: `重新投递${currentMessageLogInfo.value.exchange} 中的此条MQ消息?`,
		positiveText: '确定',
		negativeText: '取消',
		onPositiveClick: () => {
			loadingBar.start()
			messageLogApi.resendRabbitMqMessage({uid: currentMessageLogInfo.value.uid}).then(result => {
				if (result.error) {
					loadingBar.error()
				}else {
					window.$message?.success(`已重新投递 `);
					emitter.emit(EnumMittEventName.reloadData)
					loadingBar.finish()
					showDrawer.value = false
				}
			})
		},
	})
}

const handleAckMessage = () => {
	currentMessageLogInfo.value.ackStatus = true
	messageLogApi.updateData(currentMessageLogInfo.value).then(result => {
		if (!result.error) {
			window.$message?.success(`手动应答成功 `);
		}
		showDrawer.value = false
		emitter.emit(EnumMittEventName.reloadData)
	})
}

const handleConsumerMessage = () => {
	currentMessageLogInfo.value.consumeStatus = true
	messageLogApi.updateData(currentMessageLogInfo.value).then(result => {
		if (!result.error) {
			window.$message?.success(`手动消费成功 `);
		}
		showDrawer.value = false
		emitter.emit(EnumMittEventName.reloadData)
	})
}

const createColumns = (): Array<DataTableColumn> => {
	return [
		{
			type: 'selection'
		},
		{
			title: 'routingKey',
			key: 'routingKey',
			titleColSpan: 1,
			width: 220,
			sortOrder: false,
			sorter: 'default',
			fixed: 'left'
		},
		{
			title: '交换机',
			key: 'exchange',
			titleColSpan: 1,
			width: 250,
			ellipsis: true
		},
		{
			title: '应答状态',
			key: 'ackStatus',
			titleColSpan: 1,
			width: 100,
			render(row: MessageLogVo) {
				return h(
					NTag, {
						bordered: false,
						type: row.ackStatus ? 'success' : 'warning'
					},
					{
						default: () => row.ackStatus ? '应答' : '未应答'
					}
				)
			}
		},
		{
			title: '消费状态',
			key: 'consumeStatus',
			titleColSpan: 1,
			width: 100,
			render(row: MessageLogVo) {
				return h(
					NTag, {
						bordered: false,
						type: row.consumeStatus ? 'success' : 'warning'
					},
					{
						default: () => row.consumeStatus ? '已消费' : '未消费'
					}
				)
			}
		},
		{
			title: '交换机类型',
			key: 'exchangeType',
			width: 100,
			titleColSpan: 1,
			render(row: MessageLogVo) {
				return h(
					// @ts-ignore
					NTag,
					{
						type: getRandomTagType(),
						bordered: false
					},
					{
						default: () => row.exchangeType
					}
				)
			}
		},
		{
			title: '消息',
			key: 'message',
			titleColSpan: 1,
			width: 230,
			ellipsis: true
		},
		{
			title: '错误消息',
			key: 'errorMessage',
			titleColSpan: 1,
			width: 230,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '重试次数',
			key: 'tryCount',
			titleColSpan: 1,
			width: 85
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
	batchDeleteMessageLogInfoUidArr.value = keys
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
