<template>
	<div>
		<markdown-editor @vditorInput="vditorInput" :vditor="{outline: {enable: true, position: 'right'}, counter: {enable: true, max: 200}}" summary="ceshiceshi"></markdown-editor>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import MarkdownEditor from "@/components/article/MarkdownEditor.vue";

defineComponent({name: 'index'});

const vditorInput = (value: string) => {
	// console.log(value);
}
</script>

<style scoped>

</style>
