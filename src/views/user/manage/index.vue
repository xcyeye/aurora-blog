<template>
	<div>
		<user-list/>
		<user-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";
import UserList from './user-list/index.vue'
import UserModify from './user-modify/index.vue'
import {AuroraFile} from "@/theme/pojo/file/file";

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
