<template>
	<div class="render-markdown-content" v-html="renderMarkdownContent"></div>
</template>

<script lang="ts" setup>
import {defineComponent, ref, watch} from "vue";
import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import {RenderRule} from "markdown-it/lib/renderer";
import smoothscroll from 'smoothscroll-polyfill';

interface Props {
	markdownContent: string,
	scrollBehavior?: boolean,
	scrollToElement?: Element
}

defineComponent({name: 'RenderMarkdown'});

const props = withDefaults(defineProps<Props>(), {
	scrollBehavior: false
})

const renderMarkdownContent = ref<string>('')

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
	// 渲染markdown内容
	const markdown = new MarkdownIt()
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
		if (/\.(png|jpg|gif|jpeg|webp)$/.test(picSrc)) {
			return `<div role="none" class="page-image-box n-image n-image--preview-disabled"><img src="${picSrc}" loading="eager" data-error="true" data-preview-src="${picSrc}" style="object-fit: fill;" data-group-id=""></div>`;
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

watch(() => props.markdownContent, (nv: string) => handleRenderMarkdownContent(nv))

</script>

<style scoped>

</style>