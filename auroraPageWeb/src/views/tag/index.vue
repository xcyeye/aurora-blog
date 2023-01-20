<template>
	<div>
		<aurora-common :user-uid="userUid"
									 :is-show-head-line="true"
									 :is-show-top-img="true"
									 :tag-or-category="currentTagInfo">
			<template #center1>
				<aurora-center>
					<template #page-center1>
						<div class="tag tag-list" id="tag">
							<!--这里是显示标签-->
							<div class="tag-top">
								<div id="tag-select" class="tag-center-title">
									<div id="tag-select-common" class="tag-select-common"></div>
									<div class="tag-select-icomoon">
										<span :class="{tagDataControl: tagIndex === 0}" @click="showTagCloud(0)">
											<svg-icon icon="bi:text-left"/>
										</span>
										<span :class="{tagDataControl: tagIndex === 1}" @click="showTagCloud(1)">
											<svg-icon icon="bi:text-paragraph"/>
										</span>
									</div>
								</div>
								<!--<div class="tag-no-show-common">-->
								<!--	<TagItem-->
								<!--		v-for="(item,index) in tagInfoArr"-->
								<!--		@click="setIsActive($event,index,item)"-->
								<!--		:key="index" :tag="item"/>-->
								<!--</div>-->
								<TagCloud @click-cloud-tag="clickCloudTag"
													:tag-arr="tagInfoArr"/>
							</div>
							<div style="clear: both"></div>
							<div class="tag-bottom">
								<div ref="articleTopScroll" style="width: 0; height: 0"></div>
								<TagPage v-for="(item,index) in showArticleArr"
												 :article="item"
												 :is-tag="true"
												 :user-uid="userUid"
												 :key="item.uid"
								/>
							</div>
						</div>
						<div class="tag-cloud"></div>
					</template>
					<template #page-center3>
						<n-pagination v-model:page="currentPage"
													:page-size="5"
													@update:page="handleChangePage"
													:item-count="articleArr.length" />
					</template>
				</aurora-center>
			</template>
			<template #center2>
				<blog-comment
					:user-uid="userUid"
					:query-regexp="`^/tag/${userUid}`"
					:page-uid="userUid" reply-page-type="OTHER"
					:show-comment-but="true"
					:page-path="`/tag/${userUid}`"/>
			</template>
		</aurora-common>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref, watch} from "vue";
import {StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useRouterPush} from "@/composables";
import {articleApi, tagApi} from "@/service";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {TagVo} from "@/bean/vo/article/TagVo";
import {Condition} from "@/bean/core/bean";
import smoothscroll from 'smoothscroll-polyfill';
import {getPaginationStartAndEnd} from "@/utils/business";

defineComponent({name: 'index'});

const userUid = ref<string>('')
const router = useRouter()
const routerPush = useRouterPush()
const articleArr = ref<Array<ArticleVo>>([])
const showArticleArr = ref<Array<ArticleVo>>([])
const tagInfoArr = ref<Array<TagVo>>([])
const tagName = ref<string>('')
const loadArticleTagArr = ref<Array<TagVo>>([])
const currentTagInfo = ref<TagVo>({})
const showAllUserData = ref(false)
const tagIndex = ref(0)
const currentPage = ref(1)
const pageSize = ref(5)
const articleTopScroll = ref<Element>()

const setPaginationData = () => {
	if (articleArr.value.length < pageSize.value) {
		showArticleArr.value = articleArr.value
	}else {
		showArticleArr.value = articleArr.value.slice(getPaginationStartAndEnd(currentPage.value, pageSize.value, true), getPaginationStartAndEnd(currentPage.value, pageSize.value, false))
	}
	smoothscroll.polyfill();
	if (articleTopScroll.value) {
		articleTopScroll.value.scrollIntoView({behavior: "smooth"})
	}
}

const setArticleArr = (articleInfoTemp: ArticleVo) => {
	articleArr.value = []
	showArticleArr.value = []
	currentPage.value = 1
	const set: Set<ArticleVo> = new Set();
	articleInfoTemp.tagArticleList!.forEach((v, index1) => {
		const map = new Map<string, Array<ArticleVo>>(Object.entries(v))
		map.forEach((articleList, key) => {
			articleList.forEach((articleInfo, index2) => {
				set.add(articleInfo)
				if (index1 === articleInfoTemp.tagArticleList!.length - 1 && index2 === articleList.length - 1) {
					articleArr.value = Array.from(set)
					setPaginationData()
				}
			})
		})
	})
}

/**
 * 根据标签名，加载文章数据
 */
const loadArticleData = () => {
	articleArr.value = []
	if (loadArticleTagArr.value && loadArticleTagArr.value.length > 0) {
		tagInfoArr.value.forEach(v => {
			if (v.title === loadArticleTagArr.value[0].title) {
				currentTagInfo.value = v
			}
		})
		const tagTitleList: Array<string> = loadArticleTagArr.value.map(v => v.title).concat() as string[];
		const userUidTemp = showAllUserData.value ? null : userUid.value
		articleApi.queryListArticleByTagOrCategory({tagTitleList: tagTitleList, userUid: userUidTemp}).then(result => {
			if (result.data) {
				setArticleArr(result.data)
			}
		})
	}else {
		if (!tagInfoArr.value || tagInfoArr.value.length === 0) {
			articleArr.value = []
			return
		}
		
		// 查询第一次标签的文章
		currentTagInfo.value = tagInfoArr.value[0]
		articleApi.queryListArticleByTagOrCategory({tagTitleList: Array.of(tagInfoArr.value[0].title) as string[]}).then(result => {
			if (result.data) {
				setArticleArr(result.data)
			}
		})
	}
}

const loadTagInfo = () => {
	const condition: Condition = {
		delete: false,
		pageSize: 9999,
		otherUid: userUid.value,
		orderBy: 'create_time desc'
	}
	if (showAllUserData.value) {
		condition.otherUid = null
	}
  tagApi.queryListDataByCondition(condition).then(result => {
		if (result.data && result.data.result) {
			tagInfoArr.value = result.data.result
			loadArticleData()
		}
	})
}

const clickCloudTag = (tagItem: {tagItem: TagVo}) => {
	loadArticleTagArr.value = Array.of({
		title: tagItem.tagItem.title
	})
	currentTagInfo.value = tagItem.tagItem
}

const handleChangePage = (page: number) => {
	currentPage.value = page
	setPaginationData()
}

onBeforeMount(() => {
	userUid.value = router.currentRoute.value.params.userUid as string
	tagName.value = router.currentRoute.value.params.tagName as string
	if (!StringUtil.haveLength(userUid.value)) {
		routerPush.routerPush({
			name: 'home'
		})
	}
	
	if (StringUtil.haveLength(tagName.value)) {
		loadArticleTagArr.value = []
		loadArticleTagArr.value = Array.of({
			title: tagName.value
		})
	}
	loadTagInfo()
})

const showTagCloud = (tagIndexTemp: number) => {
	tagIndex.value = tagIndexTemp
	showAllUserData.value = tagIndexTemp !== 0;
	loadTagInfo()
}

watch(() => loadArticleTagArr.value, () => loadArticleData())
</script>

<style scoped lang="css">

</style>