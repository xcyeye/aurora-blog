<template>
	<div>
		<show-picture :show-drawer-operation="true" :picture-src-arr="pictureArr"/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onBeforeMount, onMounted, ref, watch} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {fileApi, loginInfoApi} from "@/service";
import {DataTableColumn, NAvatar, NButton, NSpace, NTag, NText} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getFileFormat, getRandomTagType, isImage} from "@/utils";
import {FileVo} from "@/bean/vo/file/fileVo";
import {copyContent} from "@/plugins";
import RequestResult = Service.RequestResult;
import {useAuthStore} from "@/store";

interface PictureProperties {
	uid?: string,
	src: string
}

const authStore = useAuthStore()
const condition = ref<Condition>({
	otherUid: authStore.userInfo.user_uid,
	delete: false
})

defineComponent({name: 'index'});

// 定义data
const pictureArr = ref<Array<PictureProperties>>([])

const loadFileInfo = () => {
	pictureArr.value = []
  fileApi.queryListDataByCondition(condition.value).then(result => {
		if (result.data && result.data.result) {
			pictureArr.value = result.data.result.map(v => {
				const obj: PictureProperties = {
					src: v.path!,
					uid: v.uid!
				}
				return obj
			}).concat()
		}
	})
}

loadFileInfo()

onBeforeMount(() => {
	emitter.on('file-center-change-format', e => {
		condition.value = e as Condition
		loadFileInfo()
	})
})

</script>

<style scoped>

</style>
