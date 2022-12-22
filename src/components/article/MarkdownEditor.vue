<template>
	<div class="h-full">
		<n-card class="h-full shadow-sm rounded-16px">
			<div id="vditor"/>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import Vditor from "vditor";
import 'vditor/dist/index.css';
import {useThemeStore, useAuthStore} from "@/store";
import {fileApi} from "@/service";
import {FileVo} from "@/theme/vo/file/fileVo";
import {isImage} from "@/utils";

defineComponent({name: 'MarkdownEditor'});

// 定义props
interface Props {
	storageMode?: number,
	summary?: string,
	nginxServerDomain?: string,
	vditor?: {
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
	},
	renderMdContent?: string
}

const props = withDefaults(defineProps<Props>(), {
	storageMode: 0,
	nginxServerDomain: 'http://127.0.0.1',
	vditor: () => {
		return {
			tabKey: '\t\t',
			mode: 'ir',
			icon: 'material',
			counter: {
				enable: false,
				max: 100000
			},
			outline: {
				enable: false,
				position: 'left'
			},
			minHeight: 500
		}
	}
})

// 定义data
const theme = useThemeStore();
const authStore = useAuthStore();
const vditor = ref<Vditor>();
const vditorOptionConfig: IOptions = {
	theme: theme.darkMode ? 'dark' : 'classic',
	minHeight: props.vditor.minHeight,
	cache: {
		enable: false,
		id: 'xcye-cache',
		after: (markdown: string) => {
			// console.log(`缓存内容 ${markdown}`);
		}
	},
	placeholder: `${authStore.userInfo.username} 今天记录了么( ´◔︎ ‸◔︎\`)`,

	focus(value: string) {

	},
	blur(value: string) {

	},
	tab: props.vditor.tabKey,
	mode: props.vditor.mode,
	icon: props.vditor.icon,
	toolbarConfig: {
		hide: false,
		pin: true
	},
	counter: {
		enable: props.vditor.counter?.enable!,
		max: props.vditor.counter?.max,
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
						vditor.value?.setValue(vditor.value?.getValue() + content);
						vditor.value?.enable();
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
		enable: props.vditor.outline?.enable!,
		position: props.vditor.outline?.position!
	}
}

// 定义onMounted
onMounted(() =>{
	renderVditor();
})

// 定义方法
const renderVditor = () => {
	vditor.value = new Vditor('vditor', vditorOptionConfig);
	if (props.renderMdContent) {
		vditor.value?.setValue(props.renderMdContent);
	}
}

const getAfterUploadFileContent = (fileVoInfoArr: FileVo[]): Promise<string> => {
	return new Promise((resolve, reject) => {
		let pictureMdContent = "";
		let otherFileMdContent = "";
		fileVoInfoArr.filter(v => isImage(v.fileName!)).forEach(v => {
			pictureMdContent = pictureMdContent + `\n ![${v.fileName}](${v.path})`
		})

		fileVoInfoArr.filter(v => !isImage(v.fileName!)).forEach(v => {
			otherFileMdContent = otherFileMdContent + `\n [${v.fileName}](${v.path})`
		})
		resolve(pictureMdContent + otherFileMdContent)
	})
}

</script>

<style scoped>

</style>
