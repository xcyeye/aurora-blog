<template>
	<div class="parcel-style global-common-animate" :style="`--borderRadius: ${themeStore.currentTheme.borderRadius}px; --fontColor: ${themeStore.currentTheme.fontColor}; --fontFamily: ${themeStore.currentTheme.fontFamily}; --fontSize: ${themeStore.currentTheme.fontSize};`">
		<slot/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import {useThemeStore} from "@/stores";

defineComponent({name: 'ParcelStyle'});

const themeStore = useThemeStore()
</script>

<style scoped>

</style>