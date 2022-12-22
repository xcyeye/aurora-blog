<template>
	<div>
		<markdown-editor :vditor="{outline: {enable:true}}" summary="ceshiceshi"></markdown-editor>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import MarkdownEditor from "@/components/article/MarkdownEditor.vue";

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
