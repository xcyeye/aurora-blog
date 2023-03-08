<template>
	<aurora-common :is-sticky-sidebar="true"
								 :is-show-side-bar="true"
								 :show-sidebar-link="true"
								 :user-uid="userUid"
								 :show-navbar="true"
								 :show-sidebar="true"
								 :is-article-page="false"
								 :is-home-page="false"
								 :show-tag-cloud="false"
								 :is-show-catalog="false"
								 :is-show-top-img="true"
								 :is-show-head-line="false">
		<template #center1>
			<blog-comment reply-page-type="OTHER"
										:user-uid="userUid"
										:page-uid="userUid"
										:query-by-user-uid="true"
										:show-comment-but="false"
										:page-path="`/comment/${userUid}`"/>
		</template>
	</aurora-common>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {StringUtil} from "@/utils";

defineComponent({name: 'index'});

const userUid = ref('')
const router = useRouter()
const routerPush = useRouterPush()

const getRouterParams = () => {
	userUid.value = router.currentRoute.value.params.userUid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerBack()
	}
}
getRouterParams()
</script>

<style scoped>

</style>