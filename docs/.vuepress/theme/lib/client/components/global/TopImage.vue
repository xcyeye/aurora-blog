<template>
  <div class="sidebar-single-enter-animate" v-if="isShowTopImg" :style="setBackgroundUrl" id="page-top">
    <div class="top-image" v-if="isShowHeadLine" style="color: #42a5f5">
      <h1>{{headLine}}</h1>
      <div class="page-record" id="page-record">
        <div class="page-record-item">
          <div class="page-record-top page-record-common">
            <div class="page-sug-time page-record-item-common">
              <span class="record-mark icon-file-word-o"></span>
              <span>总字数</span>
              <span>{{getWordLength}}</span>
            </div>

            <div class="page-sug-time page-record-item-common">
              <span class="record-mark icon-hourglass-start"></span>
              <span>时长</span>
              <span>{{getSugTime}}</span>
            </div>
          </div>
          <div id="page-record-bottom" class="page-record-bottom page-record-common">
            <div class="page-record-bottom-left">
              <div class="page-bottom-item">
                <span class="record-mark icon-comments"></span>
                <span>评论数</span>
                <span>{{$store.state.commentCount}}</span>
              </div>
              <div class="page-bottom-item">
                <span class="record-mark icon-eye2"></span>
                <span>总阅读数</span>
                <span>{{$store.state.readCount}}</span>
              </div>
            </div>
            <div class="page-record-bottom-right page-record-tag">
              <span class="record-mark icon-price-tags"></span>
              <div>
                <li v-for="(item,index) in tagArr"><router-link :to="goTag(item)">{{item}}</router-link></li>
              </div>
            </div>
        </div>
        </div>
      </div>
    </div>
    <slot name="top1"></slot>
    <slot name="top2"></slot>
    <slot name="top3"></slot>
    <slot name="top4"></slot>
    <slot name="top5"></slot>
  </div>
</template>

<script>
const network = require('../../public/js/network.js')
export default {
  name: "TopImage",
  data() {
    return {
      animeImg: '',
      pathname: '',
      pageMap: '',
      length: 0,
      tagArr: [],
      topBackgroundUrl: 'https://picture.cco.vin/pic/rmimg'
    }
  },
  props: {
    showMoodEdit: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowTopImg: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowHeadLine: {
      type: Boolean,
      default() {
        return false
      }
    },
    headLine: {
      type: String,
      default() {
        return "";
      }
    },
    themeProperty: {
      type: Object
    }
  },
  mounted() {
    this.pathname = window.location.pathname
    setTimeout(() =>{
      this.getPageMap()
    },500)
  },
  computed: {
    goTag() {
      return (item) => {
        return '/tag?tag=' + item
      }
    },
    setBackgroundUrl() {
      let path = this.$route.path
      let customTop = this.themeProperty.customTopImg
      if (customTop === undefined || customTop == null) {
        return "background-image: url(" + this.topBackgroundUrl + "?time=" + (+new Date()) + ");"
      }else {
        //用户自定义顶部图片
        let isCustomTop = customTop.custom
        if (isCustomTop) {
          let imgPath = ""
          //用户自定义 使用用户的图片
          if (path.search("link") !== -1) {
            imgPath = this.themeProperty.customTopImg.friend
          }else if (path.search("tag") !== -1) {
            imgPath = this.themeProperty.customTopImg.tag
          }
          else if (path.search("mood") !== -1) {
            imgPath = this.themeProperty.customTopImg.mood
          }else {
            imgPath = this.themeProperty.customTopImg.page
          }

          imgPath = imgPath + "?time=" + (+new Date())
          return "background-image: url(" + imgPath + ");"
        }else {
          //使用随机接口
          return "background-image: url(" + this.topBackgroundUrl + ");"
        }
      }

    },
    getWordLength() {
      let content = this.pageMap.content + ""
      if (content.length === undefined || content.length === null) {
        this.length = 0
      }else {
        this.length = content.length
      }
      return this.length
    },
    getSugTime() {
      return  Math.floor(this.length / 330) === 0 ? 1 : Math.floor(this.length / 330);
    },
    getRandom() {
      return this.getRandomInt(0,99999)
    }
  },
  methods: {
    getPageMap() {
      let allPageMap = this.$store.state.allPageMap
      //console.log(allPageMap)
      //console.log(this.pathname)
      for (let i = 0; i < allPageMap.length; i++) {
        if (this.pathname === allPageMap[i].articleUrl) {
          this.pageMap = allPageMap[i]
          //console.log(this.pageMap)
          if (this.pageMap.categories.length === 0) {
            this.tagArr = this.pageMap.tag
          }else {
            this.tagArr = this.pageMap.categories
          }
        }
      }
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
  },
  watch: {
    headLine(newValue,oldValue) {
      setTimeout(() => {
        this.pathname = window.location.pathname
        this.getPageMap()
      },500)
    }
  }
}
</script>

<style scoped>

</style>