<template>
  <div :style="setBackgroundStyle(888)" id="tag-page" class="tag-page">
    <div class="tag-page-item">
      <div id="tag-page-left" :style="setBackgroundImg" class="tag-page-left tag-page-item-common"></div>
      <div class="tag-page-right tag-page-item-common" id="tag-page-right">
        <div id="tag-page-right-top" class="tag-page-right-top">
          <a href="javasrcipt: ;" style="color: #2c3e50;text-decoration: none;"
             @click="goPage"><span>{{getTitle(pageMap.title)}}</span>
          </a>
        </div>
        <div id="tag-page-right-center" class="tag-page-right-center">
          <span class="tag-page-content">{{getContent}}</span>
        </div>
        <div id="tag-page-right-bottom" class="tag-page-right-bottom">
          <span class="tag-label"></span><span :data="allCategories.length" :style="setBackgroundStyle(888)" v-for="(item,index) in allCategories">{{item}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {useSiteLocaleData} from "@vuepress/client";
export default {
  name: "TagPage",
  props: {
    pageMap: null,
    themeProperty: null
  },
  created() {
  },
  computed: {
    setBackgroundImg() {
      let random = (+new Date()) / this.getRandomInt(0,10000)
      return "background-image: url(https://api.iro.tw/webp_pc.php?time=" + random + ");"
    },
    setBackgroundStyle() {
      return (index) => {
        let color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        let hexToRgb = this.hexToRgb(color);
        let style = "background-color: rgba(" + hexToRgb.r + "," +
            hexToRgb.g + "," + hexToRgb.b + "," +
        (this.$store.state.varOpacity * 1.2) + ");"
        return style
      }
    },
    getTag() {
      let tag = ""
      for (let i = 0; i < this.pageMap.tag.length; i++) {
        //tag = tag + ""
      }
    },
    getTitle() {
      return (title) => {
        if (title === "") {
          return this.themeProperty.tagNoTitle
        }
        return title
      }
    },
    getHref() {
      /*let articleUrl = this.pageMap.articleUrl
      let base = useSiteLocaleData().value.base;
      console.log(base)
      base = base === '/' ? "" : base
      return  window.location.origin + base + articleUrl;*/
      return this.pageMap.articleUrl
    },
    getContent() {
      let content = this.pageMap.content
      content = content.replace('#',"")
      content = content.replace('##',"")
      content = content.replace('###',"")
      return content
    },
    allCategories() {
      return  this.pageMap.tag.concat(this.pageMap.categories)
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    goPage() {
      this.$router.push(this.pageMap.articleUrl)
    },
    hexToRgb(hex) {
      var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    }
  }
}
</script>

<style scoped>

</style>