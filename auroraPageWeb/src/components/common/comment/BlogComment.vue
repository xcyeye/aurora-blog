<template>
	<div id="control-comment" class="aurora-control-comment-box" v-if="true">
		<n-modal v-model:show="showLoginModal">
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
									<n-input v-model:value="newCommenterLoginUserInfo.username" round size="medium" />
								</n-form-item-row>
								<n-form-item-row label="密码">
									<n-input v-model:value="newCommenterLoginUserInfo.password" round size="medium" />
								</n-form-item-row>
							</n-form>
							<n-button strong secondary tertiary round type="success" block @click="handleLoginAction(true)">
								登录
							</n-button>
						</n-tab-pane>
						<n-tab-pane name="signup" tab="请注册(￣┰￣*) ">
							<n-form>
								<n-form-item-row label="用户名">
									<n-input v-model:value="newCommenterLoginUserInfo.username" round size="medium" />
								</n-form-item-row>
								<n-form-item-row label="密码">
									<n-input v-model:value="newCommenterLoginUserInfo.password" round size="medium" />
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
		<parcel-style>
			<div v-if="showCommentBut" class="theme-comment-box"
					 :class="{'show-theme-comment-box': showCommentAnimateClass}"
					 @click="showCommentAnimate">
				<svg-icon style="margin-right: .5rem;margin-left: .5rem; font-size: 18px" icon="mdi:comment-processing"/>
				<span class="aurora-comment-common aurora-comment-text">点击评论</span>
			</div>
			<div class="aurora-comment-data-box" :class="{'aurora-comment-animate' : showCommentBut}">
				<div class="mobile-record" :class="{'aurora-show-comment-animate': showCommentAnimateClass}">
					<div class="aurora-comment-data-info-box">
						<div class="h-full">
							<n-card :bordered="false" v-if="!currentClickParentCommentDto.username">
								<n-space vertical>
									<div>
										<div class="aurora-comment-reply-box">
											<div class="aurora-comment-reply-box-son">
												<n-input v-model:value="newCommenterUserInfo.username" :disabled="isAdminUser" size="small" placeholder="用户名"/>
											</div>
											<div class="aurora-comment-reply-box-son">
												<n-input v-model:value="newCommenterUserInfo.email" :disabled="disableEditEmailStatus" size="small" placeholder="邮箱"/>
											</div>
											<div class="aurora-comment-reply-box-son">
												<n-input v-model:value="newCommenterUserInfo.site" size="small" placeholder="站点"/>
											</div>
										</div>
									</div>
									<n-space></n-space>
									<n-input rounded-16px type="textarea" v-model:value="replyCommentData.content" :autosize="{minRows: 3}"
													 :placeholder="currentClickCommentDto.uid ? `回复@ ${currentClickCommentDto.username}` : '发布一条友善的评论(○｀ 3′○)' "
													 class="aurora-comment-son-flex-textarea h-full"/>
									<n-space justify="space-between">
										<n-space justify="start">
											<upload-file v-if="!newCommenterUserInfo.avatar" :control-upload-file="false"
																	 @handleFinishUploadFile="handleFinishUploadFile"
																	 :accept-file-type-str="['.jpg','.jpeg','.png']"
																	 :show-file-list="false"
																	 :parameter-data="{userUid: props.userUid, summary: `评论者在${props.userUid}用户处创建评论时上传的头像`}">
												<template #uploadDraggerContent>
													<n-avatar
														class="aurora-comment-son-flex-avatar"
														round
														:size="45"
														:src="newCommenterUserInfo.avatar"
													/>
												</template>
											</upload-file>
											<n-avatar
												v-else
												round
												:size="45"
												:src="newCommenterUserInfo.avatar"
											/>
										</n-space>
										<n-space justify="end">
											<n-button strong secondary tertiary round type="success" @click="handleReplyCommentAction">发布</n-button>
										</n-space>
									</n-space>
								</n-space>
							</n-card>
							<n-card v-for="(item, index) in showCommentInfo.commentList" :key="index" class="h-full shadow-sm rounded-16px">
								<template #header>
									<n-space justify="start">
										<n-avatar
											round
											:size="55"
											:src="item.avatar"
										/>
										<n-space vertical>
											<n-space justify="start">
												<a :href="item.site" target="_blank">
													<n-text>{{item.username}}</n-text>
												</a>
											</n-space>
											<n-space vertical>
												<n-space justify="start">
													<n-text>{{item.content}}</n-text>
												</n-space>
												<n-space justify="start">
													<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
														{{item.createTime}}
													</n-gradient-text>
													<n-gradient-text class="aurora-comment-mouse" @click="handleClickComment(item, item)" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
														回复
													</n-gradient-text>
												</n-space>
											</n-space>
										</n-space>
									</n-space>
								</template>
								<!--下面的都是子评论-->
								<div class="aurora-comment-son-box">
									<n-space vertical>
										<!--子评论开始-->
										<n-space v-for="(sonItem, sonIndex) in item.sonCommentList" :key="sonIndex" vertical>
											<!--头像 评论内容-->
											<n-space justify="start">
												<a :href="sonItem.site" target="_blank">
													<n-avatar
														round
														:size="35"
														:src="sonItem.avatar"
													/>
												</a>
												<n-space vertical>
													<!--这是用户名和评论信息，或者回复某某-->
													<n-space justify="start">
														<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
															{{sonItem.username}}
														</n-gradient-text>
														<n-text v-if="sonItem.replyCommentUid" style="color: #18191C">回复</n-text>
														<a v-if="sonItem.replyCommentUid" href="https://baidu.com" target="_blank">
															<n-text style="color: #008AC5">@{{sonItem.replyCommentInfo.username}}</n-text>
														</a>
														<n-text style="color: #18191C">: {{sonItem.content}}</n-text>
													</n-space>
													
													<!--评论发布的日期 回复操作-->
													<n-space justify="start">
														<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
															{{sonItem.createTime}}
														</n-gradient-text>
														<n-gradient-text class="aurora-comment-mouse" @click="handleClickComment(sonItem, item)" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
															回复
														</n-gradient-text>
													</n-space>
												</n-space>
											
											</n-space>
										</n-space>
										
										
										<!--在所有子评论后面显示该父评论下共有多少条子评论-->
										<!--<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">-->
										<!--	共{{item.sonCommentList.length}}条回复，-->
										<!--	<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">-->
										<!--		点击查看-->
										<!--	</n-gradient-text>-->
										<!--</n-gradient-text>-->
										
										<!--如果点击回复之后，在多少条回复后面展示回复框-->
										<p v-if="currentClickParentCommentDto.uid === item.uid"></p>
										<p v-if="currentClickParentCommentDto.uid === item.uid"></p>
										<n-card v-if="currentClickParentCommentDto.uid === item.uid">
											<n-space vertical>
												<div>
													<div class="aurora-comment-reply-box">
														<div class="aurora-comment-reply-box-son">
															<n-input v-model:value="newCommenterUserInfo.username" :disabled="isAdminUser" size="small" placeholder="用户名"/>
														</div>
														<div class="aurora-comment-reply-box-son">
															<n-input v-model:value="newCommenterUserInfo.email" :disabled="disableEditEmailStatus" size="small" placeholder="邮箱"/>
														</div>
														<div class="aurora-comment-reply-box-son">
															<n-input v-model:value="newCommenterUserInfo.site" size="small" placeholder="站点"/>
														</div>
													</div>
												</div>
												<n-space></n-space>
												<n-input rounded-16px type="textarea" v-model:value="replyCommentData.content" :autosize="{minRows: 3}"
																 :placeholder="currentClickCommentDto.uid ? `回复@ ${currentClickCommentDto.username}` : '发布一条友善的评论(○｀ 3′○)' "
																 class="aurora-comment-son-flex-textarea h-full"/>
												<n-space justify="space-between">
													<n-space justify="start">
														<upload-file v-if="!newCommenterUserInfo.avatar" :control-upload-file="false"
																				 @handleFinishUploadFile="handleFinishUploadFile"
																				 :accept-file-type-str="['.jpg','.jpeg','.png']"
																				 :show-file-list="false"
																				 :parameter-data="{userUid: props.userUid, summary: `评论者在${props.userUid}用户处创建评论时上传的头像`}">
															<template #uploadDraggerContent>
																<n-avatar
																	class="aurora-comment-son-flex-avatar"
																	round
																	:size="45"
																	:src="newCommenterUserInfo.avatar"
																/>
															</template>
														</upload-file>
														<n-avatar
															v-else
															round
															:size="45"
															:src="newCommenterUserInfo.avatar"
														/>
													</n-space>
													<n-space justify="end">
														<n-button strong secondary tertiary round type="warning" @click="handleCancelReplyCommentAction">取消</n-button>
														<n-button strong secondary tertiary round type="success" @click="handleReplyCommentAction">发布</n-button>
													</n-space>
												</n-space>
											</n-space>
										</n-card>
									</n-space>
								</div>
								<!--<n-divider v-if="index < showCommentInfo.parentNodeNum - 2" />-->
							</n-card>
							
							<!--另一个父评论开始，使用分割线隔开-->
						</div>
					</div>
				</div>
			</div>
		</parcel-style>
		
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onBeforeMount, onMounted, ref, watch} from "vue";
import {commentApi, emailApi, userApi} from "@/service";
import {Comment} from "@/bean/pojo/comment/Comment";
import {createLocalStorage, getLocalStorage, getLocalTime, StringUtil} from "@/utils";
import {NButton, NGradientText, NText, UploadFileInfo} from "naive-ui";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {useAuthStore, useSiteInfo, useUserInfo} from "@/stores";
import $ from "jquery";
import {REGEXP_EMAIL, REGEXP_PWD, REGEXP_USERNAME} from "@/config";
import {User} from "@/bean/pojo/admin/User";
import {isNotEmptyObject} from "@/utils/business";
import {Email} from "@/bean/pojo/message/Email";
import {authApi} from "@/service/api/auth/auth";
import {OauthPasswordPo} from "@/bean/pojo/auth/oauthPassword";
import {OauthClientDetails} from "@/bean/pojo/auth/OauthClientDetails";
import {oauthClientApi} from "@/service/api/auth/oauthClientApi";
import {useRouterPush} from "@/composables";
import {OauthVo} from "@/bean/vo/auth/OauthVo";

