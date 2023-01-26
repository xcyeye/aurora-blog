<template>
	<div>
		<div :style="setBg" id="set-bg" class="set-bg-fitter"></div>
		<div id="posterShade" :class="{posterShade: false}">
			<span :class="{iconSpinner6: false}"></span>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent} from "vue";
import {useSiteInfo} from "@/stores";
import {StringUtil} from "@/utils";
import blogConfig from '@/config/blogConfig.json'
import {setRandomInterface} from "@/utils/business";

interface Props {
	userUid: string
}

defineComponent({name: 'SetBg'});

const props = withDefaults(defineProps<Props>(), {})

const setBg = computed(() => {
	const siteInfo = useSiteInfo().getSiteInfo(props.userUid)
	let bgUrl = ''
	if (StringUtil.haveLength(siteInfo.randomPictureInterface)) {
		bgUrl = siteInfo.randomPictureInterface!
	}else {
		bgUrl = blogConfig.defaultRandomPicture
	}
	return `--backgroundImageUrl: url(${setRandomInterface(bgUrl)});`
})
</script>

<style scoped>

</style>