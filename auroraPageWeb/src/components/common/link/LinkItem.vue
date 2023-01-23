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
          <span :key="item.linkTitle">{{getLinkTitle(item.linkTitle,getRandomIntNum)}}</span>
        </div>
        <div class="link-bottom-describe link-bottom-common">
          <span id="link-bottom-describe" :key="item.linkDescription">{{getLinkDescribe(item.linkDescription,getRandomIntNum)}}</span>
        </div>
      </div>
    </div>
  </a>
</template>

<script lang="ts">

import blogConfig from '@/config/blogConfig.json'
import {getRandomNum, hexToRgb, StringUtil} from "@/utils";
import {PropType} from "vue";
import {LinkVo} from "@/bean/vo/article/LinkVo";

export default {
  name: "LinkItem",
  data() {
    return {
      hexRgb: ''
    }
  },
  props: {
    item: {
			type: Object as PropType<LinkVo>
		}
  },
  created() {
    //console.log(this.item)
    let bgColor = blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)]
    this.hexRgb = hexToRgb(bgColor)
  },
  computed: {
    getRandomIntNum() {
      let min = Math.ceil(0);
      let max = Math.floor(999999999);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    getLinkTitle() {
      return (title: string, num: number) => {
        return title
      }
    },
    getLinkDescribe() {
      return (describe: string, num: number) => {
        return describe
      }
    },
    getLinkLogo() {
      return (logo: string) => {
        let randomNum1 = getRandomNum(0,9999999999)
        let randomNum2 = getRandomNum(0,9999999999)
        let logoPath = logo + "?time=" + randomNum1 + logo + randomNum2
        return logoPath
      }
    },
    setTopBg() {
      return 'background-color: rgba(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ",.85);"
    },
    setLinkColor() {
      return (cover: string) => {
        //return 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + "); background-image: url("+ cover +");"
        if (!StringUtil.haveLength(cover)) {
					let bgColor = blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)]
					let hexRgb = hexToRgb(bgColor)
          return `background-color: white;` + 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ");"
        }else {
          let randomNum1 = getRandomNum(0,9999999999)
          let randomNum2 = getRandomNum(0,9999999999)
          let logoPath = cover + "?time=" + randomNum1 + cover + randomNum2
          return "background-image: url("+ logoPath +");"
        }
      }
    }
  }
}
</script>