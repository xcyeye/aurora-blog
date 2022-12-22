<template>
	<div>
		<upload-file :control-upload-file="false" @handleFinishUploadFile="handleFinishUploadFile" upload-file-interface="single" :accept-file-type-str="['.png','.jpg']" :show-upload-dragger="true" :parameter-data="{userUid: '1522074993315815424', summary: '这是props测试', storageMode: 0}">

		</upload-file>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import {UploadFileInfo} from "naive-ui";

defineComponent({name: 'index'})

const handleFinishUploadFile = (file: UploadFileInfo) => {

}
</script>

<style scoped>

</style>
