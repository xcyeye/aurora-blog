<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content title="编辑评论">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical :size="0">
							<n-space justify="center">
								<n-avatar
									v-if="computeAvatar"
									round
									:size="100"
									:src="modifyCommentInfo.avatar"
								/>
								<n-avatar v-else :size="100" round>{{modifyCommentInfo.username}}</n-avatar>
							</n-space>
							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>用户名</n-p>
								</n-gi>
								<n-gi>
									<n-p>{{modifyCommentInfo.username}}</n-p>
								</n-gi>
							</n-grid>
							<n-divider />

							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>IP</n-p>
								</n-gi>
								<n-gi>
									<n-p>{{modifyCommentInfo.commentIp}}</n-p>
								</n-gi>
							</n-grid>
							<n-divider />

							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>操作系统</n-p>
								</n-gi>
								<n-gi>
									<n-p>{{modifyCommentInfo.operationSystemInfo}}</n-p>
								</n-gi>
							</n-grid>
							<n-divider />

							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>创建时间</n-p>
								</n-gi>
								<n-gi>
									<n-p>{{modifyCommentInfo.createTime}}</n-p>
								</n-gi>
							</n-grid>
							<n-divider />
						</n-space>
					</n-card>

					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical :size="0">
							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>邮箱地址</n-p>
								</n-gi>
								<n-gi>
									<n-input :round="true" v-model:value="modifyCommentInfo.email" />
								</n-gi>
							</n-grid>
							<n-divider />

							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>评论地址</n-p>
								</n-gi>
								<n-gi>
									<n-input :round="true" v-model:value="modifyCommentInfo.path" />
								</n-gi>
							</n-grid>
							<n-divider />

							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>用户站点</n-p>
								</n-gi>
								<n-gi>
									<n-input :round="true" v-model:value="modifyCommentInfo.site" />
								</n-gi>
							</n-grid>
							<n-divider />


						</n-space>
					</n-card>

					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-input type="textarea"
											 v-model:value="modifyCommentInfo.content"
											 :autosize="{minRows: 3}" maxlength="1000" show-count />

							<n-space justify="end">
								<n-button strong secondary tertiary round type="primary" @click="handleResentNotice">重新提醒</n-button>
								<n-button strong secondary tertiary round type="success" @click="handleClickModifyAction">更新</n-button>
							</n-space>
						</n-space>
					</n-card>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref, watch} from "vue";
import {User} from "@/bean/pojo/admin/User";
import {FormInst, FormItemRule, FormRules, MentionOption, UploadFileInfo} from "naive-ui";
import {commentApi, emailApi, userApi} from "@/service";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {Email} from "@/bean/pojo/message/Email";
import {EmailVo} from "@/bean/vo/message/EmailVo";
import {REGEXP_EMAIL} from "@/config";
import {CommentVo} from "@/bean/vo/comment/CommentVo";
import {Comment} from "@/bean/pojo/comment/Comment";
import {result} from "lodash-es";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifyCommentInfo = ref<Comment>({})
const addStatus = ref(false)
const options = ref<Array<MentionOption>>([])
const loading = ref(true)

const computeAvatar = computed(() => {
	return StringUtil.haveLength(modifyCommentInfo.value.avatar)
})

// 定义方法
const handleResentNotice = () => {
	if (!StringUtil.haveLength(modifyCommentInfo.value.uid)) {
		window.$message?.error("没有commentUid")
		return
	}
  commentApi.resendEmailNotice(modifyCommentInfo.value).then(result => {
		if (result && result.data! > 0) {
			window.$message?.success("重新提醒成功")
		}
	})
}

const handleClickModifyAction = () => {
	if (!modifyCommentInfo.value) {
		return
	}
	if (!StringUtil.haveLength(modifyCommentInfo.value.email)) {
		window.$message?.error("邮箱号不能为空")
		return;
	}

	if (!StringUtil.haveLength(modifyCommentInfo.value.path)) {
		window.$message?.error("评论地址不能为空")
		return;
	}

	if (!StringUtil.haveLength(modifyCommentInfo.value.content)) {
		window.$message?.error("评论信息不能为空")
		return;
	}

	commentApi.updateData(modifyCommentInfo.value).then(result => {
		if (result && result.data! > 0) {
			window.$message?.success("更新成功")
			emitter.emit(EnumMittEventName.reloadData)
		}else {
			window.$message?.error("更新失败")
		}
	})
}

// 监听mitt
onMounted(() => {
	emitter.on('commentManageModifyCommentAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifyCommentInfo.value = e as Comment
		}
	})
})

</script>

<style scoped>

</style>
