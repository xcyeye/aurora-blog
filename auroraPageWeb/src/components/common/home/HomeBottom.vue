<template>
  <parcel-style>
		<!--这是首页下面的文章模板-->
		<div :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle"
				 ref="home-bottom" class="home-bottom" id="home-bottom">
			<div ref="homeBottomScroll"></div>
			<div class="home-page-tag" id="home-page-tag">
				<home-page-item :index="index"
												:user-uid="userUid"
												:show-home-page-img="true"
												:data="item.delete" :key="index"
												v-for="(item,index) in articleArr" :page-item="item"/>
				<n-space justify="center">
					<n-pagination v-model:page="currentPage"
												@update:page="handlePageChangeAction"
												:page-size="currentSize"
												:item-count="pageTotal"
												:default-page="1" />
				</n-space>
		
			</div>
			<div v-if="showHomeSidebar" class="home-page-fun" id="home-page-fun">
				<HomeSidebar :user-uid="userUid"/>
			</div>
		</div>
		<slot name="home-footer"/>
	</parcel-style>
</template>

<script lang="ts" setup>

import {onBeforeMount, ref} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {Condition, PageData} from "@/bean/core/bean";
import smoothscroll from 'smoothscroll-polyfill';
import RequestResult = Service.RequestResult;
import {useArticleStore} from "@/stores";

interface Props {
	userUid?: string,
	condition?: Condition,
	showHomeSidebar?: boolean,
	queryArticleDataMethod: (condition: Condition) => Promise<RequestResult<PageData<ArticleVo>>>
}

const props = withDefaults(defineProps<Props>(), {
	condition: () => {
		return {}
	},
	showHomeSidebar: true
})

const articleArr = ref<Array<ArticleVo>>([])
const currentPage = ref<number>(1)
const currentSize = ref<number>(1)
const condition = ref<Condition>({
	delete: false,
	status: true,
	pageSize: 5,
	pageNum: 1,
	orderBy: 'create_time desc, update_time desc'
})
const homeBottomScroll = ref<Element>()
const pageTotal = ref<number>(0)
const articleStore = useArticleStore()

const setImgDom = () => {
	setTimeout(() => {
		let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
		new Promise((resolve,rejcet) => {
			for (let i = 0; i < contentHtmlImg.length; i++) {
				contentHtmlImg[i].setAttribute("src","")
			}
			resolve(null)
		}).then(() => {
			let contentHtmlImgs = document.querySelectorAll(".home-page-tag-content img")
			for (let i = 0; i < contentHtmlImgs.length; i++) {
				let nodeParent = contentHtmlImgs[i].parentNode
				if (nodeParent) {
					nodeParent.removeChild(contentHtmlImgs[i])
				}
			}
		})
	},5)
}

const resetArticleStore = () => {
	articleStore.setArticleInfo({
		condition: props.condition,
		articleArr: articleArr.value,
		currentPage: currentPage.value,
		currentPageSize: currentSize.value,
		queryArticleDataMethod: props.queryArticleDataMethod
	})
}

const loadArticleData = async () => {
  return new Promise((resolve, reject) => {
		// if (!props.userUid) {
		// 	console.error('请传入userUid');
		// 	resolve(null)
		// 	return
		// }
		props.condition.orderBy = 'create_time desc, update_time desc'
		props.queryArticleDataMethod(props.condition).then(result => {
			if (result.data && result.data.result) {
				articleArr.value = result.data.result
				pageTotal.value = result.data.total!
				currentPage.value = result.data.pageNum!
				currentSize.value = result.data.pageSize!
				resolve(null)
				resetArticleStore()
			}else {
				articleArr.value = []
				pageTotal.value = 0
				resolve(null)
				resetArticleStore()
			}
		})
	})
}

const handlePageChangeAction = async (page: number) => {
	props.condition.pageNum = page
	await loadArticleData()
	currentPage.value = page
	smoothscroll.polyfill();
	if (homeBottomScroll.value) {
		homeBottomScroll.value.scrollIntoView({behavior: "smooth"})
	}
}

onBeforeMount(() => {
	loadArticleData()
})
</script>