<template>
	<div :style="`--borderRadius: ${useThemeStore().currentTheme.borderRadius}px; --fontColor: ${useThemeStore().currentTheme.fontColor}; --fontFamily: ${useThemeStore().currentTheme.fontFamily}; --fontSize: ${useThemeStore().currentTheme.fontSize};`">
		<global-header/>
		<global-content>
			<template #globalContent>
				<slot name="content"></slot>
			</template>
		</global-content>
		<global-footer/>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent} from "vue";
import {
	GlobalFooter,
	GlobalHeader,
	GlobalSider,
	GlobalContent
} from '../common';
import {useThemeStore} from "@/stores";

defineComponent({name: 'index'});

</script>

<style scoped>

</style>