<template>
  <main  v-if="themeProperty.showRecommend === undefined ? true : themeProperty.showRecommend" :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="page box">
    <h2 id="recommend-top-title">推荐阅读</h2>
    <div id="recommend" class="recommend">
      <recommend-item
          :item="item"
          :key="index"
          :theme-property="themeProperty"
          :index="index"
          v-for="(item,index) in allPages"/>
    </div>
  </main>
</template>

<script>
import RecommendItem from "./child/RecommendItem.vue"

export default {
  name: "RecommendPage",
  components: {
    RecommendItem,
  },
  data() {
    return {
      allPages: null,
    }
  },
  props: {
    themeProperty: ''
  },
  mounted() {
    setTimeout(() => {
      let allPages = this.$store.state.allPageMap

      new Promise((resolve,reject) => {
        let allPageArr = []

        let recommendPageLength = 30
        if (this.themeProperty.recommendPageLength !== undefined && this.themeProperty.recommendPageLength != null) {
          recommendPageLength = this.themeProperty.recommendPageLength
        }
        if (recommendPageLength < allPages.length) {
          for (let i = 0; i < recommendPageLength; i++) {
            //console.log(allPages[i].articleUrl)
            allPageArr.push(allPages[this.getRandomInt(0,allPages.length -1)])
          }
        }else {
          for (let i = 0; i < allPages.length; i++) {
            //console.log(allPages[i].articleUrl)
            allPageArr.push(allPages[this.getRandomInt(0,allPages.length -1)])
          }
        }
        resolve(allPageArr)
      }).then((allPageArr) => {
        this.allPages = allPageArr
      })
    },700)
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
