<template>
  <div class="recommend-item" id="recommend-item">
    <div :style="setSpanStyle(index)" class="recommend-top">
      <div class="recommend-bottom">
        <div class="recommend-tag recommend-common">
          <div v-for="(tag,index) in item.tag">
            <span :style="setSpanStyle(index + 1)">{{tag}}</span>
          </div>
        </div>
        <div class="recommend-title recommend-common">
          <a :href="getHref">
            <span>{{item.title === "" ? recommendNoTitle : item.title}}</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {useSiteLocaleData} from "@vuepress/client";
export default {
  name: "RecommendItem",
  data() {
    return {
      recommendNoTitle: '`╮(￣▽￣)╭`'
    }
  },
  props: {
    item: null,
    index: null,
    themeProperty: ''
  },
  computed: {
    setSpanStyle() {

      return (index) => {
        let background_color = ''
        if (this.themeProperty.randomColor !== undefined) {
          background_color = this.themeProperty.randomColor[
              this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        }else {
          background_color = this.$store.state.defaultRandomColors[
              this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
        }
        return "background-color: "+background_color + ";"
      }
    },
    getHref() {
      let articleUrl = this.item.articleUrl
      let base = useSiteLocaleData().value.base;
      base = base === '/' ? "" : base
      return  window.location.origin + base + articleUrl;
    }
  },
  created() {
    if (this.themeProperty.recommendNoTitle !== undefined) {
      this.recommendNoTitle = this.themeProperty.recommendNoTitle
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    }
  },

}
</script>

<style scoped>

</style>