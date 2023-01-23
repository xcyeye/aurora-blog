<template>
	<aurora-common :is-sticky-sidebar="true" :is-show-side-bar="false"
								 :show-sidebar-link="false" :user-uid="userUid"
								 :is-show-top-img="true" :is-show-head-line="false">
		<template #center1>
			<aurora-card class="link" :key="index" v-for="(category,index) in friendLinkArrMap.keys()">
				<div>
					<div class="about-title link-title">
						<div class="about-title-single">
							<span class="about-title-single-value">{{ category }}</span>
						</div>
					</div>
					<div v-for="(item,index) in friendLinkArrMap.get(category)" :data="item" :key="item">
						<LinkItem
							:item="item"/>
					</div>
				</div>
			</aurora-card>
			<aurora-card>
				<n-tabs type="line" animated>
					<n-tab-pane name="oasis" :tab="useUserInfo().getUserInfo(userUid).username">
						<div class="self-site">
							<div class="language-javascript ext-js line-numbers-mode"><pre class="language-javascript"><code><span
								class="token punctuation">{</span>
    title<span class="token operator">:</span> <span class="token string">"{{ friendLinkSiteInformation.title }}"</span><span
									class="token punctuation">,</span><span class="token comment">//博客名称</span>
    url<span class="token operator">:</span> <span
									class="token string">"{{ friendLinkSiteInformation.url }}"</span><span
									class="token punctuation">,</span><span class="token comment">//博客url</span>
    logo<span class="token operator">:</span> <span
									class="token string">"{{ friendLinkSiteInformation.logo }}"</span><span
									class="token punctuation">,</span><span class="token comment">//博客logo</span>
    describe<span class="token operator">:</span> <span
									class="token string">"{{ friendLinkSiteInformation.describe }}"</span><span
									class="token punctuation">,</span><span class="token comment">//博客描述</span>
    cover<span class="token operator">:</span> <span class="token string">"{{ friendLinkSiteInformation.cover }}"</span><span
									class="token punctuation">,</span><span class="token comment">//博客截屏</span>
    <span class="token comment">//{{ friendLinkSiteInformation.contact }}</span>
<span class="token punctuation">}</span><span class="token punctuation">,</span>
</code></pre>
								<div class="line-numbers"><span class="line-number">1</span><br><span class="line-number">2</span><br><span
									class="line-number">3</span><br><span class="line-number">4</span><br><span
									class="line-number">5</span><br><span class="line-number">6</span><br><span
									class="line-number">7</span><br><span class="line-number">8</span><br></div>
							</div>
							
							<ul class="link-info-desc">
								<li v-for="(linkItem,index) in friendLinkSiteInformation.otherDescribe" :key="linkItem"
										v-html="linkItem"></li>
							</ul>
						</div>
					</n-tab-pane>
					<n-tab-pane name="the beatles" tab="申请">
						<n-space vertical>
							<n-card hoverable class="rounded-16px shadow-sm" size="small" title="基本信息(必填)">
								<n-space vertical>
									<n-space justify="center">
										<upload-file
											@handleFinishUploadFile="handleFinishSiteLogoUploadFile"
											:show-file-list="false"
											:parameter-data="{userUid: userUid, summary: `其他人向${useUserInfo().getUserInfo(userUid).username}申请友情链接时上传的logo`, storageMode: 0}"
											:control-upload-file="false">
											<template #uploadDraggerContent>
												<n-avatar
													round
													:size="100"
													:src="applyForFriendLink.linkLogo"
												/>
											</template>
										</upload-file>
									</n-space>
									<n-space vertical>
										<n-text>站点地址</n-text>
										<n-input placeholder="站点地址" round v-model:value="applyForFriendLink.linkUrl" type="text"/>
									</n-space>
									<n-space vertical>
										<n-text>站点名称</n-text>
										<n-input placeholder="站点名称" round v-model:value="applyForFriendLink.linkTitle" type="text"/>
									</n-space>
									<n-space vertical>
										<n-text>邮箱</n-text>
										<n-input placeholder="邮箱" round v-model:value="applyForFriendLink.email" type="text"/>
									</n-space>
									<n-tabs type="line" animated>
										<n-tab-pane name="oasis" tab="站点描述">
											<n-input type="textarea"
															 placeholder="站点描述"
															 v-model:value="applyForFriendLink.linkDescription"
															 :autosize="true" maxlength="500" show-count/>
										</n-tab-pane>
										<n-tab-pane name="the beatles" tab="主页截屏">
											<n-space vertical>
												<n-image
													v-if="applyForFriendLink.linkCover"
													:src="applyForFriendLink.linkCover"
												/>
												<upload-file
													@handleFinishUploadFile="handleFinishSiteCoverUploadFile"
													:accept-file-type-str="['.png','.jpg','.jpeg']"
													:parameter-data="{
									userUid: userUid,
									summary: `其他人向${useUserInfo().getUserInfo(userUid).username}申请友情链接时上传的封面`,
									storageMode: 0
									}"
													:show-upload-dragger="true"
												>
													<template #extraButton>
														<n-button v-if="applyForFriendLink.linkCover" round type="success"
																			@click="handleRemoveLinkCoverAction">移除封面
														</n-button>
													</template>
												</upload-file>
											</n-space>
										</n-tab-pane>
									</n-tabs>
								</n-space>
							</n-card>
							<n-card hoverable class="rounded-16px shadow-sm" size="small">
								<n-space vertical>
									<n-grid x-gap="12" :cols="2">
										<n-gi>
											<n-p>希望在哪个类别</n-p>
										</n-gi>
										<n-gi>
											<n-input v-model:value="hopeLinkCategoryName" type="text" placeholder="输入一个类别"/>
										</n-gi>
									</n-grid>
									<n-grid x-gap="12" :cols="2">
										<n-gi>
											<n-p>QQ</n-p>
										</n-gi>
										<n-gi>
											<n-input v-model:value="applyForFriendLink.qqNumber" type="number" placeholder="QQ号"/>
										</n-gi>
									</n-grid>
								</n-space>
								
								<template #footer>
									<n-space justify="end">
										<n-button strong secondary tertiary round type="success" @click="handleApplyLinkAction">申请
										</n-button>
									</n-space>
								</template>
							</n-card>
						</n-space>
					</n-tab-pane>
				</n-tabs>
			</aurora-card>
			<blog-comment
				:user-uid="userUid"
				:page-uid="userUid"
				:page-path="`/friendLink/${userUid}`"
				:query-regexp="`/friendLink/${userUid}`"
				reply-page-type="FRIEND_LINK"/>
		</template>
	</aurora-common>
