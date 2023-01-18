<template>
	<div>
		<aurora-common :user-uid="userUid">
			<template #center1>
				<aurora-archive :article-arr="articleArr" />
			</template>
		</aurora-common>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref} from "vue";
import {StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {articleApi} from "@/service";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {TagVo} from "@/bean/vo/article/TagVo";

defineComponent({name: 'index'});

const userUid = ref<string>('')
const router = useRouter()
const routerPush = useRouterPush()
const articleArr = ref<Array<ArticleVo>>([])
const tagInfoArr = ref<Array<TagVo>>([])
const tagUid = ref<string>('')
onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.userUid as string
	tagUid.value = router.currentRoute.value.params.uid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		})
	}
	
	
})
</script>

<style scoped>

</style>