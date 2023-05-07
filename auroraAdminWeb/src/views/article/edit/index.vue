<template>
	<div>
		<n-drawer v-model:show="showImportOrExportArticleModal" :width="600" placement="right">
			<n-drawer-content :title="importArticleStatus ? '执行导入向导' : '执行导出向导'">
				<n-steps vertical :current="currentImportOrExportStepNumber" :status="importOrExportStepStatus">
					<n-step
						title="FrontMatter设置"
					>
						<n-descriptions label-placement="left" :column="1">
							<n-descriptions-item label="保留frontmatter">
								<n-switch size="small" v-model:value="importOrExportArticleInfo.reservedFrontMatter"/>
							</n-descriptions-item>
							<n-descriptions-item label="使用文章中第一张图片作为封面">
								<n-switch size="small" v-model:value="importOrExportArticleInfo.useFirstArticlePictureAsCover"/>
							</n-descriptions-item>
							<n-descriptions-item label="将文件路径最后一级文件夹作为类别">
								<n-switch size="small" v-model:value="importOrExportArticleInfo.folderAsCategoryName"/>
							</n-descriptions-item>
							<n-descriptions-item label="使用文件名作为标题">
								<n-switch size="small" v-model:value="importOrExportArticleInfo.useFileNameAsTitle"/>
							</n-descriptions-item>
							<n-descriptions-item label="frontmatter中类别的名称">
								<n-input size="small" v-model:value="importOrExportArticleInfo.frontmatterCategoryName" type="text"/>
							</n-descriptions-item>
							<n-descriptions-item label="frontmatter中标签的名称">
								<n-input size="small" v-model:value="importOrExportArticleInfo.frontmatterTagName" type="text"/>
							</n-descriptions-item>
						</n-descriptions>
					</n-step>
					<n-step title="导入文章">
						<n-upload
							multiple
							directory-dnd
							directory
							show-file-list
							:max="1000"
							@update-file-list="handleUpdateFileList"
						>
							<n-upload-dragger>
								<div style="margin-bottom: 12px">
									<n-icon size="48" :depth="3">
										<icon-uiw:cloud-upload/>
									</n-icon>
								</div>
								<n-text style="font-size: 16px">
									点击或者拖动文章文件到该区域来上传
								</n-text>
								<n-p depth="3" style="margin: 8px 0 0 0">
									支持目录上传
								</n-p>
							</n-upload-dragger>
						</n-upload>
					</n-step>
				</n-steps>
				<template #footer>
					<n-space justify="end">
						<n-button strong secondary tertiary round type="success" @click="handleImportOrExportArticle">{{importArticleStatus ? '导入' : '导出'}}</n-button>
					</n-space>
				</template>
			</n-drawer-content>
		</n-drawer>
		<n-layout has-sider sider-placement="right">
			<n-layout-content content-style="padding: 24px;">
				<n-space vertical :size="50">
					<n-space justify="center">
						<div class="aurora-div">
							<input ref="inputRef" autocomplete="off" v-show="isEditingArticleTitle" class="aurora-input" id="aurora-title" type="text"
										 @blur="handleBlurArticleTitleAction"
										 :placeholder="currentArticle.title ? '' : '暂时没有标题'"
										 v-model="currentArticle.title"/>
							<n-text v-show="!isEditingArticleTitle"
											type="primary" :underline="true" style="font-size: 1.5rem"
											@click="handleClickArticleTitleAction">
								{{currentArticle.title ? currentArticle.title : '暂时没有标题' }}
							</n-text>
						</div>
					</n-space>
					<markdown-editor
						@vditorInput="handleVditorInput"
						:min-height="1000"
						:render-md-content="currentArticleContent? currentArticleContent : ''"
					/>
				</n-space>
			</n-layout-content>
			<n-layout-sider
				collapse-mode="width"
				:collapsed-width="0"
				width="calc(22vw)"
				:native-scrollbar="true"
				show-trigger="arrow-circle"
				content-style="padding: 24px;"
				bordered
			>
				<n-space vertical :size="20">
					<n-card hoverable class="rounded-16px shadow-sm" :bordered="false" size="small">
						<n-row :gutter="[0, 24]">
							<n-col :span="24">
								<div style="display: flex; justify-content: left">
									<n-space>
										<n-button round type="success" @click="handleModifyArticleAction">
											{{ !addArticleStatus ? '更新' : '发布' }}
										</n-button>
										<n-button round type="info" @click="handleSaveArticleAction"> 保存 </n-button>
										<n-button round type="success" @click="showImportOrExportArticleModal = true"> 导入 </n-button>
										<n-button v-if="!addArticleStatus" round type="warning" @click="handleNewArticleAction"> 新建 </n-button>
									</n-space>
								</div>
							</n-col>
						</n-row>
					</n-card>

					<n-card hoverable class="rounded-16px shadow-sm" :bordered="false" size="small">
						<n-space vertical>
							<n-space justify="start">
								<n-text>原创</n-text>
								<n-switch
									v-model:value="currentArticle.originalArticle"
									:default-value="true"
									size="small"
								/>
							</n-space>
							<n-input
								v-if="!getOriginArticleStatus"
								v-model:value="currentArticle.originalArticleUrl"
								type="text"
								size="small"
								:clearable="true"
								placeholder="请输入原创链接"
								round
							/>
						</n-space>
						<n-space justify="start">
							<n-text>评论</n-text>
							<n-switch
								v-model:value="currentArticle.showComment"
								:default-value="true"
								size="small"
							/>
						</n-space>
						<n-space justify="start">
							<n-text>定时</n-text>
							<n-switch
								v-model:value="currentArticle.timing"
								:default-value="false"
								size="small"
							/>
							<n-date-picker v-if="currentArticle.timing"
														 value-format="yyyy-MM-dd HH:mm:ss"
														 v-model:formatted-value="currentArticle.timingPublishTime"
														 size="small" type="datetime" clearable />
						</n-space>

					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" :bordered="false" size="small">
						<n-space vertical>
							<n-tabs type="line" animated>
								<n-tab-pane name="tag-dynamic-tag" tab="标签">
									<n-dynamic-tags :render-tag="customRenderTag" :bordered="false" size="medium" type="info" v-model:value="tagArr" />
								</n-tab-pane>
								<n-tab-pane name="tag-select" tab="选择">
									<n-select size="small" v-model:value="tagArr" multiple :options="allTagOptions" />
								</n-tab-pane>
								<n-tab-pane name="tag-add" tab="添加">
									<n-space justify="space-between">
										<n-input round v-model:value="addTagTitle" type="text"/>
										<n-button round type="success" @click="handleAddTagAction">添加</n-button>
									</n-space>
								</n-tab-pane>
							</n-tabs>
							<n-tabs type="line" animated>
								<n-tab-pane name="category-dynamic-tag" tab="类别">
									<n-dynamic-tags :render-tag="customRenderCategory" :bordered="false" size="medium" type="success" v-model:value="categoryArr" />
								</n-tab-pane>
								<n-tab-pane name="category-select" tab="选择">
									<n-select size="small" v-model:value="categoryArr" multiple :options="allCategoryOptions" />
								</n-tab-pane>
								<n-tab-pane name="category-add" tab="添加">
									<n-space justify="space-between">
										<n-input round v-model:value="addCategoryTitle" type="text"/>
										<n-button round type="success" @click="handleAddCategoryAction">添加</n-button>
									</n-space>
								</n-tab-pane>
							</n-tabs>
						</n-space>
					</n-card>

					<n-card hoverable class="rounded-16px shadow-sm" :bordered="false" size="small">
						<n-space vertical>
							<n-text>简介</n-text>
							<n-input type="textarea" round :autosize="{minRows: 5}" v-model:value="currentArticle.summary"/>
						</n-space>
						<n-space vertical>
							<n-text>封面</n-text>
							<n-tabs type="line" animated>
								<n-tab-pane name="oasis" tab="上传">
									<n-space vertical>
										<n-image
											v-if="currentArticle.coverPictureUrl"
											:src="currentArticle.coverPictureUrl"
										/>
										<upload-file
											:show-file-list="false"
											:control-upload-file="false"
											v-if="!currentArticle.coverPictureUrl"
											@handleFinishUploadFile="handleFinishCategoryCoverUploadFile"
											:accept-file-type-str="['.png','.jpg','.jpeg']"
											:parameter-data="{
									userUid: authStore.userInfo.user_uid,
									summary: `${authStore.userInfo.username} 添加${currentArticle.title} 文章上传的封面`,
									storageMode: 0
									}"
											:show-upload-dragger="true"
										/>
										<n-space justify="end">
											<n-button round type="success" @click="handleRemoveCoverUrlAction">重新上传</n-button>
										</n-space>
									</n-space>
								</n-tab-pane>
								<n-tab-pane name="the beatles" tab="手动输入">
									<n-image
										v-if="controlInputCoverUrl.length !== 0"
										:src="controlInputCoverUrl"
									/>
									<n-space vertical>
										<n-text>封面URL</n-text>
										<n-input round v-model:value="controlInputCoverUrl" type="text"/>
									</n-space>
									<n-p/>
									<n-space justify="end">
										<n-button round type="success" @click="handleControlSettingCoverUrlAction">确定</n-button>
									</n-space>
								</n-tab-pane>
							</n-tabs>
						</n-space>
					</n-card>
					<n-card v-if="!addArticleStatus" hoverable class="rounded-16px shadow-sm" :bordered="false" size="small">
						<n-space vertical>
							<n-text>文章评论</n-text>
							<n-scrollbar>
								<blog-comment
									style="max-height: calc(30vh)"
									:user-uid="currentArticle.userUid"
									:page-uid="currentArticle.uid"
									:page-path="`/article/${currentArticle.userUid}/${currentArticle.uid}`"
									:query-regexp="`/article/${currentArticle.userUid}/${currentArticle.uid}`"
									reply-page-type="ARTICLE"/>
							</n-scrollbar>
						</n-space>
					</n-card>
					<n-card hoverable class="rounded-16px shadow-sm" :bordered="false" size="small">
						<n-space vertical>
							<n-text>编辑器设置</n-text>
							<n-space justify="space-between">
								<n-text>Tab键内容</n-text>
								<n-input v-model:value="vditorConfig.tabKey" round size="small"/>
							</n-space>
							<n-space vertical>
								<n-text>模式</n-text>
								<n-radio-group v-model:value="vditorConfig.mode" name="vditorModeGroup">
									<n-space>
										<n-radio key="wysiwyg" value="wysiwyg">所见及所得</n-radio>
										<n-radio key="sv" value="sv">分屏</n-radio>
										<n-radio key="ir" value="ir">及时渲染</n-radio>
									</n-space>
								</n-radio-group>
							</n-space>
							<n-space vertical>
								<n-text>工具栏图标</n-text>
								<n-radio-group v-model:value="vditorConfig.icon" name="vditorIconGroup">
									<n-space>
										<n-radio key="ant" value="ant">ant</n-radio>
										<n-radio key="material" value="material">material</n-radio>
									</n-space>
								</n-radio-group>
							</n-space>
							<n-space vertical>
								<n-text>大纲</n-text>
								<n-switch
									v-model:value="outlineEnable"
									:default-value="false"
									size="small"
								/>
								<n-radio-group v-if="outlineEnable" v-model:value="outlinePosition" name="vditorOutLineGroup">
									<n-space>
										<n-radio key="left" value="left">左</n-radio>
										<n-radio key="right" value="right">右</n-radio>
									</n-space>
								</n-radio-group>
							</n-space>
						</n-space>
					</n-card>
				</n-space>
			</n-layout-sider>
		</n-layout>
	</div>
