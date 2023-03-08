<template>
	<div class="h-full">
		<n-card title="站点设置" class="h-full shadow-sm rounded-16px">
			<n-tabs type="line" animated>
				<template #suffix>
					<n-space justify="end">
						<n-button strong secondary tertiary round type="success" @click="handleUpdateOrInsertAction">{{addInfoStatus ? '添加': '更新'}}</n-button>
					</n-space>
				</template>
				<n-tab-pane name="oasis" tab="基本">
					<n-space vertical>
						<n-card hoverable class="rounded-16px shadow-sm" size="small">
							<n-space vertical>
								<n-grid x-gap="12" :cols="3">
									<n-gi>
										<n-statistic label="是否显示波浪(首页)">
											<n-switch size="small" v-model:value="siteSettingInfo.showWave" />
										</n-statistic>
									</n-gi>
									<n-gi>
										<n-statistic label="顶部背景图是否显示气泡">
											<n-switch size="small" v-model:value="siteSettingInfo.showTopImgBubble" />
										</n-statistic>
									</n-gi>
									<n-gi>
										<n-statistic label="手机端底部是否显示侧边栏">
											<n-switch size="small" v-model:value="siteSettingInfo.mobilePageSidebar" />
										</n-statistic>
									</n-gi>
								</n-grid>

								<n-grid x-gap="12" :cols="2">
									<n-gi>
										<n-statistic label="侧边栏最新文章数量">
											<n-input-number size="small" type="number" v-model:value="siteSettingInfo.latestPageSize"/>
										</n-statistic>
									</n-gi>
									<n-gi>
										<n-statistic label="Github地址">
											<n-input size="small" type="text" v-model:value="siteSettingInfo.githubUrl"/>
										</n-statistic>
									</n-gi>
								</n-grid>
							</n-space>
						</n-card>
						<n-card hoverable class="rounded-16px shadow-sm" size="small">
							<n-space vertical>
								<n-statistic label="pc端壁纸">
									<n-dynamic-input
										v-model:value="siteSettingInfo.pcBackgroundImageList"
										placeholder="请输入pc端壁纸"
									/>
								</n-statistic>
								<n-statistic label="手机端壁纸">
									<n-dynamic-input
										v-model:value="siteSettingInfo.mobileBackgroundImageList"
										placeholder="请输入手机端壁纸"
									/>
								</n-statistic>
								<n-statistic label="全局懒加载图片地址">
									<n-input size="small" type="text" v-model:value="siteSettingInfo.homePageLazyLoadingImg"/>
									<n-image width="300" :src="siteSettingInfo.homePageLazyLoadingImg" v-if="siteSettingInfo.homePageLazyLoadingImg"/>
								</n-statistic>
								<n-statistic label="随机图片接口地址">
									<n-input size="small" type="text" v-model:value="siteSettingInfo.randomPictureInterface"/>
									<n-image width="300" :src="siteSettingInfo.randomPictureInterface" v-if="siteSettingInfo.randomPictureInterface"/>
								</n-statistic>
								<n-statistic label="默认封面图片地址">
									<n-input size="small" type="text" v-model:value="siteSettingInfo.defaultCoverRequestInterface"/>
									<n-image width="300" :src="siteSettingInfo.defaultCoverRequestInterface" v-if="siteSettingInfo.defaultCoverRequestInterface"/>
								</n-statistic>
							</n-space>
						</n-card>
					</n-space>
				</n-tab-pane>
				<n-tab-pane name="the beatles" tab="页脚">
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-statistic label="启用">
								<n-switch v-model:value="footerInfo.enable"/>
							</n-statistic>
							<div v-if="footerInfo.enable">
								<n-grid x-gap="12" :cols="2">
									<n-gi>
										<n-statistic label="显示主题版权信息">
											<n-switch v-model:value="footerInfo.isShowThemeCopyright"/>
										</n-statistic>
									</n-gi>
									<n-gi>
										<n-statistic label="显示运行时间">
											<n-switch v-model:value="footerInfo.isShowRunTime"/>
										</n-statistic>
									</n-gi>
								</n-grid>

								<n-statistic label="运行时间前缀">
									<n-input size="small" type="text" v-model:value="footerInfo.prefixRuntime"/>
								</n-statistic>

								<n-statistic label="背景图">
									<n-input size="small" type="text" v-model:value="footerInfo.backgroundImage"/>
									<n-image width="300" :src="footerInfo.backgroundImage" v-if="footerInfo.backgroundImage"/>
								</n-statistic>
								<n-statistic label="页脚信息">
									<n-dynamic-input
										v-model:value="footerInfo.footInfo"
										placeholder="请输入页脚信息"
										:max="10"
									/>
								</n-statistic>
							</div>
						</n-space>
					</n-card>
				</n-tab-pane>
				<n-tab-pane name="randomSay" tab="随机一言">
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-statistic label="启用随机一言">
								<n-switch v-model:value="siteSettingInfo.showRandomSay"/>
							</n-statistic>
							<n-statistic v-if="siteSettingInfo.showRandomSay" label="随机一言接口">
								<n-input size="small" type="text" disabled v-model:value="siteSettingInfo.randomSayApi.urlApi"/>
							</n-statistic>
						</n-space>
					</n-card>
				</n-tab-pane>
				<n-tab-pane name="social" tab="社交信息">
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-dynamic-input v-model:value="socialInfoArr" @create="onCreateSocial">
							<template #default="{ value }">
								<n-card hoverable class="rounded-16px shadow-sm" size="small">
									<div style="display: flex; align-items: center; width: 100%">
										<n-space vertical>
											<n-grid x-gap="12" :cols="3">
												<n-gi>
													<n-statistic label="显示">
														<n-switch v-model:value="value.show"/>
													</n-statistic>
												</n-gi>
												<n-gi>
													<n-statistic label="首页显示">
														<n-switch v-model:value="value.isHome"/>
													</n-statistic>
												</n-gi>
												<n-gi>
													<n-statistic label="侧边栏显示">
														<n-switch v-model:value="value.sidebar"/>
													</n-statistic>
												</n-gi>
											</n-grid>
											<n-statistic label="图标">
												<n-space justify="start">
													<svg-icon :style="`color: ${value.color}`" :icon="value.icon"/>
													<n-input type="text" size="small" v-model:value="value.icon"/>
												</n-space>
											</n-statistic>
											<n-statistic label="图标颜色">
												<n-input type="text" size="small" v-model:value="value.color"/>
											</n-statistic>
											<n-statistic label="链接">
												<n-input type="text" size="small" v-model:value="value.aHref"/>
											</n-statistic>
											<n-statistic label="图片地址">
												<n-input size="small" type="text" v-model:value="value.showImgSrc"/>
												<n-image width="300" :src="value.showImgSrc" v-if="value.showImgSrc"/>
											</n-statistic>
										</n-space>
									</div>
								</n-card>
							</template>
						</n-dynamic-input>
					</n-card>
				</n-tab-pane>
				<n-tab-pane name="friend-link" label="友情链接">
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-space vertical>
							<n-statistic label="站点标题">
								<n-input size="small" v-model:value="friendLinkSiteInformation.title"/>
							</n-statistic>
							<n-statistic label="地址">
								<n-input size="small" v-model:value="friendLinkSiteInformation.url"/>
							</n-statistic>
							<n-statistic label="logo">
								<n-input size="small" v-model:value="friendLinkSiteInformation.logo"/>
								<n-image width="300" :src="friendLinkSiteInformation.logo" v-if="friendLinkSiteInformation.logo"/>
							</n-statistic>
							<n-statistic label="封面">
								<n-input size="small" v-model:value="friendLinkSiteInformation.cover"/>
								<n-image width="300" :src="friendLinkSiteInformation.cover" v-if="friendLinkSiteInformation.cover"/>
							</n-statistic>
							<n-statistic label="描述">
								<n-input size="small" type="textarea" :minlength="3" v-model:value="friendLinkSiteInformation.describe"/>
							</n-statistic>
							<n-statistic label="联系">
								<n-input size="small" v-model:value="friendLinkSiteInformation.contact"/>
							</n-statistic>
						</n-space>
					</n-card>
				</n-tab-pane>
				<n-tab-pane name="json" label="json导入">
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-grid x-gap="12" :cols="2">
							<n-gi>
								<n-button strong secondary tertiary round type="info" @click="handleBackupSiteSetting">备份</n-button>
								<n-code
									:hljs="hljs"
									:word-wrap="true"
									:code="JSON.stringify(JSON.parse(siteSetting.paramValue), null, 4)"
									language="json"
								/>
							</n-gi>
							<n-gi>
								<n-button strong secondary tertiary round type="error" @click="parseSiteSetting">解析</n-button>
								<n-input type="textarea" :autosize="{minRows: 20}" v-model:value="importSiteSetting.paramValue" />
							</n-gi>
						</n-grid>
					</n-card>
				</n-tab-pane>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref} from "vue";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import {useAuthStore} from "@/store";

