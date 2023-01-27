<template>
	<div>
		<div :style="globalBgImg" id="set-bg" class="set-bg-fitter"></div>
		<div id="posterShade" :class="{posterShade: false}">
			<span :class="{iconSpinner6: false}"></span>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref} from "vue";
import {useSiteInfo} from "@/stores";
import {getRandomNum, StringUtil} from "@/utils";
import blogConfig from '@/config/blogConfig.json'
import {setRandomInterface} from "@/utils/business";

interface Props {
	userUid: string
}

defineComponent({name: 'SetBg'});

const props = withDefaults(defineProps<Props>(), {})

const globalBgImg = ref('')

const setBgImg = () => {
	const siteInfo = useSiteInfo().getSiteInfo(props.userUid)
	let bgUrl = ''
	if (window.screen.width > 719) {
		if (siteInfo.pcBackgroundImageList && siteInfo.pcBackgroundImageList.length > 0) {
			bgUrl = siteInfo.pcBackgroundImageList[getRandomNum(0, siteInfo.pcBackgroundImageList?.length - 1)]
		}
	}else {
		if (siteInfo.mobileBackgroundImageList && siteInfo.mobileBackgroundImageList.length > 0) {
			bgUrl = siteInfo.mobileBackgroundImageList[getRandomNum(0, siteInfo.mobileBackgroundImageList?.length - 1)]
		}
	}
	if (!StringUtil.haveLength(bgUrl)) {
		if (StringUtil.haveLength(siteInfo.randomPictureInterface)) {
			bgUrl = siteInfo.randomPictureInterface!
		}else {
			bgUrl = blogConfig.defaultRandomPicture
		}
	}
	globalBgImg.value = `--backgroundImageUrl: url(${setRandomInterface(bgUrl)});`
}

onMounted(() => {
	setBgImg()
})
</script>

<style scoped>

</style>