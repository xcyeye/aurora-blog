<template>
	<div class="h-full">
		<n-card :bordered="bordered" class="h-full shadow-sm rounded-16px">
			<div ref="vditorRef" id="vditor"/>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from "vue";
import Vditor from "vditor";
import 'vditor/dist/index.css';
import {useAuthStore, useSysSettingStore, useThemeStore} from "@/store";
import {fileApi} from "@/service";
import {FileVo} from "@/bean/vo/file/fileVo";
import {isImage, StringUtil} from "@/utils";

defineComponent({name: 'MarkdownEditor'});

// 定义emits
const emits = defineEmits(['vditorInput'])

// 定义props
interface Props {
	storageMode?: number,
	summary?: string,
	nginxServerDomain?: string,
	bordered?: boolean,
	tabKey?: string,
	mode?: 'wysiwyg' | 'sv' | 'ir',
	icon?: 'ant' | 'material',
	counter?: {
		enable?: boolean,
		max?: number
	},
	outline?: {
		enable?: boolean,
		position?: 'left' | 'right',
	},
	minHeight?: number
	renderMdContent?: string,
	contentMaxNumber?: number
}

const props = withDefaults(defineProps<Props>(), {
	storageMode: 0,
	nginxServerDomain: 'http://127.0.0.1',
	bordered: false,
	tabKey: '\t\t',
	mode: 'ir',
	icon: 'material',
	counter: () => {
		return {
			enable: false,
			max: 100000
		}
	},
	outline: () => {
		return {
			enable: false,
			position: 'left'
		}
	},
	minHeight: 500,
	contentMaxNumber: 100000
})

// 定义data
const theme = useThemeStore();
const sysSettingStore = useSysSettingStore()
const authStore = useAuthStore();
const vditor = ref<Vditor>();
const vditorRef = ref<HTMLElement>();
const originContent = ref<string>('')
const vditorRenderFinishFlag = ref(false)
const vditorSetContentStatus = ref(false)
let vditorOptionConfig: IOptions = {
	theme: theme.darkMode ? 'dark' : 'classic',
	minHeight: 500,
	cdn: 'https://cdn.xcye.xyz/vditor',
	cache: {
		enable: false
	},
	input(value: string) {
		emits('vditorInput', value)
	},
	placeholder: `${authStore.userInfo.username} 今天记录了么( ´◔︎ ‸◔︎\`)`,

	focus(value: string) {

	},
	blur(value: string) {

	},
	esc(value: string) {
		// window.$dialog?.warning({
		// 	title: '◔ ‸◔?',
		// 	content: '你确定要退出么(⊙.⊙)',
		// 	positiveText: '(◕‿◕)',
		// 	negativeText: '(→_←)',
		// 	onPositiveClick: () => {
		// 	},
		// 	onNegativeClick: () => {
		//
		// 	}
		// })
	},
	tab: '\t\t',
	mode: 'ir',
	icon: 'material',
	toolbarConfig: {
		hide: false,
		pin: true
	},
	counter: {
		enable: false,
		max: 10000,
		type: 'markdown',
	},
	preview: {
		mode: 'both',
		markdown: {
			autoSpace: false,
			toc: true,
			paragraphBeginningSpace: true
		}
	},
	upload: {
		handler(files: File[]): null {
			if (!files.length) return null;
			vditor.value?.tip(`正在上传${files.length}个文件`);
			vditor.value?.disabled();
			const summaryTemp = `${authStore.userInfo.username} 用户从vditor上传的文件`;
			fileApi.multiUploadFile(files, props.storageMode,
				authStore.userInfo.user_uid,
				props.summary ? props.summary : summaryTemp).then(result => {
				if (!result.data) {
					window.$message?.error("上传失败，后端并没有返回任何信息");
					vditor.value?.enable();
					return null;
				}else {
					getAfterUploadFileContent(result.data).then(content => {
						// vditor.value?.setValue(vditor.value?.getValue() + content);
						vditor.value?.insertValue(content, true);
						vditor.value?.enable();
						setTimeout(() => {
							emits('vditorInput', (vditor.value?.getValue()))
						}, 700)
					})
				}
			})
			return null;
		}
	},
	resize: {
		enable: true,
		position: 'bottom'
	},
	outline: {
		enable: false,
		position: 'left'
	}
}

