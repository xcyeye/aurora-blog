<template>
	<div class="h-full">
		<n-card title="邮件发送" class="h-full shadow-sm rounded-16px">
			<n-grid x-gap="12" :cols="2">
				<n-gi>
					<n-space vertical>
						<n-card hoverable class="rounded-16px">
							<n-space vertical>
								<n-text>收件人</n-text>
								<n-input v-model:value="sendMailInfo.receiverEmail" size="small" type="text" placeholder="收件人邮箱" />
							</n-space>
						</n-card>

						<n-card hoverable class="rounded-16px">
							<n-space vertical>
								<n-text>标题</n-text>
								<n-input v-model:value="sendMailInfo.subject" size="small" type="text" placeholder="邮件标题" />
							</n-space>
						</n-card>
						<n-card hoverable class="rounded-16px">
							<n-space vertical>
								<n-text>邮件内容</n-text>
								<n-input v-model:value="sendMailInfo.content" :autosize="{minRows: 5}" type="textarea" placeholder="邮件内容，支持Html" />
							</n-space>
						</n-card>
					</n-space>
				</n-gi>
				<n-gi>
					<n-card hoverable size="large" class="rounded-16px h-full">
						<n-space vertical>
							<n-space justify="start">
								<n-avatar
									round
									size="medium"
									:src="autoStore.userInfo.userDetailInfo.avatar"
									fallback-src="https://picture.xcye.xyz/avatar.jpg"
								/>
							</n-space>
							<n-space justify="start">
								<n-text>Mail To</n-text>
								<n-a>
									<n-text>{{sendMailInfo.receiverEmail}}</n-text>
								</n-a>
							</n-space>
							<n-space>
								<div v-if="sendMailInfo.content" v-html="sendMailInfo.content"></div>
								<div v-else>
									请输入邮件内容
								</div>
							</n-space>
						</n-space>
					</n-card>
				</n-gi>
			</n-grid>
			<template #header-extra>
				<n-space justify="end">
					<n-button strong secondary tertiary round type="success" @click="handleSendMail">发送</n-button>
				</n-space>
			</template>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import {useAuthStore} from "@/store";
import {REGEXP_EMAIL} from "@/config";
import {StringUtil} from "@/utils";
import {sendMailApi} from "@/service";
import {useLoadingBar} from "naive-ui";
import {useRouter} from "vue-router";

defineComponent({name: 'index'});

// 定义data
const sendMailInfo = ref<SendMailPojo>({})
const autoStore = useAuthStore()
const loadBar = useLoadingBar()
const router = useRouter()

const handleSendMail = () => {
  if (!REGEXP_EMAIL.test(sendMailInfo.value.receiverEmail!)) {
		window.$message?.error('邮箱错啦 (ノへ￣、)')
		return
	}
	if (!StringUtil.haveLength(sendMailInfo.value.content)) {
		window.$message?.error('没内容没意思 (ノへ￣、)')
		return
	}
	if (!StringUtil.haveLength(sendMailInfo.value.subject)) {
		sendMailInfo.value.subject = `${autoStore.userInfo.username} 给你发了一封邮件o(￣ε￣*) `
	}
	loadBar.start()
	sendMailApi.customMail(sendMailInfo.value).then(result => {
		if (!result.error) {
			loadBar.finish()
			window.$message?.success(`邮件发送成功，记得提醒 ${sendMailInfo.value.receiverEmail}接收o(￣▽￣)ｄ `)
		}else {
			loadBar.error()
		}
	})
}

onMounted(() => {
	if (StringUtil.haveLength(router.currentRoute.value.query.receiverEmail as string) &&
		REGEXP_EMAIL.test(router.currentRoute.value.query.receiverEmail as string)) {
		sendMailInfo.value.receiverEmail = router.currentRoute.value.query.receiverEmail as string
	}
})
</script>

<style scoped>

</style>
