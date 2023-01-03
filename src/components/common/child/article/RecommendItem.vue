<template>
  <div class="recommend-item" id="recommend-item">
    <div :style="setSpanStyle(index)" class="recommend-top">
      <div class="recommend-bottom">
        <div class="aurora-recommend-bo">
          <router-link :to="getHref">
            <span class="aurora-hover-color-animate">{{item.title === "" ? recommendNoTitle : item.title}}</span>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

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
    getRecommendTag() {

      return this.item.categories
      if (this.themeProperty.sidebarTag === "categories") {
        if (this.item.categories.length === 0) {
          return this.item.tag
        }
        return this.item.categories
      }

      if (this.item.tag.length === 0) {
        return this.item.categories
      }
      return this.item.tag
    },
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
      return this.item.articleUrl
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