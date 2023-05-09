<template>
	<div @click="handleClick" style="cursor: pointer">
		<div>
			<svg-icon style="cursor: pointer" icon="mdi:message-fast"/>
		</div>
		<n-drawer v-model:show="showDrawer" :style="style">
			<n-drawer-content :title="talkInfo.title">
				<blog-comment :user-uid="talkInfo.userUid"
											:show-comment-but="false"
											reply-page-type="TALK"
											:page-uid="talkInfo.uid"
											:query-regexp="`/showSpace/${talkInfo.userUid}/${talkInfo.uid}`"
											:page-path="`/showSpace/${talkInfo.userUid}/${talkInfo.uid}`"/>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref} from "vue";
import {TalkVo} from "@/bean/vo/article/TalkVo";

interface Props {
	talkInfo: TalkVo
}

defineComponent({name: 'TalkComment'});

const props = withDefaults(defineProps<Props>(), {})

const showDrawer = ref(false)
const style = ref<string>('')

const handleClick = () => {
	if (props.talkInfo && !props.talkInfo.showComment) {
		window.$message?.error('此说说禁止评论┭┮﹏┭┮')
		return
	}
  showDrawer.value = !showDrawer.value
}

onMounted(() => {
	if (window.screen.width > 719) {
		style.value = 'width: calc(50vw);'
	}else {
		style.value = 'width: calc(90vw);'
	}
})
</script>

<style scoped>

</style>