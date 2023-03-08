<template>
	<div class="h-full">
		<n-card :title="`Hi ${authStore.userInfo.username} (￣┰￣*)`" class="h-full shadow-sm rounded-16px">
			<n-tabs type="line" animated>
				<n-tab-pane name="oasis" tab="信息">
					<user-info />
				</n-tab-pane>
				<n-tab-pane name="ReadMe" tab="ReadMe">
					<user-readme/>
				</n-tab-pane>
				<n-tab-pane name="jay chou" tab="安全">
					<user-security />
				</n-tab-pane>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import UserInfo from './user-info/index.vue'
import UserSecurity from './user-security/index.vue'
import UserReadme from './readme/index.vue'
import {useAuthStore} from "@/store";
const authStore = useAuthStore()

defineComponent({name: 'index'});
</script>

<style scoped>

</style>
