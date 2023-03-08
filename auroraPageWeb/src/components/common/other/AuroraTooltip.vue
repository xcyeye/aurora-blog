<template>
	<div>
		<div class="aurora-tooltip">
			<slot/>
			<span class="tooltipText">{{title}}</span>
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

interface Props {
	title: string
}

defineComponent({name: 'AuroraTooltip'});

const props = withDefaults(defineProps<Props>(), {})
</script>

<style scoped>

</style>