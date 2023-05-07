<template>
	<div>
		<n-space vertical>
			<n-upload
				ref="uploadFileRef"
				:show-download-button="showDownloadButton"
				:multiple="multipleUploadFile"
				:show-file-list="showFileList"
				:max="maxUploadFileNumber"
				:directory="directory"
				:data="parameterData"
				:accept="acceptFileType"
				:default-upload="!controlUploadFile"
				:custom-request="handleCustomUploadFile"
				@before-upload="handleBeforeUpload"
				@change="uploadFileChangeEven"
				@finish="handleFinishUploadFile"
				@error="handleErrorUploadFile"
			>
				<n-upload-dragger v-if="computedShowUploadDragger(1)">
					<div style="margin-bottom: 12px">
						<n-icon size="48" :depth="3">
							<icon-uiw:cloud-upload/>
						</n-icon>
					</div>
					<n-text style="font-size: 16px"> 点击或者拖动文件到该区域来上传 </n-text>
					<n-p depth="3" style="margin: 8px 0 0 0">
						{{uploadDraggerDescriptionText}}
					</n-p>
				</n-upload-dragger>
				<n-button v-if="computedShowUploadDragger(2)">上传文件</n-button>
				<slot name="uploadDraggerContent"/>
			</n-upload>

			<n-row v-if="showUploadButtonGroup" :gutter="[0, 24]">
				<n-col :span="24">
					<div style="display: flex; justify-content: left">
						<n-space>
							<n-button :disabled="!uploadFileLength" round type="primary" @click="handleSubmitUploadFile">
								上传
							</n-button>
							<n-button round :disabled="!uploadFileLength" type="error" @click="handleInterruptUploadFile">
								中断
							</n-button>
							<n-button round :disabled="!uploadFileLength" type="info" @click="handleClearUploadFileList">
								清除
							</n-button>
							<slot name="extraButton"/>
						</n-space>
					</div>
				</n-col>
			</n-row>
		</n-space>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, reactive, ref, useSlots} from "vue";
import {UploadCustomRequestOptions, UploadFileInfo, UploadInst} from "naive-ui";
import {fileApi} from "@/service";
import {AuroraFile} from "@/bean/pojo/file/file";
import {FileVo} from "@/bean/vo/file/fileVo";
import {useSysSettingStore} from "@/store";
import {getRealImageUrl, StringUtil} from "@/utils";
import {getSysSetting} from "@/store/modules/sysSetting/helpers";
import {REGEXP_URL} from "@/config";

defineComponent({name: 'UploadFile'})

// 定义emit
const emits = defineEmits(['handleFinishUploadFile', 'handleErrorUploadFile'])

// 定义props
interface Props {
	showUploadDragger?: boolean,
	parameterData?: AuroraFile,
	showFileList?: boolean,
	showDownloadButton?: boolean,
	controlUploadFile?: boolean,
	acceptFileTypeStr?: string[],
	maxUploadFileNumber?: number,
	uploadFileInterface?: 'single' | 'typora' | 'multi',
	multipleUploadFile?: boolean,
	uploadDraggerDescriptionText?: string,
	directory?: boolean
}

const props = withDefaults(defineProps<Props>(), {
	showUploadDragger: false,
	showFileList: true,
	showDownloadButton: true,
	controlUploadFile: true,
	acceptFileTypeStr: () => [''],
	maxUploadFileNumber: 5,
	uploadFileInterface: 'single',
	parameterData:() => {
		return {
			storageMode: 0,
			summary: '从aurora上传的文件',
			userUid: null
		}
	},
	multipleUploadFile: false,
	uploadDraggerDescriptionText: '请不要上传敏感数据，比如你的银行卡号和密码，信用卡号有效期和安全码',
	directory: false
});

// 定义data
const uploadFileInfoArr = reactive<UploadFileInfo[]>([]);
let uploadFileInfoTempArr = reactive<UploadFileInfo[]>([]);
const uploadFileRef = ref<UploadInst | null>(null);
const uploadFileLength = ref(0);
const sysSettingStore = useSysSettingStore()
const acceptFileType = computed(() => {
	if (props.acceptFileTypeStr.length === 0) {
		return ''
	}else {
		return props.acceptFileTypeStr.join(",").toString();
	}
})

const showUploadButtonGroup = computed(() => {
	return props.controlUploadFile;
})

const computedShowUploadDragger = computed(() => (index: number) => {
	if (!!useSlots().uploadDraggerContent) {
		// slot中存在内容，直接返回false
		return false;
	}
	if (index === 1) {
		return props.showUploadDragger;
	}
	return !props.showUploadDragger;
})

