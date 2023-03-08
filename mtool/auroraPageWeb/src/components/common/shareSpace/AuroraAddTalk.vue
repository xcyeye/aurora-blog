<template>
	<div>
		<aurora-login :show-login-modal="showLoginModal" :user-uid="userUid"/>
		<n-modal v-model:show="showTalkModalTemp">
			<n-card
				title="说说"
				style="width: 600px"
				:bordered="false"
				size="small"
				role="dialog"
				aria-modal="true"
				
			>
				<n-space vertical>
					<n-input type="text" v-model:value="talkInfo.title" placeholder="标题"/>
					<n-input type="textarea" :autosize="{minRows: 7}" v-model:value="talkInfo.content" placeholder="内容"/>
					<upload-file
						@handleFinishUploadFile="handleFinishUploadFile"
						:max-upload-file-number="5"
						:show-file-list="true"
						:multiple-upload-file="true"
						:show-upload-dragger="true"
						:accept-file-type-str="['.png','.jpg','.jpeg','.mp4','.wmv','.avi','.mov']"
						:parameter-data="{userUid: userUid, storagePath: 0, summary: `${userUid}用户从前台上传的说说照片`}" />
					<!--<n-image v-for="(item, index) in getPictureList" :src="item" :key="index"/>-->
					<aurora-gallery :show-load-more-but="false" :picture-list="getPictureList"/>
				</n-space>
				<template #header-extra>
					<n-gradient-text type="success" style="cursor: pointer" @click="handleAddTalkAction">Send</n-gradient-text>
				</template>
			</n-card>
		</n-modal>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, ref, watch} from "vue";
import {Talk} from "@/bean/pojo/article/Talk";
import {UploadFileInfo} from "naive-ui";
import {StringUtil} from "@/utils";
import {talkApi} from "@/service";
import {useAuthStore} from "@/stores";
import {FileVo} from "@/bean/vo/file/fileVo";

interface Props {
	showTalkModal: boolean,
	userUid: string
}

defineComponent({name: 'AuroraAddTalk'});

const props = withDefaults(defineProps<Props>(), {
	showTalkModal: false
})

const showTalkModalTemp = ref(false)
const showLoginModal = ref(false)
const talkInfo = ref<Talk>({})
const authStore = useAuthStore()

showTalkModalTemp.value = props.showTalkModal

const getPictureList = computed(() => {
	if (!StringUtil.haveLength(talkInfo.value.pictureSrcList)) return []
	return talkInfo.value.pictureSrcList?.split(",").map(v => {
		const file: FileVo = {
			path: v
		}
		return file
	}).concat()
})

const setLoginStatus = () => {
  if (!authStore.authInfo || !StringUtil.haveLength(authStore.authInfo.access_token)) {
		showLoginModal.value = true
		showTalkModalTemp.value = false
	}
}

const handleFinishUploadFile = (file: UploadFileInfo) => {
	if (StringUtil.haveLength(talkInfo.value.pictureSrcList)) {
		talkInfo.value.pictureSrcList = talkInfo.value.pictureSrcList + "," + file.url
	}else {
		talkInfo.value.pictureSrcList = file.url
	}
};

const handleAddTalkAction = () => {
	if (authStore.authInfo && authStore.authInfo.userInfo && authStore.authInfo.userInfo.user_uid !== props.userUid) {
		window.$message?.error(`目前仅支持 ${props.userUid}用户发布说说`)
		return;
	}
  if (!StringUtil.haveLength(talkInfo.value.content)) {
		window.$message?.error('需要有说说内容')
		return
	}
	talkApi.insertData(talkInfo.value).then(result => {
		if (!result.error) {
			window.$message?.success(('添加成功'))
			talkInfo.value = {}
			showTalkModalTemp.value = false
		}
	})
}

watch(() => props.showTalkModal, () => {
	showTalkModalTemp.value = props.showTalkModal
	setLoginStatus()
})
</script>

<style scoped>

</style>