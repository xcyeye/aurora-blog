<template>
	<div>
		<div id="set-bg" class="set-bg-fitter"
				 :style="'--opacity: ' + $store.state.varOpacity +
         '; --borderRadius: ' + $store.state.varBorderRadius +
         'px; --backgroundImageUrl: url(' + $store.state.homeWps + ')'"
		></div>
		<div id="posterShade" :class="{posterShade: $store.state.showPosterShadow}">
			<span :class="{iconSpinner6: $store.state.showShadeLoad}"></span>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'SetBg'});
</script>

<style scoped>

</style>