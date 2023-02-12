<template>
	<!--<div class="test-parent">-->
	<!--	<div class="son1">我是son1</div>-->
	<!--	<span>我是son1和son2中间的元素</span>-->
	<!--	<div class="son2">我是son2</div>-->
	<!--</div>-->
	<!--<aurora-login :show-login-modal="true" user-uid="34856"/>-->
	<div class="galaxy">
		<aurora-gallery :picture-list="pictureArr" :mobile-gallery-column="2" :pc-gallery-column="3">
			<div>aksdjf</div>
		</aurora-gallery>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref} from "vue";
import {fileApi, talkApi} from "@/service";
import {emitter, StringUtil} from "@/utils";
import {AuroraFile} from "@/bean/pojo/file/file";
import {FileVo} from "@/bean/vo/file/fileVo";

defineComponent({name: 'index'});

const pictureArr = ref<Array<FileVo>>([])

const loadPicture = () => {
	fileApi.queryListDataByCondition({otherUid: '1522074993315815424', delete: false}).then(result => {
		if (result.data && result.data.result) {
			pictureArr.value = result.data.result
		}
	})
}

loadPicture()

onBeforeMount(() => {
	emitter.on('galleryClickPicture', e => {
		console.log(e);
	})
})
</script>

<style scoped lang="css">
.test-parent {
	position: relative;
	width: 70%;
	margin: 0 auto;
	height: 20rem;
	background-color: black;
	overflow: scroll;
}
.son1 {
	height: 10rem;
	background-color: red;
}
.son2 {
	height: 15rem;
	background-color: palevioletred;
	position: absolute;
	bottom: 0;
	right: 0;
	width: 100%;
}
.son2:after {
	content: '';
	display: block;
	clear: both;
	width: 100%;
}

.galaxy {
	width: 70%;
	height: 20rem;
	margin: 0 auto;
	background-color: red;
}
</style>