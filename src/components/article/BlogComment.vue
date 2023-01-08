<template>
	<div class="h-full">
		<n-card :bordered="false" v-if="!currentClickParentCommentDto.username">
			<n-space vertical>
				<div>
					<div class="aurora-comment-reply-box">
						<div class="aurora-comment-reply-box-son">
							<n-input v-model:value="replyUserInfo.username" :disabled="isAdminUser" size="small" placeholder="用户名"/>
						</div>
						<div class="aurora-comment-reply-box-son">
							<n-input v-model:value="replyUserInfo.email" :disabled="isAdminUser" size="small" placeholder="邮箱"/>
						</div>
						<div class="aurora-comment-reply-box-son">
							<n-input v-model:value="replyUserInfo.site" :disabled="isAdminUser" size="small" placeholder="站点"/>
						</div>
					</div>
				</div>
				<n-space></n-space>
				<n-input rounded-16px type="textarea" v-model:value="replyCommentData.content" :autosize="{minRows: 3}"
								 :placeholder="currentClickCommentDto.uid ? `回复@ ${currentClickCommentDto.username}` : '发布一条友善的评论(○｀ 3′○)' "
								 class="aurora-comment-son-flex-textarea h-full"/>
				<n-space justify="space-between">
					<n-space justify="start">
						<upload-file v-if="!authStore.userInfo.username" :control-upload-file="false"
												 @handleFinishUploadFile="handleFinishUploadFile"
												 :accept-file-type-str="['.jpg','.jpeg','.png']"
												 :show-file-list="false"
												 :parameter-data="{userUid: replyUserInfo.userUid, summary: `用户在${replyUserInfo.userUid}处评论上传的头像`}">
							<template #uploadDraggerContent>
								<n-avatar
									class="aurora-comment-son-flex-avatar"
									round
									:size="45"
									:src="replyUserInfo.avatar"
								/>
							</template>
						</upload-file>
						<n-avatar
							v-else
							round
							:size="45"
							:src="replyUserInfo.avatar"
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
										<n-input v-model:value="replyUserInfo.username" :disabled="isAdminUser" size="small" placeholder="用户名"/>
									</div>
									<div class="aurora-comment-reply-box-son">
										<n-input v-model:value="replyUserInfo.email" :disabled="isAdminUser" size="small" placeholder="邮箱"/>
									</div>
									<div class="aurora-comment-reply-box-son">
										<n-input v-model:value="replyUserInfo.site" :disabled="isAdminUser" size="small" placeholder="站点"/>
									</div>
								</div>
							</div>
							<n-space></n-space>
							<n-input rounded-16px type="textarea" v-model:value="replyCommentData.content" :autosize="{minRows: 3}"
											 :placeholder="currentClickCommentDto.uid ? `回复@ ${currentClickCommentDto.username}` : '发布一条友善的评论(○｀ 3′○)' "
											 class="aurora-comment-son-flex-textarea h-full"/>
							<n-space justify="space-between">
								<n-space justify="start">
										<upload-file v-if="!authStore.userInfo.username" :control-upload-file="false"
																 @handleFinishUploadFile="handleFinishUploadFile"
																 :accept-file-type-str="['.jpg','.jpeg','.png']"
																 :show-file-list="false"
																 :parameter-data="{userUid: item.userUid, summary: `用户在${item.username}处评论上传的头像`}">
											<template #uploadDraggerContent>
												<n-avatar
													class="aurora-comment-son-flex-avatar"
													round
													:size="45"
													:src="replyUserInfo.avatar"
												/>
											</template>
										</upload-file>
									<n-avatar
										v-else
										round
										:size="45"
										:src="replyUserInfo.avatar"
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
			<n-divider v-if="index < showCommentInfo.parentNodeNum - 2" />
		</n-card>

	<!--另一个父评论开始，使用分割线隔开-->
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref, watch} from "vue";
import {commentApi} from "@/service";
import {Comment} from "@/theme/pojo/comment/Comment";
import {StringUtil} from "@/utils";
import {useAuthStore} from "@/store";
import {UploadFileInfo} from "naive-ui";

defineComponent({name: 'BlogComment'});

interface Props {
	parentCommentUidArr: string[]
	replyPageType: 'ARTICLE' | 'TALK',
	pagePath: string,
	pageUid: string
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
	replyPageType: 'ARTICLE'
})

const currentClickCommentDto = ref<CommentDto>({})
const currentClickParentCommentDto = ref<CommentDto>({})
const showCommentInfo = ref<ShowCommentVo>({})
const replyCommentData = ref<Comment>({})
const authStore = useAuthStore()
const isAdminUser = ref(false)
const replyUserInfo = ref<ReplyCommentUserInfo>({})

const handleClickComment = (commentInfo: CommentDto, parentCommentDto: CommentDto) => {
	currentClickCommentDto.value = commentInfo
	currentClickParentCommentDto.value = parentCommentDto
}