const setVditorProperties = () => {
	if (props.mode) {
		vditorOptionConfig.mode = props.mode
	}
	if (props.outline) {
		if (props.outline.enable) {
			vditorOptionConfig.outline!.enable = props.outline.enable
		}
		if (props.outline.position) {
			vditorOptionConfig.outline!.position = props.outline.position
		}
	}

	if (props.counter) {
		if (props.counter.enable) {
			vditorOptionConfig.counter!.enable = props.counter.enable
		}
		if (props.counter.max) {
			vditorOptionConfig.counter!.max = props.counter.max
		}
	}

	if (props.minHeight) {
		vditorOptionConfig.minHeight = props.minHeight
	}
	if (props.tabKey) {
		vditorOptionConfig.tab = props.tabKey
	}
	if (props.icon) {
		vditorOptionConfig.icon = props.icon
	}
	if (props.contentMaxNumber) {
		vditorOptionConfig.counter!.enable = true
		vditorOptionConfig.counter!.max = props.contentMaxNumber
		vditorOptionConfig.counter!.type = 'markdown'
	}
}

const setContent = () => {
	console.debug("正在设置内容");
	const time = setInterval(() => {
		if (vditorRenderFinishFlag.value) {
			if (vditor.value) {
				if (StringUtil.haveLength(props.renderMdContent)) {
					vditor.value.setValue(props.renderMdContent as string)
					vditorSetContentStatus.value = true
				}
				clearInterval(time)
			}
		}
	}, 2)
}

// 定义方法
const renderVditor = () => {
	setVditorProperties()
	if (!vditorRef.value) return;
	vditor.value = new Vditor(vditorRef.value, vditorOptionConfig);
	setTimeout(() =>{
		vditorRenderFinishFlag.value = true
		setContent()
	}, 10)
}

const getAfterUploadFileContent = (fileVoInfoArr: FileVo[]): Promise<string> => {
	let host = ''
	if (sysSettingStore.sysSettingMap.get('nginx_file_host')) {
		if (StringUtil.haveLength(sysSettingStore.sysSettingMap.get('nginx_file_host')!.paramValue)) {
			host = sysSettingStore.sysSettingMap.get('nginx_file_host')!.paramValue as string
		}
	}
	return new Promise((resolve, reject) => {
		let pictureMdContent = "";
		let otherFileMdContent = "";
		fileVoInfoArr.filter(v => isImage(v.fileName!)).forEach(v => {
			pictureMdContent = pictureMdContent + `\n ![${v.fileName}](${host}/${v.path})`
		})

		fileVoInfoArr.filter(v => !isImage(v.fileName!)).forEach(v => {
			otherFileMdContent = otherFileMdContent + `\n [${v.fileName}](${host}/${v.path})`
		})
		// resolve(pictureMdContent + otherFileMdContent)
		resolve(pictureMdContent)
	})
}

watch(() => props.renderMdContent, () =>{
	if (!vditorSetContentStatus.value) {
		setContent()
		vditorSetContentStatus.value = true
	}
})

// 监听大纲
// watch(() => props.outline, () => {
// 	vditor.value?.disabled()
// 	window.$message?.success('正在设置 o(￣▽￣)ｄ ')
// 	originContent.value = vditor.value?.getValue()!
// 	renderVditor()
// 	setTimeout(() => {
// 		vditor.value?.setValue(originContent.value)
// 		vditor.value?.enable()
// 	}, 100)
// })
// 监听模式
// 监听icon
// 监听tabKey

// 定义onMounted
onMounted(() =>{
	renderVditor();
})

</script>

<style scoped>

</style>
