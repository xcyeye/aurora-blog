<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!showPassword ? `编辑 ${modifyUserInfo.username}` : '新增用户'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space justify="center">
							<upload-file v-if="!showPassword"
													 @handleFinishUploadFile="handleFinishUploadFile"
													 :show-file-list="false"
													 :parameter-data="{userUid: modifyUserInfo.uid, summary: `${modifyUserInfo.username} 用户上传的头像`}"
													 :control-upload-file="false" >
								<template #uploadDraggerContent>
									<n-avatar
										round
										:size="100"
										:src="modifyUserInfo.avatar"
									/>
								</template>
							</upload-file>
							<n-avatar v-else :size="100" round>{{modifyUserInfo.username}}</n-avatar>
						</n-space>
						<n-form ref="addUserFormRef" :model="modifyUserInfo" :rules="addUserFormRules">
							<n-form-item path="username" label="用户名">
								<n-input v-model:value="modifyUserInfo.username" :round="true" @keydown.enter.prevent />
							</n-form-item>

							<n-form-item v-if="showPassword" path="password" label="密码">
								<n-input
									:round="true"
									v-model:value="modifyUserInfo.password"
									type="text"
									@keydown.enter.prevent
								/>
							</n-form-item>

							<n-form-item path="nickname" label="昵称">
								<n-input :round="true" v-model:value="modifyUserInfo.nickname" />
							</n-form-item>

							<n-form-item v-if="!showPassword" label="邮箱">
								<n-p>{{userEmail}}</n-p>
							</n-form-item>
						</n-form>
					</n-card>
					<n-card v-if="!showPassword" hoverable class="rounded-16px shadow-sm" size="small">
						<n-grid x-gap="12" :cols="2">
							<n-gi>
								<n-p>邮箱绑定状态</n-p>
							</n-gi>
							<n-gi>
								<n-switch
									:disabled="true"
									:value="modifyUserInfo.verifyEmail"
								/>
							</n-gi>
						</n-grid>
						<n-divider />
						<n-grid x-gap="12" :cols="2">
							<n-gi>
								<n-p>账户状态</n-p>
							</n-gi>
							<n-gi>
								<n-switch v-model:value="modifyUserInfo.accountLock" />
							</n-gi>
						</n-grid>
						<n-divider  />
						<n-grid x-gap="12" :cols="2">
							<n-gi>
								<n-p>创建时间</n-p>
							</n-gi>
							<n-gi>
								<n-p>{{modifyUserInfo.createTime}}</n-p>
							</n-gi>
						</n-grid>

						<n-divider />
						<n-grid  x-gap="12" :cols="2">
							<n-gi>
								<n-p>最后更新时间</n-p>
							</n-gi>
							<n-gi>
								<n-p>{{modifyUserInfo.updateTime}}</n-p>
							</n-gi>
						</n-grid>
					</n-card>

					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-tabs type="line" animated>
								<n-tab-pane name="oasis" tab="描述">
									<n-input type="textarea"
													 v-model:value="modifyUserInfo.userSummary"
													 :autosize="{minRows: 3}" maxlength="500" show-count />
								</n-tab-pane>
								<n-tab-pane name="the beatles" tab="专业">
									<n-input type="textarea"
													 v-model:value="modifyUserInfo.profession"
													 :autosize="{minRows: 1}" maxlength="50" show-count />
								</n-tab-pane>
							</n-tabs>

							<n-row :gutter="[0, 24]">
								<n-col :span="24">
									<div style="display: flex; justify-content: flex-end">
										<n-button round type="primary" @click="handleClickModifyAction">
											{{ !showPassword ? '更新' : '添加' }}
										</n-button>
									</div>
								</n-col>
							</n-row>
						</n-space>
					</n-card>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from "vue";
