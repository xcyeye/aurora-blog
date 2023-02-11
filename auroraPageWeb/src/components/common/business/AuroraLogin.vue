<template>
	<div>
		<n-modal v-model:show="showLoginModalTemp">
			<n-card
				style="width: 600px"
				:bordered="false"
				size="small"
				role="dialog"
				aria-modal="true"
			>
				<n-card :bordered="false">
					<n-tabs
						class="card-tabs"
						default-value="signin"
						size="large"
						animated
						style="margin: 0 -4px"
						pane-style="padding-left: 4px; padding-right: 4px; box-sizing: border-box;"
					>
						<n-tab-pane name="signin" tab="请登录o(￣▽￣)ｄ">
							<n-form>
								<n-form-item-row label="用户名">
									<n-input v-model:value="loginUserInfo.username" round size="medium" />
								</n-form-item-row>
								<n-form-item-row label="密码">
									<n-input v-model:value="loginUserInfo.password" round size="medium" />
								</n-form-item-row>
							</n-form>
							<n-button strong secondary tertiary round type="success" block @click="handleLoginAction(true)">
								登录
							</n-button>
						</n-tab-pane>
						<n-tab-pane name="signup" tab="请注册(￣┰￣*) ">
							<n-form>
								<n-form-item-row label="用户名">
									<n-input v-model:value="loginUserInfo.username" round size="medium" />
								</n-form-item-row>
								<n-form-item-row label="密码">
									<n-input v-model:value="loginUserInfo.password" round size="medium" />
								</n-form-item-row>
								<n-form-item-row label="邮箱">
									<n-input v-model:value="loginUserEmailInfo.email" round size="medium" />
								</n-form-item-row>
							</n-form>
							<n-button strong secondary tertiary round type="success" block @click="handleLoginAction(false)">
								注册
							</n-button>
						</n-tab-pane>
					</n-tabs>
				</n-card>
			</n-card>
		</n-modal>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, ref, watch} from "vue";
import {User} from "@/bean/pojo/admin/User";
import {Email} from "@/bean/pojo/message/Email";
import {REGEXP_EMAIL, REGEXP_PWD, REGEXP_USERNAME} from "@/config";
import {emailApi, userApi} from "@/service";
import blogConfig from "@/config/blogConfig.json";
import {getLocalTime} from "@/utils";
import {NButton} from "naive-ui";
import {OauthClientDetails} from "@/bean/pojo/auth/OauthClientDetails";
import {oauthClientApi} from "@/service/api/auth/oauthClientApi";
import {OauthPasswordPo} from "@/bean/pojo/auth/oauthPassword";
import {authApi} from "@/service/api/auth/auth";
import {useAuthStore} from "@/stores";
import {OauthVo} from "@/bean/vo/auth/OauthVo";
import {isNotEmptyObject} from "@/utils/business";

interface Props {
	showLoginModal: boolean,
	userUid: string
}

defineComponent({name: 'AuroraLogin'});

const props = withDefaults(defineProps<Props>(), {
	showLoginModal: false
})

const loginUserInfo = ref<User>({})
const loginUserEmailInfo = ref<Email>({})
const authStore = useAuthStore()
const showLoginModalTemp = ref(false)

showLoginModalTemp.value = props.showLoginModal

const loadUserEmailInfo = (oauthInfo: OauthVo = {}) => {
	// 查询用户邮箱
	if (!isNotEmptyObject(oauthInfo)) {
		oauthInfo = authStore.authInfo
	}
	if (oauthInfo.userInfo?.verify_email) {
	
	}else {
		// 重新查询用户信息
		// userApi.queryOneDataByUid({uid: oauthInfo.userInfo?.user_uid}).then(result => {
		// 	if (result.data && result.data.verifyEmail) {
		// 		emailApi.queryEmailByUserUid({userUid: oauthInfo.userInfo?.user_uid}).then(result => {
		// 			if (result.data) {
		// 				newCommenterUserInfo.value.email = result.data.email
		// 				disableEditEmailStatus.value = true
		// 			}else {
		// 				disableEditEmailStatus.value = true
		// 			}
		// 		})
		// 	}else {
		// 		disableEditEmailStatus.value = false
		// 		window.$message?.success('您的账户暂未绑定邮箱，请发布评论的时候，输入邮箱号，以便接收评论通知')
		// 	}
		// })
	}
}

