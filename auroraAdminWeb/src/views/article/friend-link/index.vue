<template>
	<div>
		<friend-link-list/>
		<friend-link-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import FriendLinkList from './friend-link-list/index.vue'
import FriendLinkModify from './friend-link-modify/index.vue'

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
