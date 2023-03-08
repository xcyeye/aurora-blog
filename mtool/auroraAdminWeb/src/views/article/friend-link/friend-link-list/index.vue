<template>
	<div>
		<n-modal
			v-model:show="showModal"
			preset="dialog"
			:title="currentLinkInfo.publish ? '下架' : '审核通过'"
			:positive-text="!currentLinkInfo.publish ? '(○｀ 3′○)' : ''"
			:negative-text="!currentLinkInfo.publish? '' : '→)╥﹏╥)'"
			@positive-click="handleLinkPublishStatus"
			@negative-click="handleLinkPublishStatus"
		>
			<n-input type="textarea" :placeholder="!currentLinkInfo.publish ? '审核通过后系统会发邮件通知该用户o(￣▽￣)ｄ ' : '可以告知对方下架原因 (ノへ￣、)'" :autosize="{minRows: 4}" v-model:value="currentLinkInfo.replyMessage"/>
		</n-modal>
		<show-table-data
			:data-table-info="{title: '友情链接', rowKey: 'uid', striped: true, scrollX: 1700}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			:page-sizes="[1,2,3,4,5,6]">
			<template #cardHeader1>
				<n-space>
					<n-button strong secondary tertiary round type="success" @click="handleAddLinkAction">添加</n-button>
				</n-space>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {linkApi} from "@/service";
import {DataTableColumn, NA, NAvatar, NButton, NSpace, NSwitch, NText} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {LinkVo} from "@/bean/vo/article/LinkVo";
import {useRouterPush} from "@/composables";
import {Link} from "@/bean/pojo/article/Link";
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()
const showModal = ref(false)
const currentLinkInfo = ref<Link>({})

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<LinkVo>>> => {
	return linkApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: LinkVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.linkUrl} ◔ ‸◔?`,
		content: '其实还有个下架选项(ノへ￣、)',
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			linkApi.physicalDeleteData(data as Link).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.linkUrl} 成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleModifyAction = (data: LinkVo) => {
	emitter.emit('linkManageModifyLinkAction', data)
}

const handleAddLinkAction = () => {
	emitter.emit('linkManageAddLinkAction')
}

const handleLinkPublishStatus = () => {
	if (!StringUtil.haveLength(currentLinkInfo.value.replyMessage)) {
		window.$message?.error('请输入回复消息')
		return
	}
	currentLinkInfo.value.publish = !currentLinkInfo.value.publish
  if (currentLinkInfo.value.publish) {
		// 从未发布到发布
		linkApi.updateLinkPublishStatus(currentLinkInfo.value).then(result => {
			if (!result.error && result.data === 1) {
				window.$message?.success('已修改状态为发布 o(￣▽￣)ｄ ')
				showModal.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}else {
		// 从发布到未发布
		linkApi.updateLinkPublishStatus(currentLinkInfo.value).then(result => {
			if (!result.error && result.data === 1) {
				window.$message?.success('已下架该链接 (ノへ￣、)')
				showModal.value = false
				emitter.emit(EnumMittEventName.reloadData)
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
			title: '头像',
			key: 'linkLogo',
			titleColSpan: 1,
			width: 70,
			fixed: 'left',
			render(row: LinkVo) {
				return h(
					// @ts-ignore
					NAvatar,
					{
						round: true,
						size: 'small',
						src: row.linkLogo,
						fallbackSrc: ''
					}
				)
			}
		},
		{
			title: '地址',
			key: 'linkUrl',
			titleColSpan: 1,
			width: 250,
			fixed: 'left',
			render(row: LinkVo) {
				return h(
					NA,
					{
						href: row.linkUrl,
						target: '_blank'
					},
					{
						default: () => h(
							NText,
							{
							},
							{
								default: () => row.linkTitle
							}
						)
					}
				)
			}
		},
		{
			title: '描述',
			key: 'linkDescription',
			titleColSpan: 1,
			width: 300,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '邮箱',
			key: 'email',
			titleColSpan: 1,
			width: 200,
			ellipsis: true,
			render(row: LinkVo) {
				return h(
					NText,
					{
						onClick() {
							router.routerPush({
								name: 'message-center_send-mail',
								query: {
									receiverEmail: row.email
								}
							}, true)
						}
					},
					{
						default: () => row.email
					}
				)
			}
		},
		{
			title: '审核状态',
			key: 'publish',
			width: 100,
			titleColSpan: 1,
			render(row: LinkVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.publish,
						onUpdateValue(value: boolean) {
							showModal.value = true
							currentLinkInfo.value = row
						}
					}
				)
			}
		},
		{
			title: 'QQ号',
			key: 'qqNumber',
			titleColSpan: 1,
			width: 130,
			render(row: LinkVo) {
				return h(
					NA, {
						href: `tencent://message/?uin=${row.qqNumber}`
					},
					{
						default: () => h(
							NText,
							{

							},
							{
								default: () => row.qqNumber
							}
						)
					}
				)
			}
		},
		{
			title: '所属用户',
			key: 'userUid',
			titleColSpan: 1,
			width: 150,
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
