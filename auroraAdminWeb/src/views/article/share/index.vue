<template>
	<div>
		<show-table-data
			:data-table-info="{title: '说说', rowKey: 'uid', striped: true, scrollX: 1600}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:page-sizes="[10, 20, 30]">
			<template #cardHeader1>
				<n-space>
					<n-button v-if="batchDeleteTalkInfoUidArr.length !== 0" strong secondary tertiary round type="error" @click="handleBatchDeleteBulletin">删除</n-button>
					<n-button strong secondary tertiary round type="success" @click="handleAddTalkInfo">添加</n-button>
				</n-space>
			</template>
		</show-table-data>
		<n-drawer v-model:show="showDrawer" width="calc(35vw)" placement="left">
			<n-drawer-content :title="addStatus ? '添加说说': `修改${currentTalkInfo.title}`">
				<n-tabs type="line" animated>
					<n-tab-pane name="content" tab="内容">
						<n-space vertical>
							<n-card hoverable class="rounded-16px">
								<n-space vertical>
									<n-text>标题</n-text>
									<n-input v-model:value="currentTalkInfo.title" :autosize="{minRows: 1}" type="textarea"/>
								</n-space>
							</n-card>
							<n-card hoverable class="rounded-16px">
								<n-space vertical>
									<markdown-editor
										:content-max-number="300"
										:render-md-content="addStatus ? '' : currentTalkInfo.content"
										@vditorInput="handleVditorInputValue"
										:min-height="270"
										:bordered="false"/>
								</n-space>
							</n-card>
						</n-space>
					</n-tab-pane>
					<n-tab-pane name="picture" tab="照片">
						<aurora-gallery @clickPictureDesc="clickPictureDesc" :show-load-more-but="false" :picture-list="pictureFileArr">
							<n-space justify="end">
								<n-gradient-text type="success" style="cursor: pointer">移除</n-gradient-text>
							</n-space>
						</aurora-gallery>
						<upload-file
							@handleFinishUploadFile="handleFinishUploadFile"
							:accept-file-type-str="['.png','.jpg','.jpeg','.mp4','.wmv','.avi','.mov']"
							:show-upload-dragger="true"
							:max-upload-file-number="20"
							:multiple-upload-file="true"
							:parameter-data="{storageMode: 0, userUid: authStore.userInfo.user_uid, summary: `${authStore.userInfo.username} 从说说上传的图片`}"
						/>
					</n-tab-pane>
					<n-tab-pane name="comment" tab="评论">
						<blog-comment
							:user-uid="currentTalkInfo.userUid"
							:page-uid="currentTalkInfo.uid"
							:page-path="`/showSpace/${currentTalkInfo.userUid}/${currentTalkInfo.uid}`"
							:query-regexp="`/showSpace/${currentTalkInfo.userUid}/${currentTalkInfo.uid}`"
							reply-page-type="TALK"/>
					</n-tab-pane>
				</n-tabs>

				<template #footer>
					<n-space>
						<n-button type="primary" ghost @click="handleModifyOrAddAction(null)" >{{addStatus ? '添加' : '修改'}}</n-button>
					</n-space>
				</template>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {bulletinApi, fileApi, talkApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NSwitch, NTag, UploadFileInfo} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {EmailLogVo} from "@/bean/vo/message/EmailLogVo";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import {Talk} from "@/bean/pojo/article/Talk";
import RequestResult = Service.RequestResult;
import {useAuthStore} from "@/store";
import {FileVo} from "@/bean/vo/file/fileVo";

defineComponent({name: 'index'});

interface PictureInfo {
	uid?: string,
	src: string
}

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const batchDeleteTalkInfoUidArr = ref<Array<string>>([])
const showDrawer = ref(false)
const addStatus = ref(false)
const currentTalkInfo = ref<Talk>({})
const pictureFileArr = ref<Array<FileVo>>([])
const authStore = useAuthStore()
const currentTalkCommentUidArr = ref<Array<string>>([])
const currentPictureFile = ref<FileVo>({})


