<template>
	<div>
		<button @click="test">sdf</button>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()
export default defineComponent({
	methods:{
		test() {
			console.log(this.$route.params);
		}
	},
	mounted() {
		console.log(this.$route.params);
	}
})
</script>

<style scoped>

</style>