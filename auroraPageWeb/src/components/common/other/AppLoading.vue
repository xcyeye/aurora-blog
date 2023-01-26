<template>
	<div class="app-load-box">
		<div class="ball" data-splitting>
			<div class="ball-top"></div>
		</div>
		
		<div class="phrase" data-splitting="items">
			<span class="word slide" data-splitting="chars">aur</span>
			<span class="word slide" data-splitting="chars">ora</span>
			<span class="word slide" data-splitting="chars">blog</span>
			<span class="word smiley" data-splitting="chars">:)</span>
		</div>
	</div>
</template>

<script setup lang="ts">
import Splitting from 'splitting';
import {onMounted, ref} from "vue";

const wordArr = ref<Array<string>>([])

const setWordArr = () => {

}

setWordArr()

onMounted(() => {
	Splitting();
	// // Replay animation by hiding & showing the element again
	// let el = document.body;
	// el.addEventListener("click", function (e) {
	// 	el.hidden = true;
	// 	requestAnimationFrame(() => {
	// 		el.hidden = false;
	// 	});
	// });
})

</script>

<style scoped lang="css">

</style>