defineComponent({name: 'BlogComment'});

// TODO 用户从博客前台评论，需要先重定向到后台管理界面进行注册，注册成功之后才能评论，如果
// 用户需要回复评论的话，直接到管理后台进行回复即可

interface Props {
	parentCommentUidArr?: string[]
	replyPageType: 'ARTICLE' | 'TALK' | 'FRIEND_LINK' | 'OTHER',
	pagePath: string,
	pageUid: string,
	userUid: string,
	showCommentBut?: boolean,
	queryRegexp?: string,
	queryByUserUid?: boolean
}

interface ReplyCommentUserInfo {
	site?: string | null,
	avatar?: string | null,
	email?: string | null,
	username?: string | null,
	userUid?: string | null,
	pagePath?: string,
	pageUid?: string
}

const props = withDefaults(defineProps<Props>(), {
	parentCommentUidArr: () => [],
	replyPageType: 'OTHER',
	showCommentBut: true,
	queryByUserUid: false
})

const currentClickCommentDto = ref<CommentDto>({})
const currentClickParentCommentDto = ref<CommentDto>({})
const showCommentInfo = ref<ShowCommentVo>({})
const replyCommentData = ref<Comment>({})
const isAdminUser = ref(false)
const newCommenterUserInfo = ref<ReplyCommentUserInfo>({})
const currentUserInfo = ref<UserVo>({})
const currentSiteInfo = ref<SiteSettingInfo>({})
const showCommentAnimateClass = ref<boolean>(true)
const showLoginModal = ref(false)
const newCommenterLoginUserInfo = ref<User>({})
const loginUserEmailInfo = ref<Email>({})
const isNoticeBindEmailStatus = ref(false)
const authStore = useAuthStore()
const routerPush = useRouterPush()
const disableEditEmailStatus = ref(false)

