<template>
	<div id="aurora-slider-picture-box">
		<div class="buttons" v-if="false">
			<button class="next" onclick="go(-1)"></button>
			<button class="prev" onclick="go(1)"></button>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, nextTick, onMounted} from "vue";
import {go} from '@/assets/sliderPicture'

defineComponent({name: 'AuroraSliderPicture'});

onMounted(() => {
	nextTick(() =>{
		import("@/assets/sliderPicture").then(module => {
			module.init(['https://picture.xcye.xyz/animate/178533.png','https://picture.xcye.xyz/animate/533.png'])
		})
	})
})
</script>

<style scoped>

</style>