</template>

<script lang="ts" setup>
import {computed, h, onBeforeMount, ref} from 'vue';
import {NTag, UploadFileInfo} from 'naive-ui';
import 'vditor/dist/index.css';
import {useAppStore, useAuthStore, useThemeStore} from '@/store';
import {removeDuplicateElement, StringUtil} from '@/utils';
import {Article} from "@/bean/pojo/article/Article";
import {useRouter} from "vue-router";
import {REGEXP_URL} from "@/config";
import {articleApi, categoryApi, tagApi} from "@/service";
import {Tag} from "@/bean/pojo/article/Tag";
import {TagVo} from "@/bean/vo/article/TagVo";
import {CategoryVo} from "@/bean/vo/article/CategoryVo";
import {Category} from "@/bean/pojo/article/Category";
import {useRouterPush} from "@/composables";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";

interface VditorPropsProperties {
	tabKey?: string,
	mode?: 'wysiwyg' | 'sv' | 'ir',
	icon?: 'ant' | 'material',
	renderMdContent?: string
}

interface SelectOption {
	value: string,
	label: string
}

// 定义data
const isEditingArticleTitle = ref(false)
const addArticleStatus = ref(true)
const currentArticle = ref<Article>({})
const currentArticleContent = ref<string>('')
const tagArr = ref<Array<string>>([])
const categoryArr = ref<Array<string>>([])
const allCategoryOptions = ref<Array<SelectOption>>([])
const allTagOptions = ref<Array<SelectOption>>([])
const allTagArr = ref<Array<TagVo>>([])
const allCategoryArr = ref<Array<CategoryVo>>([])
const addCategoryTitle = ref<string>('')
const addTagTitle = ref<string>('')
const outline = ref(false)
const authStore = useAuthStore();
const themeStore = useThemeStore();
const router = useRouter()
const routerPush = useRouterPush()
const app = useAppStore();
const inputRef = ref<HTMLElement>()
const controlInputCoverUrl = ref<string>('')
const originTitle = ref<string>('')
const outlineEnable = ref(false)
const outlinePosition = ref<'left' | 'right'>('left')
const vditorConfig = ref<VditorPropsProperties>({
	tabKey: '  ',
	mode: 'ir',
	icon: 'material'
})
const showImportOrExportArticleModal = ref(false)
const importArticleStatus = ref(true)
const importOrExportStepStatus = ref<'error' | 'process' | 'wait' | 'finish'>('process')
const currentImportOrExportStepNumber = ref<number>(1)
const importOrExportArticleInfo = ref<Article>({
	frontmatterCategoryName: 'category',
	frontmatterTagName: 'tag',
	useFileNameAsTitle: true,
	folderAsCategoryName: true,
	reservedFrontMatter: false,
	articleDataFileList: [],
	useFirstArticlePictureAsCover: true
})

