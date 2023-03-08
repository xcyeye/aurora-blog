<template>
	<div>
		<category-tag-list/>
		<category-tag-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import CategoryTagList from './category-list/index.vue'
import CategoryTagModify from './category-modify/index.vue'

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
