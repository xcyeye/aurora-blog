<template>
	<div @click="handleClick" style="cursor: pointer">
		<div>
			<svg-icon style="cursor: pointer" icon="mdi:message-fast"/>
		</div>
		<n-drawer v-model:show="showDrawer" :width="502">
			<n-drawer-content :title="talkInfo.title">
				<blog-comment :user-uid="talkInfo.userUid"
											:show-comment-but="false"
											reply-page-type="TALK"
											:page-uid="talkInfo.uid"
											:page-path="`/showSpace/${talkInfo.userUid}/${talkInfo.uid}`"/>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";
import {TalkVo} from "@/bean/vo/article/TalkVo";

interface Props {
	talkInfo: TalkVo
}

defineComponent({name: 'TalkComment'});

const props = withDefaults(defineProps<Props>(), {})

const showDrawer = ref(false)

const handleClick = () => {
  showDrawer.value = !showDrawer.value
}
</script>

<style scoped>

</style>