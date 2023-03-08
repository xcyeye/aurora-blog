<template>
	<div>
		<article-list/>
		<article-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import ArticleList from './article-list/index.vue'
import ArticleModify from './article-modify/index.vue'

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
