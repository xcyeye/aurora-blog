<template>
	<div>
		<white-url-list/>
		<white-url-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import WhiteUrlList from './white-url-list/index.vue'
import WhiteUrlModify from './white-url-modify/index.vue'

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
