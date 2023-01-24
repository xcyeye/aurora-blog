<template>
	<div>
		<div id="set-bg" class="set-bg-fitter"></div>
		<div id="posterShade" :class="{posterShade: false}">
			<span :class="{iconSpinner6: false}"></span>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'SetBg'});
</script>

<style scoped>

</style>