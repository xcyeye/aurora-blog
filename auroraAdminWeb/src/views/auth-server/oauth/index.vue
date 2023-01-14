<template>
	<div>
		<oauth-client-list/>
		<oauth-client-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";
import OauthClientList from './oauth-client-list/index.vue'
import OauthClientModify from './oauth-client-modify/index.vue'

defineComponent({name: 'index'});

</script>

<style scoped>

</style>
