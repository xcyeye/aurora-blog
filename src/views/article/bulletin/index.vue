<template>
	<div>
		<show-table-data
			:data-table-info="{title: '公告', rowKey: 'uid', striped: true, scrollX: 1600}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:page-sizes="[10, 20, 30]">
			<template #cardHeader1>
				<n-space>
					<n-button v-if="batchDeleteBulletinInfoUidArr.length !== 0" strong secondary tertiary round type="error" @click="handleBatchDeleteBulletin">删除</n-button>
					<n-button strong secondary tertiary round type="success" @click="handleAddBulletinInfo">添加</n-button>
				</n-space>
			</template>
		</show-table-data>
		<n-drawer v-model:show="showDrawer" width="calc(35vw)" placement="left">
			<n-drawer-content :title="addStatus ? '添加公告': `修改${currentBulletinInfo.title}`">
				<n-space vertical>
					<n-card hoverable class="rounded-16px">
						<n-space vertical>
							<n-text>标题</n-text>
							<n-input v-model:value="currentBulletinInfo.title" :autosize="{minRows: 1}" type="textarea"/>
						</n-space>
					</n-card>
					<n-card hoverable class="rounded-16px">
						<n-space vertical>
							<n-space justify="start">
								<n-text>定时</n-text>
								<n-switch v-model:value="currentBulletinInfo.timing" />
								<n-text v-if="currentBulletinInfo.timing">时间</n-text>
								<n-date-picker v-if="currentBulletinInfo.timing"
															 value-format="yyyy-MM-dd HH:mm:ss"
															 v-model:formatted-value="currentBulletinInfo.timingPublishTime"
															 size="small" type="datetime" clearable />
							</n-space>
						</n-space>
					</n-card>
					<n-card hoverable class="rounded-16px">
						<n-space vertical>
							<n-text>内容</n-text>
							<markdown-editor
								:content-max-number="300"
								:render-md-content="addStatus ? ''
								:currentBulletinInfo.content"
								@vditorInput="handleVditorInputValue"
								:min-height="270"
								:bordered="false"/>
						</n-space>
					</n-card>
				</n-space>

				<template #footer>
					<n-space>
						<n-button type="primary" ghost @click="handleModifyOrAddAction" >{{addStatus ? '添加' : '修改'}}</n-button>
					</n-space>
				</template>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import {bulletinApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NSwitch, NTag, useLoadingBar} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {EmailLogVo} from "@/theme/vo/message/EmailLogVo";
import {BulletinVo} from "@/theme/vo/article/BulletinVo";
import {Bulletin} from "@/theme/pojo/article/Bulletin";
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const batchDeleteBulletinInfoUidArr = ref<Array<string>>([])
const showDrawer = ref(false)
const addStatus = ref(false)
const currentBulletinInfo = ref<Bulletin>({})
const loadingBar = useLoadingBar()

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<BulletinVo>>> => {
	return bulletinApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: BulletinVo) => {
	window.$dialog?.success({
		title: `删除 ${data.title} ◔ ‸◔?`,
		content: '确定的话，就点删除',
		positiveText: '删除',
		negativeText: '永久删除',
		onPositiveClick: () => {
			bulletinApi.logicDeleteData(data as Bulletin).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.title} 公告成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
		onNegativeClick: () => {
			bulletinApi.physicalDeleteData(data as Bulletin).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.title} 公告成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleShowBulletinInfoAction = (data: Bulletin) => {
	currentBulletinInfo.value = data
	showDrawer.value = true
	addStatus.value = false
}

const handleModifyOrAddAction = () => {
  if (!StringUtil.haveLength(currentBulletinInfo.value.title)) {
		window.$message?.error('需要输入一个标题(ノへ￣、) ')
		return
	}
	if (!StringUtil.haveLength(currentBulletinInfo.value.content)) {
		window.$message?.error('需要输入一个内容(ノへ￣、) ')
		return
	}
	if (addStatus.value) {
		bulletinApi.insertData(currentBulletinInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('添加成功 ○|￣|_')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		bulletinApi.updateData(currentBulletinInfo.value).then(result => {
			if (result.data && result.data === 1) {
				window.$message?.success('修改成功 ○|￣|_')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}
}

const handleVditorInputValue = (value: string) => {
  currentBulletinInfo.value.content = value
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
			title: '状态',
			key: 'delete',
			titleColSpan: 1,
			width: 100,
			render(row: BulletinVo) {
				return h(
					NTag, {
						bordered: true,
						type: row.delete ? 'warning' : 'success'
					},
					{
						default: () => row.delete ? '已删除' : '正常'
					}
				)
			}
		},
		{
			title: '定时',
			key: 'timing',
			titleColSpan: 1,
			width: 100,
			render(row: BulletinVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.timing,
						onUpdateValue(value: boolean) {
							if (!StringUtil.haveLength(row.timingPublishTime)) {
								window.$message?.error('需要设置定时时间(*￣︿￣) ')
							}else {
								row.timing = value
								bulletinApi.updateData(row).then(result => {
									if (result.data && result.data === 1) {
										window.$message?.success(`修改成功o(￣▽￣)ｄ `)
									}
								})
							}
						}
					}
				)
			}
		},
		{
			title: '定时时间',
			key: 'timingPublishTime',
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
			title: '用户',
			key: 'userUid',
			titleColSpan: 1,
			width: 180,
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
									onClick: () => handleShowBulletinInfoAction(row)
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
	batchDeleteBulletinInfoUidArr.value = keys
}

const handleBatchDeleteBulletin = () => {

}

const handleAddBulletinInfo = () => {
	showDrawer.value = true
	addStatus.value = true
	currentBulletinInfo.value = {}
}

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
