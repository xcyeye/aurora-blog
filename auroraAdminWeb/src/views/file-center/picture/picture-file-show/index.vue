<template>
	<div>
		<aurora-gallery @clickLoadMorePicture="clickLoadMorePicture" :picture-list="pictureFileList">
			<!--<div class="picture-description" v-if="showDrawerOperation">-->
			<div class="picture-description">
				<n-button text @click="handleShowFileDetails($event)">
					<template #icon>
						<n-icon>
							<icon-ic:baseline-all-inclusive />
						</n-icon>
					</template>
					详情
				</n-button>
			</div>
		</aurora-gallery>
		<!--<show-picture :show-drawer-operation="true" :picture-src-arr="pictureArr"/>-->
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
	delete: false,
})
const totalTalkNumber = ref(0)

defineComponent({name: 'index'});

// 定义data
const pictureArr = ref<Array<PictureProperties>>([])
const pictureFileList = ref<Array<FileVo>>([])

const loadFileInfo = () => {
	pictureArr.value = []
	pictureFileList.value = []
  fileApi.queryListDataByCondition(condition.value).then(result => {
		if (result.data && result.data.result) {
			pictureFileList.value = result.data.result
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

const clickLoadMorePicture = () => {
	if (condition.value.pageSize! > totalTalkNumber.value) {
		window.$message?.success('没有更多啦')
		return
	}
	if (!condition.value.pageSize) {
		condition.value.pageSize = 0
	}
	condition.value.pageSize = condition.value.pageSize! + 10
	loadFileInfo()
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
