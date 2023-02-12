<template>
	<div>
		<aurora-common :is-sticky-sidebar="true"
									 :show-navbar="false"
									 :show-tag-cloud="false"
									 :show-sidebar-animate-class="false"
									 :is-show-side-bar="false"
									 :user-uid="userUid"
									 :show-sidebar="false"
									 :is-show-top-img="true"
									 :is-show-head-line="false">
			<template #center1>
				<aurora-card class="aurora-photo-card">
					<div class="photo-list">
						<!--<n-scrollbar>-->
						<!--	<photo-waterfall :picture-src-list="photoArr"-->
						<!--									 :mobile-waterfall-img-col="2"-->
						<!--									 :pc-waterfall-img-col="5"-->
						<!--									 :show-bg-color="true">-->
						<!--		&lt;!&ndash;<div style="height: 4rem;background-color: red"></div>&ndash;&gt;-->
						<!--	</photo-waterfall>-->
						<!--</n-scrollbar>-->
						<aurora-gallery @clickLoadMorePicture="clickLoadMorePicture" :pc-gallery-column="4" :mobile-gallery-column="2" :picture-list="photoFileArr"></aurora-gallery>
					</div>
				</aurora-card>
			</template>
			<template #center2>
				<blog-comment
					:page-uid="userUid"
					:page-path="`/photo/${userUid}`"
					:query-regexp="`^/photo/${userUid}.*`"
					reply-page-type="OTHER"
					:user-uid="userUid"/>
			</template>
		</aurora-common>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import GiveLike from "@/components/common/business/GiveLike.vue";
import {Condition, PageData} from "@/bean/core/bean";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, fileApi, talkApi} from "@/service";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import RequestResult = Service.RequestResult;
import TalkComment from "@/components/common/shareSpace/TalkComment.vue";
import {isImage, setMetaDescription, setMetaKeywords, setMetaTitle, StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {FileVo} from "@/bean/vo/file/fileVo";

defineComponent({name: 'index'});

const userUid = ref<string>('')
const router = useRouter()
const routerPush = useRouterPush()
const photoFileArr = ref<Array<FileVo>>([])
const photoArr = ref<Array<string>>([])
const pictureFormatList = ref<Array<string>>([])
const currentPageSize = ref(10)
const totalTalkNumber = ref(0)

const queryListFileFormat = () => {
	pictureFormatList.value = []
	fileApi.queryListFileFormat({userUid: userUid.value}).then(result => {
		if (result.data) {
			result.data.forEach(v => {
				if (isImage(`.${v}`)) {
					pictureFormatList.value.push(v)
				}
			})
		}
	})
}

const loadPhoto = () => {
	photoArr.value = []
	photoFileArr.value = []
  // TODO 目前只使用说说部分的图片
	talkApi.queryListDataByCondition({otherUid: userUid.value, pageSize: currentPageSize.value}).then(result => {
		if (result.data && result.data.result) {
			totalTalkNumber.value = result.data.total as number
			result.data.result.filter(v => StringUtil.haveLength(v.pictureSrcList)).forEach(v => {
				v.pictureSrcList?.split(",").forEach(f => {
					photoFileArr.value.push({
						path: f,
						userUid: v.userUid
					})
					photoArr.value.push(f)
				})
			})
		}
	})
}

const clickLoadMorePicture = () => {
	if (currentPageSize.value > totalTalkNumber.value) {
		window.$message?.success('没有更多啦')
		return
	}
  currentPageSize.value = currentPageSize.value + 10
	loadPhoto()
}

const getRouterParams = () => {
	userUid.value = router.currentRoute.value.params.userUid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		});
	}
	loadPhoto()
	queryListFileFormat()
	// setMetaTitle(`${userInfo.value.username} - ${userInfo.value.userSummary ? userInfo.value.userSummary : ''}`)
	// setMetaDescription(userInfo.value.userSummary)
	// setMetaKeywords(`${userInfo.value.nickname} ${userInfo.value.username}`)
}
getRouterParams()

</script>

<style scoped lang="css">

</style>