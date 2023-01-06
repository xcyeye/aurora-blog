<template>
  <a id="link-a" target="_blank" :href="item.linkUrl">
    <div class="link-item" :style="setTopBg" id="link-item">
      <div class="link-top">
        <div class="link-img">
          <img id="link-img" :src="getLinkLogo(item.linkLogo)" :alt="item.linkTitle">
        </div>
      </div>
      <div class="link-bottom" :style="setLinkColor(item.linkCover)">
        <div class="link-bottom-title link-bottom-common">
          <span :key="item.linkTitle" :data="item.linkTitle">{{getLinkTitle(item.linkTitle,getRandomIntNum)}}</span>
        </div>
        <div class="link-bottom-describe link-bottom-common">
          <span id="link-bottom-describe" :data="item.linkDescription" :key="item.linkDescription">{{getLinkDescribe(item.linkDescription,getRandomIntNum)}}</span>
        </div>
      </div>
    </div>
  </a>
</template>

<script lang="ts" setup>

import {LinkVo} from "@/bean/vo/article/LinkVo";
import {computed, onBeforeMount, ref} from "vue";
import blogConfig from '@/config/blogConfig.json';
import {getRandomNum, hexToRgb, StringUtil} from "@/utils";

interface HexRgb {
	r?: number,
	g?: number,
	b?: number
}

interface Props {
	item: LinkVo
}

const props = withDefaults(defineProps<Props>(), {})

const hexRgb = ref<HexRgb>({})

const getRandomIntNum = computed(() => {
	let min = Math.ceil(0);
	let max = Math.floor(999999999);
	return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
})

const getLinkTitle = computed(() => {
	return (title: string, num: number) => {
		return title
	}
})

const getLinkDescribe = computed(() => {
	return (describe: string, num: number) => {
		return describe
	}
})

const getLinkLogo = computed(() => {
	return (logo: string) => {
		let randomNum1 = getRandomNum(0,9999999999)
		let randomNum2 = getRandomNum(0,9999999999)
		return logo + "?time=" + randomNum1 + logo + randomNum2
	}
})

const setTopBg = computed(() => {
	return 'background-color: rgba(' + hexRgb.value.r +"," + hexRgb.value.g + "," + hexRgb.value.b + ",.85);"
})

const setLinkColor = computed(() => {
	return (cover: string) => {
		//return 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + "); background-image: url("+ cover +");"
		if (!StringUtil.haveLength(cover)) {
			return "background-color: white;" + 'color: rgb(' + hexRgb.value.r +"," + hexRgb.value.g + "," + hexRgb.value.b + ");"
		}else {
			let randomNum1 = getRandomNum(0,9999999999)
			let randomNum2 = getRandomNum(0,9999999999)
			let logoPath = cover + "?time=" + randomNum1 + cover + randomNum2
			return "background-image: url("+ logoPath +");"
		}
	}
})

onBeforeMount(() => {
	let bgColor = ''
	if (blogConfig.randomColor !== undefined) {
		bgColor = blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)]
	}
	hexRgb.value = hexToRgb(bgColor)!
})
</script>