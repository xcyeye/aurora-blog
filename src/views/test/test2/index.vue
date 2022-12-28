<template>
	<n-space vertical>
		<n-button @click="handleClick">
			没有任何意义的改动
		</n-button>
		<n-tabs ref="tabsInstRef" v-model:value="value">
			<n-tab v-for="tab in tabs" :key="tab" :name="tab">
				我是 {{ tab }}
			</n-tab>
		</n-tabs>
	</n-space>
</template>

<script lang="ts">
import { defineComponent, nextTick, ref } from 'vue'
import { TabsInst } from 'naive-ui'

export default defineComponent({
	setup () {
		const tabsInstRef = ref<TabsInst | null>(null)
		const tabsRef = ref(['a', 'b'])
		const valueRef = ref('a')
		const handleClick = () => {
			tabsRef.value.reverse()
			valueRef.value = 'a'
			nextTick(() => tabsInstRef.value?.syncBarPosition())
		}
		return {
			tabsInstRef,
			tabs: tabsRef,
			value: valueRef,
			handleClick
		}
	}
})
</script>
