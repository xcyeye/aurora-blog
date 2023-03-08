<template>
	<div>
		<user-list/>
		<user-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import UserList from './user-list/index.vue'
import UserModify from './user-modify/index.vue'

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
