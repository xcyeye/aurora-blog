<template>
	<n-dynamic-tags v-model:value="tags" />
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from 'vue';
import {getFileFormat, getRandomNum} from "@/utils";
import {EnumFileStorageModeConstant} from "@/enum";
const tags = ref([])

onMounted(() => {
	// console.log(EnumFileStorageModeConstant.LOCAL_STORAGE.toString(0));
	console.log(getFileFormat('.png'));
})
</script>
