<template>
	<div class="h-full">
		<n-drawer v-model:show="showDrawerFlag" width="502" placement="right">
			<n-drawer-content title="上传文件">
				<upload-file
					:max-upload-file-number="20"
					@handleFinishUploadFile="handleFinishUploadFile"
					:show-upload-dragger="true"
					:multiple-upload-file="true"
					upload-file-interface="single"
					:accept-file-type-str="['.png','.jpg','.jpeg','.mp4','.wmv','.avi','.mov']"
					:parameter-data="{
					userUid: authStore.userInfo.user_uid,
					summary: `${authStore.userInfo.username} 上传的文件`,
					storageMode: EnumFileStorageModeConstant.LOCAL_STORAGE
					}"
				/>
			</n-drawer-content>
		</n-drawer>
		<n-card size="huge" class="h-full shadow-sm rounded-16px">
			<n-tabs type="line" animated>
				<n-tab-pane name="oasis" tab="展示">
					<picture-file-show/>
				</n-tab-pane>
				<n-tab-pane name="show" tab="列表">
					<picture-file-list/>
				</n-tab-pane>
				<template #suffix>
					<n-space>
						<n-tag
							v-for="(item, index) in fileFormatOptions"
							:key="index"
							round
							:checked="item.checked"
							checkable
							@update:checked="handleCheckAction(item)"
						>
							{{ item.label }}
						</n-tag>
						<n-button strong secondary tertiary round type="success" @click="handleClickAction"> 上传</n-button>
					</n-space>
				</template>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, onMounted, ref, watch} from "vue";
import PictureFileList from './picture-file-list/index.vue'
import PictureFileShow from './picture-file-show/index.vue'
import {fileApi} from "@/service";
import {useAuthStore} from "@/store";
import {Condition} from "@/bean/core/bean";
import {emitter, isImage, isVideo} from "@/utils";
import {EnumFileStorageModeConstant, EnumMittEventName} from "@/enum";

defineComponent({name: 'index'});

interface FileFormatSelectOption {
	checked: boolean,
	label: string
}

const fileFormatOptions = ref<Set<FileFormatSelectOption>>(new Set<FileFormatSelectOption>())
const authStore = useAuthStore()
const condition = ref<Condition>({
	otherUid: authStore.userInfo.user_uid,
	delete: false
})
const showDrawerFlag = ref(false)

const handleCheckAction = (value: FileFormatSelectOption): void => {
	fileFormatOptions.value.forEach(v => {
		if (v.label === value.label) {
			v.checked = !value.checked
		}
	})

	let fileFormatSelectOptions = Array.from(fileFormatOptions.value).filter(v => v.checked);
	if (fileFormatSelectOptions.length === 0) {
		fileFormatOptions.value.forEach(v => v.checked = true)
	}

	let fileFormatSelectOptionsTemp = Array.from(fileFormatOptions.value).filter(v => v.checked);
	condition.value.otherField = fileFormatSelectOptionsTemp.map(v => v.label).join(',')
	emitter.emit('file-center-change-format', condition.value)
}

const handleFinishUploadFile = () => {
	emitter.emit(EnumMittEventName.reloadData)
}

const queryListFileFormat = () => {
  fileApi.queryListFileFormat({userUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			fileFormatOptions.value = new Set<FileFormatSelectOption>()
			result.data.forEach(v => {
				if (isImage(`.${v}`) || isVideo(`.${v}`)) {
					fileFormatOptions.value.add({
						checked: true,
						label: v
					})
				}
			})
		}
	})
}

const handleClickAction = () => {
	showDrawerFlag.value = true
}

onBeforeMount(() => {
	queryListFileFormat()
})

</script>

<style scoped>

</style>
