<template>
	<div class="test">
		<photo-waterfall @handleClickImage="handleClickImage"
										 :picture-src-list="pictureSrcList"
										 :mobile-waterfall-img-col="2"
										 :pc-waterfall-img-col="2"
										 :show-bg-color="true"/>
		<!--<give-like icon="mdi:cards-heart"-->
		<!--					 cookie-name="article_cookie"-->
		<!--					 :multi-click-give-like="false"-->
		<!--					 like-number-field-name="likeNumber"-->
		<!--					 :give-like-info="articleInfo"-->
		<!--					 :control-like-number="true"-->
		<!--					 :update-like-num-request-method="queryDataMethod"/>-->
		<!--<talk-comment/>-->
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import GiveLike from "@/components/common/business/GiveLike.vue";
import {Condition, PageData} from "@/bean/core/bean";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {articleApi, talkApi} from "@/service";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import RequestResult = Service.RequestResult;
import TalkComment from "@/components/common/shareSpace/TalkComment.vue";

defineComponent({name: 'index'});

const articleInfo: ArticleVo = {
	uid: '1614602784120250368'
}

const handleClickImage = (photoInfo:{photoUrl: string}) => {

}

const queryDataMethod = (article: ArticleVo): Promise<RequestResult<void>> => {
	return articleApi.updateArticleLikeNum(article);
}

const pictureSrcList: Array<string> = ['http://127.0.0.1/aurora-upload/png/2023/1/illust_88479853_20220906_081459.png','http://127.0.0.1/aurora-upload/png/2023/1/illust_88380383_20220717_200036.png','http://127.0.0.1/aurora-upload/png/2023/1/illust_88911364_20220928_080456.png','http://127.0.0.1/aurora-upload/png/2023/1/illust_88927530_20220928_081118.png','http://127.0.0.1/aurora-upload/jpg/2023/1/illust_88314013_20220928_080426.jpg']
</script>

<style scoped lang="css">
.test {
	width: calc(50vw);
	height: calc(50vh);
	position: fixed;
	bottom: 0;
	background-color: red;
}
</style>