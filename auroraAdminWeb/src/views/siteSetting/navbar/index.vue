<template>
	<div class="h-full">
		<n-card title="站点设置" class="h-full shadow-sm rounded-16px">
			<n-space vertical>
				<n-card :bordered="false" hoverable class="shadow-sm rounded-16px">
					<n-descriptions label-placement="left" :column="2" title="系统内置页面">
						<n-descriptions-item :label="`${authStore.userInfo.username}的主页`">
							<span @click="copyContent(`/article/${authStore.userInfo.user_uid}/${articleInfo.uid}`, false)">/article/{{authStore.userInfo.user_uid}}/{{articleInfo.uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="友情链接">
							<span @click="copyContent(`/friendLink/${authStore.userInfo.user_uid}`, false)">/friendLink/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="关于">
							<span @click="copyContent(`/about/${authStore.userInfo.user_uid}`, false)">/about/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="评论">
							<span @click="copyContent(`/comment/${authStore.userInfo.user_uid}`, false)">/comment/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="相册">
							<span @click="copyContent(`/photo/${authStore.userInfo.user_uid}`, false)">/photo/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="时间轴">
							<span @click="copyContent(`/archive/${authStore.userInfo.user_uid}`, false)">/archive/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="说说1">
							<span @click="copyContent(`/shareSpace/${authStore.userInfo.user_uid}`, false)">/shareSpace/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="说说2">
							<span @click="copyContent(`/shareSpace-page/${authStore.userInfo.user_uid}`, false)">/shareSpace-page/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="类别">
							<span @click="copyContent(`/category/${authStore.userInfo.user_uid}`, false)">/category/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
						<n-descriptions-item label="标签">
							<span @click="copyContent(`/tag/${authStore.userInfo.user_uid}`, false)">/tag/{{authStore.userInfo.user_uid}}</span>
						</n-descriptions-item>
					</n-descriptions>
					<n-p>
						<n-space justify="start">
						以上这些便是系统内置的页面，如果你需要为导航链接添加icon，目前系统使用的是<a href="https://icones.js.org/" target="_blank">icones</a>，你只需要进入他们官网
						找到自己喜欢的图标，然后点击该图标，复制图标的名称便可以了，例如<a href="https://icones.js.org/collection/fa" target="_blank"><svg-icon icon="fa:at"/></a>图标的名称为<span style="color: red">fa:at</span>
						</n-space>
						<n-p>
							添加前台导航的步骤为: 1.添加页面 2.点击更新所有页面(为了将你添加的页面先保存到数据库，防止丢失) 3.从左边的《页面》部分将你
							需要的导航按住鼠标键拖动到右侧 4.设置好了之后，需要点击更新导航信息(防止丢失)
						</n-p>
					</n-p>
				</n-card>
				<n-card :bordered="false" hoverable class="shadow-sm rounded-16px">
					<n-grid x-gap="12" :cols="2">
						<n-gi>
							<n-space vertical>
								<n-space vertical>
									<n-text>页面名称</n-text>
									<n-input round placeholder="请输入页面名称" size="small" v-model:value="currentNavbarInfo.name"/>
								</n-space>
								<n-space vertical>
									<n-text>页面地址</n-text>
									<n-input round placeholder="请输入页面地址" size="small" v-model:value="currentNavbarInfo.url" type="text"/>
								</n-space>
							</n-space>
						</n-gi>
						<n-gi>
							<n-space vertical>
								<n-space vertical>
									<n-text>icon</n-text>
									<n-input round placeholder="请输入icon" size="small" v-model:value="currentNavbarInfo.icon"/>
								</n-space>
								<n-space vertical>
									<n-text>是否外部链接</n-text>
									<n-switch v-model:value="currentNavbarInfo.outLink" size="small" />
								</n-space>
								<n-space justify="end">
									<n-button strong secondary tertiary :disabled="!currentNavbarInfo.name" round type="error" @click="handleDeletePageInfoAction">删除页面</n-button>
									<n-button strong secondary tertiary round type="info" @click="handleAddPageInfoAction">添加页面</n-button>
									<n-button strong secondary tertiary round type="info" @click="handleResetPageAction">重置</n-button>
									<n-button strong secondary tertiary round type="success" @click="handleSaveAllPageAction">{{navbarAddStatus ? '保存所有页面' : '更新所有页面'}}</n-button>
									<n-button strong secondary tertiary round type="warning" @click="handleUpdateNavbarAction">{{draggableNavbarAddStatus ? '添加导航信息' : '更新导航信息'}}</n-button>
								</n-space>
							</n-space>
						</n-gi>
					</n-grid>
				</n-card>
				<n-grid x-gap="12" :cols="2">
					<n-gi>
						<n-card :bordered="false" title="页面" hoverable class="h-full shadow-sm rounded-16px">
							<ul class="dragArea">
								<draggable
									class="list-group"
									:list="navbarInfoArr"
									group="navbarInfo"
									itemKey="name"
								>
									<template #item="{ element, index }">
										<li class="list-group-item" @click="handleClickPage(element)">{{ element.name }}</li>
									</template>
								</draggable>
							</ul>
						</n-card>
					</n-gi>
					<n-gi>
						<n-card title="前台导航" :bordered="false" hoverable class="h-full shadow-sm rounded-16px">
							<aurora-draggable @handleDeleteNavbar="handleDeleteNavbar"
																@handleFinallyNavbarData="handleFinallyNavbarData"
																:navbar-info="draggableNavbarInfoArr"/>
						</n-card>
					</n-gi>
				</n-grid>
			</n-space>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref} from "vue";
import {useAuthStore} from "@/store";
import {articleApi} from "@/service";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {removeDuplicateElement, StringUtil} from "@/utils";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import draggable from 'vuedraggable'
import {copyContent} from "@/plugins";
const authStore = useAuthStore()

defineComponent({name: 'index'});

const navbarInfoArr = ref<Array<NavbarInfo>>([])
const draggableNavbarInfoArr = ref<Array<NavbarInfo>>([])
const articleInfo = ref<ArticleVo>({})
const currentNavbarInfo = ref<NavbarInfo>({})
const navbarAddStatus = ref(false)
const draggableNavbarAddStatus = ref(false)
const currentNavbarSiteSetting = ref<SiteSetting>({})
const currentPageSiteSetting = ref<SiteSetting>({})

const setDefault = () => {
	currentNavbarInfo.value = {}
  currentNavbarInfo.value.name = ''
  currentNavbarInfo.value.url = ''
  currentNavbarInfo.value.icon = ''
  currentNavbarInfo.value.outLink = false
	currentNavbarInfo.value.children = []
}

const handleFinallyNavbarData = (navbarInfoArr: Array<NavbarInfo>) => {

}

const handleDeleteNavbar = (navbar: NavbarInfo) => {
	if (draggableNavbarInfoArr.value.indexOf(navbar) === -1) return
	draggableNavbarInfoArr.value.splice(draggableNavbarInfoArr.value.indexOf(navbar), 1)
	handleUpdateNavbarAction()
}

const loadUserArticle = () => {
  articleApi.queryListDataByCondition({otherUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data && result.data.result) {
			articleInfo.value = result.data.result[0]
		}
	})
}

