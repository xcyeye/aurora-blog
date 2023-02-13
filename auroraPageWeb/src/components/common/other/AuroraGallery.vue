<template>
	<n-scrollbar>
		<div id="gallery" class="container-fluid" :style="`--pc-gallery-column: ${pcGalleryColumn};--mobile-gallery-column: ${mobileGalleryColumn};`">
			<div @click="clickPicture(item)" v-for="(item) in pictureListTemp" :key="item.uid" class="gallery-item aurora-gallery-img-lazy-loading">
				<n-image :src="getImageSrc(item)"/>
				<div class="gallery-img-desc">
					<slot/>
				</div>
			</div>
		</div>
		<div v-if="showLoadMoreBut" class="gallery-more">
			<span @click="clickLoadMorePicture">More</span>
		</div>
	</n-scrollbar>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref, watch} from "vue";
import {FileVo} from "@/bean/vo/file/fileVo";
import {useSysSettingStore} from "@/stores";
import {REGEXP_URL} from "@/config";
import {isNotEmptyObject} from "@/utils/business";
import {emitter, StringUtil} from "@/utils";

interface Props {
	pictureList: Array<FileVo>,
	pcGalleryColumn: number,
	mobileGalleryColumn: number,
	showLoadMoreBut: boolean
}

defineComponent({name: 'AuroraGallery'});

const props = withDefaults(defineProps<Props>(), {
	pcGalleryColumn: 4,
	mobileGalleryColumn: 2,
	showLoadMoreBut: true
})

const sysSettingStore = useSysSettingStore()
const pictureListTemp = ref<Array<FileVo>>([])

pictureListTemp.value = props.pictureList

const emits = defineEmits(['clickLoadMorePicture', 'clickPicture'])

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
	emits('clickPicture', pictureFile)
}

const handleScroll = () => {
	let clientHeight = document.documentElement.clientHeight
	// console.log(document.getElementById('gallery')!.getBoundingClientRect().top);
	console.log(document.getElementById('gallery')!.parentElement);
	// let articleLazyLoadingImg = document.querySelectorAll(".aurora-gallery-img-lazy-loading")
	// for (let i = 0; i < articleLazyLoadingImg.length; i++) {
	// 	let distance_top = articleLazyLoadingImg[i].getBoundingClientRect().top
	// 	if (distance_top < clientHeight) {
	// 		//加载图片
	// 		let elementsByTagName = articleLazyLoadingImg[i].getElementsByTagName("img");
	// 		let dataSrc = elementsByTagName[0].getAttribute("data-src") as string;
	// 		elementsByTagName[0].setAttribute("src",dataSrc)
	// 	}
	// }
}

const clickLoadMorePicture = () => {
	emits('clickLoadMorePicture');
}

watch(() => props.pictureList, () => {
	pictureListTemp.value = []
	pictureListTemp.value = props.pictureList
})

onMounted(() => {
	// window.addEventListener('scroll', handleScroll, true)
})


</script>

<style scoped>

</style>