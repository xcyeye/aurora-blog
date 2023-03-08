<template>
	<div class="render-markdown-content" v-html="renderMarkdownContent"></div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from "vue";
import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import {RenderRule} from "markdown-it/lib/renderer";
import smoothscroll from 'smoothscroll-polyfill';
import {StringUtil} from "@/utils";
import {useSiteInfo} from "@/stores";
import blogConfig from '@/config/blogConfig.json'

interface Props {
	markdownContent: string,
	scrollBehavior?: boolean,
	scrollToElement?: Element,
	userUid: string
}

defineComponent({name: 'RenderMarkdown'});

const props = withDefaults(defineProps<Props>(), {
	scrollBehavior: false,
})

const renderMarkdownContent = ref<string>('')
const currentSiteInfo = ref<SiteSettingInfo>({})
const lazyImg = ref('')

const emits = defineEmits(['finishRenderMarkdown'])

// @ts-ignore
const wrap = (wrapped) => (...args) => {
	const [tokens, idx] = args
	const token = tokens[idx]
	const rawCode = wrapped(...args)
	return `<!--beforebegin--><div class="language-${token.info.trim()} ext-${token.info.trim()} line-numbers-mode">`
		+ `<!--afterbegin-->${rawCode}<!--beforeend--></div><!--afterend-->`
}

const handleRenderMarkdownContent = (markdownContent: string) => {
	if (!StringUtil.haveLength(markdownContent)) return
	// 渲染markdown内容
	const markdown = new MarkdownIt({html: true})
	markdown.set({
		highlight: function (str, lang) {
			if (lang && hljs.getLanguage(lang)) {
				try {
					return hljs.highlight(lang, str, true).value;
				} catch (__) {}
			}
			return hljs.highlightAuto(str).value; // 使用额外的默认转义
		}
	})
	
	// 代码块增强
	const { fence, code_block: codeBlock } = markdown.renderer.rules
	markdown.renderer.rules.fence = wrap(fence as RenderRule)
	markdown.renderer.rules.code_block = wrap(codeBlock as RenderRule)
	
	
	let defaultRender = markdown.renderer.rules.image!
	markdown.renderer.rules.image = function (tokens, idx, options, env, self) {
		let token = tokens[idx]
		let picSrcIndex = token.attrIndex('src')
		let picSrc = token.attrs![picSrcIndex][1]
		let picAltIndex = token.attrIndex('alt')
		let picAlt = token.attrs![picAltIndex]
		let pictureSplits = picSrc.split("?");
		if (/\.(png|jpg|gif|jpeg|webp)$/.test(pictureSplits[0])) {
			return `<div role="none" class="page-image-box aurora-article-img-lazy-loading">
<img data-sizes="auto" class="lazyload" src="${lazyImg.value}" data-src="${picSrc}" style="object-fit: fill;"/>
</div>`;
		}
		return defaultRender(tokens, idx, options, env, self)
	}
	renderMarkdownContent.value = markdown.render(props.markdownContent)
	
	if (props.scrollBehavior) {
		smoothscroll.polyfill();
		if (props.scrollToElement) {
			props.scrollToElement.scrollIntoView({behavior: "smooth"})
		}
	}
}

handleRenderMarkdownContent(props.markdownContent)

const handleScroll = () => {
	let clientHeight = document.documentElement.clientHeight
	let articleLazyLoadingImg = document.querySelectorAll(".aurora-article-img-lazy-loading")
	for (let i = 0; i < articleLazyLoadingImg.length; i++) {
		let distance_top = articleLazyLoadingImg[i].getBoundingClientRect().top
		if (distance_top < clientHeight) {
			//加载图片
			let elementsByTagName = articleLazyLoadingImg[i].getElementsByTagName("img");
			let dataSrc = elementsByTagName[0].getAttribute("data-src") as string;
			elementsByTagName[0].setAttribute("src",dataSrc)
		}
	}
}

const setLazyImg = () => {
	if (StringUtil.haveLength(props.userUid)) {
		currentSiteInfo.value = useSiteInfo().getSiteInfo(props.userUid)
	}
	if (StringUtil.haveLength(currentSiteInfo.value.homePageLazyLoadingImg)) {
		lazyImg.value = currentSiteInfo.value.homePageLazyLoadingImg!
	}else {
		lazyImg.value = blogConfig.defaultLazyImgSrc
	}
}

setLazyImg()

onMounted(() => {
	// window.addEventListener('scroll', handleScroll, true)
})

watch(() => props.markdownContent, (nv: string) => handleRenderMarkdownContent(nv))
watch(() => props.userUid, (nv: string) => setLazyImg())

</script>

<style scoped lang="css">
/*@import "@/styles/dyzj/dyzj-dark.css";*/
/*@import "@/styles/dyzj/darkcode.css";*/
/*@import "@/styles/dyzj/dyzj.css";*/
@import "@/styles/dyzj/dyzj-dark.css";
</style>