<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="addDataStatus ? '新增配置' : `编辑 ${modifyOauthClientConfigInfo.clientId}`">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-form ref="addOauthClientFormRef" :model="modifyOauthClientConfigInfo">
							<n-form-item label="客户端ID">
								<n-input :disabled="!addDataStatus" v-model:value="modifyOauthClientConfigInfo.clientId" :round="true" @keydown.enter.prevent />
							</n-form-item>

							<n-form-item label="资源ID">
								<n-input
									:round="true"
									v-model:value="modifyOauthClientConfigInfo.resourceIds"
									type="text"
									placeholder="多个使用,隔开"
									@keydown.enter.prevent
								/>
							</n-form-item>
							<n-form-item label="客户端秘钥">
								<n-input
									:round="true"
									v-model:value="modifyOauthClientConfigInfo.clientSecret"
									type="text"
									@keydown.enter.prevent
								/>
							</n-form-item>
							<n-form-item label="授权码模式跳转URI">
								<n-input
									:round="true"
									v-model:value="modifyOauthClientConfigInfo.webServerRedirectUri"
									type="text"
									placeholder="请输入一个URL"
									@keydown.enter.prevent
								/>
							</n-form-item>
							<n-form-item label="令牌过期时间">
								<n-input
									:round="true"
									v-model:value="accessTokenValidity"
									type="number"
									@keydown.enter.prevent
								/>
							</n-form-item>
							<n-form-item label="刷新令牌过期时间">
								<n-input
									:round="true"
									v-model:value="refreshTokenValidity"
									type="number"
									@keydown.enter.prevent
								/>
							</n-form-item>
						</n-form>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-form>
							<n-form-item label="授权类型">
								<n-checkbox-group v-model:value="authorizedGrantTypeArr">
									<n-space v-for="(item, index) in authorizedGrantTypes" :key="index" item-style="display: flex;">
										<n-checkbox :value="item" :label="item" />
									</n-space>
								</n-checkbox-group>
							</n-form-item>
							<n-form-item label="客户端权限">
								<n-dynamic-tags closable type="success" :round="true" v-model:value="scopeArr" />
							</n-form-item>
							<n-form-item label="权限">
								<n-dynamic-tags closable type="info" :round="true" v-model:value="authorityArr" />
							</n-form-item>
						</n-form>
						<n-grid x-gap="12" :cols="2">
							<n-gi>
								<n-p>自动授权</n-p>
							</n-gi>
							<n-gi>
								<n-switch
									v-model:value="modifyOauthClientConfigInfo.autoapprove"
								/>
							</n-gi>
						</n-grid>
						<n-row :gutter="[0, 24]">
							<n-col :span="24">
								<div style="display: flex; justify-content: flex-end">
									<n-button round type="primary" @click="handleClickModifyAction">
										{{ !addDataStatus ? '更新' : '添加' }}
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
import {defineComponent, onMounted, ref} from "vue";
import {removeDuplicateElement, StringUtil, emitter} from "@/utils";
import {EnumMittEventName} from "@/enum";
import {OauthClientDetails} from "@/theme/pojo/auth/OauthClientDetails";
import {authorizedGrantTypes} from "@/constants";
import {REGEXP_PWD} from "@/config";
import {oauthClientApi} from "@/service/api/auth/oauthClientApi";
defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const addDataStatus = ref(false)
const modifyOauthClientConfigInfo = ref<OauthClientDetails>({})
const authorizedGrantTypeArr = ref<Array<string>>([])
const scopeArr = ref<Array<string>>([])
const authorityArr = ref<Array<string>>([])
const refreshTokenValidity = ref<string>('')
const accessTokenValidity = ref<string>('')

// 定义方法
const handleClickModifyAction = () => {
	if (addDataStatus.value) {
		if (modifyOauthClientConfigInfo.value.clientId) {
			if (!/^[\w-]{4,16}$/.test(modifyOauthClientConfigInfo.value.clientId!)) {
				window.$message?.error('客户端ID不符合规范 4-16字母以及下划线')
				return
			}
		}else {
			window.$message?.error('请输入客户端ID')
			return
		}
	}
	if (addDataStatus.value) {
		if (!REGEXP_PWD.test(modifyOauthClientConfigInfo.value.clientSecret!)) {
			window.$message?.error('客户端秘钥为6-18位数字/字符/符号的组合')
			return;
		}
	}else {
		if (modifyOauthClientConfigInfo.value) {
			if (StringUtil.haveLength(modifyOauthClientConfigInfo.value.clientSecret)) {
				if (!REGEXP_PWD.test(modifyOauthClientConfigInfo.value.clientSecret!)) {
					window.$message?.error('客户端秘钥为6-18位数字/字符/符号的组合')
					return;
				}
			}
		}
	}
	// 判断令牌过期时间

	if (authorizedGrantTypeArr.value.length === 0) {
		window.$message?.error('必须要选择一个授权类型')
		return;
	}

	if (authorizedGrantTypeArr.value.length > 0) {
		modifyOauthClientConfigInfo.value.authorizedGrantTypes = authorizedGrantTypeArr.value.join(",")
	}

	if (scopeArr.value.length > 0) {
		modifyOauthClientConfigInfo.value.scope = removeDuplicateElement<string>(scopeArr.value).join(",")
	}

	if (authorityArr.value.length > 0) {
		modifyOauthClientConfigInfo.value.authorities = removeDuplicateElement<string>(authorityArr.value).join(",")
	}

	if (addDataStatus.value) {
		oauthClientApi.insertData(modifyOauthClientConfigInfo.value).then(result => {
			if (!result.error) {
				emitter.emit(EnumMittEventName.reloadData)
				window.$message?.success(`插入 ${modifyOauthClientConfigInfo.value.clientId}成功`)
				showDrawer.value = false
			}
		})
	}else {
		oauthClientApi.updateData(modifyOauthClientConfigInfo.value).then(result => {
			if (!result.error) {
				emitter.emit(EnumMittEventName.reloadData)
				window.$message?.success('修改成功')
				showDrawer.value = false
			}
		})
	}
}


// 监听mitt
onMounted(() => {
	emitter.on('oauthAddUserOauthClientConfigAction', e => {
		addDataStatus.value = true
		modifyOauthClientConfigInfo.value = {}
		showDrawer.value = !showDrawer.value
	})
	emitter.on('oauthManageModifyOauthClientConfigAction', e => {
		showDrawer.value = !showDrawer.value
		addDataStatus.value = false
		if (e) {
			modifyOauthClientConfigInfo.value = e as OauthClientDetails
			modifyOauthClientConfigInfo.value.clientSecret = ''
			if (modifyOauthClientConfigInfo.value.authorizedGrantTypes) {
				authorizedGrantTypeArr.value = modifyOauthClientConfigInfo.value.authorizedGrantTypes.split(",")
			}
			if (modifyOauthClientConfigInfo.value.scope) {
				scopeArr.value = modifyOauthClientConfigInfo.value.scope.split(",")
			}
			if (modifyOauthClientConfigInfo.value.authorities) {
				authorityArr.value = modifyOauthClientConfigInfo.value.authorities.split(",")
			}
		}
	})
})
</script>

<style scoped>

</style>
