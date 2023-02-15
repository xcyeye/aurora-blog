<template>
	<!--<n-dynamic-input v-model:value="customValue" :on-create="onCreate">-->
	<!--	<template #create-button-default>-->
	<!--		随便搞点啥-->
	<!--	</template>-->
	<!--	<template #default="{ value }">-->
	<!--		<div style="display: flex; align-items: center; width: 100%">-->
	<!--			<n-checkbox-->
	<!--				v-model:checked="value.isCheck"-->
	<!--				style="margin-right: 12px"-->
	<!--			/>-->
	<!--			<n-input-number-->
	<!--				v-model:value="value.num"-->
	<!--				style="margin-right: 12px; width: 160px"-->
	<!--			/>-->
	<!--			<n-input v-model:value="value.string" type="text" />-->
	<!--		</div>-->
	<!--	</template>-->
	<!--</n-dynamic-input>-->
	<!--<pre>{{ JSON.stringify(customValue, null, 2) }}</pre>-->
	<div style="height: 20rem;width: 30rem;">
		<aurora-video :video-source-list="[{src: '/欣小萌.mp4', poster: 'https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/pc/10.jpg'}]" />
	</div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'

export default defineComponent({
	setup () {
		return {
			customValue: ref([
				{
					isCheck: true,
					num: 1,
					string: '一个字符串'
				}
			]),
			onCreate () {
				return {
					isCheck: false,
					num: 1,
					string: '一个字符串'
				}
			}
		}
	}
})
</script>
