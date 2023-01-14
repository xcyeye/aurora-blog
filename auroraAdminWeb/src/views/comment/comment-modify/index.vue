<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyEmailInfo.email}` : '新增邮箱'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-form ref="addUserFormRef" :model="modifyEmailInfo">
							<n-form-item path="email" label="邮箱">
								<n-input v-if="addStatus" v-model:value="modifyEmailInfo.email" :round="true" @keydown.enter.prevent />
								<n-p v-else>{{modifyEmailInfo.email}}</n-p>
							</n-form-item>

							<n-form-item path="password" label="授权码">
								<n-input
									:round="true"
									v-model:value="modifyEmailInfo.emailPassword"
									type="text"
									@keydown.enter.prevent
								/>
							</n-form-item>

							<n-form-item path="nickname" label="端口">
								<n-input :round="true" type="number" v-model:value="modifyEmailInfo.port" />
							</n-form-item>

							<n-form-item label="协议">
								<n-input :round="true" type="text" v-model:value="modifyEmailInfo.protocol" />
							</n-form-item>
							<n-form-item label="拥有者">
								<n-mention
									:options="options"
									prefix="@"
									:loading="loading"
									v-model:value="modifyEmailInfo.userUid"
									@search="handleSearch"
								/>
							</n-form-item>
						</n-form>
						<n-row :gutter="[0, 24]">
							<n-col :span="24">
								<div style="display: flex; justify-content: flex-end">
									<n-button round type="primary" @click="handleClickModifyAction">
										{{ !addStatus ? '更新' : '添加' }}
									</n-button>
								</div>
							</n-col>
						</n-row>
					</n-card>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from "vue";
import {User} from "@/theme/pojo/admin/User";
import {FormInst, FormItemRule, FormRules, MentionOption, UploadFileInfo} from "naive-ui";
import {emailApi, userApi} from "@/service";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {Email} from "@/theme/pojo/message/Email";
import {EmailVo} from "@/theme/vo/message/EmailVo";
import {REGEXP_EMAIL} from "@/config";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifyEmailInfo = ref<Email>({})
const addStatus = ref(false)
const options = ref<Array<MentionOption>>([])
const loading = ref(true)

// 定义方法
const handleClickModifyAction = () => {
	if (addStatus.value) {
		if (!StringUtil.haveLength(modifyEmailInfo.value.userUid)) {
			window.$message?.error('该邮箱所有者不能为空，使用@触发搜索')
			return
		}else {
			if (modifyEmailInfo.value.userUid?.indexOf("@") != -1) {
				modifyEmailInfo.value.userUid = modifyEmailInfo.value.userUid?.substring(1)
			}
		}
		if (!StringUtil.haveLength(modifyEmailInfo.value.email)) {
			window.$message?.error('邮箱不能为空')
			return
		}else {
			if (!REGEXP_EMAIL.test(modifyEmailInfo.value.email!)) {
				window.$message?.error('邮箱不符合规范')
				return;
			}
		}
		emailApi.insertData(modifyEmailInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入邮箱成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		if (StringUtil.haveLength(modifyEmailInfo.value.userUid)) {
			if (modifyEmailInfo.value.userUid?.indexOf("@") != -1) {
				modifyEmailInfo.value.userUid = modifyEmailInfo.value.userUid?.substring(1)
			}
		}
		emailApi.updateData(modifyEmailInfo.value).then(result => {
			if (result.data) {
				window.$message?.success('操作成功')
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

const handleSearch = () => {
	options.value = []
	userApi.queryListDataByCondition({}).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				options.value.push({
					label: v.username!,
					value: v.uid!
				})
			})
			loading.value = false
		}
	})
}

// 监听mitt
onMounted(() => {
	emitter.on('messageCenterManageAddEmailAction', e => {
		modifyEmailInfo.value = {}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('messageCenterManageModifyEmailAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifyEmailInfo.value = e as Email
		}
	})
})

</script>

<style scoped>

</style>
