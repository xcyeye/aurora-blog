<template>
  <div class="">
    <!--一级标题-->
    <div v-for="(itemLevel1,itemLevel1Index) in getCatalogLevel1"
         id="catalog-single" :class="getCatalogOpenStatus(itemLevel1Index)"
         class="catalog-single sidebar-catalog-item">
      <!--展示一级标题-->
      <div class="page-catalog-parent">
        <div class="catalog-page-title">
          <router-link :to="itemLevel1.path">
            <span class="content-single-show">{{itemLevel1.title}}</span>
          </router-link>
        </div>
        <div @click="changeCurrentLevel1Active(event,itemLevel1Index)" class="catalog-page-spread">
          <span :class="getSpreadClass(itemLevel1Index)"></span>
        </div>
      </div>

      <!--展示二级标题-->
      <div class="page-catalog-children-parent">
        <div class="page-catalog-children">
          <div v-for="(itemLevel2,itemLevel2Index) in itemLevel1.headers"
               :class="{catalogChildrenActive: catalogChildrenActive === itemLevel2Index }"
               class="catalog-page-children-item">
            <div class="catalog-page-children-title">
              <router-link :to="itemLevel1.path + '#' + itemLevel2.title">
                <span :slug="itemLevel2.slug">{{itemLevel2.title}}</span>
              </router-link>
            </div>

            <!--展示三级标题-->
            <div class="page-catalog-children-level3-parent">
              <div :style="setLevel3Style(itemLevel2Index)" class="page-catalog-children-level3">
                <div v-for="item in itemLevel2.children"
                     class="page-catalog-children-level3-title">
                  <router-link :to="itemLevel1.path + '#' + itemLevel2.title">
                    <span :slug="item.slug">{{item.title}}</span>
                  </router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {computed} from "vue";
