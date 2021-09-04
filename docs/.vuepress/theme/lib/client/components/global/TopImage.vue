<template>
  <div v-if="isShowTopImg" :style="setBackgroundUrl" id="page-top">
    <div v-if="isShowHeadLine" style="color: #42a5f5">
      <h1>{{headLine}}</h1>

      <!--<h1>{{pageMap.title === null ? "sdf" : pageMap.title}}</h1>-->
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
                <li v-for="(item,index) in tagArr"><a href="/tag">{{item}}</a></li>
              </div>
            </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TopImage",
  data() {
    return {
      animeImg: '',
      pathname: '',
      pageMap: '',
      length: 0,
      tagArr: []
    }
  },
  props: {
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
    }
  },
  mounted() {
    this.pathname = window.location.pathname
    setTimeout(() =>{
      this.getPageMap()
    },500)
  },
  computed: {
    setBackgroundUrl() {
      return "background-image: url(" + this.$store.state.animeImg + ");"
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
      return  Math.floor(this.length / 400) === 0 ? 1 : Math.floor(this.length / 400);
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