// computed
const getOriginArticleStatus = computed(() =>{
	if (currentArticle.value.originalArticle === null || currentArticle.value.originalArticle === undefined) {
		return true
	}
	return currentArticle.value.originalArticle
})

// 定义方法
const setArticleShowTitle = () => {
	if (StringUtil.haveLength(currentArticle.value.title) && currentArticle.value.title!.length > 17) {
		originTitle.value = currentArticle.value.title!
		currentArticle.value.title = currentArticle.value.title!.substring(0, 17) + "..."
	}
}

const handleImportOrExportArticle = () => {
  if (!importOrExportArticleInfo.value.articleDataFileList || importOrExportArticleInfo.value.articleDataFileList.length === 0) {
		window.$message?.error('请上传博客文章数据')
		return
	}
	importOrExportArticleInfo.value.userUid = authStore.userInfo.user_uid
	articleApi.importArticle(importOrExportArticleInfo.value).then(result => {
		if (!result.error) {
			window.$message?.success('导入成功')
			showImportOrExportArticleModal.value = false
			importOrExportArticleInfo.value = {
				frontmatterCategoryName: 'category',
				frontmatterTagName: 'tag',
				useFileNameAsTitle: true,
				folderAsCategoryName: true,
				reservedFrontMatter: false,
				articleDataFileList: [],
				useFirstArticlePictureAsCover: true
			}
		}
	})
}

