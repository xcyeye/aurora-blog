<template>
  <!-- 这是文章页面底部的下一篇文章 -->
  <div :style="getBgSrc" class="page-bottom-next">
    <div class="page-next-left page-next-item">
      <router-link :to="prePage.path === undefined ? '' : prePage.path">
        <span :data="prePage.path" class="aurora-hover-color-animate">{{prePage.title === "" ? recommendNoTitle : prePage.title}}</span>
      </router-link>
    </div>
    <div v-if="nextPage !== ''" class="page-next-right page-next-item">
      <router-link :to="nextPage.path === undefined ? '' : nextPage.path">
        <span :data="nextPage.path" class="aurora-hover-color-animate">{{nextPage.title === "" ? recommendNoTitle : nextPage.title}}</span>
      </router-link>
    </div>
  </div>
</template>

<script>
import {useThemeData, useThemeLocaleData} from "../../../composables";

export default {
  name: "PageNextItem",
  data() {
    return {
      //当前的激活的目录的数组
      currentCatalogObjectArr: null,
      prePage: '',
      nextPage: '',
      themeConfig: {},
      recommendNoTitle: "╮(￣▽￣)╭"
    }
  },
  computed: {
    getBgSrc() {
      let num1 = this.getRandomInt(-9999,999)
      let num2 = this.getRandomInt(0,300)
      let num3 = this.getRandomInt(0,30)
      let num = num2 / num3 * num1 + num2

      const themeLocale = useThemeLocaleData()

      let homePageImgApi = themeLocale.value.homePageImgApi

      if (homePageImgApi === undefined) {
        homePageImgApi = this.$store.state.defaultHomePageImgApi
      }
      let path = homePageImgApi + "?time=" + num
      return '--homePageImgApi: url('+ path +');'
    }
  },
  created() {
    this.themeConfig = useThemeData().value
    if (this.themeConfig.recommendNoTitle !== undefined) {
      this.recommendNoTitle = this.themeConfig.recommendNoTitle
    }
    let loadingCatalog = setInterval(() =>{
      if (this.$store.state.currentCatalogObjectArr.length !== 0) {
        this.currentCatalogObjectArr = this.$store.state.currentCatalogObjectArr
        clearInterval(loadingCatalog)
        this.setPageNext()
      }
    },50)
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    setPageNext() {
      if (this.currentCatalogObjectArr.length === 1) {
        //只有一个
        this.prePage = this.currentCatalogObjectArr[0]
        return
      }

      //侧边栏对象数组存在多个值 获取当前路由
      let routePath = this.$route.path
      let targetIndex = 0

      let excludePathArr = []

      if (useThemeData().value.excludePath !== undefined) {
        excludePathArr = useThemeData().value.excludePath
      }

      new Promise((resolve,reject) => {
        let currentCatalogObjectArr = this.currentCatalogObjectArr.filter(page => {
          return !excludePathArr.includes(page.path)
        })
        resolve(currentCatalogObjectArr)
      }).then((currentCatalogObjectArr) => {
        new Promise((resolve,reject) => {
          for (let i = 0; i < currentCatalogObjectArr.length; i++) {
            if (currentCatalogObjectArr[i].path === routePath) {
              targetIndex = i
              break
            }
          }
          resolve(currentCatalogObjectArr)
        }).then((currentCatalogObjectArr) => {
          let prePageObject = currentCatalogObjectArr[(targetIndex - 1) % currentCatalogObjectArr.length]
          this.prePage = prePageObject === undefined ? currentCatalogObjectArr[currentCatalogObjectArr.length -1] : prePageObject
          this.nextPage = currentCatalogObjectArr[(targetIndex + 1) % currentCatalogObjectArr.length]
        })
      })
    }
  }
}
</script>