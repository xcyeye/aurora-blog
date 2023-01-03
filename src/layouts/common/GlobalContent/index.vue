<template>
	<div class="aurora-main-content">
		<slot name="globalContent"></slot>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'});
</script>

<style scoped>

</style>