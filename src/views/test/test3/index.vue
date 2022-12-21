<template>
	<div>
		<operate-drawer drawer-title="这是测试标题" drawer-placement="right" :show-drawer="true">
			<template #drawerContent1>
				<n-button>asdf</n-button>
			</template>
		</operate-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'})
</script>

<style scoped>

</style>
