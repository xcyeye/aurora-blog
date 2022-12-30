<template>
	<div>
		<sys-setting-list/>
		<sys-setting-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import SysSettingList from './sys-setting-list/index.vue'
import SysSettingModify from './sys-setting-modify/index.vue'

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
