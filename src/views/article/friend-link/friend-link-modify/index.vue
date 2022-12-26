<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyLinkInfo.linkUrl}` : '新增友情链接'">
				<n-space vertical>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-space justify="center">
								<upload-file
									@handleFinishUploadFile="handleFinishSiteLogoUploadFile"
									:show-file-list="false"
									:parameter-data="{userUid: authStore.userInfo.user_uid, summary: `${authStore.userInfo.username} 用户添加 ${modifyLinkInfo.linkUrl} 友情链接上传的Logo`, storageMode: 0}"
									:control-upload-file="false" >
									<template #uploadDraggerContent>
										<n-avatar
											round
											:size="100"
											:src="modifyLinkInfo.linkLogo"
										/>
									</template>
								</upload-file>
							</n-space>
							<n-space vertical>
								<n-text>站点地址</n-text>
								<n-input round v-model:value="modifyLinkInfo.linkUrl" type="text"/>
							</n-space>
							<n-space vertical>
								<n-text>站点标题</n-text>
								<n-input round v-model:value="modifyLinkInfo.linkTitle" type="text"/>
							</n-space>
							<n-space vertical>
								<n-text>对方邮箱</n-text>
								<n-input round v-model:value="modifyLinkInfo.email" type="text"/>
							</n-space>
						</n-space>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>审核状态</n-p>
								</n-gi>
								<n-gi>
									<n-switch v-model:value="modifyLinkInfo.publish" />
								</n-gi>
							</n-grid>
							<n-grid x-gap="12" :cols="2">
								<n-gi>
									<n-p>分类</n-p>
								</n-gi>
								<n-gi>
									<n-select size="small" v-model:value="modifyLinkInfo.categoryName" :options="categoryOptions" />
								</n-gi>
							</n-grid>
							<n-space vertical>
								<n-text>QQ号</n-text>
								<n-input round v-model:value="modifyLinkInfo.qqNumber" type="number"/>
							</n-space>
						</n-space>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-tabs type="line" animated>
							<n-tab-pane name="message" tab="留言">
								<n-input type="textarea"
												 v-model:value="modifyLinkInfo.replyMessage"
												 :autosize="{minRows: 3}" maxlength="500" show-count />
							</n-tab-pane>
							<n-tab-pane name="oasis" tab="描述">
								<n-input type="textarea"
												 v-model:value="modifyLinkInfo.linkDescription"
												 :autosize="{minRows: 3}" maxlength="500" show-count />
							</n-tab-pane>
							<n-tab-pane name="the beatles" tab="封面">
								<n-space vertical>
									<n-image
										v-if="modifyLinkInfo.linkCover"
										:src="modifyLinkInfo.linkCover"
									/>
									<upload-file
										@handleFinishUploadFile="handleFinishSiteCoverUploadFile"
										:accept-file-type-str="['.png','.jpg','.jpeg']"
										:parameter-data="{
									userUid: authStore.userInfo.user_uid,
									summary: `${authStore.userInfo.username} 添加${modifyLinkInfo.linkUrl} 上传的封面`,
									storageMode: 0
									}"
										:show-upload-dragger="true"
									>
										<template #extraButton>
											<n-button v-if="modifyLinkInfo.linkCover" round type="success" @click="handleRemoveLinkCoverAction">移除封面</n-button>
										</template>
									</upload-file>
								</n-space>
							</n-tab-pane>
							<n-tab-pane name="addCategory" tab="添加分类">
								<n-space justify="space-between">
									<n-input round v-model:value="categoryInfo.title" type="text"/>
									<n-button round type="success" @click="handleAddCategoryAction">添加</n-button>
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
import {categoryApi, linkApi} from "@/service";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {Link} from "@/theme/pojo/article/Link";
import {useAuthStore} from "@/store";
import {REGEXP_EMAIL, REGEXP_URL} from "@/config";
import {Category} from "@/theme/pojo/article/Category";

defineComponent({name: 'index'});

interface SelectOption {
	value: string,
	label: string
}

// 定义data
const showDrawer = ref<boolean>(false)
const modifyLinkInfo = ref<Link>({})
const addStatus = ref(false)
const authStore = useAuthStore()
const categoryOptions = ref<Array<SelectOption>>([])
const categoryInfo = ref<Category>({})
const originPublishStatus = ref(false)

// 定义方法
const handleClickModifyAction = () => {
	if (!REGEXP_URL.test(modifyLinkInfo.value.linkUrl!)) {
		window.$message?.error('友情链接地址貌似不是一个URL(ノへ￣、)')
		return
	}
	if (!StringUtil.haveLength(modifyLinkInfo.value.linkTitle)) {
		window.$message?.error('需要输入站点标题(ノへ￣、)')
		return
	}
	if (!StringUtil.haveLength(modifyLinkInfo.value.linkLogo)) {
		window.$message?.error('貌似你还没有上传站点Logo(ノへ￣、)')
		return
	}
	if (!StringUtil.haveLength(modifyLinkInfo.value.categoryName)) {
		window.$message?.error('必须要选择一个分类(ノへ￣、)')
		return
	}
	if (!REGEXP_EMAIL.test(modifyLinkInfo.value.email!)) {
		window.$message?.error('貌似还没有添加对方邮箱号(ノへ￣、)')
		return
	}
	if (originPublishStatus.value !== modifyLinkInfo.value.publish) {
		if (!modifyLinkInfo.value.publish) {
			// 下架
			if (!StringUtil.haveLength(modifyLinkInfo.value.replyMessage)) {
				window.$message?.error(`需要在留言那里输入下架原因(ノへ￣、)`)
				return;
			}
		}else {
			if (!StringUtil.haveLength(modifyLinkInfo.value.replyMessage)) {
				window.$message?.error(`审核通过，留言提醒站长吧o(￣▽￣)ｄ`)
				return;
			}
		}
	}
	if (!addStatus.value) {
		// 修改操作
		linkApi.updateData(modifyLinkInfo.value).then(result => {
			if (result.data === 1) {
				window.$message?.success(`修改友情链接 ${modifyLinkInfo.value.linkUrl} 成功o(￣▽￣)ｄ`)
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}else {
		modifyLinkInfo.value.userUid = authStore.userInfo.user_uid
		linkApi.insertData(modifyLinkInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success(`添加友情链接 ${modifyLinkInfo.value.linkUrl}成功 o(￣▽￣)ｄ`)
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

const handleFinishSiteLogoUploadFile = (file: UploadFileInfo) => {
  // 修改站点logo
	modifyLinkInfo.value.linkLogo = file.url
	if (!addStatus.value) {
		linkApi.updateData(modifyLinkInfo.value).then(result => {
			if (result.data === 1) {
				window.$message?.success('修改站点Logo成功')
			}else {
				window.$message?.error('修改站点Logo失败')
			}
		})
	}
}

const handleFinishSiteCoverUploadFile = (file: UploadFileInfo) => {
	// 修改站点封面
	modifyLinkInfo.value.linkCover = file.url
	if (!addStatus.value) {
		linkApi.updateData(modifyLinkInfo.value).then(result => {
			if (result.data === 1) {
				window.$message?.success('修改站点封面成功')
			}else {
				window.$message?.error('修改站点封面失败')
			}
		})
	}
}

const loadCategoryInfo = () => {
	categoryOptions.value = []
	// 获取分类
	categoryApi.queryListDataByCondition({delete: false}).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				categoryOptions.value.push({
					value: v.title!,
					label: v.title!
				})
			})
		}
	})
}

const handleRemoveLinkCoverAction = () => {
  modifyLinkInfo.value.linkCover = null
}

const handleAddCategoryAction = () => {
	if (!StringUtil.haveLength(categoryInfo.value.title)) {
		window.$message?.error('没有输入分类名称(ノへ￣、)')
		return
	}
	categoryInfo.value.userUid = authStore.userInfo.user_uid
	categoryInfo.value.summary = `${authStore.userInfo.username} 添加的友情链接分类`
	categoryApi.insertData(categoryInfo.value).then(result => {
		if (!result.error) {
			window.$message?.success(`添加分类 ${categoryInfo.value.title}成功 o(￣▽￣)ｄ`)
			loadCategoryInfo()
			categoryInfo.value = {}
		}
	})
}

onBeforeMount(() => {
	loadCategoryInfo()
})

// 监听mitt
onMounted(() => {
	emitter.on('linkManageAddLinkAction', e => {
		modifyLinkInfo.value = {}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
		modifyLinkInfo.value.publish = false
	})
	emitter.on('linkManageModifyLinkAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifyLinkInfo.value = e as Link
			originPublishStatus.value = modifyLinkInfo.value.publish!
			// 查询此分类是否有效
			if (modifyLinkInfo.value.categoryName) {
				categoryApi.queryListDataByCondition({keyword: modifyLinkInfo.value.categoryName}).then(result => {
					if (result.data && result.data.result) {
						const categoryVos = result.data.result.filter(v => v.title === modifyLinkInfo.value.categoryName);
						if (categoryVos.length === 0) {
							window.$message?.error(`分类 ${modifyLinkInfo.value.categoryName} 无效`)
							modifyLinkInfo.value.categoryName = null
						}
					}
				})
			}
		}
	})
})
</script>

<style scoped>

</style>
