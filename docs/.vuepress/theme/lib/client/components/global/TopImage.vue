<template>
  <div class="sidebar-single-enter-animate page-top" v-if="isShowTopImg" :style="setBackgroundUrl" id="page-top">
    <div class="top-image" id="top-image" v-if="isShowHeadLine">
      <h1>{{headLine}}</h1>
    </div>
    <slot name="top1"></slot>
    <slot name="top2"></slot>
    <slot name="top3"></slot>
    <slot name="top4"></slot>
    <slot name="top5"></slot>
    <div class="page-record-control" v-if="isShowHeadLine">
      <div class="page-top-record" id="page-top-record">
        <div class="page-record-bot-common page-record-top">
          <div class="page-record-top-left page-record-single-common">
            <span class="home-menu-ico" style="--homeIcoCode: '\e716'"></span>&nbsp;
            <span class="page-record-single-desc">总字数</span>
            <span>{{getWordLength}}</span>
          </div>
          <div class="page-record-top-right page-record-single-common">
            <span class="home-menu-ico" style="--homeIcoCode: '\e682'"></span>&nbsp;
            <span class="page-record-single-desc">时长</span>
            <span>{{getSugTime}}</span>
          </div>
        </div>
        <div class="page-record-bot-common page-record-center">
          <div class="page-record-center-left page-record-single-common">
            <span class="home-menu-ico" style="--homeIcoCode: '\ecb1'"></span>&nbsp;
            <span class="page-record-single-desc">评论数</span>
            <span :id="pathName" class="waline-comment-count" />
            <!--<span>{{$store.state.commentCount}}</span>-->
          </div>
          <div class="page-record-center-right page-record-single-common">
            <span class="home-menu-ico" style="--homeIcoCode: '\e7b9'"></span>&nbsp;
            <span class="page-record-single-desc">总阅读数</span>
            <span :id="pathName" class="waline-visitor-count" />
            <!--<span>{{$store.state.readCount}}</span>-->
          </div>
        </div>
        <div class="page-record-bot-common page-record-bot">
          <div class="page-record-bot-tag" id="page-record-bot-tag">
            <span class="home-menu-ico" style="--homeIcoCode: '\e7b5'"></span>&nbsp;
            <router-link v-for="(item,index) in tagArr" :to="goTag(item)">
              <span class="home-page-tag-span page-record-tag-span">{{item}}</span>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {useThemeLocaleData} from "../../composables";

const network = require('../../public/js/network.js')
export default {
  name: "TopImage",
  data() {
    return {
      animeImg: '',
      pageMap: '',
      length: 0,
      tagArr: [],
      topBackgroundUrl: 'https://picture.cco.vin/pic/rmimg',
      pathName: '',
      pathname: ''
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
    },
    themeProperty: {
      type: Object
    }
  },
  created() {
    this.setPathName(this.$route.path)
    this.$router.beforeEach((to,from,next) => {
      this.setPathName(to.path)
      next()
    })
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

      let imgPath = ""
      if (customTop === undefined) {
        return this.getRandomBg()
      }

      if (customTop.custom === undefined || !customTop.custom) {
        //用户自定义
        return this.getRandomBg()
      }

      //用户自定义 使用用户的图片
      if (path.search("link") !== -1) {
        let arr = this.themeProperty.customTopImg.friend
        imgPath = this.getCustomTopImgPath(arr)
      }else if (path.search("tag") !== -1) {
        let arr = this.themeProperty.customTopImg.tag
        imgPath = this.getCustomTopImgPath(arr)
      }
      else if (path.search("mood") !== -1) {

        let arr = this.themeProperty.customTopImg.mood
        imgPath = this.getCustomTopImgPath(arr)
      }else {
        let arr = this.themeProperty.customTopImg.page
        imgPath = this.getCustomTopImgPath(arr)
      }

      return "background-image: url(" + imgPath + ");"
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
    setPathName(pathName) {
      this.pathName = pathName
    },
    getRandomBg() {
      //用户没有自定义图片，使用随机图片
      let num1 = this.getRandomInt(-9999,999)
      let num2 = this.getRandomInt(0,300)
      let num3 = this.getRandomInt(0,30)
      let num = num2 / num3 * num1 + num2

      const themeLocale = useThemeLocaleData()

      let homePageImgApi = themeLocale.value.homePageImgApi

      if (homePageImgApi === undefined) {
        homePageImgApi = "https://api.ixiaowai.cn/api/api.php"
      }

      let path = homePageImgApi + "?time=" + num
      return "background-image: url(" + path + ");"
    },
    getCustomTopImgPath(pathArr) {
      if (pathArr.length === 0) {
        try {
          let path = pathArr[0]
          return path
        }catch (e) {
          return pathArr
        }
      }
      //随机选择一个
      let path = pathArr[this.getRandomInt(0,pathArr.length - 1)]
      return path
    },
    getPageMap() {
      let allPageMap = this.$store.state.allPageMap
      for (let i = 0; i < allPageMap.length; i++) {
        if (this.pathname === allPageMap[i].articleUrl) {
          this.pageMap = allPageMap[i]
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