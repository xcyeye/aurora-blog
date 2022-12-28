<template>
	<div>
		<comment-list/>
		<comment-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import CommentList from './comment-list/index.vue'
import CommentModify from './comment-modify/index.vue'

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
