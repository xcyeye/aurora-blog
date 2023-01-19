<template>
	<div :style="setBackgroundStyle(getRandomIntValue(1,1000))" id="tag-page" class="tag-page">
		<div class="tag-page-item">
			<div style="display: none" v-html="article.content" ref="tagContent" :class="'tagItemContent' + index"></div>
			<div id="tag-page-left" :style="setBackgroundImg" class="tag-page-left tag-page-item-common"></div>
			<div class="tag-page-right tag-page-item-common" id="tag-page-right">
				<div id="tag-page-right-top" class="tag-page-right-top">
					<router-link :to="`/article/${article.userUid}/${article.uid}`">
						<span>{{ getTitle(article.title) }}</span>
					</router-link>
				</div>
				<div id="tag-page-right-center" class="tag-page-right-center">
					<span class="tag-page-content">{{ tagContent }}</span>
				</div>
				<div id="tag-page-right-bottom" v-if="allCategories.length !== 0" class="tag-page-right-bottom">
					<span class="home-menu-ico" style="--homeIcoCode: '\e7b5';color: rgba(98, 182, 203, 0.48);"></span>&nbsp;
					<span :style="setBackgroundStyle(888)" v-for="(item,index) in allCategories">{{ item }}</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">

import {PropType} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import blogConfig from '@/config/blogConfig.json';
import {getRandomNum, StringUtil} from "@/utils";
import {useRouter} from "vue-router";
import {useSiteInfo} from "@/stores";
import {setRandomInterface} from "@/utils/business";

const router = useRouter();

export default {
	name: "TagPage",
	data() {
		return {
			tagContent: ''
		};
	},
	props: {
		article: {
			type: Object as PropType<ArticleVo>
		},
		index: '',
		userUid: {
			type: String
		},
		isTag: {
			type: Boolean
		}
	},
	computed: {
		setBackgroundImg() {
			let path = "";
			const siteInfo = useSiteInfo().getSiteInfo(this.userUid);
			if (StringUtil.haveLength(this.article.coverPictureUrl)) {
				path = this.article.coverPictureUrl;
			} else {
				path = setRandomInterface(siteInfo.randomPictureInterface);
			}
			return "background-image: url(" + path + ");";
		},
		getRandomIntValue() {
			return (min, max) => {
				min = Math.ceil(min);
				max = Math.floor(max);
				return Math.floor(Math.random() * (max - min)) + min;
			};
		},
		setBackgroundStyle() {
			return (index) => {
				let color = '';
				color = blogConfig.randomColor[
					getRandomNum(0, blogConfig.randomColor.length - 1)];
				
				let hexToRgb = this.hexToRgb(color);
				let style = "background-color: rgba(" + hexToRgb.r + "," +
					hexToRgb.g + "," + hexToRgb.b + ", .45);";
				return style;
			};
		},
		getTag() {
			let tag = "";
			for (let i = 0; i < this.pageMap.tag.length; i++) {
				//tag = tag + ""
			}
		},
		getTitle() {
			return (title) => {
				if (title === "") {
					return '去啥独立开发';
				}
				return title;
			};
		},
		getHref() {
			return this.pageMap.articleUrl;
		},
		getContent() {
			let content = this.pageMap.content;
			content = content.replace('#', "");
			content = content.replace('##', "");
			content = content.replace('###', "");
			return content;
		},
		allCategories() {
			if (this.isTag) {
				return this.article.tagNames.split(",")
			}else {
				return this.article.categoryNames.split(",");
			}
		}
	},
	mounted() {
		this.$nextTick(() => {
			new Promise((resolve, reject) => {
				let tagContent = "";
				let tagContentPsDom = this.$refs.tagContent.getElementsByTagName("p");
				for (let i = 0; i < tagContentPsDom.length; i++) {
					tagContent = tagContent + tagContentPsDom[i].innerText;
				}
				resolve(tagContent);
			}).then((tagContent) => {
				this.tagContent = tagContent;
			});
		});
	},
	methods: {
		getRandomInt(min, max) {
			min = Math.ceil(min);
			max = Math.floor(max);
			return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
		},
		goPage() {
			router.push({
				path: `/article/${this.article.userUid}/${this.article?.uid}`
			});
		},
		hexToRgb(hex) {
			var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
			return result ? {
				r: parseInt(result[1], 16),
				g: parseInt(result[2], 16),
				b: parseInt(result[3], 16)
			} : null;
		}
	},
	watch: {
		pageMap(nValue, oValue) {
			this.$nextTick(() => {
				new Promise((resolve, reject) => {
					let tagContent = "";
					let tagContentPsDom = this.$refs.tagContent.getElementsByTagName("p");
					for (let i = 0; i < tagContentPsDom.length; i++) {
						tagContent = tagContent + tagContentPsDom[i].innerText;
					}
					resolve(tagContent);
				}).then((tagContent) => {
					this.tagContent = tagContent;
				});
			});
		}
	}
};
</script>

<style scoped>

</style>