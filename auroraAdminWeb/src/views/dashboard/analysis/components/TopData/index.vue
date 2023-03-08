<template>
	<div>
		<n-card title="仪表盘" :bordered="false" class="rounded-16px shadow-sm">
			<h1>{{getNightOrDayInfo}} <span style="font-weight: bold">{{authStore.userInfo.username}}</span> o(￣▽￣)ｄ</h1>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent} from "vue";
import {useAuthStore} from "@/store";

defineComponent({name: 'index'});

const authStore = useAuthStore()

const getNightOrDayInfo = computed(() => {
	const hour = new Date().getHours()
	if (hour >= 6 && hour <= 19) {
		return '你好 '
	}else {
		return '晚上好 '
	}
})
</script>

<style scoped>

</style>
