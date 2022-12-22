<template>
	<div class="h-full">
		<n-card :title="`Nacos ${systemInfo.backendEnvironment.cloud.Nacos}`" class="h-full shadow-sm rounded-16px">
			<template #header-extra>
				<n-input v-model:value="monitorSrc" size="small" round placeholder="请输入监控地址" />
			</template>
			<iframe style="margin-top: 10px;" class="wh-full" :src="monitorSrc"></iframe>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, ref} from "vue";
import systemInfo from "~/src/field/systemInfo/systemInfo.json"

defineComponent({name: 'index'});

const monitorSrc = ref('https://aurora.xcye.xyz')
</script>

<style scoped>

</style>