const loadCommentInfo = () => {
	showCommentInfo.value = {}
	replyCommentData.value.content = ''
 	if (props.parentCommentUidArr.length === 0) {
		 window.$message?.error('没有需要加载的评论数据')
		return
	}
	 commentApi.queryListCommentByUidArr({commentUidArr: props.parentCommentUidArr}).then(result => {
		 if (!result.error && result.data) {
			 showCommentInfo.value = result.data
		 }
	 })
}

const setReplyUserInfo = () => {
	if (!authStore.userInfo) {
		isAdminUser.value = false
	}
	if (!authStore.userInfo.userDetailInfo ||
		!StringUtil.haveLength(authStore.userInfo.userDetailInfo.username)) {
		window.$message?.error('请重新登录再评论')
		authStore.resetAuthStore();
		return
	}
	if (isAdminUser.value) {
		replyUserInfo.value = {
			avatar: authStore.userInfo.userDetailInfo.avatar,
			username: authStore.userInfo.userDetailInfo.username,
			userUid: authStore.userInfo.user_uid,
			email: authStore.userInfo.emailInfo.email,
			site: `https://aurora.xcye.xyz/${authStore.userInfo.username}`
		}
		console.log(replyUserInfo.value);
	}
}

const handleCancelReplyCommentAction = () => {
  currentClickParentCommentDto.value = {}
	currentClickCommentDto.value = {}
}

const handleReplyCommentAction = () => {
	if (!StringUtil.haveLength(replyCommentData.value.content)) {
		window.$message?.error('请输入评论信息')
		return
	}
	replyCommentData.value.replyCommentUid = currentClickCommentDto.value.uid
	replyCommentData.value.avatar = replyUserInfo.value.avatar
	replyCommentData.value.userUid = replyUserInfo.value.userUid
	replyCommentData.value.username = replyUserInfo.value.username
	replyCommentData.value.site = replyUserInfo.value.site
	replyCommentData.value.email = replyUserInfo.value.email
	replyCommentData.value.path = replyUserInfo.value.pagePath
	replyCommentData.value.pageUid = replyUserInfo.value.pageUid
	if (!StringUtil.haveLength(props.replyPageType)) {
		replyCommentData.value.pageType = 'ARTICLE'
	}else {
		replyCommentData.value.pageType = props.replyPageType
	}

	commentApi.insertData(replyCommentData.value).then(result => {
		if (!result.error) {
			window.$message?.success(`回复成功o(￣▽￣)ｄ `)
			loadCommentInfo()
		}
	})
}

const handleFinishUploadFile = (file: UploadFileInfo) => {
	replyUserInfo.value.avatar = file.url
}

onBeforeMount(() => {
	if (StringUtil.haveLength(authStore.userInfo.username)) {
		isAdminUser.value = true
	}
	loadCommentInfo()
	setReplyUserInfo()
	if (!StringUtil.haveLength(props.pagePath)) {
		window.$message?.error('请传入pagePath')
	}else {
		replyUserInfo.value.pagePath = props.pagePath
	}

	if (!StringUtil.haveLength(props.pageUid)) {
		window.$message?.error('请传入pageUid')
	}else {
		replyUserInfo.value.pageUid = props.pageUid
	}
})

watch(() => props.parentCommentUidArr, () => {
	loadCommentInfo()
	setReplyUserInfo()
})
</script>

<style scoped lang="css">
.aurora-comment-mouse {
	cursor: pointer;
}

.aurora-comment-son-box {
	/*border: 1px solid red;*/
	height: 100%;
	width: auto;
	/*background-color: aquamarine;*/
	margin-left: 4.3rem;
}

.aurora-comment-son-flex {

}

.aurora-comment-son-flex-avatar {

}

.aurora-comment-son-flex-input {
	width: 35rem;
	background-color: #F1F2F3;
	border-radius: 10px;
}

.aurora-comment-son-flex-textarea {
	width: 100%;
}

.aurora-comment-son-flex-input:focus {
	background-color: rgba(1, 2, 4, 0);
	outline: none;
}

.aurora-comment-son-flex-button {

}

.aurora-comment-reply-box {
	display: inline-block;
	width: 100%;
}
.aurora-comment-reply-box-son {
	width: calc(100% / 3);
	height: 1rem;
	display: inline-block;
	padding-left: 0.4rem;
	padding-right: 0.4rem;
}

.aurora-comment-reply-input-box {
	display: inline-block;
	height: 2rem;
	width: 100%;
}
.aurora-comment-reply-input-box-avatar {
	display: inline-block;
	width: 15%;
	height: 1rem;
}

.aurora-comment-reply-input-box-input {
	display: inline-block;
	width: 85%;
	background-color: red;
	height: 1rem;
}
</style>