import hljs from 'highlight.js/lib/core';
import json from 'highlight.js/lib/languages/json';
import {copyContent} from "@/plugins";
import {StringUtil} from "@/utils";

defineComponent({name: 'index'});
hljs.registerLanguage('json', json);

const addInfoStatus = ref(true)
const siteSettingInfo = ref<SiteSettingInfo>({
	randomSayApi: {
		requestMethod: 'GET',
		urlApi: 'https://v1.hitokoto.cn/?encode=text&c=a'
	}
})
const footerInfo = ref<FooterInfo>({})
const siteSetting = ref<SiteSetting>({})
const importSiteSetting = ref<SiteSetting>({})
const friendLinkSiteInformation = ref<FriendLinkSiteInformation>({})
const authStore = useAuthStore()
const socialInfoArr = ref<Array<SocialInfo>>([])

const loadSiteInfo = () => {
	siteSettingApi.queryListDataByCondition({otherUid: authStore.userInfo.user_uid, keyword: `${authStore.userInfo.user_uid}SiteInfo`}).then(result => {
		if (result.data && result.data.result && result.data.result.length > 0) {
			siteSetting.value = result.data.result[0]
			siteSettingInfo.value = JSON.parse(result.data.result[0].paramValue!);
			addInfoStatus.value = false
			footerInfo.value = siteSettingInfo.value.footerInfo as FooterInfo
			console.log(siteSettingInfo.value);
			socialInfoArr.value = siteSettingInfo.value.socialsArr as Array<SocialInfo>
			friendLinkSiteInformation.value = siteSettingInfo.value.friendLinkSiteInformation as FriendLinkSiteInformation

		}else {
			addInfoStatus.value = true
		}
	})
}
loadSiteInfo()

