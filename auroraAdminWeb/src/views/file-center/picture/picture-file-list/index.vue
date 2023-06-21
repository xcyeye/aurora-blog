<template>
	<div>
		<show-table-data
			:data-table-info="{rowKey: 'uid', striped: true, scrollX: 1800, bordered: false}"
			:data-table-columns="columns"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			:query-data-method="queryDataMethod"
			:page-sizes="[10, 20, 30]">
			<template #cardHeader1>
				<n-space v-if="batchDeleteFileUidArr.length !== 0">
					<n-button strong secondary tertiary round type="error" @click="handleBatchDeleteFileInfo">删除</n-button>
				</n-space>
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
import {emitter, getFileFormat, getRandomTagType, getRealImageUrl, isImage, StringUtil} from "@/utils";
import {FileVo} from "@/bean/vo/file/fileVo";
import {copyContent} from "@/plugins";
import RequestResult = Service.RequestResult;
import {useSysSettingStore} from "@/store";
import {REGEXP_URL} from "@/config";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
})
const batchDeleteFileUidArr = ref<Array<string>>([])

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

const nginxInfo = useSysSettingStore().sysSettingMap.get('nginx_file_host')
let host = ''
if (nginxInfo && StringUtil.haveLength(nginxInfo.paramValue)) {
	host = nginxInfo.paramValue!
}
const createColumns = (): Array<DataTableColumn> => {
	return [
		{
			type: 'selection'
		},
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
							src: getRealImageUrl(host, row.path)
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
							copyContent(getRealImageUrl(host, row.path), false)
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
							if (row.storageMode === 2) return 'UPYUN_OSS_STORAGE'
							if (row.storageMode === 3) return 'QINIU_OSS_STORAGE'
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

const handleBatchDeleteFile = () => {
	// window.$dialog?.info({
	// 	title: `删除 ${batchDeleteFileUidArr.value.length}条登录信息◔ ‸◔?`,
	// 	positiveText: '删除',
	// 	negativeText: '取消',
	// 	onPositiveClick: () => {
	// 		fileApi.batch({uids: batchDeleteLoginInfoUidArr.value}).then(result => {
	// 			if (result.data) {
	// 				window.$message?.success(`成功删除 ${result.data}条登录信息o(￣▽￣)ｄ`)
	// 				emitter.emit(EnumMittEventName.reloadData)
	// 			}
	// 		})
	// 	}
	// })
}

const handleCheckedRowKeys = (keys: Array<string>) => {
	batchDeleteFileUidArr.value = keys
}

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);

	emitter.on('file-center-change-format', e => {
		if (e) {
			condition.value = e as Condition
			emitter.emit(EnumMittEventName.reloadData)
		}
	})
})

</script>

<style scoped>

</style>
