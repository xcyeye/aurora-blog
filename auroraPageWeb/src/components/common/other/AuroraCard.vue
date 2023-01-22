<template>
	<div class="aurora-card global-common-animate" :class="{'bg-linear-gradient': showLinearGradient}" :style="getCardStyle">
		<div class="aurora-card-title" v-if="showHeader">
			<div v-if="props.icon" class="aurora-card-title-icon">
				<svg-icon :icon="props.icon"/>
			</div>
			<span v-if="props.title">{{props.title}}</span>
			<slot name="header"/>
		</div>
		<div class="aurora-card-content">
			<slot/>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref, useSlots} from "vue";
import {useThemeStore} from "@/stores";
import blogConfig from '@/config/blogConfig.json'
import {getRandomNum, hexToRgb, StringUtil} from "@/utils";

interface Props {
	customStyle?: string,
	title?: string,
	icon?: string,
	showLinearGradient?: boolean
}

defineComponent({name: 'AuroraCard'});

const props = withDefaults(defineProps<Props>(), {
	showLinearGradient: false
})

const showHeader = ref(true)

const themeStore = useThemeStore()

const getCardStyle = computed(() => {
	let style = `--borderRadius: ${themeStore.currentTheme.borderRadius}px; --fontColor: ${themeStore.currentTheme.fontColor}; --fontFamily: ${themeStore.currentTheme.fontFamily}; --fontSize: ${themeStore.currentTheme.fontSize}; ${props.customStyle}`
	if (props.showLinearGradient) {
		let bg = '--linerGradient: linear-gradient(315deg, '
		for (let i = 0; i < 4; i++) {
			const hex = hexToRgb(blogConfig.randomColor[getRandomNum(0, blogConfig.randomColor.length)])
			let color = `rgba(${hex?.r}, ${hex?.g}, ${hex?.b}, 1) ${i * 30 + 3}%,`
			bg = bg + color
		}
		bg = bg.substring(0, bg.length - 1) + ");"
		style = bg + style
	}
	return style
})

onMounted(() => {
	const slotHeader = !!useSlots().header;
	if (!StringUtil.haveLength(props.title) && !StringUtil.haveLength(props.icon) && !slotHeader) {
		showHeader.value = false
	}
})
</script>

<style scoped lang="css">

</style>