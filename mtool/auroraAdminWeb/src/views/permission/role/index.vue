<template>
	<div>
		<role-list/>
		<role-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import RoleList from './role-list/index.vue'
import RoleModify from './role-modify/index.vue'

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
