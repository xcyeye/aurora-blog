<template>
	<n-scrollbar :x-scrollable="false">
		<div id="gallery" class="container-fluid" :style="`--pc-gallery-column: ${pcGalleryColumn};--mobile-gallery-column: ${mobileGalleryColumn};`">
			<div @click.stop="clickPicture(item)" v-for="(item) in pictureListTemp" :key="item.uid" class="gallery-item aurora-gallery-img-lazy-loading">
				<n-image v-if="assertIsImg(item)" :src="getImageSrc(item)">
					<template #placeholder>
						<img id="gallery-lazy-img" :src="lazyImg" alt="">
					</template>
				</n-image>
				<aurora-video v-if="assertIsVideo(item)" :video-source-list="getVideoSource(item)"/>
				<div @click.stop="clickPictureDesc(item)" class="gallery-img-desc">
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
import {isNotEmptyObject, setLazyImg} from "@/utils/business";
import {emitter, getRealImageUrl, isImage, isVideo, StringUtil} from "@/utils";

interface Props {
	pictureList: Array<FileVo>,
	pcGalleryColumn?: number,
	mobileGalleryColumn?: number,
	showLoadMoreBut?: boolean,
	userUid: string
}

defineComponent({name: 'AuroraGallery'});

const props = withDefaults(defineProps<Props>(), {
	pcGalleryColumn: 4,
	mobileGalleryColumn: 2,
	showLoadMoreBut: true
})

const sysSettingStore = useSysSettingStore()
const pictureListTemp = ref<Array<FileVo>>([])
const lazyImg = ref('')

pictureListTemp.value = props.pictureList

const emits = defineEmits(['clickLoadMorePicture', 'clickPicture', 'clickPictureDesc'])

lazyImg.value = setLazyImg(props.userUid)!

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
		return getRealImageUrl(host, pictureFile.path)
	}
})

const assertIsImg = computed(() => {
	return (file: FileVo) => {
		return isImage(file.path!);
	}
})

const assertIsVideo = computed(() => {
	return (file: FileVo) => {
		return isVideo(file.path!);
		
	}
})

const getVideoSource = computed(() => {
	return (file: FileVo): Array<VideoSource> => {
		console.log(REGEXP_URL.test('http://aurora-cdn.xcye.xyz/aurora-upload/mp4/2023/2/欣小萌1676446757457.mp4'));
		if (REGEXP_URL.test(file.path!)) return [{src: file.path!}]
		let host = ""
		if (isNotEmptyObject(sysSettingStore.sysSettingMap.get('nginx_file_host')) && StringUtil.haveLength(sysSettingStore.sysSettingMap.get('nginx_file_host')!.paramValue)) {
			host = sysSettingStore.sysSettingMap.get('nginx_file_host')!.paramValue as string
		}
		if (host.endsWith("/")) {
			host = host.substring(0, host.length - 1)
		}
		return [{src: host + file.path!}]
	}
})

const getVideoStyle = computed(() => {
	return (file: FileVo) => {
		if (isVideo(file.path!)) {
			return 'min-height: 7rem;'
		}
	}
})

const clickPicture = (pictureFile: FileVo) => {
	emitter.emit('galleryClickPicture', pictureFile)
	emits('clickPicture', pictureFile)
}

const clickPictureDesc = (pictureFile: FileVo) => {
	emitter.emit('galleryClickPicture', pictureFile)
	emits('clickPictureDesc', pictureFile)
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