const handleClickPage = (page: NavbarInfo) => {
	currentNavbarInfo.value = page
}

const loadSiteSetting = () => {
	currentNavbarInfo.value = {}
  // 先加载页面
	siteSettingApi.queryListDataByCondition({otherUid: authStore.userInfo.user_uid, keyword: `${authStore.userInfo.user_uid}AllPageInfo`}).then(result => {
		if (result.data && result.data.result && result.data.result.length > 0) {
			const siteSetting: SiteSetting = result.data.result[0]
			// 取出第一个
			navbarInfoArr.value = JSON.parse(siteSetting.paramValue!)
			currentPageSiteSetting.value = siteSetting
			navbarAddStatus.value = false
		}else {
			navbarAddStatus.value = true
		}
	})

	siteSettingApi.queryListDataByCondition({otherUid: authStore.userInfo.user_uid, keyword: `${authStore.userInfo.user_uid}NavbarInfo`}).then(result => {
		if (result.data && result.data.result  && result.data.result.length > 0) {
			const siteSetting: SiteSetting = result.data.result[0]
			// 取出第一个
			draggableNavbarInfoArr.value = JSON.parse(siteSetting.paramValue!)
			currentNavbarSiteSetting.value = siteSetting
			draggableNavbarAddStatus.value = false
		}else {
			draggableNavbarAddStatus.value = true
			draggableNavbarInfoArr.value = []
		}
	})
}

