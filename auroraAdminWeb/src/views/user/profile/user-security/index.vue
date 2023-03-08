<template>
	<div>
		<n-grid cols="2" item-responsive>
			<n-grid-item span="l:3">
				<n-space vertical :size="28">
					<n-card title="修改密码" :bordered="false">
						<n-space vertical :size="28">
							<n-space vertical :size="14">
								<n-text> 旧密码 </n-text>
								<n-input v-model:value="originPwd" :minlength="4" :maxlength="15" round placeholder="旧密码" />
							</n-space>

							<n-space vertical :size="14">
								<n-text> 新密码 </n-text>
								<n-input v-model:value="newPwd" :minlength="4" :maxlength="15" round placeholder="新密码" />
							</n-space>

							<n-space vertical :size="14">
								<n-text> 重复密码 </n-text>
								<n-input v-model:value="repeatPwd" :minlength="4" :maxlength="15" round placeholder="重复密码" />
							</n-space>

							<n-row :gutter="[0, 24]">
								<n-col :span="24">
									<div style="display: flex; justify-content: right">
										<n-button round type="primary" @click="handleUpdatePwd"> 修改 </n-button>
									</div>
								</n-col>
							</n-row>
						</n-space>
					</n-card>

					<n-card title="绑定邮箱" :bordered="false">
						<n-space vertical :size="28">
							<n-space vertical :size="14">
								<n-text> 邮箱号 </n-text>
								<n-input
									v-model:value="emailNumber"
									:minlength="4"
									:maxlength="254"
									round
									:placeholder="authStore.userInfo.verify_email ? '请输入新邮箱' : '请输入邮箱'"
								/>
							</n-space>
							<n-space justify="end">
								<n-button round type="primary" @click="handleBindEmail">
									{{ authStore.userInfo.verify_email ? '换绑' : '添加' }}
								</n-button>
							</n-space>
						</n-space>
					</n-card>
				</n-space>
			</n-grid-item>
			<n-grid-item span="l:3"> </n-grid-item>
		</n-grid>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import {REGEXP_EMAIL, REGEXP_PWD} from "@/config";
import {emailApi, userApi} from "@/service";
import {useAuthStore} from "@/store";
import app from "@/App.vue";
import {localStg} from "@/utils";

defineComponent({name: 'index'});

const originPwd = ref<string>('')
const newPwd = ref<string>('')
const repeatPwd = ref<string>('')
const emailNumber = ref<string>('')
const authStore = useAuthStore()

const handleUpdatePwd = () => {
	if (newPwd.value.length === 0 || repeatPwd.value.length === 0 || originPwd.value.length === 0) {
		window.$message?.error('表单信息不能为空')
		return
	}
	if (newPwd.value !== repeatPwd.value) {
		window.$message?.error('两个密码必须相同')
		return
	}
	if (!REGEXP_PWD.test(newPwd.value) ||
		!REGEXP_PWD.test(repeatPwd.value) ||
		!REGEXP_PWD.test(originPwd.value)) {
		window.$message?.error('密码: 至少1个大写字母，1个小写字母，1个数字，1个特殊字符')
		return;
	}
	userApi.updatePassword({
		originPwd: originPwd.value,
		newPwd: newPwd.value,
		username: authStore.userInfo.username
	}).then(result => {
		if (result.data && result.data === 1) {
			window.$message?.success('修改密码成功')
			authStore.resetAuthStore()
			app.reloadPage();
		}else {
			window.$message?.error('修改密码失败')
		}
	})
}

const handleBindEmail = () => {
	if (!REGEXP_EMAIL.test(emailNumber.value)) {
		window.$message?.error('请输入有效的邮箱号')
		return
	}
	userApi.bindingEmail({uid: authStore.userInfo.user_uid, emailNumber: emailNumber.value}).then(result => {
		if (result.data && result.data === 1) {
			window.$message?.success(`已将绑定链接发送到 ${emailNumber.value}，请到邮箱查看`)
			authStore.userInfo.emailInfo = {}
			localStg.set('userInfo', authStore.userInfo);
			window.$notification?.success({
				content: 'Tip',
				duration: 4000,
				meta: '如果你已经绑定成功，请退出重新登录'
			})
		}
	})
}

onMounted(() =>{
	if (authStore.userInfo.emailInfo) {
		if (!authStore.userInfo.emailInfo.email) {
			userApi.queryOneDataByUid({uid: authStore.userInfo.user_uid}).then(result => {
				if (result.data) {
					if (result.data.verifyEmail) {
						emailApi.queryOneDataByUid({uid: result.data.emailUid}).then(result => {
							if (result.data) {
								authStore.userInfo.emailInfo = result.data
								localStg.set('userInfo', authStore.userInfo)
							}
						})
					}
				}
			})
		}
	}
})
</script>

<style scoped>

</style>
