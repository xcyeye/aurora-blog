<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyTagInfo.title}` : '新增标签'">
				<n-space vertical>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-space vertical>
								<n-text>标签名称</n-text>
								<n-input round v-model:value="modifyTagInfo.title" type="text"/>
							</n-space>
							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>{{modifyTagInfo.delete ? '已删除' : '正常'}}</n-p>
								</n-gi>
								<n-gi>
									<n-switch :disabled="addStatus" v-model:value="modifyTagInfo.delete" />
								</n-gi>
							</n-grid>
							<n-space vertical>
								<n-text>标签简介</n-text>
								<n-input round v-model:value="modifyTagInfo.summary" :autosize="{minRows: 4}" type="textarea"/>
							</n-space>
						</n-space>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-tabs type="line" animated>
							<n-tab-pane name="oasis" tab="上传">
								<n-space vertical>
									<n-image
										v-if="modifyTagInfo.coverUrl"
										:src="modifyTagInfo.coverUrl"
									/>
									<upload-file
										@handleFinishUploadFile="handleFinishTagCoverUploadFile"
										:accept-file-type-str="['.png','.jpg','.jpeg']"
										:parameter-data="{
									userUid: authStore.userInfo.user_uid,
									summary: `${authStore.userInfo.username} 添加${modifyTagInfo.title} 标签上传的封面`,
									storageMode: 0
									}"
										:show-upload-dragger="true"
									>
										<template #extraButton>
											<n-button v-if="modifyTagInfo.coverUrl" round type="success" @click="handleRemoveTagCoverAction">移除封面</n-button>
										</template>
									</upload-file>
								</n-space>
							</n-tab-pane>
							<n-tab-pane name="the beatles" tab="手动输入">
								<n-image
									v-if="coverUrl.length !== 0"
									:src="coverUrl"
								/>
								<n-space vertical>
									<n-text>封面URL</n-text>
									<n-input round v-model:value="coverUrl" type="text"/>
								</n-space>
								<n-space justify="end">
									<n-button round type="success" @click="handleControlSettingCoverUrlAction">确定</n-button>
								</n-space>
							</n-tab-pane>
						</n-tabs>
					</n-card>
				</n-space>
				<template #footer>
					<n-button round type="primary" @click="handleClickModifyAction">
						{{ !addStatus ? '更新' : '添加' }}
					</n-button>
				</template>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, onMounted, ref} from "vue";
import {UploadFileInfo} from "naive-ui";
import {categoryApi, linkApi, tagApi} from "@/service";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {useAuthStore} from "@/store";
import {REGEXP_EMAIL, REGEXP_URL} from "@/config";
import {Tag} from "@/bean/pojo/article/Tag";

defineComponent({name: 'index'});

// 定义data
const modifyTagInfo = ref<Tag>({})
const addStatus = ref(false)
const authStore = useAuthStore()
const showDrawer = ref<boolean>(false)
const coverUrl = ref<string>('')

// 定义方法
const handleClickModifyAction = () => {
	if (!StringUtil.haveLength(modifyTagInfo.value.title)) {
		window.$message?.error('需要输入标签名称ノへ￣、)')
		return
	}
	if (!addStatus.value) {
		// 修改操作
		tagApi.updateData(modifyTagInfo.value).then(result => {
			if (result.data === 1) {
				window.$message?.success(`修改标签 ${modifyTagInfo.value.title} 成功o(￣▽￣)ｄ`)
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		modifyTagInfo.value.userUid = authStore.userInfo.user_uid
		tagApi.insertData(modifyTagInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success(`添加标签 ${modifyTagInfo.value.title}成功 o(￣▽￣)ｄ`)
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

const handleFinishTagCoverUploadFile = (file: UploadFileInfo) => {
	// 修改站点封面
	modifyTagInfo.value.coverUrl = file.url
}

const handleRemoveTagCoverAction = () => {
  modifyTagInfo.value.coverUrl = null
}

const handleControlSettingCoverUrlAction = () => {
  if (!REGEXP_URL.test(coverUrl.value)) {
		window.$message?.error('你输入的封面地址不是一个URL')
		coverUrl.value = ''
		return
	}
	modifyTagInfo.value.coverUrl = coverUrl.value
	window.$message?.success('设置成功o(￣▽￣)ｄ ')
}

// 监听mitt
onMounted(() => {
	emitter.on('tagManageAddTagAction', e => {
		modifyTagInfo.value = {}
		addStatus.value = true
		showDrawer.value = true
	})
	emitter.on('tagManageModifyTagAction', e => {
		addStatus.value = false
		showDrawer.value = true
		if (e) {
			modifyTagInfo.value = e as Tag
		}
	})
})
</script>

<style scoped>

</style>
