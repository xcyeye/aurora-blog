<template>
	<div>
		<show-table-data
			:show-table-data="false"
			:data-table-info="{rowKey: 'uid', striped: true, scrollX: 1800, bordered: false}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			:page-sizes="[10, 20, 30]">
			<template #content>
				<div>
					<n-grid
						ref="picture-grid"
						:collapsed="false"
						cols="4 s:3 m:4 l:5 xl:4 2xl:7"
						:x-gap="20"
						:y-gap="20"
						:item-responsive="true"
						responsive="self"
					>
					</n-grid>
				</div>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {fileApi, loginInfoApi} from "@/service";
import {DataTableColumn, NAvatar, NButton, NSpace, NTag, NText} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getFileFormat, getRandomTagType, isImage} from "@/utils";
import {FileVo} from "@/bean/vo/file/fileVo";
import {copyContent} from "@/plugins";
import RequestResult = Service.RequestResult;

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
})
const batchDeleteLoginInfoUidArr = ref<Array<string>>([])

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<FileVo>>> => {
	return fileApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: FileVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.fileName} ?`,
		content: '文件不会被回收，是永久删除?',
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			fileApi.deleteFile({uid:data.uid}).then(result => {
				if (result.data && result.data === 1) {
					window.$message?.success(`${data.fileName} 已被删除 `)
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
	})
}

const handleOpenFileAction = (data: FileVo) => {
	window.open(data.path!, '_blank');
}

const createColumns = (): Array<DataTableColumn> => {
	return [
		{
			title: '文件',
			key: 'path',
			titleColSpan: 1,
			width: 65,
			fixed: 'left',
			render(row: FileVo) {
				if (isImage(row.fileName!)) {
					return h(
						// @ts-ignore
						NAvatar,
						{
							size: 'small',
							src: row.path
						}
					)
				}else {
					return h(
						NAvatar,
						{
							size: 'small'
						},
						{
							// @ts-ignore
							default: () => getFileFormat(row.fileName)
						}
					)
				}
			}
		},
		{
			title: '文件名',
			key: 'fileName',
			titleColSpan: 1,
			width: 150,
			ellipsis: true,
			sorter: 'default',
			sortOrder: false,
			render(row: FileVo) {
				return h(
					NTag,
					{
						checkable: true,
						bordered: false,
						onUpdateChecked() {
							copyContent(row.fileName)
						}
					},
					{
						default: () => row.fileName
					}
				)
			}
		},
		{
			title: 'URL',
			key: 'path',
			titleColSpan: 1,
			width: 250,
			ellipsis: true,
			render(row: FileVo) {
				return h(
					NTag,
					{
						checkable: true,
						bordered: false,
						onUpdateChecked() {
							copyContent(row.path)
						}
					},
					{
						default: () => row.path
					}
				)
			}
		},
		{
			title: '存储路径',
			key: 'storagePath',
			titleColSpan: 1,
			width: 200,
			ellipsis: true,
			render(row: FileVo) {
				return h(
					NTag,
					{
						checkable: true,
						bordered: false,
						onUpdateChecked() {
							copyContent(row.storagePath)
						}
					},
					{
						default: () => row.storagePath
					}
				)
			}
		},
		{
			title: '大小',
			key: 'size',
			titleColSpan: 1,
			width: 130,
			sorter: 'default',
			sortOrder: false,
			render(row: FileVo) {
				return h(
					NText,
					{

					},
					{
						default: () => {
							const mbSize = (row.size! / 1024/ 1024)
							if (mbSize < 1) {
								return (row.size! / 1024).toFixed(2) + ' KB'
							}else if (mbSize > 1024) {
								return (row.size! / 1024 / 1024 / 1024).toFixed(2) + ' GB'
							}else return (row.size! / 1024 / 1024).toFixed(2) + ' MB'
						}
					}
				)
			}
		},
		{
			title: '存储模式',
			key: 'storageMode',
			titleColSpan: 1,
			width: 170,
			render(row: FileVo) {
				return h(
					// @ts-ignore
					NTag, {
						bordered: false,
						type: getRandomTagType(),
					},
					{
						default: () => {
							if (row.storageMode === 0) return 'LOCAL_STORAGE'
							if (row.storageMode === 1) return 'ALIYUN_OSS_STORAGE'
							return 'LOCAL_STORAGE'
						}
					}
				)
			}
		},
		{
			title: '描述',
			key: 'summary',
			width: 200,
			titleColSpan: 1,
			ellipsis: {
				tooltip: true
			},
		},
		{
			title: '用户Uid',
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
									onClick: () => handleOpenFileAction(row)
								},
								{ default: () => '打开' }
							)
						)
					}
				);
			}
		}
	]
}

const columns = ref<Array<DataTableColumn>>(createColumns())

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

	emitter.on('fileCenterSetQueryCondition', e => {
		if (e) {
			condition.value = e as Condition
			emitter.emit(EnumMittEventName.reloadData)
		}
	})
})

</script>

<style scoped>

</style>
