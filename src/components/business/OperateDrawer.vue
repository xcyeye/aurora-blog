<template>
	<div>
		<n-drawer v-model:show="showDrawerFlag" :width="width" :placement="drawerPlacement">
			<n-drawer-content :title="drawerTitle">
				<n-space vertical>
					<slot name="drawerContent1"/>
					<slot name="drawerContent2"/>
					<slot name="drawerContent3"/>
					<slot name="drawerContent4"/>
					<slot name="drawerContent5"/>
					<slot name="drawerContent6"/>
					<slot name="drawerContent7"/>
					<slot name="drawerContent8"/>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";

defineComponent({name: 'OperateDrawer'});

interface Props {
	showDrawer?: boolean,
	drawerTitle?: string,
	drawerPlacement?: 'top' | 'right' | 'bottom' | 'left',
	width: number
}

// @ts-ignore
const props = withDefaults(defineProps<Props>(), {
	showDrawer: false,
	drawerTitle: '',
	drawerPlacement: 'left',
	width: 502
});

const showDrawerFlag = ref(true);

</script>

<style scoped>

</style>