const handleUpdateFileList = (fileList: UploadFileInfo[]) => {
	importOrExportArticleInfo.value.articleDataFileList = fileList.map(v => v.file).concat() as Array<File>
}

const loadCurrentArticleInfo = (uid: string) => {
	currentArticle.value = {}
	articleApi.queryOneDataByUid({uid: uid}).then(result => {
		if (!result.error) {
			if (result.data) {
				addArticleStatus.value = false
				currentArticle.value = result.data
				console.log(currentArticle.value);
				currentArticleContent.value = currentArticle.value.content!
				if (StringUtil.haveLength(currentArticle.value.tagNames)) {
					tagArr.value = currentArticle.value.tagNames!.split(",")
				}

				if (StringUtil.haveLength(currentArticle.value.categoryNames)) {
					categoryArr.value = currentArticle.value.categoryNames!.split(",")
				}
				setArticleShowTitle()
			}
		}
	})
}

const loadAllCategory = () => {
	allCategoryArr.value = []
	allCategoryOptions.value = []
  categoryApi.queryListDataByCondition({delete: false}).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				allCategoryArr.value.push(v)
				allCategoryOptions.value.push({
					label: v.title!,
					value: v.title!
				})
			})
		}
	})
}

const handleClickImportNegativeBut = () => {
  showImportOrExportArticleModal.value = false
}

