<template>
	<n-dynamic-tags v-model:value="tags" />
</template>

<script lang="ts" setup>
import {defineComponent, ref, watch} from 'vue';
const tags = ref([])

watch(tags, () => {
	console.log(tags.value);
})
</script>
