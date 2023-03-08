<template>
	<div>
		<login-info-list/>
		<login-info-log/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import LoginInfoList from './login-info-list/index.vue'
import LoginInfoLog from './login-info-log/index.vue'

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
