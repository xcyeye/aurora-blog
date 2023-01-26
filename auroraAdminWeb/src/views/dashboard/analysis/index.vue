<template>
  <n-space :vertical="true" :size="16">
		<top-data/>
    <!--<top-chart />-->
    <data-card />
    <!--<bottom-part />-->
  </n-space>
</template>

<script lang="ts" setup>
import { BottomPart, DataCard, TopChart, TopData } from './components';
</script>

<style scoped></style>