const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<TalkVo>>> => {
	return talkApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: TalkVo) => {
	window.$dialog?.success({
		title: `删除 ${data.title} ◔ ‸◔?`,
		content: '确定的话，就点删除',
		positiveText: '删除',
		negativeText: '永久删除',
		onPositiveClick: () => {
			talkApi.logicDeleteData(data as Talk).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.title} 说说成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
		onNegativeClick: () => {
			talkApi.physicalDeleteData(data as Talk).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.title} 说说成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleShowTalkInfoAction = (data: TalkVo) => {
	pictureFileArr.value = []
	currentTalkInfo.value = data
	showDrawer.value = true
	addStatus.value = false

	if (!StringUtil.haveLength(data.pictureSrcList)) return
	data.pictureSrcList?.split(",").forEach(v => {
		// fileApi.queryOneDataByUid({uid: v}).then(result => {
		// 	if (!result.error && result.data) {
		//
		// 	}
		// })
		pictureFileArr.value.push({
			path: v
		})
	})

	if (StringUtil.haveLength(currentTalkInfo.value.commentUids)) {
		currentTalkCommentUidArr.value = currentTalkInfo.value.commentUids?.split(",")!
	}
}

const clickPictureDesc = (pictureFile: FileVo) => {
	pictureFileArr.value.splice(pictureFileArr.value.indexOf(pictureFile), 1);
	currentTalkInfo.value.pictureSrcList = pictureFileArr.value.map(v => v.path).join(",")
}

const removeTalkPicture = () => {
	// pictureFileArr.value.splice(pictureFileArr.value.indexOf(currentPictureFile.value))
}

const handleModifyOrAddAction = (row?: TalkVo | null) => {
	if (row) {
		bulletinApi.updateData(row!).then(result => {
			if (result.data && result.data === 1) {
				window.$message?.success('修改成功 ○|￣|_')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}
	// if (!StringUtil.haveLength(currentTalkInfo.value.title)) {
	// 	window.$message?.error('需要输入一个标题(ノへ￣、) ')
	// 	return
	// }
	if (!StringUtil.haveLength(currentTalkInfo.value.content)) {
		window.$message?.error('需要输入一个内容(ノへ￣、) ')
		return
	}
	if (addStatus.value) {
		talkApi.insertData(currentTalkInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('添加成功 ○|￣|_')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		talkApi.updateData(currentTalkInfo.value).then(result => {
			if (result.data && result.data === 1) {
				window.$message?.success('修改成功 ○|￣|_')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}
}

const handleVditorInputValue = (value: string) => {
	currentTalkInfo.value.content = value
}

const createColumns = (): Array<DataTableColumn> => {
	return [
		{
			type: 'selection'
		},
		{
			title: '标题',
			key: 'title',
			titleColSpan: 1,
			width: 250,
			sortOrder: false,
			sorter: 'default',
			fixed: 'left',
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '删除',
			key: 'delete',
			titleColSpan: 1,
			width: 100,
			render(row: TalkVo) {
				return h(
					NTag, {
						bordered: false,
						type: row.delete ? 'warning' : 'success'
					},
					{
						default: () => row.delete ? '已删除' : '正常'
					}
				)
			}
		},
		{
			title: '状态',
			key: 'show',
			titleColSpan: 1,
			width: 100,
			render(row: TalkVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.show,
						onUpdateValue(value: boolean) {
							row.show = value
							handleModifyOrAddAction(row)
						}
					}
				)
			}
		},
		{
			title: '评论',
			key: 'showComment',
			titleColSpan: 1,
			width: 100,
			render(row: TalkVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.showComment,
						onUpdateValue(value: boolean) {
							row.showComment = value
							handleModifyOrAddAction(row)
						}
					}
				)
			}
		},
		{
			title: '用户',
			key: 'userUid',
			titleColSpan: 1,
			width: 200,
			ellipsis: true
		},
		{
			title: '内容',
			key: 'content',
			titleColSpan: 1,
			width: 300,
			ellipsis: true
		},
		{
			title: '点赞',
			key: 'likeNumber',
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
									onClick: () => handleShowTalkInfoAction(row)
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
	batchDeleteTalkInfoUidArr.value = keys
}

const handleBatchDeleteBulletin = () => {

}

const handleAddTalkInfo = () => {
	showDrawer.value = true
	addStatus.value = true
	currentTalkInfo.value = {}
}

const handleFinishUploadFile = (file: UploadFileInfo) => {
	pictureFileArr.value.push({
		uid: file.thumbnailUrl!,
		path: file.url!
	})
	if (!currentTalkInfo.value.pictureSrcList) {
		currentTalkInfo.value.pictureSrcList = file.url
	}else {
		currentTalkInfo.value.pictureSrcList = currentTalkInfo.value.pictureSrcList + ',' + file.url
	}
}

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
