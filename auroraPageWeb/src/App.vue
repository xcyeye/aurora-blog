<template>
	<n-config-provider
		:locale="zhCN"
		:date-locale="dateZhCN"
		class="h-full"
	>
		<naive-provider>
			<BasicLayout>
				<template #content>
					<metainfo>
						<template v-slot:title="{ content }">{{ content }}</template>
					</metainfo>
					<router-view />
				</template>
			</BasicLayout>
		</naive-provider>
	</n-config-provider>
</template>

<script setup lang="ts">
import { dateZhCN, zhCN } from 'naive-ui';
import BasicLayout from './layouts/BasicLayout/index.vue'
import {computed} from "vue";
import {getRandomNum, hexToRgb} from "@/utils";
import blogConfig from "@/config/blogConfig.json";
import {useThemeStore} from "@/stores";
// import { subscribeStore, useThemeStore } from '@/store';
// import { useGlobalEvents } from '@/composables';

// const bean = useThemeStore();
//
// subscribeStore();
// useGlobalEvents();
</script>

<style scoped></style>
