<template>
	<div class="aurora-card global-common-animate" :style="getCardStyle">
		<div class="aurora-card-title">
			<div v-if="props.icon" class="aurora-card-title-icon">
				<svg-icon :icon="props.icon"/>
			</div>
			<span v-if="props.title">{{props.title}}</span>
			<slot name="header"/>
		</div>
		<div class="aurora-card-content">
			<slot name="content"/>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent} from "vue";
import {useThemeStore} from "@/stores";
import {StringUtil} from "@/utils";

interface Props {
	customStyle?: string,
	title?: string,
	icon?: string
}

defineComponent({name: 'AuroraCard'});

const props = withDefaults(defineProps<Props>(), {})

const themeStore = useThemeStore()

const getCardStyle = computed(() => {
	return `--borderRadius: ${themeStore.currentTheme.borderRadius}px; --fontColor: ${themeStore.currentTheme.fontColor}; --fontFamily: ${themeStore.currentTheme.fontFamily}; --fontSize: ${themeStore.currentTheme.fontSize}; ${props.customStyle}`
})
</script>

<style scoped lang="css">

</style>