<template>
	<div class="error-content">
		<aurora-snow/>
		<div class="main-text">
			<h1>Aurora.<br/>This page not found!</h1>
			<span @click="goBack" style="cursor: pointer">Go Back</span>
		</div>
		<div class="ground">
			<div class="mound">
				<div class="mound_text">404</div>
				<div class="mound_spade"></div>
			</div>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted} from "vue";
import {useRouter} from "vue-router";

defineComponent({name: 'index'});

const router = useRouter()

const goBack = () => {
  router.push({name: 'home'})
}


</script>

<style scoped lang="css">
@import "@/styles/theme/error-page.css";
</style>