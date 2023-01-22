<template>
	<div class="aurora-top-bubble-par" v-if="showBubble">
		<div class="aurora-bubble-box">
			<div class="aurora-top-bubble-box" id="aurora-top-bubble-box"></div>
			<canvas class="aurora-top-bubble-canvas" id="aurora-top-bubble-canvas" v-if="showBubble"/>
		</div>
		<div class="aurora-bubble-content">
			<slot/>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, nextTick} from "vue";

interface Props {
	showBubble?: boolean
}

defineComponent({name: 'AuroraBubble'});

const props = withDefaults(defineProps<Props>(), {
	showBubble: true
})

onMounted(() => {
	let bubbleNumber = 0.15
	let bubbleAlpha = 0.7
	let alphaChangeSpeed = 0.0005
	let size = 0.5
	let sizeChangeSpeed = 0.002
	let riseSpeed = 0.9
	let color = '255,255,255'
	if (props.showBubble) {
		nextTick(() =>{
			import("@/assets/bubble").then(module => {
				module.bubble(bubbleNumber,bubbleAlpha,alphaChangeSpeed,size,sizeChangeSpeed,riseSpeed,color)
			})
		})
	}
})
</script>

<style scoped>

</style>