const handleUpdateOrInsertAction = () => {
	siteSettingInfo.value.footerInfo = footerInfo.value
	siteSettingInfo.value.friendLinkSiteInformation = friendLinkSiteInformation.value
	siteSettingInfo.value.socialsArr = socialInfoArr.value
	let readmeStr = siteSettingInfo.value.readme
	siteSettingInfo.value.readme = 'auReadMeua'
	const obj: object = {
		readme: readmeStr
	}
	let objJson = JSON.stringify(obj, null, 0)
	objJson = objJson.substring('{"readme":"'.length, (objJson.length - ('};'.length)))
	let siteInfoJson = JSON.stringify(siteSettingInfo.value, null, 0).replaceAll(' ', '')
	siteInfoJson = siteInfoJson.replaceAll('auReadMeua', objJson)
	siteSetting.value.paramValue = siteInfoJson
	// siteSetting.value.paramValue = JSON.stringify(siteSettingInfo.value, null, 0)
	if (addInfoStatus.value) {
		siteSetting.value.paramName = `${authStore.userInfo.user_uid}SiteInfo`
		siteSetting.value.userUid = authStore.userInfo.user_uid
		siteSettingApi.insertData(siteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('添加成功')
				loadSiteInfo()
			}
		})
	}else {
		siteSettingApi.updateData(siteSetting.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
				loadSiteInfo()
			}
		})
	}
}

const handleBackupSiteSetting = () => {
  copyContent(siteSetting.value.paramValue, false)
}

const parseSiteSetting = () => {
  if (!StringUtil.haveLength(importSiteSetting.value.paramValue)) {
		window.$message?.error('请输入json类型的站点信息')
		return
	}
	siteSettingInfo.value = JSON.parse(importSiteSetting.value.paramValue!)
	window.$message?.success('解析成功，请查看预览,若预览没问题，请点击更新或者添加保存数据')
	footerInfo.value = siteSettingInfo.value.footerInfo as FooterInfo
	socialInfoArr.value = siteSettingInfo.value.socialsArr as Array<SocialInfo>
	friendLinkSiteInformation.value = siteSettingInfo.value.friendLinkSiteInformation as FriendLinkSiteInformation
}

const onCreateSocial = (): SocialInfo => {
	return {
		aHref: '',
		isHome: true,
		show: true,
		sidebar: false,
		icon: '',
		color: '',
		showImgSrc: ''
	}
}
</script>

<style scoped>

</style>