const handleClickComment = (commentInfo: CommentDto, parentCommentDto: CommentDto) => {
	currentClickCommentDto.value = commentInfo
	currentClickParentCommentDto.value = parentCommentDto
}

const loadCommentInfo = () => {
	if (props.queryByUserUid) {
		commentApi.queryListCommentByUidArr({userUid: props.userUid}).then(result => {
			if (!result.error && result.data) {
				showCommentInfo.value = result.data
			}
		})
	}else {
		if (!StringUtil.haveLength(props.queryRegexp) && props.parentCommentUidArr.length === 0) return
		showCommentInfo.value = {}
		let queryRegexp = props.queryRegexp
		if (!StringUtil.haveLength(queryRegexp)) {
			if (!StringUtil.haveLength(props.pagePath) && props.parentCommentUidArr.length === 0) return
			queryRegexp = props.pagePath
		}
		replyCommentData.value.content = ''
		commentApi.queryListCommentByUidArr({commentUidArr: props.parentCommentUidArr, queryRegexp: queryRegexp}).then(result => {
			if (!result.error && result.data) {
				showCommentInfo.value = result.data
			}
		})
	}
}

const loginByPwd = (isInsertEmailInfo: boolean = false) => {
	const oauthPasswordInfo: OauthPasswordPo = {
		username: newCommenterLoginUserInfo.value.username!,
		password: newCommenterLoginUserInfo.value.password!,
		client_id: newCommenterLoginUserInfo.value.username!,
		client_secret: newCommenterLoginUserInfo.value.password!,
		grant_type: 'password'
	}
	authApi.loginByPassword(oauthPasswordInfo).then(result => {
		if (result.data) {
			authStore.setAuthInfo(result.data)
			showLoginModal.value = false
			// 登录成功
			newCommenterUserInfo.value.username = newCommenterLoginUserInfo.value.username
			newCommenterUserInfo.value.userUid = result.data.userInfo!.user_uid
			newCommenterUserInfo.value.avatar = result.data.userInfo!.avatar
			isAdminUser.value = true
			if (isInsertEmailInfo) {
				// 尝试注册邮箱，如果邮箱注册失败，不影响
				loginUserEmailInfo.value.userUid = result.data.userInfo!.user_uid
				newCommenterUserInfo.value.email = loginUserEmailInfo.value.email
				emailApi.insertData(loginUserEmailInfo.value).then(emailInsertResult => {
					if (!emailInsertResult.error) {
						disableEditEmailStatus.value = true
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
					}else {
						disableEditEmailStatus.value = false
					}
				})
			}else {
				// 用户登录，如果账户已经验证了，则查询邮箱
				loadUserEmailInfo(result.data)
			}
		}else {
			isAdminUser.value = false
		}
	})
}

