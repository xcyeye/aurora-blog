<template>
	<div>
		<button @click="request">click</button>
	</div>
</template>

<script lang="ts">
import {roleApi} from "@/service/api/admin/roleApi";

export default {
	name: "index",
	components: {},
	data() {
		return {}
	},
	created() {

	},
	methods: {
		request() {
			// roleApi.batchInsertData({})
		}
	},
	computed: {}
}
</script>

<style scoped>

</style>
