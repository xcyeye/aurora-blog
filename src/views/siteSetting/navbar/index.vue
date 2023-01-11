<template>
	<div class="h-full">
		<n-card title="站点设置" class="h-full shadow-sm rounded-16px">
			<n-space vertical>
				<n-card :bordered="false" hoverable class="shadow-sm rounded-16px">
					<n-space vertical>
						<n-p>友情链接、用户首页、说说、关于页面等绝大多数页面的的规则为 /pageName/userUid，如/user/{{authStore.userInfo.user_uid}}便表示您的用户首页</n-p>
						<n-p>对于文章等部分特殊的页面规则为/pageName/uid，如/article/{{articleInfo.uid}} 链接便是文章 “{{articleInfo.title}}”的文章详情页面</n-p>
					</n-space>
				</n-card>
				<n-card :bordered="false" hoverable class="shadow-sm rounded-16px">
					<n-grid x-gap="12" :cols="2">
						<n-gi>
							<n-space vertical>
								<n-space vertical>
									<n-text>页面地址</n-text>
									<n-input round placeholder="请输入页面地址" size="small" v-model:value="currentNavbarInfo.url"/>
								</n-space>
								<n-space vertical>
									<n-text>页面名称</n-text>
									<n-input round placeholder="请输入页面名称" size="small" v-model:value="currentNavbarInfo.name"/>
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
									<n-button strong secondary tertiary round type="info" @click="handleAddPageInfoAction">添加页面</n-button>
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
										<li class="list-group-item">{{ element.name }}</li>
									</template>
								</draggable>
							</ul>
						</n-card>
					</n-gi>
					<n-gi>
						<n-card title="前台导航" :bordered="false" hoverable class="h-full shadow-sm rounded-16px">
							<aurora-draggable @handleFinallyNavbarData="handleFinallyNavbarData" :navbar-info="draggableNavbarInfoArr"/>
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
import {ArticleVo} from "@/theme/vo/article/ArticleVo";
import {StringUtil} from "@/utils";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import draggable from 'vuedraggable'
const authStore = useAuthStore()

defineComponent({name: 'index'});

const originNavbar = ref<Array<NavbarInfo>>([
	{
		name: '首页',
		url: `/user/23u47`,
		outLink: false,
		children: []
	},
	{
		name: '友情链接',
		url: '/friendLink/3476345',
		children: []
	},
	{
		name: 'article',
		url: '/article/3476',
		children: [
			{
				name: 'spring',
				url: '/article/3487634',
				children: []
			},
			{
				name: 'java',
				url: '/article/495873489',
				children: []
			}
		]
	}
])

const navbarInfoArr = ref<Array<NavbarInfo>>([])
const draggableNavbarInfoArr = ref<Array<NavbarInfo>>([])
const articleInfo = ref<ArticleVo>({})
const currentNavbarInfo = ref<NavbarInfo>({})
const navbarAddStatus = ref(false)
const draggableNavbarAddStatus = ref(false)
const currentNavbarSiteSetting = ref<SiteSetting>({})
const currentPageSiteSetting = ref<SiteSetting>({})

const handleFinallyNavbarData = (navbarInfoArr: Array<NavbarInfo>) => {
	// console.log(draggableNavbarInfoArr.value);
}

const loadUserArticle = () => {
  articleApi.queryListDataByCondition({otherUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data && result.data.result) {
			articleInfo.value = result.data.result[0]
		}
	})
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
		}
	})
}

const handleAddPageInfoAction = () => {
	if (!StringUtil.haveLength(currentNavbarInfo.value.name)) {
		window.$message?.error('请输入页面名称')
		return
	}

	if (!StringUtil.haveLength(currentNavbarInfo.value.url)) {
		window.$message?.error('请输入页面链接')
		return
	}
	if (!currentNavbarInfo.value.outLink) {
		currentNavbarInfo.value.outLink = false
	}
	currentNavbarInfo.value.children = []
	navbarInfoArr.value.push(currentNavbarInfo.value)
	console.log(navbarInfoArr.value);
}

const handleSaveAllPageAction = () => {
  // 将所有页面信息保存到siteSetting
	if (navbarInfoArr.value.length === 0) {
		window.$message?.error('没有需要保存的页面')
		return
	}
	const siteSetting: SiteSetting = {}
	siteSetting.paramName = `${authStore.userInfo.user_uid}AllPageInfo`
	siteSetting.paramValue = JSON.stringify(navbarInfoArr.value, null, 2)
	if (navbarAddStatus.value) {
		// 是添加新页面
		siteSetting.userUid = authStore.userInfo.user_uid
		siteSettingApi.insertData(siteSetting).then(result => {
			if (!result.error) {
				window.$message?.success('保存成功')
				loadSiteSetting()
			}
		})
	}else {
		// 更新
		currentPageSiteSetting.value.paramValue = siteSetting.paramValue
		siteSettingApi.updateData(currentPageSiteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
				loadSiteSetting()
			}
		})
	}
}

const handleUpdateNavbarAction = () => {
	// 将导航信息保存到siteSetting
	if (draggableNavbarInfoArr.value.length === 0) {
		window.$message?.error('没有需要保存的导航信息')
		return
	}
	const siteSetting: SiteSetting = {}
	siteSetting.paramName = `${authStore.userInfo.user_uid}NavbarInfo`
	siteSetting.paramValue = JSON.stringify(draggableNavbarInfoArr.value, null, 2)
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
