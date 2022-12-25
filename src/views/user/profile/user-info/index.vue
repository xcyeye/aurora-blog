<template>
	<div>
		<n-layout has-sider :embedded="true">
			<n-layout-sider content-style="padding: 24px;" width="450">
				<n-space vertical>
					<n-space justify="center">
						<upload-file
							:control-upload-file="false"
							:show-file-list="false"
							:parameter-data="{
							userUid: authStore.userInfo.user_uid,
							summary: `${authStore.userInfo.username} 用户的照片`,
							storageMode: 0
						}"
							@handleFinishUploadFile="handleFinishUploadFile">
							<template #uploadDraggerContent>
								<n-avatar
									class="profile-avatar"
									round
									:bordered="true"
									:size="220"
									style="cursor: pointer"
									fallback-src="https://picture.xcye.xyz/avatar.jpg"
									:src="currentUserInfo.avatar"
								/>
							</template>
						</upload-file>
					</n-space>
					<n-descriptions label-placement="top">
						<n-descriptions-item label="Address">
							{{loginInfoArr[0].loginLocation}}
						</n-descriptions-item>
						<n-descriptions-item label="LoginTime">
							{{loginInfoArr[0].createTime}}
						</n-descriptions-item>
					</n-descriptions>
					<n-space justify>
						<n-tag v-for="(item, index) in authStore.userInfo.authority" :key="index" type="success" size="small" round>
							{{ item }}
						</n-tag>
					</n-space>

					<n-space justify>
						<n-radio
							:checked="currentUserInfo.gender === 'MALE'"
							value="MALE"
							name="basic-demo"
							@change="handleChangeGender">
							男
						</n-radio>
						<n-radio
							:checked="currentUserInfo.gender === 'FEMALE'"
							value="FEMALE"
							name="basic-demo"
							@change="handleChangeGender"
						>
							女
						</n-radio>
						<n-radio
							value="SECRET"
							:checked="currentUserInfo.gender === 'SECRET' || currentUserInfo.gender === null"
							name="basic-demo"
							@change="handleChangeGender"
						>
							秘密
						</n-radio>
					</n-space>
				</n-space>

			</n-layout-sider>
			<n-layout-content
				content-style="padding: 24px;background: rgba(255, 255, 255, 0);"
				style="background: rgba(255, 255, 255, 0)"
			>
				<n-space vertical :size="28">
					<n-form ref="formRef" :model="currentUserInfo" :rules="rules">
						<n-form-item path="username" label="用户名">
							<n-input
								v-model:value="currentUserInfo.username"
								:minlength="4"
								:maxlength="15"
								round
								@keydown.enter.prevent
								placeholder="username"
							/>
						</n-form-item>
						<n-form-item label="昵称">
							<n-input
								v-model:value="currentUserInfo.nickname"
								:minlength="4"
								:maxlength="15"
								round
								placeholder="nickname"
							/>
						</n-form-item>
						<n-form-item label="邮箱">
							<n-input
								:value="authStore.userInfo.emailInfo === undefined ? '未绑定' : authStore.userInfo.emailInfo.email"
								:minlength="4"
								disabled
								:maxlength="254"
								round
								placeholder="email"
							/>
						</n-form-item>
						<n-form-item label="专业">
							<n-input v-model:value="currentUserInfo.profession" round placeholder="profession" />
						</n-form-item>

						<n-form-item label="描述">
							<n-input v-model:value="currentUserInfo.userSummary" round placeholder="summary" />
						</n-form-item>
					</n-form>

					<n-row :gutter="[0, 24]">
						<n-col :span="24">
							<div style="display: flex; justify-content: left">
								<n-button round type="primary" @click="handleSaveAction" > 保存 </n-button>
							</div>
						</n-col>
					</n-row>
				</n-space>
			</n-layout-content>
		</n-layout>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref} from "vue";
import {useAuthStore} from "@/store";
import {User} from "@/theme/pojo/admin/User";
import {emailApi, loginInfoApi, userApi} from "@/service";
import {FormInst, FormRules, UploadFileInfo} from "naive-ui";
import {LoginInfo} from "@/theme/pojo/auth/LoginInfo";
import {LoginInfoVo} from "@/theme/vo/auth/LoginInfoVo";
import {formRules, localStg} from "@/utils";

defineComponent({name: 'index'});

const authStore = useAuthStore()

// 定义data
const currentUserInfo = ref<User>({})
const loginInfoArr = ref<Array<LoginInfoVo>>([{
	loginLocation: '未登录系统',
	createTime: '未登录系统'
}])
const rules: FormRules = {
	username: formRules.username,
};
const formRef = ref<HTMLElement & FormInst>();

// 定义方法
const loadUserInfo = () => {
  userApi.queryOneDataByUid({uid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			currentUserInfo.value = result.data
		}
	})
}

const loadLoginInfo = () => {
  loginInfoApi.queryListDataByCondition({
		keyword: authStore.userInfo.username,
		orderBy: 'createTime desc'
	}).then(result => {
		if (result.data) {
			if (result.data.result) {
				loginInfoArr.value = result.data.result
			}else {
				window.$message?.error('系统找不到你最后登录的信息 ┭┮﹏┭┮')
			}
		}
	})
}

const loadUserEmailInfo = () => {
	if (authStore.userInfo.verify_email) {
		if (!authStore.userInfo.emailInfo || !authStore.userInfo.emailInfo.email) {
			emailApi.queryOneDataByUid({uid: authStore.userInfo.userDetailInfo.emailUid}).then(result => {
				if (result.data) {
					authStore.userInfo.emailInfo = result.data
					localStg.set('userInfo', authStore.userInfo)
				}
			})
		}
	}
}

const handleFinishUploadFile = (file: UploadFileInfo) => {
  // 修改用户头像
	userApi.updateData({uid: currentUserInfo.value.uid, avatar: file.url}).then(result => {
		if (result.data === 1) {
			currentUserInfo.value.avatar = file.url
			window.$message?.success('修改头像成功 ╰(￣▽￣)╭')
		}else {
			window.$message?.error('头像貌似上传失败了 ╮（╯＿╰）╭')
		}
	})
}

const handleChangeGender = (e: Event) => {
	currentUserInfo.value.gender = (e.target as HTMLInputElement).value
}

async function handleSaveAction() {
  // 校验用户名
	if (!currentUserInfo.value.nickname) {
		window.$message?.error('昵称不能为空')
		return
	}
	await formRef.value?.validate();
	userApi.updateData(currentUserInfo.value).then(result => {
		if (result.data) {
			window.$message?.success('保存成功')
		}else {
			window.$message?.error('保存失败')
		}
	})
}

onBeforeMount(() => {
	loadUserInfo()
	loadLoginInfo()
	loadUserEmailInfo()
})
</script>

<style scoped>

.profile-upload .n-upload {
	display: flex;
	justify-content: center;
	align-items: center;
}

.profile-avatar {
	margin: 0 auto;
}
</style>