import myData from '@temp/my-data'
import {logger} from "html2canvas/dist/types/core/__mocks__/logger";
export default {
  name: "Catalog",
  data() {
    return {
      catalogActive: 0,
      catalogChildrenActive: -1,
      currentCatalog: [],
      pathname: '',
      clickSpreadStatus: false,
      catalogOpen: false,
      clickCatalogNum: 0,
      catalogOpenStatus: {
        openStatus: true,
        index: 0
      },
      currentCatalogObject: {},
      currentHeaderIndex: 0,
      themeProperty: null,
      sidebarCatalogLevel: 1
    }
  },
  props: {
    isMobileSidebar: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowCatalog: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  computed: {
    setLevel3Style() {
      return (level2Index) => {
        try {
          if (this.currentCatalogObject.headers[this.catalogChildrenActive] !== undefined) {
            if (this.currentCatalogObject.headers[this.catalogChildrenActive].children.length !== 0) {
              if (level2Index === this.catalogChildrenActive) {
                return 'display: block;'
              }
            }
          }
        }catch (e) {}
      }
    },
    getCatalogOpenStatus() {
      return (index) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return 'catalogActive'
          }
        }
      }
    },
    getCatalogLevel1() {
      // 获取一级标题
      for (let i = 0; i < this.currentCatalog.length; i++) {
        if (this.currentCatalog[i].path === this.$route.path) {
          this.catalogOpenStatus.index = i
          this.currentCatalogObject = this.currentCatalog[i]
        }
      }
      return this.currentCatalog
    },
    getSpreadClass() {
      return (index) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return "chevron-down"
          }else {
            return "icon-cheveron-right"
          }
        }else {
          return "icon-cheveron-right"
        }
      }
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
      resolve()
    })
    this.$router.beforeEach((to,from,next) => {
      this.pathname = to.path
      this.getCurrentCatalogArr(this.pathname)
      next()
    })
  },
  mounted() {
    setTimeout(() => {
      this.pathname = this.$route.path
      this.getCurrentCatalogArr(this.pathname)
    },400)
    window.addEventListener('scroll', this.handleScroll, true)
  },
  methods: {
    //处理滚动，标题激活状态的改变
    handleScroll() {
      if (!this.isShowCatalog) {
        return
      }

      let childrenHeaders = this.currentCatalogObject.headers

      let allHeader_anchors = document.querySelectorAll(".header-anchor")
      for (let i = 0; i < allHeader_anchors.length; i++) {
        let distance_top = allHeader_anchors[i].getBoundingClientRect().top
        if (distance_top < 45 && distance_top > 0) {

          // 当前到达顶部的标题
          let activeHeader = allHeader_anchors[i].parentElement.getAttribute("id")
          //为当前激活的a标签设置类名

          new Promise((resolve,reject) => {
            let isLevel2 = false

            try {
              let length = childrenHeaders.length
            }catch (e) {
              return
            }
            for (let j = 0; j < childrenHeaders.length; j++) {
              if (activeHeader === childrenHeaders[j].slug) {
                this.catalogChildrenActive = j
                isLevel2 = true
              }

              let level2Spans = document.querySelectorAll(".catalogActive .catalog-page-children-title span")
              let level3Spans = document.querySelectorAll(".catalogActive .page-catalog-children .catalog-page-children-item .page-catalog-children-level3-parent .page-catalog-children-level3-title span")

              new Promise((resolve,reject) => {
                let activeTemp = []
                for (let k = 0; k < level2Spans.length; k++) {
                  activeTemp.push(level2Spans[k])
                }

                for (let k = 0; k < level3Spans.length; k++) {
                  activeTemp.push(level3Spans[k])
                }
                resolve(activeTemp)
              }).then((activeTemp) => {
                for (let k = 0; k < activeTemp.length; k++) {
                  if (activeTemp[k].getAttribute("slug") === activeHeader) {
                    activeTemp[k].setAttribute("id","ccds-active")
                  }else {
                    activeTemp[k].setAttribute("id","")
                  }
                }
              })
            }
            resolve(isLevel2)
          }).then((isLevel2) => {
            if (!isLevel2) {
              new Promise((resolve,reject) => {
                let isContainLevel2 = false
                if (this.currentCatalogObject.headers[this.catalogChildrenActive] === undefined) {
                  return
                }
                for (let j = 0; j < this.currentCatalogObject.headers[this.catalogChildrenActive].children.length; j++) {
                  // 依次判断当前激活是否在当前激活的二级标题内
                  if (this.currentCatalogObject.headers[this.catalogChildrenActive].children[j].slug === activeHeader) {
                    isContainLevel2 = true
                    break
                  }
                }
                resolve(isContainLevel2)
              }).then((isContainLevel2) => {
                if (!isContainLevel2) {
                  //没在
                  if (this.catalogChildrenActive >= 0) {
                    this.catalogChildrenActive = this.catalogChildrenActive -1
                  }
                }
              })
            }
          })
        }
      }
    },
    changeCurrentLevel1Active(e,index) {
      if (this.catalogOpenStatus.openStatus) {
        //是打开状态
        if (this.catalogOpenStatus.index === index) {
          this.catalogOpenStatus.openStatus = !this.catalogOpenStatus.openStatus
        }
      }else {
        this.catalogOpenStatus.openStatus = !this.catalogOpenStatus.openStatus
      }
      this.catalogOpenStatus.index = index
      // this.catalogActive = index
    },
    getCurrentCatalogArr(path) {
      this.currentCatalog = []
      new Promise((resolve,reject) => {
        let tempCatalogArr = []
        let split = path.split("/");

        if (split.length < 3) {
          return
        }

        let sidebarCatalogLevel = this.themeProperty.sidebarCatalogLevel
        if (sidebarCatalogLevel !== undefined) {
          this.sidebarCatalogLevel = sidebarCatalogLevel

          // 如果侧边栏截取长度大于路径分割数组长度-2，则使用路径分割长度
          if (split.length -2 < sidebarCatalogLevel) {
            this.sidebarCatalogLevel = split.length -2
          }
        }

        for (let i = split.length - this.sidebarCatalogLevel -1; i > (split.length - this.sidebarCatalogLevel -2); i--) {
          tempCatalogArr.push(split[i])
        }
        resolve(tempCatalogArr)
      }).then((tempCatalogArr) => {
        let catalogSet = new Set()

        new Promise((resolve,reject) => {
          for (let i = 0; i < myData.length; i++) {
            for (let j = 0; j < tempCatalogArr.length; j++) {
              new Promise((resolve,reject) => {
                let pageDataCatalog = []
                // 获取文件的切割数组
                let pageDataPathSplit = myData[i].path.split("/")

                for (let f = (pageDataPathSplit.length - this.sidebarCatalogLevel -1); f > (pageDataPathSplit.length - this.sidebarCatalogLevel -2); f--) {

                  pageDataCatalog.push({
                    path: pageDataPathSplit[f],
                    page: myData[i]
                  })
                }
                resolve(pageDataCatalog)
              }).then((pageDataCatalog) => {
                for (let k = 0; k < pageDataCatalog.length; k++) {
                  if (pageDataCatalog[k].path === tempCatalogArr[j]) {
                    catalogSet.add(pageDataCatalog[k].page)
                  }
                }
              })
            }
          }
          resolve()
        }).then(() => {
          this.currentCatalog = Array.from(catalogSet)
          this.$emit("getCurrentCatalogObject",{
            currentCatalog: Array.from(catalogSet)
          })
        })
      })
    }
  }
}
</script>