</template>

<script lang="ts" setup>
import {
	computed,
	defineComponent, onBeforeMount, onMounted, ref,
	Transition,
	h
} from 'vue';
import {blogPageData} from "@/assets/config";
import {LinkVo} from "@/bean/vo/article/LinkVo";
import {useAuthStore, useSiteInfo, useUserInfo} from "@/stores";
import blogConfig from '@/config/blogConfig.json';
import {getRandomNum, StringUtil} from "@/utils";
import {articleApi, linkApi} from "@/service";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {Condition} from "@/bean/core/bean";
import {isNotEmptyObject} from "@/utils/business";
import {Link} from "@/bean/pojo/article/Link";
import {REGEXP_EMAIL, REGEXP_URL} from "@/config";
import {NButton, UploadFileInfo} from "naive-ui";

const friendLinkArrMap = ref<Map<String, Array<LinkVo>>>(new Map<String, Array<LinkVo>>());
const color = ref<string>('');
const currentSiteInfo = ref<SiteSettingInfo>({});
const useSite = useSiteInfo();
const useUser = useUserInfo();
const router = useRouter();
const routerPush = useRouterPush();
const userUid = ref<string>('');
const friendLinkSiteInformation = ref<FriendLinkSiteInformation>({});
const applyForFriendLink = ref<Link>({});
const hopeLinkCategoryName = ref<string>('');

const setSpanStyle = computed(() => {
	return (index: number) => {
		let background_color = blogConfig.randomColor[
			getRandomNum(0, blogConfig.randomColor.length - 1)];
		return "background-color: " + background_color + ";";
	};
});

const setBottomStyle = computed(() => {
	return (index: number) => {
		return "color: " + color.value + ";";
	};
});

