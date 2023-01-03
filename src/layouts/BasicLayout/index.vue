<template>
	<div>
		<global-header/>
		<global-content>
			<template #globalContent>
				<slot name="content"></slot>
			</template>
		</global-content>
		<global-footer/>
		<div id="set-bg" class="set-bg-fitter"
				 :style="'--opacity: ' + $store.state.varOpacity +
         '; --borderRadius: ' + $store.state.varBorderRadius +
         'px; --backgroundImageUrl: url(https://pic4.zhimg.com/v2-5523ed93593ee4c7fd97b972d0af92d3_r.jpg)'"
		></div>
		<div id="posterShade" :class="{posterShade: $store.state.showPosterShadow}">
			<span :class="{iconSpinner6: $store.state.showShadeLoad}"></span>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import {
	GlobalFooter,
	GlobalHeader,
	GlobalSider,
	GlobalContent
} from '../common';

defineComponent({name: 'index'});
</script>

<style scoped>

</style>