import {User} from "@/theme/pojo/admin/User";
import {FormInst, FormItemRule, FormRules, UploadFileInfo} from "naive-ui";
import {emailApi, userApi} from "@/service";
import {EnumMittEventName} from "@/enum";
import {emitter} from "@/utils";
import {REGEXP_PWD, REGEXP_USERNAME} from "@/config";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const currentModifyUserInfo = ref<User>()
const modifyUserInfo = ref<User>({})
const showPassword = ref(false)
const userEmail = ref<string>('正在获取╥﹏╥...')
const addUserFormRef = ref<FormInst | null>(null)
const addUserFormRules: FormRules = {
	username: [
		{
			required: true,
			trigger: 'blur',
			validator(rule: FormItemRule, value: string) {
				if (!value) {
					return new Error('怎么会不需要名字呢(o_ _)ﾉ')
				}else if (value.length > 15) {
					return new Error('长度为5-15(o_ _)ﾉ')
				}else if (!REGEXP_USERNAME.test(value)) {
					return new Error('只能包含数字字母和_(o_ _)ﾉ')
				}
				return true
			}
		}
	],
	password: [
		{
			required: true,
			trigger: 'blur',
			validator(rule: FormItemRule, value: string) {
				if (showPassword) {
					if (!value) {
						return new Error('没密码怎么登录〒▽〒')
					}else if (value.length < 5) {
						return new Error('被盗了别怪我ヾ(´∀`o)+')
					}else if (value.length > 20) {
						return new Error('太长了记不住(⊙﹏⊙) ')
					}else if (!REGEXP_PWD.test(value)) {
						return new Error('密码不符合规范')
					}
					return true
				}else {
					return true
				}
			}
		}
	],
	nickname: [
		{
			required: true,
			message: '昵称也是一个身份',
			trigger: 'blur'
		}
	],
	// email: [
	// 	{
	// 		required: true,
	// 		trigger: 'blur',
	// 		validator(rule: FormItemRule, value: string) {
	// 			console.log(value);
	// 			if (!/^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\.[a-zA-Z0-9])+$/.test(value)) {
	// 				return new Error('我以后会联系不到你(* ￣︿￣)')
	// 			}
	// 			return true
	// 		}
	// 	}
	// ]
}



// 定义方法
const handleClickModifyAction = () => {
	if (!showPassword.value) {
		// 修改操作
		userApi.updateData(modifyUserInfo.value).then(result => {
			if (result.data === 1) {
				window.$message?.success(`修改用户 ${modifyUserInfo.value.username} 信息成功`)
				emitter.emit(EnumMittEventName.reloadData)
			}else {
				window.$message?.error(`修改用户 ${modifyUserInfo.value.username} 信息失败`)
			}
		})
	}else {
		addUserFormRef.value!.validate(errors => {
			if (errors) {
				window.$message?.error("字段验证失败(* ￣︿￣)")
				return
			}
		})

		// 先插入用户
		userApi.insertData(modifyUserInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success(`添加用户 ${modifyUserInfo.value.username}成功`)
				showDrawer.value = false
			}else {
				window.$message?.error(`添加用户 ${modifyUserInfo.value.username}失败`)
			}
		})
	}
}

const handleFinishUploadFile = (file: UploadFileInfo) => {
  // 修改用户的头像
	if (file) {
		modifyUserInfo.value.avatar = file.url
		userApi.updateData(modifyUserInfo.value).then(result => {
			if (result.data === 1) {
				window.$message?.success('修改头像成功')
			}else {
				window.$message?.error('修改头像失败')
			}
		})
	}
}

const fetchUserEmailInfo = () => {
	if (modifyUserInfo.value.emailUid) {
		emailApi.queryOneDataByUid({uid: modifyUserInfo.value.emailUid}).then(result => {
			if (result.data) {
				if (result.data.email) {
					userEmail.value = result.data.email
				}else {
					userEmail.value = '我还没邮箱 ┭┮﹏┭┮'
				}
			}else {
				userEmail.value = '我还没邮箱 ┭┮﹏┭┮'
			}
		})
	}else {
		userEmail.value = '马上去绑定 ┭┮﹏┭┮'
	}

}


// 监听mitt
onMounted(() => {
	emitter.on('userManageAddUserAction', e => {
		currentModifyUserInfo.value = undefined
		modifyUserInfo.value = {}
		showDrawer.value = !showDrawer.value
		showPassword.value = true
		userEmail.value = '正在获取╥﹏╥...'
	})
	emitter.on('userManageModifyUserAction', e => {
		userEmail.value = '正在获取╥﹏╥...'
		showDrawer.value = !showDrawer.value
		showPassword.value = false
		if (e) {
			currentModifyUserInfo.value = e as User
			modifyUserInfo.value = e as User
			fetchUserEmailInfo()
		}
	})
})

watch(() => showDrawer.value, (n,v) => currentModifyUserInfo.value = undefined)
</script>

<style scoped>

</style>