const loadAllTag = () => {
	allTagArr.value = []
	allTagOptions.value = []
	categoryApi.queryListDataByCondition({delete: false}).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				allCategoryArr.value.push(v)
				allCategoryOptions.value.push({
					label: v.title!,
					value: v.title!
				})
			})
		}
	})

	tagApi.queryListDataByCondition({delete: false}).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				allTagArr.value.push(v)
				allTagOptions.value.push({
					label: v.title!,
					value: v.title!
				})
			})
		}
	})
}

const handleBlurArticleTitleAction = () => {
	isEditingArticleTitle.value = false
	if (currentArticle.value.title && currentArticle.value.title.length > 17) {
		originTitle.value = currentArticle.value.title
		currentArticle.value.title = currentArticle.value.title.substring(0, 17) + "..."
	}
}

const handleClickArticleTitleAction = () => {
  isEditingArticleTitle.value = true
	if (StringUtil.haveLength(originTitle.value)) {
		currentArticle.value.title = originTitle.value
	}
	setTimeout(() => {
		inputRef.value?.focus()
	}, 10)
}

const handleFinishCategoryCoverUploadFile = (file: UploadFileInfo) => {
	// 修改站点封面
	currentArticle.value.coverPictureUrl = file.url
}

const handleRemoveCoverUrlAction = () => {
	currentArticle.value.coverPictureUrl = null
}

const handleControlSettingCoverUrlAction = () => {
	if (!REGEXP_URL.test(controlInputCoverUrl.value)) {
		window.$message?.error('你输入的封面地址不是一个URL')
		controlInputCoverUrl.value = ''
		return
	}
	currentArticle.value.coverPictureUrl = controlInputCoverUrl.value
	window.$message?.success('设置成功o(￣▽￣)ｄ ')
}

const handleModifyArticleAction = () => {
	if (categoryArr.value.length > 0) {
		currentArticle.value.categoryNames = removeDuplicateElement(categoryArr.value).join(",")
	}

	if (tagArr.value.length > 0) {
		currentArticle.value.tagNames = removeDuplicateElement(tagArr.value).join(",")
	}
	if (currentArticle.value.timing) {
		if (!StringUtil.haveLength(currentArticle.value.timingPublishTime)) {
			window.$message?.error('需要设置定时发布时间')
			return;
		}
	}
	if (!StringUtil.haveLength(currentArticle.value.content)) {
		window.$message?.error('没有内容')
		return;
	}
	if (StringUtil.haveLength(originTitle.value)) {
		currentArticle.value.title = originTitle.value
	}
	if (addArticleStatus.value) {
		currentArticle.value.userUid = authStore.userInfo.user_uid
		if (!StringUtil.haveLength(currentArticle.value.summary)) {
			if (currentArticle.value.content!.length > 120) {
				currentArticle.value.summary = currentArticle.value.content?.substring(0, 120)
			}else {
				currentArticle.value.summary = currentArticle.value.content
			}
		}
		// 增加文章
		if (!StringUtil.haveLength(currentArticle.value.title)) {
			window.$message?.error('需要设置标题')
			return;
		}
		articleApi.insertData(currentArticle.value).then(result => {
			if (!result.error && result.data) {
				window.$message?.success('发布成功o(￣▽￣)ｄ ')
				addArticleStatus.value = false
				setArticleShowTitle()
			}
		})
	}else {
		// 修改文章
		articleApi.updateData(currentArticle.value).then(result => {
			if (!result.error && result.data === 1) {
				window.$message?.success('修改成功')
				setArticleShowTitle()
			}
		})
	}
}