// 方法
const handleFinishUploadFile = ({file, event}:
																	{ file: UploadFileInfo, event?: ProgressEvent }): UploadFileInfo => {
	window.$message?.success(`文件 ${file.name} 上传成功`);
	emits('handleFinishUploadFile', uploadFileInfoTempArr.filter(v => v.id === file.id)[0]);
	return uploadFileInfoTempArr.filter(v => v.id === file.id)[0];
}

const handleErrorUploadFile = (options: { file: UploadFileInfo, event?: ProgressEvent }): void => {
	window.$message?.error(`文件 ${options.file.name} 上传失败`);
	emits('handleErrorUploadFile', options.file);
}

const handleCustomUploadFile = ({file, data, headers,
																	withCredentials, action,
																	onFinish, onError, onProgress}: UploadCustomRequestOptions) => {
	if (file.file === null) {
		window.$message?.error(`待上传的文件为null，已取消上传`);
		return;
	}
	// @ts-ignore
	let {storageMode, userUid, summary} = data;
	if (storageMode === null || storageMode === undefined) {
		storageMode = 0;
	}
	if (userUid === null || userUid === undefined) {
		window.$message?.error("上传失败，请传入userUid值");
		return;
	}
	if (summary === null || summary === undefined) {
		summary = `用户 ${userUid} 上传的文件`;
	}

	// TODO 存储模式目前使用配置的默认，不接受传递
	let defaultFileStorageMode = sysSettingStore.sysSettingMap.get("default_file_storageMode");
	if (defaultFileStorageMode && StringUtil.haveLength(defaultFileStorageMode.paramValue)) {
		storageMode = defaultFileStorageMode.paramValue;
	}

	if (props.uploadFileInterface === 'single') {
		fileApi.singleUploadFile(file.file, storageMode, userUid, summary).then(result => {
			// 上传成功，保存文件的信息
			generateFileInfo(file, Array.of(result.data!)).then(data => {
				data.forEach(v => uploadFileInfoTempArr.push(v));
				onFinish();
			})
		}).catch(e => {
			onError();
		})
	}else if (props.uploadFileInterface === 'typora') {
		fileApi.typoraUploadFile(file.file, storageMode, userUid, `${userUid} 用户通过typora上传的文件`).then(result => {
			const url: string = result.data!;
			// 上传成功，保存文件的信息
			uploadFileInfoTempArr.push({
				id: file.id,
				url: url,
				fullPath: null,
				file: file.file,
				type: file.type,
				percentage: 100,
				status: 'finished',
				batchId: file.batchId,
				name: url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")),
				thumbnailUrl: file.thumbnailUrl
			})
		}).catch(e => onError());
	}
}

async function handleBeforeUpload(data: { file: UploadFileInfo, fileList: UploadFileInfo[] }): Promise<boolean> {
	return true
}

const handleInterruptUploadFile = () => {

}

const handleClearUploadFileList = () => {
	uploadFileInfoTempArr = []
}

const handleSubmitUploadFile = () => {
	uploadFileRef.value?.submit();
}

const uploadFileChangeEven = (options: { file: UploadFileInfo,
	fileList: Array<UploadFileInfo>, event?: Event }): void => {
	uploadFileLength.value = options.fileList.length;
	uploadFileInfoArr.push(options.file);
}

const generateFileInfo = (file: UploadFileInfo, fileVoArr: FileVo[]): Promise<UploadFileInfo[]> => {
	let host = ''
	// TODO 暂时修复，不优雅
	if (getSysSetting().get('nginx_file_host')) {
		if (StringUtil.haveLength(getSysSetting().get('nginx_file_host')!.paramValue)) {
			host = getSysSetting().get('nginx_file_host')!.paramValue as string
			return  returnFileInfo(file, fileVoArr, host)
		}else {
			console.error("配置nginx_file_host没有配置值，请配置，否则静态文件无法访问");
			return  returnFileInfo(file, fileVoArr, null)
		}
	}else {
		console.error("你尚未配置nginx_file_host，请配置，否则静态文件无法访问");
		return  returnFileInfo(file, fileVoArr, null)
	}
}

const returnFileInfo = (file: UploadFileInfo, fileVoArr: FileVo[], host: string | null): Promise<UploadFileInfo[]> => {
	if (!StringUtil.haveLength(host)) {
		host = ''
	}
	return new Promise((resolve, reject) => {
		const temp: UploadFileInfo[] = [];
		fileVoArr.forEach((v, index) => {
			temp.push({
				id: file.id,
				url: getRealImageUrl(host, v.path),
				fullPath: v.storagePath,
				file: file.file,
				type: file.type,
				percentage: 100,
				status: 'finished',
				batchId: file.batchId,
				name: v.fileName!,
				thumbnailUrl: v.uid
			});
			if (index === fileVoArr.length - 1) {
				resolve(temp);
			}
		})
	})
}
</script>

<style scoped>

</style>
