<template>
	<div>
		<admin-router-list/>
		<admin-router-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import AdminRouterList from './admin-router-list/index.vue'
import AdminRouterModify from './admin-router-modify/index.vue'

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