const handleSaveArticleAction = () => {

}

const handleNewArticleAction = () => {
	addArticleStatus.value = false
	routerPush.routerPush({
		name: 'article_edit',
		query: {
			pageUid: ''
		}
	})
	app.reloadPage()
}

const customRenderTag = (tag: string, index: number) => {
	return h(
		NTag,
		{
			type: 'success',
			closable: true,
			onClose: () => {
				tagArr.value.splice(index, 1)
			},
			bordered: false,
			style: {
				borderRadius: '5px'
			}
		},
		{
			default: () => tag
		}
	)
}

const customRenderCategory = (tag: string, index: number) => {
	return h(
		NTag,
		{
			type: 'warning',
			closable: true,
			onClose: () => {
				categoryArr.value.splice(index, 1)
			},
			bordered: false,
			style: {
				borderRadius: '5px'
			}
		},
		{
			default: () => tag
		}
	)
}

const handleVditorInput = (value: string) => {
  currentArticle.value.content = value
}

const handleAddCategoryAction = () => {
  if (!StringUtil.haveLength(addCategoryTitle.value)) {
		window.$message?.error('请输入类别')
		return
	}

	const addCategory: Category = {
		userUid: authStore.userInfo.user_uid,
		title: addCategoryTitle.value
	}
	categoryApi.insertData(addCategory).then(result => {
		if (!result.error) {
			window.$message?.success(`添加类别 ${addCategory.title} 成功`)
			loadAllCategory()
			addCategoryTitle.value = ''
		}
	})
}

const handleAddTagAction = () => {
	if (!StringUtil.haveLength(addTagTitle.value)) {
		window.$message?.error('请输入标签')
		return
	}

	const addTag: Tag = {
		userUid: authStore.userInfo.user_uid,
		title: addTagTitle.value
	}
	tagApi.insertData(addTag).then(result => {
		if (!result.error) {
			window.$message?.success(`添加标签 ${addTag.title} 成功`)
			addTagTitle.value = ''
			loadAllTag()
		}
	})
}

onBeforeMount(() => {
	loadAllTag()
	loadAllCategory()
	const pageUid = router.currentRoute.value.query.pageUid as string
	if (StringUtil.haveLength(pageUid)) {
		loadCurrentArticleInfo(pageUid)
	}else {
		currentArticle.value = {}
	}
})

</script>

<style scoped lang="css">
.aurora-input {
	width: 500px;
	text-align: center;
	font-size: 1.5rem;
	font-weight: bold;
	height: 86%;
	border-bottom: 0.1rem solid rgba(155, 50, 50, 0);
}

.aurora-input:focus {
	outline: none;
	-webkit-animation: inputFocusBorder 0.8s;
	animation: inputFocusBorder 0.8s;
	-webkit-animation-iteration-count: 1;
	animation-iteration-count: 1;
	-webkit-animation-fill-mode: forwards;
	animation-fill-mode: forwards
}

/* .aurora-input:-webkit-autofill {
	box-shadow: 0 0 0px 1000px red inset !important;
	-webkit-text-fill-color: red !important;
	-webkit-background-clip:text;
} */

@-webkit-keyframes inputFocusBorder {
		0% {
			border-bottom: 0.1rem solid rgba(155, 50, 50, 0);
		}
		100% {
			border-bottom: 0.1rem solid var(--primary-color);
		}
}

.aurora-div {
	height: 2.5rem;
}
</style>