const loginByPwd = (isInsertEmailInfo: boolean = false) => {
	const oauthPasswordInfo: OauthPasswordPo = {
		username: loginUserInfo.value.username!,
		password: loginUserInfo.value.password!,
		client_id: loginUserInfo.value.username!,
		client_secret: loginUserInfo.value.password!,
		grant_type: 'password'
	}
	authApi.loginByPassword(oauthPasswordInfo).then(result => {
		if (result.data) {
			authStore.setAuthInfo(result.data)
			showLoginModalTemp.value = false
			// 登录成功
			if (isInsertEmailInfo) {
				// 尝试注册邮箱，如果邮箱注册失败，不影响
				loginUserEmailInfo.value.userUid = result.data.userInfo!.user_uid
				emailApi.insertData(loginUserEmailInfo.value).then(emailInsertResult => {
					if (!emailInsertResult.error) {
						// 邮箱添加成功，尝试发送一条绑定邮箱的邮件到该用户邮箱
						emailApi.queryByEmailNumber({email: loginUserEmailInfo.value.email}).then(emailResult => {
							if (emailResult.data) {
								const time = setInterval(() => {
									if (localStorage.getItem('auth_info')) {
										clearInterval(time)
										userApi.bindingEmail({uid: result.data.userInfo?.user_uid, emailNumber: loginUserEmailInfo.value.email, password: oauthPasswordInfo.password}).then(result => {
											if (result.data && result.data === 1) {
												window.$message?.success(`邮箱添加成功， ${loginUserEmailInfo.value.email}，请到邮箱查看进行账户绑定`)
											}
										})
									}
								}, 10)
							}
						})
					}
				})
			}else {
				// 用户登录，如果账户已经验证了，则查询邮箱
				loadUserEmailInfo(result.data)
			}
		}
	})
}

const handleLoginAction = async (isLoginAction: boolean) => {
	if (authStore.authInfo && isNotEmptyObject(authStore.authInfo) && authStore.authInfo.access_token) {
		window.$message?.error('你已登录')
		showLoginModalTemp.value = false
		return
	}
	if (!REGEXP_USERNAME.test(loginUserInfo.value.username!)) {
		window.$message?.error('用户名 5-15字符')
		return
	}
	
	if (!REGEXP_PWD.test(loginUserInfo.value.password!)) {
		window.$message?.error('密码 密码为6-18位数字/字符/符号的组合')
		return
	}
	
	if (!isLoginAction) {
		if (!REGEXP_EMAIL.test(loginUserEmailInfo.value.email!)) {
			window.$message?.error('请输入有效邮箱')
			return
		}
	}
	if (isLoginAction) {
		loginByPwd()
	}else {
		// 先查询邮箱是否有效
		let effectiveEmailStatus = false
		await emailApi.queryByEmailNumber({email: loginUserEmailInfo.value.email}).then(result => {
			if (result.data) {
				if (result.data.uid) {
					window.$message?.error('此邮箱已被占用(⊙﹏⊙) ')
				}else {
					effectiveEmailStatus = true
				}
			}
		})
		if (effectiveEmailStatus) {
			// 注册
			loginUserInfo.value.userSummary = `新用户在${props.userUid}用户处评论系统创建的用户`,
				userApi.insertData(loginUserInfo.value).then(result => {
					if (!result.error) {
						
						let markAsRead = false
						const n = window.$notification?.create({
							title: `Hi ${loginUserInfo.value.username} o(￣▽￣)ｄ`,
							content: `你已注册成功\n用户名: ${loginUserInfo.value.username}\n密码: ${loginUserInfo.value.password}\n后台登录地址: ${blogConfig.adminWebUrl}`,
							meta: getLocalTime(new Date(), true),
							action: () =>
								h(
									NButton,
									{
										text: true,
										type: 'primary',
										onClick: () => {
											markAsRead = true
											n!.destroy()
										}
									},
									{
										default: () => '已读'
									}
								),
							onClose: () => {
								if (!markAsRead) {
									window.$message?.warning('请置为已读')
									return false
								}
							}
						})
						const oauthClientDetails: OauthClientDetails = {
							scope: 'all',
							clientId: loginUserInfo.value.username,
							clientSecret: loginUserInfo.value.password,
							authorizedGrantTypes: 'authorization_code,client_credentials,refresh_token,password'
						}
						oauthClientApi.insertData(oauthClientDetails).then(result => {
							if (!result.error) {
								window.$message?.success('秘钥注册成功')
								loginByPwd(true)
							}
						})
					}
				})
		}
	}
}

watch(() => props.showLoginModal, () => {
	showLoginModalTemp.value = props.showLoginModal
})
</script>

<style scoped>

</style>