const handleLoginAction = async (isLoginAction: boolean) => {
  if (!REGEXP_USERNAME.test(newCommenterLoginUserInfo.value.username!)) {
		window.$message?.error('用户名 5-15字符')
		return
	}

	if (!REGEXP_PWD.test(newCommenterLoginUserInfo.value.password!)) {
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
			newCommenterLoginUserInfo.value.userSummary = `新用户在${props.userUid}用户处评论系统创建的用户`,
			userApi.insertData(newCommenterLoginUserInfo.value).then(result => {
				if (!result.error) {
					window.$message?.success('注册成功 o(￣▽￣)ｄ')
					const oauthClientDetails: OauthClientDetails = {
						scope: 'all',
						clientId: newCommenterLoginUserInfo.value.username,
						clientSecret: newCommenterLoginUserInfo.value.password,
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

const loadUserEmailInfo = (oauthInfo: OauthVo = {}) => {
	// 查询用户邮箱
	if (!isNotEmptyObject(oauthInfo)) {
		oauthInfo = authStore.authInfo
	}
	if (oauthInfo.userInfo?.verify_email) {
		emailApi.queryEmailByUserUid({userUid: oauthInfo.userInfo?.user_uid}).then(result => {
			if (result.data) {
				newCommenterUserInfo.value.email = result.data.email
				disableEditEmailStatus.value = true
			}else {
				disableEditEmailStatus.value = true
			}
		})
	}else {
		// 重新查询用户信息
		userApi.queryOneDataByUid({uid: oauthInfo.userInfo?.user_uid}).then(result => {
			if (result.data && result.data.verifyEmail) {
				emailApi.queryEmailByUserUid({userUid: oauthInfo.userInfo?.user_uid}).then(result => {
					if (result.data) {
						newCommenterUserInfo.value.email = result.data.email
						disableEditEmailStatus.value = true
					}else {
						disableEditEmailStatus.value = true
					}
				})
			}else {
				disableEditEmailStatus.value = false
				window.$message?.success('您的账户暂未绑定邮箱，请发布评论的时候，输入邮箱号，以便接收评论通知')
			}
		})
	}
	isNoticeBindEmailStatus.value = true
}

const showCommentAnimate = () => {
	if (!isNoticeBindEmailStatus.value && isAdminUser.value) {
		loadUserEmailInfo()
	}
	if (showCommentAnimateClass.value) {
		setTimeout(() => {
			showCommentAnimateClass.value = !showCommentAnimateClass.value
		},500)
	}else {
		showCommentAnimateClass.value = !showCommentAnimateClass.value
	}
	$(".aurora-comment-animate").slideToggle(500)
}

const handleCancelReplyCommentAction = () => {
  currentClickParentCommentDto.value = {}
	currentClickCommentDto.value = {}
}

const createNewUserInfo = (): Promise<null> => {
	return new Promise((resolve, reject) => {
		let pwd: string = (newCommenterUserInfo.value.username!.toUpperCase() + newCommenterUserInfo.value.email!.toLowerCase())
		if (pwd.length > 16) {
			pwd = pwd.substr(0, 15) + '*&'
		}else {
			pwd = pwd + '*&'
		}
		// 先创建此用户，密码为username + email + *&
		const generatorUserInfo: User = {
			username: newCommenterUserInfo.value.username,
			password: pwd,
			avatar: newCommenterUserInfo.value.avatar,
			userSummary: `新用户在${props.userUid}用户处评论系统创建的用户`,
			nickname: newCommenterUserInfo.value.username
		}
		let markAsRead = false
		
		userApi.insertData(generatorUserInfo).then(result => {
			if (!result.error) {
				resolve(null)
				createLocalStorage('newCommenterUserInfo', newCommenterUserInfo.value)
				isAdminUser.value = true
				const n = window.$notification?.create({
					title: '请记住您的信息',
					content: `系统已自动在该系统为您注册了身份，你可以使用该登录信息在后台管理界面查看评论或者回复评论以及体验其他的新东西o(￣▽￣)ｄ\n
		\n username: ${newCommenterUserInfo.value.username} \n 密码: ${pwd}\n后台地址: http://localhost/ `,
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
			}else {
				reject(null)
			}
		})
	})
}

const handleReplyCommentAction = () => {
	// 如果没有登录，需要先登录
	if (!getLocalStorage('auth_info') || !isNotEmptyObject(getLocalStorage('auth_info'))) {
		showLoginModal.value = true
		window.$message?.error('需要先登录或者注册才能发布评论o(￣▽￣)ｄ')
		return;
	}
	if (!StringUtil.haveLength(replyCommentData.value.content)) {
		window.$message?.error('请输入评论信息')
		return
	}
	if (!StringUtil.haveLength(newCommenterUserInfo.value.username)) {
		window.$message?.error('请输入用户名')
		return
	}
	if (!StringUtil.haveLength(newCommenterUserInfo.value.email) || !REGEXP_EMAIL.test(newCommenterUserInfo.value.email!)) {
		window.$message?.error('请输入正确邮箱')
		return
	}
	newCommenterUserInfo.value.pagePath = props.pagePath
	newCommenterUserInfo.value.pageUid = props.pageUid
	
	if (isAdminUser.value) {
		replyCommentData.value.replyCommentUid = currentClickCommentDto.value.uid
		replyCommentData.value.avatar = newCommenterUserInfo.value.avatar
		replyCommentData.value.userUid = props.userUid
		replyCommentData.value.username = newCommenterUserInfo.value.username
		replyCommentData.value.site = newCommenterUserInfo.value.site
		replyCommentData.value.email = newCommenterUserInfo.value.email
		replyCommentData.value.path = newCommenterUserInfo.value.pagePath
		replyCommentData.value.pageUid = newCommenterUserInfo.value.pageUid
		if (!StringUtil.haveLength(props.replyPageType)) {
			replyCommentData.value.pageType = 'OTHER'
		}else {
			replyCommentData.value.pageType = props.replyPageType
		}
		
		commentApi.insertData(replyCommentData.value).then(result => {
			if (!result.error) {
				window.$message?.success(`新建评论成功o(￣▽￣)ｄ `)
				loadCommentInfo()
			}
		})
	}else {
		window.$message?.error('请重新点击发送(ノへ￣、)')
	}
}

const handleFinishUploadFile = (file: UploadFileInfo) => {
	newCommenterUserInfo.value.avatar = file.url
	createLocalStorage('newCommenterUserInfo', newCommenterUserInfo.value)
}

onBeforeMount(() => {
	if (!StringUtil.haveLength(props.pagePath)) {
		window.$message?.error('请传入pagePath')
	}else {
		newCommenterUserInfo.value.pagePath = props.pagePath
	}
	
	loadCommentInfo()
	if (!StringUtil.haveLength(props.pageUid)) {
		console.error('请传入pageUid')
	}else {
		newCommenterUserInfo.value.pageUid = props.pageUid
	}
	
	if (!StringUtil.haveLength(props.userUid)) {
		console.error('请传入userUid')
	}else {
		currentUserInfo.value = useUserInfo().getUserInfo(props.userUid)
		currentSiteInfo.value = useSiteInfo().getSiteInfo(props.userUid)
	}
	
	// 从本地存储中获取用户注册的信息，如果存在的话
	if (authStore.authInfo && isNotEmptyObject(authStore.authInfo)) {
		newCommenterUserInfo.value.username = authStore.authInfo.userInfo?.username
		newCommenterUserInfo.value.userUid = authStore.authInfo.userInfo?.user_uid
		newCommenterUserInfo.value.pagePath = props.pagePath
		newCommenterUserInfo.value.pageUid = props.pageUid
		newCommenterUserInfo.value.avatar = authStore.authInfo.userInfo?.avatar
		isAdminUser.value = true
	}
	// const newCommenterUserInfoTemp: ReplyCommentUserInfo = getLocalStorage('newCommenterUserInfo')
	// newCommenterUserInfo.value = newCommenterUserInfoTemp
	// newCommenterUserInfo.value.pagePath = props.pagePath
	// newCommenterUserInfo.value.pageUid = props.pageUid
	// if (isNotEmptyObject(newCommenterUserInfoTemp) && StringUtil.haveLength(newCommenterUserInfoTemp.username)) {
	// 	userApi.queryUserByUsername({username: newCommenterUserInfoTemp.username}).then(result => {
	// 		if (!result.error) {
	// 			newCommenterUserInfo.value.userUid = result.data.uid
	// 		}
	// 	})
	// 	isAdminUser.value = true
	// }
})

onMounted(() => {
	if (!props.showCommentBut) {
		if (!isNoticeBindEmailStatus.value && isAdminUser.value) {
			// 查询用户邮箱
			loadUserEmailInfo()
		}
	}
})

watch(() => props.parentCommentUidArr, () => {
	loadCommentInfo()
})

watch(() => props.queryRegexp, () => {
	loadCommentInfo()
})

watch(() => props.userUid, () => {
	loadCommentInfo()
})
</script>

<style scoped lang="css">

</style>