const handleApplyLinkAction = () => {
	if (!REGEXP_EMAIL.test(applyForFriendLink.value.email!)) {
		window.$message?.error('添加邮箱更方便联系o(￣▽￣)ｄ');
		return;
	}
	if (!REGEXP_URL.test(applyForFriendLink.value.linkUrl!)) {
		window.$message?.error('添加你的站点地址呗(￣┰￣*)');
		return;
	}
	
	if (!StringUtil.haveLength(applyForFriendLink.value.linkTitle)) {
		window.$message?.error('站点名称貌似是必填的○|￣|_');
		return;
	}
	if (!StringUtil.haveLength(applyForFriendLink.value.linkLogo)) {
		window.$message?.error('Logo也很重要o(￣▽￣)ｄ');
		return;
	}
	if (StringUtil.haveLength(hopeLinkCategoryName.value)) {
		applyForFriendLink.value.linkDescription = `${applyForFriendLink.value.linkDescription}(希望的分类为${hopeLinkCategoryName.value})`;
	}
	applyForFriendLink.value.userUid = userUid.value;
	linkApi.insertData(applyForFriendLink.value).then(result => {
		if (!result.error) {
			let categoryName = '';
			if (StringUtil.haveLength(hopeLinkCategoryName.value)) {
				categoryName = hopeLinkCategoryName.value;
			} else {
				categoryName = applyForFriendLink.value.linkTitle!;
			}
			if (friendLinkArrMap.value) {
				if (friendLinkArrMap.value.get(categoryName)) {
					friendLinkArrMap.value.get(categoryName)!.push(applyForFriendLink.value);
				} else {
					friendLinkArrMap.value.set(categoryName, Array.of(applyForFriendLink.value));
				}
			} else {
				friendLinkArrMap.value = new Map<String, Array<LinkVo>>();
				friendLinkArrMap.value.set(categoryName, Array.of(applyForFriendLink.value));
			}
			let markAsRead = false;
			const n = window.$notification?.success({
				meta: '申请成功',
				keepAliveOnHover: true,
				onClose: () => {
					if (!markAsRead) {
						return false;
					}
				},
				content: `申请成功，需${useUserInfo().getUserInfo(userUid.value).username}审核通过之后才能显示，审核结果会通过邮箱进行通知，请留意邮箱信息o(￣▽￣)ｄ`,
				action: () =>
					h(
						NButton,
						{
							text: true,
							type: 'primary',
							onClick: () => {
								markAsRead = true;
								n!.destroy();
							}
						},
						{
							default: () => '已读'
						}
					)
			});
		}
	});
};

const handleFinishSiteLogoUploadFile = (file: UploadFileInfo) => {
	// 修改站点logo
	applyForFriendLink.value.linkLogo = file.url;
};

const handleFinishSiteCoverUploadFile = (file: UploadFileInfo) => {
	// 修改站点封面
	applyForFriendLink.value.linkCover = file.url;
};

const loadFriendInfo = () => {
	const condition: Condition = {
		status: true,
		otherUid: userUid.value,
		pageSize: 400
	};
	linkApi.queryListDataByCondition(condition).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				let linkArr = friendLinkArrMap.value.get(v.categoryName!);
				if (linkArr && linkArr.length > 0) {
					linkArr.push(v);
					friendLinkArrMap.value.set(v.categoryName!, linkArr);
				} else {
					friendLinkArrMap.value.set(v.categoryName!, Array.of(v));
				}
				console.log(friendLinkArrMap.value);
			});
		}
	});
};

onMounted(() => {
	//如果手机端侧边栏打开的，那么就关闭
	// if (this.$store.state.openMobileSidebar) {
	// 	this.$store.commit("setOpenMobileSidebar",{
	// 		openMobileSidebar: false
	// 	})
	// }
});

onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.userUid as string;
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		});
	}
	loadFriendInfo();
	currentSiteInfo.value = useSite.getSiteInfo(userUid.value);
	if (!isNotEmptyObject(currentSiteInfo.value)) {
		friendLinkSiteInformation.value = currentSiteInfo.value.friendLinkSiteInformation!;
	}
});
</script>
