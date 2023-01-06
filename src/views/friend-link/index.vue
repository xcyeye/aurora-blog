<template>
	<aurora-common :is-sticky-sidebar="true" :is-show-side-bar="false"
					:show-sidebar-link="false" :user-uid="userUid"
					:is-show-top-img="true" :is-show-head-line="false">
		<template #center1>
			<div class="link" :key="index" v-for="(category,index) in friendLinkArrMap.keys()">
				<div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
						 class="box link-common" id="c-link">
					<div>
						<div class="about-title link-title">
							<div class="about-title-single">
								<span class="about-title-single-value">{{category}}</span>
							</div>
						</div>
						<div v-for="(item,index) in friendLinkArrMap.get(category)" :data="item" :key="item">
							<LinkItem
								:item="item"/>
						</div>
					</div>
				</div>
			</div>
<!--			<aurora-center v-if="siteInformation !== ''">-->
<!--				<template #page-center1>-->
<!--					<div class="about-title link-title">-->
<!--						<div class="about-title-single">-->
<!--							<span class="about-title-single-value">友链申请</span>-->
<!--						</div>-->
<!--					</div>-->
<!--					<div class="self-site">-->
<!--						<div class="language-javascript ext-js line-numbers-mode"><pre class="language-javascript"><code><span class="token punctuation">{</span>-->
<!--    title<span class="token operator">:</span> <span class="token string">"{{siteInformation.title}}"</span><span class="token punctuation">,</span><span class="token comment">//博客名称</span>-->
<!--    url<span class="token operator">:</span> <span class="token string">"{{siteInformation.url}}"</span><span class="token punctuation">,</span><span class="token comment">//博客url</span>-->
<!--    logo<span class="token operator">:</span> <span class="token string">"{{siteInformation.logo}}"</span><span class="token punctuation">,</span><span class="token comment">//博客logo</span>-->
<!--    describe<span class="token operator">:</span> <span class="token string">"{{siteInformation.describe}}"</span><span class="token punctuation">,</span><span class="token comment">//博客描述</span>-->
<!--    cover<span class="token operator">:</span> <span class="token string">"{{siteInformation.cover}}"</span><span class="token punctuation">,</span><span class="token comment">//博客截屏</span>-->
<!--    <span class="token comment">//{{siteInformation.contact}}</span>-->
<!--<span class="token punctuation">}</span><span class="token punctuation">,</span>-->
<!--</code></pre><div class="line-numbers"><span class="line-number">1</span><br><span class="line-number">2</span><br><span class="line-number">3</span><br><span class="line-number">4</span><br><span class="line-number">5</span><br><span class="line-number">6</span><br><span class="line-number">7</span><br><span class="line-number">8</span><br></div></div>-->
<!--						-->
<!--						<ul class="link-info-desc">-->
<!--							<li v-for="(linkItem,index) in siteInformation.otherDescribe" :key="linkItem" v-html="linkItem"></li>-->
<!--						</ul>-->
<!--					</div>-->
<!--				</template>-->
<!--			</aurora-center>-->
			<comment :path-name="$route.path" />
		</template>
	</aurora-common>
</template>

<script lang="ts" setup>
import {
	computed,
	defineComponent, onBeforeMount, onMounted, ref,
	Transition,
} from 'vue';
import {blogPageData} from "@/assets/config";
import {LinkVo} from "@/bean/vo/article/LinkVo";
import {useSiteInfo, useUserInfo} from "@/stores";
import blogConfig from '@/config/blogConfig.json'
import {getRandomNum, StringUtil} from "@/utils";
import {articleApi, linkApi} from "@/service";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {Condition} from "@/bean/core/bean";

const friendLinkArrMap = ref<Map<String, Array<LinkVo>>>(new Map<String, Array<LinkVo>>())
const friendLinkSiteInformation = ref<FriendLinkSiteInformation>({})
const color = ref<string>('')
const currentSiteInfo = ref<SiteSettingInfo>({})
const useSite = useSiteInfo()
const useUser = useUserInfo()
const router = useRouter()
const routerPush = useRouterPush()
const userUid = ref<string>('')

const setSpanStyle = computed(() => {
	return (index: number) => {
		let background_color = blogConfig.randomColor[
			getRandomNum(0,blogConfig.randomColor.length -1)]
		return "background-color: "+background_color + ";"
	}
})

const setBottomStyle = computed(() => {
	return (index: number) => {
		return "color: "+color.value + ";"
	}
})

const loadFriendInfo = () => {
	const condition: Condition = {
		status: true,
		otherUid: userUid.value,
		pageSize: 400
	}
  linkApi.queryListDataByCondition(condition).then(result => {
		if (result.data && result.data.result) {
			result.data.result.forEach(v => {
				let linkArr = friendLinkArrMap.value.get(v.categoryName!);
				if (linkArr && linkArr.length > 0) {
					linkArr.push(v)
					friendLinkArrMap.value.set(v.categoryName!, linkArr)
				}else {
					friendLinkArrMap.value.set(v.categoryName!, Array.of(v))
				}
				console.log(friendLinkArrMap.value);
			})
		}
	})
}

onMounted(() => {
	//如果手机端侧边栏打开的，那么就关闭
	// if (this.$store.state.openMobileSidebar) {
	// 	this.$store.commit("setOpenMobileSidebar",{
	// 		openMobileSidebar: false
	// 	})
	// }
})

onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.userUid as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		})
	}
	loadFriendInfo()
})
</script>
