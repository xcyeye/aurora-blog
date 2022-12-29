<template>
	<div class="h-full">
		<n-card class="h-full shadow-sm rounded-16px">
			<n-tabs ref="tabsInstRef" v-model:value="tabPaneValue" type="line" animated>
				<n-tab-pane name="commentList" tab="列表">
					<show-table-data
						@handleCheckedRowKeys="handleCheckedRowKeys"
						:data-table-info="{rowKey: 'uid', striped: true, scrollX: 2500, bordered: false}"
						:data-table-columns="columns"
						:query-data-method="queryDataMethod">
					</show-table-data>
				</n-tab-pane>
				<n-tab-pane name="commentDetail" tab="详情">
					<blog-comment :page-path="currentCommentInfo.path"
												:page-uid="currentCommentInfo.uid"
												:parent-comment-uid-arr="[currentCommentInfo.uid]"/>
				</n-tab-pane>
				<template #suffix>
					<n-button v-if="batchDeleteCommentInfoUidArr.length !== 0" strong secondary tertiary round type="error" @click="handleBatchDeleteComment">删除</n-button>
				</template>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import {commentApi,} from "@/service";
import {DataTableColumn, NA, NAvatar, NButton, NSpace, NSwitch, NTag, TabsInst} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getRandomTagType} from "@/utils";
import {useRouterPush} from "@/composables";
import {CommentVo} from "@/theme/vo/comment/CommentVo";
import {Comment} from "@/theme/pojo/comment/Comment";
import {useAuthStore} from "@/store";
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()
const parentCommentUidArr = ref<Array<string>>([])
const showCommentInfo = ref<ShowCommentVo>({})
const currentCommentInfo = ref<CommentVo>({})
const tabsInstRef = ref<TabsInst | null>(null)
const tabPaneValue = ref<string>('commentList')
const autoStore = useAuthStore()
const batchDeleteCommentInfoUidArr = ref<Array<string>>([])

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<CommentVo>>> => {
	return commentApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: CommentVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.username}用户的评论 ◔ ‸◔?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			commentApi.physicalDeleteData(data as Comment).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.username} 用户的评论成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleModifyAction = (data: CommentVo) => {
	emitter.emit('commentManageModifyCommentAction', data)
}

const loadCommentDetailInfo = () => {
  if (parentCommentUidArr.value.length === 0) return

	commentApi.queryListCommentByUidArr({commentUidArr: parentCommentUidArr.value}).then(result => {
		if (!result.error && result.data) {
			showCommentInfo.value = result.data
		}
	})
}

const handleShowDetailAction = (data: CommentVo) => {
	// tabsInstRef.value!.reverse()
	// nextTick(() => tabsInstRef.value?.syncBarPosition())
	parentCommentUidArr.value = []
	showCommentInfo.value = {}
	currentCommentInfo.value = data
	parentCommentUidArr.value.push(currentCommentInfo.value.uid!)
	loadCommentDetailInfo()
	tabPaneValue.value = 'commentDetail'
}


const handleCheckedRowKeys = (keys: Array<string>) => {
	batchDeleteCommentInfoUidArr.value = keys
}

const handleBatchDeleteComment = () => {
  window.$dialog?.success({
		title: `删除${batchDeleteCommentInfoUidArr.value.length}条评论`,
		content: `你确定要删除 ${batchDeleteCommentInfoUidArr.value.length}条评论么`,
		positiveText: '确定',
		negativeText: '再想想',
		onPositiveClick() {

		}
	})
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '头像',
			key: 'avatar',
			titleColSpan: 1,
			width: 50,
			fixed: 'left',
			render(row: CommentVo) {
				return h(
					// @ts-ignore
					NA,
					{
						href: row.site,
						target: '_blank'
					},
					{
						default: () => h(
							// @ts-ignore
							NAvatar,
							{
								round: true,
								size: 'medium',
								src: row.avatar,
								fallbackSrc: ''
							}
						)
					}
				)
			}
		},
		{
			title: '用户名',
			key: 'username',
			titleColSpan: 1,
			width: 120,
			ellipsis: true
		},
		{
			title: '评论',
			key: 'content',
			titleColSpan: 1,
			width: 200,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '删除状态',
			key: 'delete',
			width: 100,
			titleColSpan: 1,
			render(row: CommentVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.delete,
						onUpdateValue(value: boolean) {
							row.delete = value
							commentApi.updateData(row as Comment).then(result => {
								if (result.data && result.data === 1) {
									window.$message?.success('修改成功')
								}
							})
						}
					}
				)
			}
		},
		{
			title: '展示状态',
			key: 'showComment',
			width: 100,
			titleColSpan: 1,
			render(row: CommentVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.showComment,
						onUpdateValue(value: boolean) {
							row.showComment = value
							commentApi.updateData(row as Comment).then(result => {
								if (result.data && result.data === 1) {
									window.$message?.success('修改成功')
								}
							})
						}
					}
				)
			}
		},
		{
			title: '邮箱',
			key: 'email',
			titleColSpan: 1,
			width: 150,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '邮件提醒',
			key: 'emailNotice',
			titleColSpan: 1,
			width: 100,
			render(row: CommentVo) {
				return h(
					NTag,
					{
						type: row.emailNotice ? 'success' : 'error',
						bordered: false,
						style: {
							borderRadius: '10px'
						}
					},
					{
						default: () => row.emailNotice ? '已提醒' : '未发送或失败'
					}
				)
			}
		},
		{
			title: '类型',
			key: 'pageType',
			titleColSpan: 1,
			width: 100,
			render(row: CommentVo) {
				return h(
					// @ts-ignore
					NTag,
					{
						type: getRandomTagType(),
						bordered: false,
						style: {
							borderRadius: '10px'
						}
					},
					{
						default: () => row.pageType
					}
				)
			}
		},
		{
			title: '评论页面',
			key: 'path',
			titleColSpan: 1,
			width: 130,
			ellipsis: {
				tooltip: true
			},
			render(row: CommentVo) {
				return h(
					NA,
					{
						href: row.path,
						target: '_blank'
					},
					{
						default: () => row.path
					}
				)
			}
		},
		{
			title: '所属用户',
			key: 'userUid',
			titleColSpan: 1,
			width: 170
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
									onClick: () => handleShowDetailAction(row)
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

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
