<template>
	<div>
		<n-card class="h-full shadow-sm rounded-16px">
			<n-tabs type="line" animated>
				<n-tab-pane name="oasis" tab="列表">
					<show-table-data
						:data-table-info="{title: '', rowKey: 'uid', striped: true, bordered: false}"
						:data-table-columns="tagColumns"
						:query-data-method="queryTagDataMethod">
					</show-table-data>
				</n-tab-pane>
				<n-tab-pane name="the beatles" tab="图表">
					<tag-chart/>
				</n-tab-pane>
				<template #suffix>
					<n-space>
						<n-button strong secondary tertiary round type="success" @click="handleAddTagAction">添加</n-button>
					</n-space>
				</template>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {tagApi} from "@/service";
import {DataTableColumn, NButton, NSpace} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter} from "@/utils";
import {useRouterPush} from "@/composables";
import {TagVo} from "@/bean/vo/article/TagVo";
import {Tag} from "@/bean/pojo/article/Tag";
import RequestResult = Service.RequestResult;
import TagChart from '../chart/index.vue'

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()
const showModal = ref(false)
const currentTagInfo = ref<Tag>({})

const queryTagDataMethod = (condition: Condition): Promise<RequestResult<PageData<TagVo>>> => {
	return tagApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: TagVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.title} ◔ ‸◔?`,
		content: '其实还有个下架选项(ノへ￣、)',
		positiveText: '临时删除',
		negativeText: '永久删除',
		onPositiveClick: () => {
			tagApi.logicDeleteData(data as Tag).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.title} 成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
		onNegativeClick: () => {
			tagApi.physicalDeleteData(data as Tag).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.title} 成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleModifyAction = (data: TagVo) => {
	emitter.emit('tagManageModifyTagAction', data)
}

const handleAddTagAction = () => {
	emitter.emit('tagManageAddTagAction')
}

const createTagColumns = (): Array<DataTableColumn> => {
	return [
		{
			type: 'selection'
		},
		{
			title: '名称',
			key: 'title',
			titleColSpan: 1,
			width: 150,
			sorter: 'default',
			sortOrder: false,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '简介',
			key: 'summary',
			titleColSpan: 1,
			width: 200,
			ellipsis: {
				tooltip: true
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

const tagColumns = ref<Array<DataTableColumn>>(createTagColumns())

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
