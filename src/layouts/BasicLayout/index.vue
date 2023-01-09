<template>
	<div>
		<global-header/>
		<global-content>
			<template #globalContent>
				<slot name="content"></slot>
			</template>
		</global-content>
		<global-footer/>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import {
	GlobalFooter,
	GlobalHeader,
	GlobalSider,
	GlobalContent
} from '../common';

defineComponent({name: 'index'});
</script>

<style scoped>

</style>