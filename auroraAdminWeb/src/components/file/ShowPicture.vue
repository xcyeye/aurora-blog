<template>
	<div>
		<n-drawer v-if="showDrawerOperation" v-model:show="showDrawer" :width="502" placement="bottom">
			<n-drawer-content>
				<n-space vertical :size="30">
					<n-descriptions label-placement="left" title="文件详情" size="large" :column="2">
						<n-descriptions-item label="文件名">
							<template #default>
								<n-tag checkable @click="handleCopyContent(currentFileInfo.fileName)">
									{{currentFileInfo.fileName }}
								</n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="拥有者">
							<template #default>
								<n-tag checkable @click="handleCopyContent(currentFileInfo.userUid)">{{ currentFileInfo.userUid }}</n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="访问路径">
							<template #default>
								<n-tag checkable @click="handleCopyContent(currentFileInfo.path)">
									{{currentFileInfo.path }}
								</n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="存储路径">
							<template #default>
								<n-tag checkable @click="handleCopyContent(currentFileInfo.storagePath)">
									{{currentFileInfo.storagePath }}
								</n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="存储模式">
							<template #default>
								<n-tag type="success">{{ currentFileInfo.storageMode }}</n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="大小">
							{{getFileSize(currentFileInfo.size)}}
						</n-descriptions-item>
						<n-descriptions-item label="下载">
							<template #default>
								<n-tag checkable @click="downloadFile"> 点击下载 </n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="上传时间"> {{ currentFileInfo.createTime }} </n-descriptions-item>
						<n-descriptions-item label="是否删除">
							<template #default>
								<n-tag :type="currentFileInfo.delete ? 'error' : 'success'">
									{{currentFileInfo.delete ? '已删除' : '未删除' }}
								</n-tag>
							</template>
						</n-descriptions-item>
						<n-descriptions-item label="删除时间"> {{ currentFileInfo.deleteTime }} </n-descriptions-item>
					</n-descriptions>

					<n-input
						v-model:value="currentFileInfo.summary"
						type="textarea"
						placeholder="文件描述"
						round
						clearable
					/>
					<n-row :gutter="[0, 24]" v-if="currentFileInfo.uid">
						<n-col :span="24">
							<div style="display: flex; justify-content: right">
								<n-space>
									<n-button round type="error" @click="handleDeleteFileInfo()">
										{{ currentFileInfo.delete ? '删除文件信息' : '删除文件' }}
									</n-button>
									<n-button round type="primary" @click="handleUpdateFileInfo"> 修改 </n-button>
								</n-space>
							</div>
						</n-col>
					</n-row>
				</n-space>
			</n-drawer-content>
		</n-drawer>
		<photo-waterfall class="aurora-all-picture-box" :picture-src-list="getWaterFallPictureArr">
			<div class="picture-description" v-if="showDrawerOperation">
				<n-button text @click="handleShowFileDetails($event)">
					<template #icon>
						<n-icon>
							<icon-ic:baseline-all-inclusive />
						</n-icon>
					</template>
					详情
				</n-button>
			</div>
		</photo-waterfall>
		<!--<n-grid-->
		<!--	ref="picture-grid"-->
		<!--	:collapsed="false"-->
		<!--	cols="4 s:3 m:4 l:5 xl:4 2xl:7"-->
		<!--	:x-gap="20"-->
		<!--	:y-gap="20"-->
		<!--	:item-responsive="true"-->
		<!--	responsive="self"-->
		<!--&gt;-->
		<!--	<n-grid-item v-for="(item, index) in pictureSrcArr" ref="picture-grid-item" :key="index">-->
		<!--		<n-card class="h-full shadow-sm rounded-16px" :bordered="false">-->
		<!--			<n-space vertical>-->
		<!--				<n-image :key="index" :show-toolbar-tooltip="true" :src="item.src" />-->
		<!--				-->
		<!--			</n-space>-->
		<!--		</n-card>-->
		<!--	</n-grid-item>-->
		<!--</n-grid>-->
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, ref} from "vue";
import {AuroraFile} from "@/bean/pojo/file/file";
import {FileVo} from "@/bean/vo/file/fileVo";
import {StringUtil, getFileSize} from "@/utils";
import {fileApi} from "@/service";
import {copyContent} from "@/plugins";
import {useSysSettingStore} from "@/store";

defineComponent({name: 'ShowPicture'});

interface PictureProperties {
	uid?: string,
	src: string
}

interface Props {
	pictureSrcArr: Array<PictureProperties>,
	showDrawerOperation?: boolean
}

const props = withDefaults(defineProps<Props>(), {
	pictureSrcArr: () => new Array<PictureProperties>(),
	showDrawerOperation: true
})

// 定义data
const showDrawer = ref(false)
const currentFileInfo = ref<FileVo>({})

const nginxInfo = useSysSettingStore().sysSettingMap.get('nginx_file_host')
let host = ''
if (nginxInfo && StringUtil.haveLength(nginxInfo.paramValue)) {
	host = nginxInfo.paramValue!
}

const getWaterFallPictureArr = computed(() => {
	if (!props.pictureSrcArr || props.pictureSrcArr.length === 0) return []
	return props.pictureSrcArr.map(v => {
		if (v.src.startsWith('http')) return v.src
		return `${host}/${v.src}`
	}).concat()
})

const handleShowFileDetails = (e: Event) => {
	const pictureInfo: PictureProperties = {}
	currentFileInfo.value = {}
	// 查询此文件是否是系统的pictureInfo: PictureProperties
	if (!StringUtil.haveLength(pictureInfo.uid)) {
		window.$message?.info('此文件并未存储在系统')
		return
	}
	fileApi.queryOneDataByUid({uid: pictureInfo.uid}).then(result => {
		if (!result.error) {
			if (result.data) {
				currentFileInfo.value = result.data
			}else {
				window.$message?.info('此文件并未存储在系统')
				return
			}
		}
	})
	showDrawer.value = true
}

const handleCopyContent = (value: string) => {
  copyContent(value)
}

const downloadFile = () => {
	window.open(currentFileInfo.value.path!, '_blank')
}

const handleDeleteFileInfo = () => {
	if (!currentFileInfo.value.uid) return
	fileApi.deleteFile(currentFileInfo.value as AuroraFile).then(result => {
		if (!result.error && result.data === 1) {
			window.$message?.success('删除文件成功 o(￣▽￣)ｄ ')
		}
	})
}

const handleUpdateFileInfo = () => {
	if (!currentFileInfo.value.uid) return
	fileApi.updateData(currentFileInfo.value as AuroraFile).then(result => {
		if (!result.error && result.data === 1) {
			showDrawer.value = false
			window.$message?.success('修改文件成功 o(￣▽￣)ｄ ')
		}
	})
}
</script>

<style scoped lang="css">
.aurora-all-picture-box {
	height: calc(100vh);
}

.picture-description {
	margin: 0 auto;
	text-align: right;
	box-sizing: border-box;
	padding-left: 1rem;
	padding-right: 1rem;
}
</style>
