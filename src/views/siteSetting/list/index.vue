<template>
	<div>
		<site-setting-list/>
		<site-setting-modify/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import SiteSettingList from './site-setting-list/index.vue'
import SiteSettingModify from './site-setting-modify/index.vue'

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
