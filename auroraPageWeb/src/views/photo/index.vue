<template>
	<div>
		<aurora-common :is-sticky-sidebar="true" :show-sidebar-animate-class="false"
									 :is-show-side-bar="false" :user-uid="userUid" :is-show-top-img="true" :is-show-head-line="false">
			<template #center1>
				<div class="photo-list">
					<n-scrollbar>
						<photo-waterfall :picture-src-list="photoArr"
														 :mobile-waterfall-img-col="2"
														 :pc-waterfall-img-col="5"
														 :show-bg-color="true"/>
					</n-scrollbar>
				</div>
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
import {articleApi, talkApi} from "@/service";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import RequestResult = Service.RequestResult;
import TalkComment from "@/components/common/shareSpace/TalkComment.vue";
import {StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {FileVo} from "@/bean/vo/file/fileVo";

defineComponent({name: 'index'});

const userUid = ref<string>('')
const router = useRouter()
const routerPush = useRouterPush()
const photoFileArr = ref<Array<FileVo>>([])
const photoArr = ref<Array<string>>([])

const loadPhoto = () => {
	photoArr.value = []
	photoFileArr.value = []
  // TODO 目前只使用说说部分的图片
	talkApi.queryListDataByCondition({otherField: userUid.value, pageSize: 7000}).then(result => {
		if (result.data && result.data.result) {
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

onMounted(() => {
	userUid.value = router.currentRoute.value.params.userUid as string;
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		});
	}
	loadPhoto()
})

</script>

<style scoped lang="css">

</style>