<template>
  <div :style="setBackgroundStyle(getRandomIntValue(1,1000))" id="tag-page" class="tag-page">
    <div class="tag-page-item">
      <div style="display: none" v-html="pageMap.contentRendered" ref="tagContent" :class="'tagItemContent' + index"></div>
      <div id="tag-page-left" :style="setBackgroundImg" class="tag-page-left tag-page-item-common"></div>
      <div class="tag-page-right tag-page-item-common" id="tag-page-right">
        <div id="tag-page-right-top" class="tag-page-right-top">
          <span @click="goPage">{{getTitle(pageMap.title)}}</span>
        </div>
        <div id="tag-page-right-center" class="tag-page-right-center">
          <span class="tag-page-content">{{tagContent}}</span>
        </div>
        <div id="tag-page-right-bottom" v-if="allCategories.length !== 0" class="tag-page-right-bottom">
          <span class="home-menu-ico" style="--homeIcoCode: '\e7b5';color: rgba(98, 182, 203, 0.48);"></span>&nbsp;<span :data="allCategories.length" :style="setBackgroundStyle(888)" v-for="(item,index) in allCategories">{{item}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "TagPage",
  data() {
    return {
      tagContent: ''
    }
  },
  props: {
    pageMap: '',
    themeProperty: '',
    index: ''
  },
  computed: {
    setBackgroundImg() {
      let num1 = this.getRandomInt(-9999,999)
      let num2 = this.getRandomInt(0,300)
      let num3 = this.getRandomInt(0,30)
      let num = num2 / num3 * num1 + num2

      let homePageImgApi = this.themeProperty.homePageImgApi

      if (homePageImgApi === undefined) {
        homePageImgApi = this.$store.state.defaultHomePageImgApi
      }

      let path = homePageImgApi + "?time=" + num
      return "background-image: url(" + path + ");"
    },
    getRandomIntValue() {
      return (min, max) => {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min;
      }
    },
    setBackgroundStyle() {
      return (index) => {
        let color = ''
        if (this.themeProperty.randomColor !== undefined && this.themeProperty.randomColor != null) {
          color = this.themeProperty.randomColor[
              this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        }else {
          color = this.$store.state.defaultRandomColors[
              this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
        }

        let hexToRgb = this.hexToRgb(color);
        let style = "background-color: rgba(" + hexToRgb.r + "," +
            hexToRgb.g + "," + hexToRgb.b + ", .45);"
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
  mounted() {
    this.$nextTick(() => {
      new Promise((resolve,reject) => {
        let tagContent = ""
        let tagContentPsDom = this.$refs.tagContent.getElementsByTagName("p")
        for (let i = 0; i < tagContentPsDom.length; i++) {
          tagContent = tagContent + tagContentPsDom[i].innerText
        }
        resolve(tagContent)
      }).then((tagContent) => {
        this.tagContent = tagContent
      })
    })
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
  },
  watch: {
    pageMap(nValue,oValue) {
      this.$nextTick(() => {
        new Promise((resolve,reject) => {
         let tagContent = ""
         let tagContentPsDom = this.$refs.tagContent.getElementsByTagName("p")
         for (let i = 0; i < tagContentPsDom.length; i++) {
           tagContent = tagContent + tagContentPsDom[i].innerText
         }
         resolve(tagContent)
       }).then((tagContent) => {
         this.tagContent = tagContent
       })
      })
    }
  }
}
</script>

<style scoped>

</style>