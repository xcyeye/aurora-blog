<template>
	<div>
		<div class="aurora-flower-box" id="aurora-flower-box"></div>
		<button @click="handleButtonAction" id="aurora-flower-button">button</button>
		<canvas class="aurora-flower-canvas" id="aurora-flower-canvas"></canvas>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, nextTick} from "vue";

defineComponent({name: 'AuroraButtonFlower'});

const handleButtonAction = () => {
	nextTick(() => {
		import('@/assets/flowers-less').then(result => {
			result.initBurst()
		})
	})
}
</script>

<style scoped>

</style>