const handleDeletePageInfoAction = () => {
	if (navbarInfoArr.value.indexOf(currentNavbarInfo.value) === -1) {
		return
	}
	navbarInfoArr.value.splice(navbarInfoArr.value.indexOf(currentNavbarInfo.value), 1)
	handleSaveAllPageAction()
}

const handleAddPageInfoAction = async () => {
	if (!StringUtil.haveLength(currentNavbarInfo.value.name)) {
		window.$message?.error('请输入页面名称')
		return
	}
	currentNavbarInfo.value.children = []
	navbarInfoArr.value.push(currentNavbarInfo.value)
	const set: Set<NavbarInfo> = new Set();
	navbarInfoArr.value.forEach((v, index) => {
		set.add(v)
		if (index === navbarInfoArr.value.length -1) {
			navbarInfoArr.value = Array.from(set)
			currentNavbarInfo.value = {}
			setDefault()
		}
	})
}

const handleResetPageAction = () => {
  setDefault()
}

const setChildrenInfo = (navbarArr: Array<NavbarInfo>) => {
  navbarArr.forEach(v => {
		if (!v.children) {
			v.children = []
		}
		if (v.children) {
			setChildrenInfo(v.children)
		}
	})
}

const handleSaveAllPageAction = () => {
  // 将所有页面信息保存到siteSetting
	if (navbarInfoArr.value.length === 0 && navbarAddStatus.value) {
		window.$message?.error('没有需要保存的页面')
		return
	}
	const siteSetting: SiteSetting = {}
	siteSetting.paramName = `${authStore.userInfo.user_uid}AllPageInfo`
	setChildrenInfo(navbarInfoArr.value)
	siteSetting.paramValue = JSON.stringify(navbarInfoArr.value, null, 0).replaceAll(" ", "")
	if (navbarAddStatus.value) {
		// 是添加新页面
		siteSetting.userUid = authStore.userInfo.user_uid
		siteSettingApi.insertData(siteSetting).then(result => {
			if (!result.error) {
				window.$message?.success('保存成功')
				setDefault()
				loadSiteSetting()
			}
		})
	}else {
		// 更新
		currentPageSiteSetting.value.paramValue = siteSetting.paramValue
		siteSettingApi.updateData(currentPageSiteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
				setDefault()
				loadSiteSetting()
			}
		})
	}
}

const handleUpdateNavbarAction = () => {
	// 将导航信息保存到siteSetting
	if (draggableNavbarInfoArr.value.length === 0 && draggableNavbarAddStatus.value) {
		window.$message?.error('没有需要保存的导航信息')
		return
	}
	const siteSetting: SiteSetting = {}
	siteSetting.paramName = `${authStore.userInfo.user_uid}NavbarInfo`
	setChildrenInfo(draggableNavbarInfoArr.value)
	siteSetting.paramValue = JSON.stringify(draggableNavbarInfoArr.value, null, 0).replaceAll(" ", "")
	if (draggableNavbarAddStatus.value) {
		// 是添加导航
		siteSetting.userUid = authStore.userInfo.user_uid
		siteSettingApi.insertData(siteSetting).then(result => {
			if (!result.error) {
				window.$message?.success('保存成功')
				loadSiteSetting()
			}
		})
	}else {
		// 更新
		currentNavbarSiteSetting.value.paramValue = siteSetting.paramValue
		siteSettingApi.updateData(currentNavbarSiteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
				loadSiteSetting()
			}
		})
	}
}

onBeforeMount(() => {
	loadUserArticle()
	loadSiteSetting()
})
</script>

<style scoped lang="css">

</style>
