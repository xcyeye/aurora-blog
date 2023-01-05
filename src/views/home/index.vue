<template>
	<div>
		<n-space justify="space-around">
			<router-link to="/user">没有数据</router-link>
			<router-link to="/user/1522074993315815424">有数据</router-link>
		</n-space>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'});
</script>

<style scoped>

</style>