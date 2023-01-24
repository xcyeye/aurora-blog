<template>
	<div class="aurora-random-picture-box" :style="`--aurora-random-picture-pc-height: ${pcHeight}rem;--aurora-random-picture-mobile-height: ${mobileHeight}rem;`">
		<div v-if="randomPictureInterface.length > 0" :class="customPictureClass" :style="setRandomPicture" class="aurora-random-picture-box-bg"/>
		<div class="aurora-random-picture-content" :class="customContentClass">
			<slot/>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref} from "vue";
import {useSiteInfo} from "@/stores";
import {StringUtil} from "@/utils";

interface Props {
	userUid: string,
	customContentClass?: string,
	customPictureClass?: string,
	pcHeight?: number,
	mobileHeight?: number
}

defineComponent({name: 'AuroraRandomPicture'});

const props = withDefaults(defineProps<Props>(), {})

const useSite = useSiteInfo()
const siteInfo = ref<SiteSettingInfo>({})
const randomPictureInterface = ref<string>('')

const setRandomPicture = computed(() => {
	return `--aurora-random-picture-interface: url("${randomPictureInterface.value}");`
})

onMounted(() => {
	if (StringUtil.haveLength(props.userUid)) {
		siteInfo.value = useSite.getSiteInfo(props.userUid)
		if (StringUtil.haveLength(siteInfo.value.randomPictureInterface)) {
			randomPictureInterface.value = siteInfo.value.randomPictureInterface!
		}else if (StringUtil.haveLength(siteInfo.value.defaultCoverRequestInterface)) {
			randomPictureInterface.value = siteInfo.value.defaultCoverRequestInterface!
		}
	}
})
</script>

<style scoped>

</style>