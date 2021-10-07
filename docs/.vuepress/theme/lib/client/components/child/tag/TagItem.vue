<template>
  <div
       :class="{tagItem: isTagItem}"
       :style="setBackgroundStyle"
       class="tag-item" id="tag-item">
    <span style="color: rgba(255,255,255,.8)">{{tag}}{{itemSplit(tagPageLength)}}{{tagPageLength}}</span>
  </div>
</template>

<script>
export default {
  name: "TagItem",
  data() {
    return {
      tagPageLength: null
    }
  },
  props: {
    themeProperty: '',
    tag: '',
    padding: {
      type: Number,
      default() {
        return 10
      }
    },
    isCategories: {
      type: Boolean,
      default () {
        return false
      }
    },
    isTagItem: {
      type: Boolean,
      default() {
        return true;
      }
    }
  },
  computed: {
    setBackgroundStyle() {
      let background_color = ''
      if (this.themeProperty.randomColor !== undefined && this.themeProperty.randomColor != null) {
        background_color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
      }else {
        background_color = this.$store.state.defaultRandomColors[
            this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
      }
      return "background-color: "+ background_color + "; padding: " + this.padding +"px;"
    },
    itemSplit() {
      return (tagPageLength) => {
        if (tagPageLength === 0 || tagPageLength === undefined || tagPageLength === null) {
          return ""
        }else {
          return this.themeProperty.split
        }
      }
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
  },

  created() {
    let allPages = this.$store.state.allPageMap
    new Promise((resolve,reject) => {
      let temPage = []
      for (let i = 0; i < allPages.length; i++) {
        let tagArr = []
        if (!this.isCategories) {
          tagArr = allPages[i].tag
        }else {
          tagArr = allPages[i].categories
        }
        for (let j = 0; j < tagArr.length; j++) {

          let pageTag = tagArr[j]
          if (this.tag === pageTag) {
            temPage.push(allPages[i])
          }
          continue
        }
      }
      resolve(temPage)
    }).then((temPage) => {
      if (temPage.length >0) {
        this.tagPageLength = temPage.length
      }
    })
  },
}
</script>

<style scoped>

</style>