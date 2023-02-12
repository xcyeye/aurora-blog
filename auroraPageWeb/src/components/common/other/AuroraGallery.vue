<template>
	<n-scrollbar>
		<div id="gallery" class="container-fluid" :style="`--pc-gallery-column: ${pcGalleryColumn};--mobile-gallery-column: ${mobileGalleryColumn};`">
			<div @click="clickPicture(item)" v-for="(item) in pictureList" :key="item.uid" class="gallery-item">
				<n-image :src="getImageSrc(item)"/>
				<div class="gallery-img-desc">
					<slot/>
				</div>
			</div>
		</div>
	</n-scrollbar>
</template>

<script lang="ts" setup>
import {computed, defineComponent} from "vue";
import {FileVo} from "@/bean/vo/file/fileVo";
import {useSysSettingStore} from "@/stores";
import {REGEXP_URL} from "@/config";
import {isNotEmptyObject} from "@/utils/business";
import {emitter, StringUtil} from "@/utils";

interface Props {
	pictureList: Array<FileVo>,
	pcGalleryColumn: number,
	mobileGalleryColumn: number,
}

defineComponent({name: 'AuroraGallery'});

const props = withDefaults(defineProps<Props>(), {
	pcGalleryColumn: 5,
	mobileGalleryColumn: 3
})

const sysSettingStore = useSysSettingStore()

const getImageSrc = computed(() => {
	return (pictureFile: FileVo) => {
		if (REGEXP_URL.test(pictureFile.path!)) return pictureFile.path
		let host = ""
		if (isNotEmptyObject(sysSettingStore.sysSettingMap.get('nginx_file_host')) && StringUtil.haveLength(sysSettingStore.sysSettingMap.get('nginx_file_host')!.paramValue)) {
			host = sysSettingStore.sysSettingMap.get('nginx_file_host')!.paramValue as string
		}
		if (host.endsWith("/")) {
			host = host.substring(0, host.length - 1)
		}
		return host + pictureFile.path
	}
})

const clickPicture = (pictureFile: FileVo) => {
	emitter.emit('galleryClickPicture', pictureFile)
}
</script>

<style scoped>

</style>