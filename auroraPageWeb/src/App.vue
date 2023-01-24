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
import {dateZhCN, zhCN} from 'naive-ui';
import BasicLayout from './layouts/BasicLayout/index.vue';
import blogConfig from "@/config/blogConfig.json";
// import { subscribeStore, useThemeStore } from '@/store';
// import { useGlobalEvents } from '@/composables';

// const bean = useThemeStore();
//
// subscribeStore();
// useGlobalEvents();

console.log(`%c ${blogConfig.projectInfo.projectName} %c by ${blogConfig.projectInfo.author}","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;`)
console.log(`%c Version %c ${blogConfig.projectInfo.version}`,`font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(255,202,212,.8);padding: 10px;border-bottom-left-radius: 13px;border-top-left-radius: 13px;","font-weight: bold;color: white;display: inline-block;text-align: center;height: 1.5rem;line-height: 1.5rem;background-color: rgba(178,247,239,.85);padding: 10px;border-bottom-right-radius: 13px;border-top-right-radius: 13px;`)
</script>

<style scoped></style>
