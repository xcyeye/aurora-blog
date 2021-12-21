<template>
  <!--这是首页下面的文章模板-->
  <div :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle" ref="home-bottom" class="home-bottom" id="home-bottom">
    <div ref="home-bottom-scroll"></div>
    <div class="home-page-tag" :style="getHomePageStyle" id="home-page-tag">
      <home-page-item :index="index" :theme-property="themeProperty" :data="item.articleUrl" :key="item.articleUrl" v-for="(item,index) in showPageArr" :page-item="item"/>
      <Pagination @changePage="handleCurrentChange"
                  :total="allPageArr.length"
                  :current-page="$store.state.currentPageNum"
                  :page-size="pageSize"/>

    </div>
    <div class="home-page-fun" id="home-page-fun">
      <HomeSidebar></HomeSidebar>
    </div>
  </div>
  <slot name="home-footer"/>
</template>

<script>
import HomePageItem from "./child/home/HomePageItem.vue";
import Pagination from "./Pagination.vue";

import {useThemeData} from "../composables";
export default {
  name: "HomeBottom",
  components: {
    HomePageItem,
    Pagination
  },
  data() {
    return {
      themeProperty: '',
      allPageMaps: [],
      allPageArr: [],
      showPageArr: [],
      pageSize: 3,
      currentPage: 1,
    }
  },
  computed: {
    getHomePageStyle() {
      if (!this.themeProperty.isHomePageFollow) {
        return "--opacity: 1;"
      }
    }
  },
  created() {
    const loadAllPageMap = setInterval(() => {
      if (this.$store.state.allPageMap.length !== 0) {
        clearInterval(loadAllPageMap)
        this.setShowAllPage(this.$store.state.allPageMap)
      }
    },100)
    this.themeProperty = useThemeData().value

    if (this.themeProperty.pageSize === undefined) {
      // 默认的分页数
      this.pageSize = 3
    }else {
      this.pageSize = this.themeProperty.pageSize
    }
  },
  methods: {
    handleCurrentChange(currentPageNum) {
      this.$store.commit("setCurrentPageNum", {
        currentPageNum: currentPageNum
      })

      this.setImgDom()
      let start = (currentPageNum -1) * this.pageSize
      let end = start + this.pageSize
      this.showPageArr = this.allPageArr.slice(start, end)
      const smoothscroll = require('smoothscroll-polyfill');
      smoothscroll.polyfill();
      this.$refs["home-bottom-scroll"].scrollIntoView({behavior: "smooth"})
    },
    getLocalTime(time) {
      if (time === undefined) {
        //没有时间戳
        return ''
      }
      let date = new Date(time);
      let day = date.getDate()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let hours = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
      return year + "-" + month + "-" + day + " " + hours + ":" + min
    },
    cutPageActive(e,index) {
      this.cutPageIndex = index
    },
    compare(updatedTime) {
      return  function( object1, object2) {
        let value1  = object1.pageCreateTime;
        let value2  = object2.pageCreateTime;
        if (value2  < value1) {
          return  1;
        }  else  if (value2  > value1) {
          return  - 1;
        }  else {
          return  0;
        }
      }
    },
    setImgDom() {
      setTimeout(() => {
        let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
        new Promise((resolve,rejcet) => {
        for (let i = 0; i < contentHtmlImg.length; i++) {
          contentHtmlImg[i].setAttribute("src","")
        }
        resolve()
      }).then(() => {
        let contentHtmlImgs = document.querySelectorAll(".home-page-tag-content img")
        for (let i = 0; i < contentHtmlImgs.length; i++) {
          let nodeParent = contentHtmlImgs[i].parentNode
          nodeParent.removeChild(contentHtmlImgs[i])

        }
      })
      },5)
    },
    setShowAllPage(allPageMaps) {
      new Promise((resolve,reject) => {
        this.allPageArr = []
        //先判断是否置顶，置顶不参与时间排序
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