<template>
	<div>
		<show-table-data
			:data-table-info="{title: '用户管理', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			:page-sizes="[1,2,3,4,5,6]">
			<template #cardHeader1>
				<n-space>
					<n-button strong secondary tertiary round type="success" @click="handleAddUserAction">添加用户</n-button>
				</n-space>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {emailApi, userApi} from "@/service";
import {DataTableColumn, NButton, NSpace} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter} from "@/utils";
import {EmailVo} from "@/bean/vo/message/EmailVo";
import {Email} from "@/bean/pojo/message/Email";
import RequestResult = Service.RequestResult;
import {useRouterPush} from "@/composables";
import {useRouter} from "vue-router";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<EmailVo>>> => {
	return emailApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: EmailVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.email} ◔ ‸◔?`,
		content: '用户删除被永久删除之后，此用户绑定的信息将会被取消',
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			emailApi.physicalDeleteData(data as Email).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.email} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleModifyAction = (data: EmailVo) => {
	emitter.emit('messageCenterManageModifyEmailAction', data)
}

const handleSendMailAction = (data: EmailVo) => {
	router.routerPush({
		name: 'message-center_send-mail',
		query: {
			receiverEmail: data.email
		}
	})
}

const handleAddUserAction = () => {
	emitter.emit('messageCenterManageAddEmailAction')
}

async function getUserInfo(row: EmailVo) {
	await userApi.queryOneDataByUid({uid: row.userUid}).then(result => {
		if (result.data) {
			return result.data.username
		}else {
			return '未知用户'
		}
	})
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '邮箱',
			key: 'email',
			titleColSpan: 1,
			width: 200,
			sortOrder: false,
			sorter: 'default'
		},
		{
			title: '所属用户',
			key: 'userUid',
			titleColSpan: 1,
			width: 170
		},
		{
			title: '端口',
			key: 'port',
			titleColSpan: 1,
			width: 100,
		},
		{
			title: '协议',
			key: 'protocol',
			titleColSpan: 1,
			width: 100,
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
							),
							h(
								NButton,
								{
									size: 'small',
									type: 'primary',
									ghost: true,
									onClick: () => handleSendMailAction(row)
								},
								{ default: () => '发送' }
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
