<template>
  <!-- 这是文章页面底部的下一篇文章 -->
  <div :style="getBgSrc" class="page-bottom-next">
    <div class="page-next-left page-next-item">
      <router-link :to="prePage.path === undefined ? '' : prePage.path">
        <span :data="prePage.path">{{prePage.title}}</span>
      </router-link>
    </div>
    <div v-if="nextPage !== ''" class="page-next-right page-next-item">
      <router-link :to="nextPage.path === undefined ? '' : nextPage.path">
        <span :data="nextPage.path">{{nextPage.title}}</span>
      </router-link>
    </div>
  </div>
</template>

<script>
import {useThemeLocaleData} from "../../../composables";

export default {
  name: "PageNextItem",
  data() {
    return {
      //当前的激活的目录的数组
      currentCatalogObjectArr: null,
      prePage: '',
      nextPage: ''
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
        homePageImgApi = "https://api.ixiaowai.cn/api/api.php"
      }
      let path = homePageImgApi + "?time=" + num
      return '--homePageImgApi: url('+ path +');'
    }
  },
  created() {
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

      new Promise((resolve,reject) => {
        for (let i = 0; i < this.currentCatalogObjectArr.length; i++) {
          //获取当前路由在当前侧边栏数组中的位置
          /*if (routePath.search(this.currentCatalogObjectArr[i].path) !== -1) {
            console.log(i)
            console.log(this.currentCatalogObjectArr[i].path)
            targetIndex = i
            break
          }*/
          if (this.currentCatalogObjectArr[i].path === routePath) {
            targetIndex = i
            break
          }
        }
        resolve()
      }).then(() => {
        let prePageObject = this.currentCatalogObjectArr[(targetIndex - 1) % this.currentCatalogObjectArr.length]
        this.prePage = prePageObject === undefined ? this.currentCatalogObjectArr[this.currentCatalogObjectArr.length -1] : prePageObject
        this.nextPage = this.currentCatalogObjectArr[(targetIndex + 1) % this.currentCatalogObjectArr.length]
      })
    }
  }
}
</script>