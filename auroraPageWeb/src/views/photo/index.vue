<template>
	<div>
		<photo-waterfall @handleClickImage="handleClickImage" :picture-src-list="pictureSrcList" :show-bg-color="false"/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'});

const handleClickImage = (photoInfo:{photoUrl: string}) => {

}

const pictureSrcList: Array<string> = ['http://127.0.0.1/aurora-upload/png/2023/1/illust_88479853_20220906_081459.png','http://127.0.0.1/aurora-upload/png/2023/1/illust_88380383_20220717_200036.png','http://127.0.0.1/aurora-upload/png/2023/1/illust_88911364_20220928_080456.png','http://127.0.0.1/aurora-upload/png/2023/1/illust_88927530_20220928_081118.png','http://127.0.0.1/aurora-upload/jpg/2023/1/illust_88314013_20220928_080426.jpg']
</script>

<style scoped>

</style>