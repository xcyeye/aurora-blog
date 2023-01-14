<template>
	<div>
		<n-drawer v-model:show="showDrawer" :width="502" placement="right">
			<n-drawer-content :title="currentLoginInfo.username + ' 登录日志'">
				<n-timeline :icon-size="20">
					<n-timeline-item
						v-for="(item, index) in userLoginInfoArr" :key="index"
						:type="item.status ? 'success' : 'warning'"
						:title="item.status ? '成功': '失败'"
						:content="item.message"
						:time="item.createTime"
					>
						<template #icon>
							<n-icon>
								<n-avatar
									round
									size="small"
									:src="currentUserInfo.avatar"
								/>
							</n-icon>
						</template>
						<template #header>
							<n-gradient-text :type="item.status ? 'success' : 'error'">
								{{item.status ? '成功': '失败'}}
							</n-gradient-text>
						</template>
						<template #default>
							<n-space vertical>
								<n-descriptions label-placement="left">
									<n-descriptions-item label="IP">
										{{item.loginIp}}
									</n-descriptions-item>
								</n-descriptions>
								<n-descriptions label-placement="left">
									<n-descriptions-item label="Address">
										{{item.loginLocation}}
									</n-descriptions-item>
								</n-descriptions>
								<n-descriptions label-placement="left">
									<n-descriptions-item label="Message">
										{{item.message}}
									</n-descriptions-item>
								</n-descriptions>
							</n-space>
						</template>
						<template #footer>
							{{item.createTime}}
						</template>
					</n-timeline-item>
				</n-timeline>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref, watch} from "vue";
import {LoginInfoVo} from "@/theme/vo/auth/LoginInfoVo";
import {loginInfoApi, userApi} from "@/service";
import {UserVo} from "@/theme/vo/admin/UserVo";
import {emitter} from "@/utils";

defineComponent({name: 'index'});

const showDrawer = ref(false)
const currentLoginInfo = ref<LoginInfoVo>({})
const userLoginInfoArr = ref<Array<LoginInfoVo>>([])
const currentUserInfo = ref<UserVo>({})

onBeforeMount(() => {
	emitter.on('authServerLoginInfoShowLogAction', e => {
		if (e) {
			currentLoginInfo.value = e as LoginInfoVo
			showDrawer.value = true

			// 查询用户信息
			userApi.queryOneData({username: currentLoginInfo.value.username}).then(result => {
				if (result.data) {
					currentUserInfo.value = result.data
				}
			})
		}
	})
})

watch(() => currentLoginInfo.value, (nv, ov) => {
	console.log(currentLoginInfo.value);
	// 如果登录信息改变，重新获取该用户的所有登录信息
	loginInfoApi.queryListDataByCondition({keyword: currentLoginInfo.value?.username}).then(result => {
		if (result.data && result.data.result) {
			userLoginInfoArr.value = result.data.result
		}
	})
})
</script>

<style scoped>

</style>
