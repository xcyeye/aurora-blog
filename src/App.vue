<template>
	<n-config-provider
		:locale="zhCN"
		:date-locale="dateZhCN"
		class="h-full"
	>
		<naive-provider>
			<BasicLayout>
				<template #content>
					<router-view />
				</template>
			</BasicLayout>
		</naive-provider>
	</n-config-provider>
</template>

<script setup lang="ts">
import { dateZhCN, zhCN } from 'naive-ui';
import BasicLayout from './layouts/BasicLayout/index.vue'
// import { subscribeStore, useThemeStore } from '@/store';
// import { useGlobalEvents } from '@/composables';

// const theme = useThemeStore();
//
// subscribeStore();
// useGlobalEvents();
</script>

<style scoped></style>
