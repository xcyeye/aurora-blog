<template>
	<div>
		<n-modal
			v-model:show="showModal"
			preset="dialog"
			:title="setTimingStatus ? '请设置时间' : '请输入原创链接'"
			:positive-text="setTimingStatus ? '定时' : ''"
			:negative-text="setTimingStatus ? '' : '原创'"
			@positive-click="handleSetTimingStatus"
			@negative-click="handleSetOriginalAction"
		>
			<n-space v-if="setTimingStatus" vertical>
				<n-date-picker
					value-format="yyyy-MM-dd HH:mm:ss"
					v-model:formatted-value="currentArticleInfo.timingPublishTime"
					size="small" type="datetime" clearable />
			</n-space>
			<n-space v-else vertical>
				<n-input round v-model:value="currentArticleInfo.originalArticleUrl" type="text"/>
			</n-space>
		</n-modal>
		<show-table-data
			:data-table-info="{title: '友情链接', rowKey: 'uid', striped: true, scrollX: 2700}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import {articleApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NSwitch, NTag} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getRandomTagType, StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";
import {Article} from "@/theme/pojo/article/Article";
import {ArticleVo} from "@/theme/vo/article/ArticleVo";
import {REGEXP_URL} from "@/config";
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
const setTimingStatus = ref(false)
const currentArticleInfo = ref<Article>({})

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<ArticleVo>>> => {
	return articleApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: ArticleVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.title} ◔ ‸◔?`,
		content: '写作不易，其实可以取消发布(ノへ￣、)',
		positiveText: '临时删除',
		negativeText: '永久删除',
		onPositiveClick: () => {
			articleApi.logicDeleteData(data as Article).then(result => {
				if (result.data === 1) {
					window.$message?.success(`临时删除 ${data.title} 成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
		onNegativeClick: () => {
			articleApi.physicalDeleteData(data as Article).then(result => {
				if (result.data === 1) {
					window.$message?.success(`永久删除 ${data.title} 成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleSetTimingStatus = () => {
	if (!StringUtil.haveLength(currentArticleInfo.value.timingPublishTime)) {
		window.$message?.error('请设置定时发布的时间')
		return
	}
	currentArticleInfo.value.timing = true
	updateArticleInfo(currentArticleInfo.value)
}

const handleSetOriginalAction = () => {
	if (!StringUtil.haveLength(currentArticleInfo.value.originalArticleUrl)) {
		window.$message?.error('请设置原创地址')
		return
	}
	if (!REGEXP_URL.test(currentArticleInfo.value.originalArticleUrl!)) {
		window.$message?.error('你输入的并不是一个URL')
		return
	}
	currentArticleInfo.value.originalArticle = true
	updateArticleInfo(currentArticleInfo.value)
}

const handleModifyAction = (data: ArticleVo) => {
	router.routerPush({
		name: 'article_edit',
		query: {
			pageUid: data.uid
		}
	})
}

const updateArticleInfo = (row: ArticleVo) => {
	articleApi.updateData(row).then(result => {
		if (!result.error && result.data === 1) {
			window.$message?.success(`修改 ${row.title}成功`)
			emitter.emit(EnumMittEventName.reloadData)
		}
	})
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
			fixed: 'left',
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '内容',
			key: 'content',
			titleColSpan: 1,
			width: 200,
			ellipsis: true
		},
		{
			title: '简介',
			key: 'content',
			titleColSpan: 1,
			width: 200,
			ellipsis: true
		},
		{
			title: '点赞量',
			key: 'likeNumber',
			titleColSpan: 1,
			width: 100
		},
		{
			title: '阅读量',
			key: 'likeNumber',
			titleColSpan: 1,
			width: 100
		},
		{
			title: '发布',
			key: 'publish',
			width: 100,
			titleColSpan: 1,
			render(row: ArticleVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.publish,
						onUpdateValue(value: boolean) {
							row.publish = value
							updateArticleInfo(row)
						}
					}
				)
			}
		},
		{
			title: '评论状态',
			key: 'showComment',
			width: 100,
			titleColSpan: 1,
			render(row: ArticleVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.showComment,
						onUpdateValue(value: boolean) {
							row.showComment = value
							updateArticleInfo(row)
						}
					}
				)
			}
		},
		{
			title: '原创状态',
			key: 'originalArticleUrl',
			width: 100,
			titleColSpan: 1,
			render(row: ArticleVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.originalArticleUrl,
						onUpdateValue(value: boolean) {
							if (!value) {
								row.originalArticle = value
								updateArticleInfo(row)
							}else {
								currentArticleInfo.value = row
								showModal.value = true
								setTimingStatus.value = false
							}
						}
					}
				)
			}
		},
		{
			title: '定时',
			key: 'timing',
			width: 100,
			titleColSpan: 1,
			render(row: ArticleVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.timing,
						onUpdateValue(value: boolean) {
							if (!value) {
								row.timing = value
								updateArticleInfo(row)
							}else {
								currentArticleInfo.value = row
								showModal.value = true
								setTimingStatus.value = true
							}
						}
					}
				)
			}
		},
		{
			title: '定时发布时间',
			key: 'timingPublishTime',
			titleColSpan: 1,
			width: 200,
			sorter: 'default',
			sortOrder: false,
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
			title: '标签',
			key: 'tagNames',
			width: 300,
			titleColSpan: 1,
			render (row: ArticleVo) {
				const tags = row.tagNames?.split(",").map((tagKey) => {
					return h(
						// @ts-ignore
						NTag,
						{
							style: {
								marginRight: '6px'
							},
							type: getRandomTagType(),
							bordered: false
						},
						{
							default: () => tagKey
						}
					)
				})
				return tags
			}
		},
		{
			title: '分类',
			key: 'categoryNames',
			width: 300,
			titleColSpan: 1,
			render (row: ArticleVo) {
				const tags = row.categoryNames?.split(",").map((tagKey) => {
					return h(
						// @ts-ignore
						NTag,
						{
							style: {
								marginRight: '6px'
							},
							type: getRandomTagType(),
							bordered: false
						},
						{
							default: () => tagKey
						}
					)
				})
				return tags
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
	console.log("发送重置搜索");
	console.log(condition.value);
	console.log("------------------");
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
