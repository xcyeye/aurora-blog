<template>
<!--  这是博客首页的下面的模板-->
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="home-bottom" id="home-bottom">
    <div class="home-page-tag" id="home-page-tag">
      <home-page-item :key="index" :show-home-page-img="showHomePageImg" v-for="(item,index) in showPageArr" :page-item="item"/>
      <!--分页条-->
      <cute-page :show-home-page-img="showHomePageImg" @changePage="changePage" :name="'chuchen'"
                 :total="allPageArr.length"
                 :page-size="pageSize"/>
    </div>
    <div class="home-page-fun" id="home-page-fun">
      <HomeSidebar></HomeSidebar>
    </div>
  </div>
  <slot name="home-footer"/>
</template>

<script>
import HomePageItem from "./child/HomePageItem";
import myData from '@temp/my-data'
import CutePage from "./child/CutePage";
export default {
  name: "HomeBottom",
  components: {
    HomePageItem,
    CutePage
  },
  data() {
    return {
      themeProperty: null,
      allPageMaps: [],
      allPageArr: [],
      showPageArr: [],
      pageSize: 3,
      currentPage: 1,
      showHomePageImg: false
    }
  },
  computed: {
    getAllPageMap() {
      return this.$store.state.allPageMap
    },
    getCutePageArr() {
      return Math.ceil(this.allPageArr.length / this.cutePageNum)
    }
  },
  created() {
    const loadAllPageMap = setInterval(() => {
      if (this.$store.state.allPageMap.length !== 0) {
        clearInterval(loadAllPageMap)
        this.setShowAllPage(this.$store.state.allPageMap)
      }
    },100)
    // this.setShowAllPage()
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
          if (myData[i].frontmatter.pageSize === undefined || myData[i].frontmatter.pageSize == null) {
            // 默认的分页数
            this.pageSize = 3
          }else {
            this.pageSize = myData[i].frontmatter.pageSize
          }

          if (myData[i].frontmatter.showHomePageImg === undefined || myData[i].frontmatter.showHomePageImg == null) {
            // 默认的分页数
            this.showHomePageImg = false
          }else {
            this.showHomePageImg = true
          }
        }
      }
      resolve()
    })
  },
  methods: {
    cutPageActive(e,index) {
      this.cutPageIndex = index
    },
    compare(updatedTime) {
      return  function( object1, object2) {
        var value1  = object1.data.git.updatedTime;
        var value2  = object2.data.git.updatedTime;
        if (value2  < value1) {
          return  1;
        }  else  if (value2  > value1) {
          return  - 1;
        }  else {
          return  0;
        }
      }
    },
    changePage(currentPage) {
      // this.currentPage = currentPage
      let start = (this.$store.state.currentPageNum -1) * this.pageSize
      let end = start + this.pageSize
      this.showPageArr = this.allPageArr.slice(start, end)
    },
    setShowAllPage(allPageMaps) {
      new Promise((resolve,reject) => {
        this.allPageArr = []
        for (let i = 0; i < allPageMaps.length; i++) {
          let isStick = allPageMaps[i].frontmatter.stick
          if (isStick) {
            this.allPageArr.push(allPageMaps[i])
          }
        }
        resolve()
      }).then(() => {
        let tempPageArr = []
        new Promise((resolve,reject) => {
          for (let i = 0; i < allPageMaps.length; i++) {
            let isStick = allPageMaps[i].frontmatter.stick
            if (!isStick) {
              // this.allPageArr.push(allPageMaps[i])
              tempPageArr.push(allPageMaps[i])
            }
            if (i === allPageMaps.length -1) {
              //全部完成
              tempPageArr.sort(this.compare("updatedTime"))
            }
          }
          resolve(tempPageArr)
        }).then((tempPageArr) => {
          new Promise((resolve,reject) => {
            for (let i = tempPageArr.length; i >0; i--) {
              this.allPageArr.push(tempPageArr[i -1])
            }
            resolve()
          }).then(() => {
            let num = (this.$store.state.currentPageNum -1) * this.pageSize
            this.showPageArr = this.allPageArr.slice(num, num + this.pageSize)
          })
        })
      })
    }
  },
}
</script>

<style scoped>

</style>