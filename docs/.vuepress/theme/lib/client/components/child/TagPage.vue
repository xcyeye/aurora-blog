<template>
  <div :style="setBackgroundStyle(888)" id="tag-page" class="tag-page">
    <div class="tag-page-top">
      <div class="tag-page-title tag-common">
        <a style="color: #2c3e50;text-decoration: none;"
           :href="getHref"><span>{{getTitle(pageMap.title)}}</span></a>
      </div>
    </div>
    <div class="tag-page-bottom">
      <div class="tag-page-tag tag-common">
        <span :data="allCategories.length" :style="setBackgroundStyle(index)" v-for="(item,index) in allCategories">{{item}}</span>
      </div>
    </div>

    <div class="tag-page-bottom tag-page-content">
      <div class="tag-content">
        <span id="tag-content-span">{{getContent}}</span>
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
    //console.log(this.pageMap)
    //console.log(this.pageMap.contentRendered)
    //console.log(this.pageMap.content)
  },
  computed: {
    setBackgroundStyle() {
      return (index) => {
        let background_color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        return "background-color: "+ background_color + ";"
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
      let localhost = this.themeProperty.localhost
      let articleUrl = this.pageMap.articleUrl
      let base = useSiteLocaleData().value.base;
      base = base === '/' ? "" : base
      return  window.location.origin + base + articleUrl;
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
    }
  }
}
</script>

<style scoped>

</style>