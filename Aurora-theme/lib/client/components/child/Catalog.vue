<template>
  <div class="">
    <!--一级标题-->
    <div :date="getCatalogLevel1" :key="itemLevel1Index" :data="getCatalogLevel1" v-for="(itemLevel1,itemLevel1Index) in currentCatalog"
         id="catalog-single" :class="getCatalogOpenStatus(itemLevel1Index)"
         class="catalog-single sidebar-catalog-item">
      <!--展示一级标题-->
      <div class="page-catalog-parent">
        <div class="catalog-page-title">
          <router-link :to="itemLevel1.path">
            <span @click="changeCurrentLevel1Active(event,itemLevel1Index,true)" class="content-single-show">{{getCatalogLevel1Title(itemLevel1)}}</span>
          </router-link>
        </div>
        <div @click="changeCurrentLevel1Active(event,itemLevel1Index,false)" class="catalog-page-spread">
          <span :class="getSpreadClass(itemLevel1Index)" class="aurora-iconfont-common"></span>
        </div>
      </div>

      <!--展示二级标题-->
      <div class="page-catalog-children-parent">
        <div class="page-catalog-children">
          <div :data="itemLevel2.slug" :key="itemLevel2.slug" v-for="(itemLevel2,itemLevel2Index) in itemLevel1.headers"
               :class="{catalogChildrenActive: catalogChildrenActive === itemLevel2Index }"
               class="catalog-page-children-item">
            <div class="catalog-page-children-title">
                <span class="catalog-title" @click="clickCatalogTitle($event,itemLevel2.slug,itemLevel1.path,itemLevel1Index)" :slug="itemLevel2.slug">{{itemLevel2.title}}</span>
            </div>

            <!--展示三级标题-->
            <div class="page-catalog-children-level3-parent">
              <div :style="setLevel3Style(itemLevel2Index)" class="page-catalog-children-level3">
                <div :key="index" :data="item.slug" v-for="(item,index) in itemLevel2.children"
                     class="page-catalog-children-level3-title">
                  <span class="catalog-title" @click="clickCatalogTitle($event,item.slug,itemLevel1.path,itemLevel1Index)" :slug="item.slug">{{item.title}}</span>
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
import myData from '@temp/my-data'
import {useThemeData} from "../../composables";
export default {
  name: "Catalog",
  data() {
    return {
      catalogActive: 0,
      catalogChildrenActive: -1,
      currentCatalog: [],
      showCurrentCatalog: [],
      pathname: '',
      clickSpreadStatus: false,
      catalogOpen: false,
      clickCatalogNum: 0,
      catalogOpenStatus: {
        openStatus: true,
        index: 0,
        currentRouterIndex: 0
      },
      currentCatalogObject: {},
      currentHeaderIndex: 0,
      themeProperty: '',
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
    getCatalogPath() {
      return (path,title) => {
        /*let pathSubstr = path.substr(path.length -1,1);
        if (pathSubstr === "/") {
          path = path.substr(0,path.length -1)
        }

        return path + "#" + title*/

        return this.$route.path + "#" + title
      }
    },
    getCatalogLevel1Title() {
      return (itemLevel1) => {
        let title = itemLevel1.title
        if (title === "") {
          if (this.themeProperty.tagNoTitle !== undefined) {
            title = this.themeProperty.tagNoTitle
          }else {
            title = "暂时还没有标题"
          }
        }
        return title
      }
    },
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
          this.catalogOpenStatus.currentRouterIndex = i
          this.currentCatalogObject = this.currentCatalog[i]
          this.showCurrentCatalog = this.currentCatalog[i]
        }
      }
      // return this.currentCatalog
    },
    getSpreadClass() {
      return (index) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return "aurora-sidebar-catalog-spread-open"
          }else {
            return "aurora-sidebar-catalog-spread-off"
          }
        }else {
          return "aurora-sidebar-catalog-spread-off"
        }
      }
    },
    getCurrentPath() {
      return this.$route.path
    }
  },
  created() {

    this.themeProperty = useThemeData().value
    this.getCurrentCatalogArr(this.$route.path)
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll, true)
  },
  methods: {
    clickCatalogTitle(e,title,path,itemLevel1Index) {
      this.$route.hash = ""

      if (itemLevel1Index !== this.catalogOpenStatus.currentRouterIndex) {
        this.$router.push(path)
      }else {
        this.$router.push(`#${title}`)
      }

      //如果手机端侧边栏打开的，那么就关闭
      if (this.$store.state.openMobileSidebar) {
        this.$store.commit("setOpenMobileSidebar",{
          openMobileSidebar: false
        })
      }
    },

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
                    activeTemp[k].setAttribute("id","aurora-active")
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
    changeCurrentLevel1Active(e,index,clickRouter) {
      if (clickRouter) {
        this.catalogOpenStatus.currentRouterIndex = index
      }
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
      let needSplitArr = []
      let length = 0
      //判断是否是友情链接，关于，心情，标签，相册页面
      if ((path.search("/link") !== -1) || (path.search("/mood") !== -1) || (path.search("/about") !== -1) || (path.search("/tag") !== -1) || (path.search("/photo") !== -1)) {
        this.$emit("getCurrentCatalogObject",{
          currentCatalog: []
        })
        return
      }

      new Promise((resolve,reject) => {
        let tempCatalogArr = []
        let split = path.split("/");

        if (split.length < 3) {
          this.setRootPageMap(path)
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
        length = this.sidebarCatalogLevel
        if (this.sidebarCatalogLevel === 1) {
          length = 2
        }
        for (let i = split.length - 2; i > (split.length - this.sidebarCatalogLevel -2); i--) {
          tempCatalogArr.push(split[i])
        }
        needSplitArr = split
        resolve(tempCatalogArr)
      }).then((tempCatalogArr) => {
        let catalogSet = new Set()
        new Promise((resolve,reject) => {
          for (let i = 0; i < myData.length; i++) {
            /*if (path.search(myData[i].path) === -1) {
              continue
            }*/

            if (myData[i].path.search(needSplitArr[1]) !== 1) {
              continue
            }
            for (let j = 0; j < tempCatalogArr.length; j++) {
              new Promise((resolve,reject) => {
                let pageDataCatalog = []
                // 获取文件的切割数组
                let pageDataPathSplit = myData[i].path.split("/")
                for (let f = (pageDataPathSplit.length - 2); f > (pageDataPathSplit.length - this.sidebarCatalogLevel -2); f--) {
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
          this.$store.commit("setCurrentCatalogObjectArr",{
            currentCatalogObjectArr: this.currentCatalog
          })

          this.setCurrentCatalog(path)
        })
      })
    },
    setRootPageMap(path) {
      let catalogSet = new Set()
      new Promise((resolve,reject) => {
        for (let i = 0; i < myData.length; i++) {
          // 获取文件的切割数组
          let pageDataPathSplit = myData[i].path.split("/")

          if (pageDataPathSplit.length < 3 && myData[i].path !== "/404.html") {
            if (myData[i].path !== "/") {
              catalogSet.add(myData[i])
            }
          }
        }
        resolve()
      }).then(() => {
        this.currentCatalog = Array.from(catalogSet)
        this.$emit("getCurrentCatalogObject",{
          currentCatalog: Array.from(catalogSet)
        })
        this.$store.commit("setCurrentCatalogObjectArr",{
          currentCatalogObjectArr: this.currentCatalog
        })

        this.setCurrentCatalog(path)
      })
    },
    setCurrentCatalog(path) {
      // 获取一级标题
      for (let i = 0; i < this.currentCatalog.length; i++) {
        if (this.currentCatalog[i].path === path) {
          this.catalogOpenStatus.index = i
          this.currentCatalogObject = this.currentCatalog[i]
        }
      }
    }
  },
  watch: {
    getCurrentPath(nV,oV) {
      this.getCurrentCatalogArr(nV)
    }
  }
}
</script>