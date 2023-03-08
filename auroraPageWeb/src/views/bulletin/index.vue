<template>
	<aurora-common :is-sticky-sidebar="true"
								 :is-show-side-bar="true"
								 :show-sidebar-link="true"
								 :user-uid="userUid"
								 :show-navbar="true"
								 :show-sidebar="true"
								 :is-article-page="false"
								 :is-home-page="false"
								 :is-show-top-img="true"
								 :is-show-head-line="false">
		<template #center1>
			<aurora-card :id="item.uid" :title="item.title" v-for="item in bulletinArr" :key="item.uid">
				<render-markdown :markdown-content="item.content"/>
			</aurora-card>
			<blog-comment :user-uid="userUid"
										:page-path="`/bulletin/${userUid}`"
										:show-comment-but="true"
										:page-uid="userUid"
										:query-regexp="`/bulletin/${userUid}`"
										reply-page-type="OTHER"/>
		</template>
	</aurora-common>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from "vue";
import {StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {BulletinVo} from "@/bean/vo/article/BulletinVo";
import {bulletinApi} from "@/service";
import smoothscroll from 'smoothscroll-polyfill';

defineComponent({name: 'index'});

const userUid = ref('')
const bulletinUid = ref('')
const router = useRouter()
const routerPush = useRouterPush()
const bulletinArr = ref<Array<BulletinVo>>([])

const loadAllBulletin = () => {
	bulletinApi.queryListDataByCondition({otherUid: userUid.value, delete: false, show: true}).then(result => {
		if (result.data && result.data.result) {
			bulletinArr.value = result.data.result
		}
	})
}

const jumpTargetTalk = () => {
	let number = 1
	if (StringUtil.haveLength(bulletinUid.value)) {
		if (document) {
			smoothscroll.polyfill();
			const time = setInterval(() => {
				if (number === 15) {
					clearInterval(time)
				}
				if (document.getElementById(bulletinUid.value)) {
					document.getElementById(bulletinUid.value)!.scrollIntoView({behavior: "smooth", block: 'start'})
					clearInterval(time)
				}
				number = number + 1
			}, 10)
		}
	}
}

const getRouterParams = () => {
	userUid.value = router.currentRoute.value.params.userUid as string
	bulletinUid.value = router.currentRoute.value.params.bulletinUid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerBack()
	}
	
	loadAllBulletin()
}
getRouterParams()

onMounted(() => {
	jumpTargetTalk()
})
</script>

<style scoped>

</style>