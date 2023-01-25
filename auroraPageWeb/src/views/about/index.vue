<template>
	<aurora-common :is-sticky-sidebar="true"
								 :is-show-side-bar="true"
								 :show-sidebar-link="true"
								 :user-uid="userUid"
								 :show-navbar="true"
								 :show-sidebar="false"
								 :is-article-page="false"
								 :is-home-page="false"
								 :is-show-top-img="true"
								 :head-line="useUserInfo().getUserInfo(userUid).username"
								 :is-show-head-line="false">
		<template #center1>
			<aurora-card class="aurora-page-card">
				<render-markdown :markdown-content="currentSiteInfo.readme"/>
			</aurora-card>
			<blog-comment :user-uid="userUid"
										:page-path="`/about/${userUid}`"
										:show-comment-but="true"
										:page-uid="userUid"
										:query-regexp="`/about/${userUid}`"
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
import {useSiteInfo, useUserInfo} from "@/stores";

defineComponent({name: 'index'});

const userUid = ref('')
const router = useRouter()
const routerPush = useRouterPush()
const currentSiteInfo = ref<SiteSettingInfo>({})

const getRouterParams = () => {
	userUid.value = router.currentRoute.value.params.userUid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerBack()
	}
	currentSiteInfo.value = useSiteInfo().getSiteInfo(userUid.value)
}
getRouterParams()
</script>

<style scoped>

</style>