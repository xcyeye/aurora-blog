<template>
  <!--这是首页下面的文章模板-->
  <div :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle" ref="home-bottom" class="home-bottom" id="home-bottom">
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
											:item-count="pageTotal"
											:default-page-size="10" :default-page="1" />
			</n-space>

    </div>
    <div class="home-page-fun" id="home-page-fun">
      <HomeSidebar :user-uid="userUid"></HomeSidebar>
    </div>
  </div>
  <slot name="home-footer"/>
</template>

<script lang="ts" setup>

import {computed, onBeforeMount, ref} from "vue";
import {blogPageData} from "@/assets/config";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi} from "@/service";
import {Condition} from "@/bean/core/bean";
import smoothscroll from 'smoothscroll-polyfill'

interface Props {
	userUid: string
}

const props = withDefaults(defineProps<Props>(), {})

const articleArr = ref<Array<ArticleVo>>([])
const currentPage = ref<number>(1)
const condition = ref<Condition>({
	delete: false,
	status: true,
	pageSize: 10,
	pageNum: 1,
	orderBy: 'update_time desc'
})
const homeBottomScroll = ref<Element>()
const pageTotal = ref<number>(0)

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

const loadArticleData = async () => {
  return new Promise((resolve, reject) => {
		if (!props.userUid) {
			console.error('请传入userUid');
			resolve(null)
			return
		}
		condition.value.otherField = props.userUid
		articleApi.queryListDataByCondition(condition.value).then(result => {
			if (result.data && result.data.result) {
				articleArr.value = result.data.result
				pageTotal.value = result.data.total!
				resolve(null)
			}else {
				articleArr.value = []
				pageTotal.value = 0
				resolve(null)
			}
		})
	})
}

const handlePageChangeAction = async (page: number) => {
	condition.value.pageNum = page
	await loadArticleData()
	currentPage.value = page
	smoothscroll.polyfill();
	if (homeBottomScroll.value) {
		homeBottomScroll.value.scrollIntoView({behavior: "smooth"})
	}
}

onBeforeMount(() => loadArticleData())
</script>