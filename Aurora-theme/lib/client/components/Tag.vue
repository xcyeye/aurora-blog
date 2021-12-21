<template>
  <Common :is-sticky-sidebar="true" :is-show-top-img="true"
          :show-tag-cloud="false" :is-show-side-bar="false">
    <template #center1>
      <BCenter>
        <template #page-center1>
          <div class="tag tag-list" id="tag">
            <div class="tag-top">
              <div id="tag-select" class="tag-center-title">
                <div id="tag-select-common" class="tag-select-common">
                  <h2 class="tag-scroll">类别</h2>
                </div>
                <div class="tag-select-icomoon">
                  <span :class="{tagCloudControl: tagIndex === 0}" @click="showTagCloud(event,0)"
                        class="aurora-iconfont-common aurora-tag-left"></span>
                  <span :class="{tagListControl: tagIndex === 1}" @click="showTagCloud(event,1)"
                        class="aurora-iconfont-common aurora-tag-right"></span>
                </div>
              </div>

              <!--这里显示的是类别-->
              <div class="tag-no-show-common" :class="{tagListActive: tagIndex === 1}">
                <TagItem
                    :class="isCategoriesActive === index ? 'active' : ''"
                    v-for="(item,index) in $store.state.categories"
                    @click="setIsCategoriesActive($event,index,item)"
                    :theme-property="themeProperty"
                    :is-categories="true"
                    :key="index" :tag="item"/>
              </div>

            <!-- 显示类别云 -->
              <TagCloud class="tag-no-show-common" :class="{tagCloudActive: tagIndex === 0}"
                        @click-cloud-tag="clickCloudCategories"
                        :theme-property="themeProperty"
                        :tag-arr="$store.state.categories"/>
            </div>
            <div style="clear: both"></div>

            <!--这里是显示标签-->
            <div class="tag-top">
              <div class="tag-center-title">
                <h2>标签</h2>
              </div>
              <div class="tag-no-show-common" :class="{tagListActive: tagIndex === 1}">
                <TagItem
                    :class="isActive === index ? 'active' : ''"
                    v-for="(item,index) in tagArr"
                    @click="setIsActive($event,index,item)"
                    :theme-property="themeProperty"
                    :key="index" :tag="item"/>
              </div>

            <!-- 显示类别标签云 -->
              <TagCloud class="tag-no-show-common" :class="{tagCloudActive: tagIndex === 0}"
                        @click-cloud-tag="clickCloudTag"
                        :theme-property="themeProperty"
                        :tag-arr="tagArr"/>
            </div>
            <div style="clear: both"></div>
            <div class="tag-bottom">
              <TagPage v-for="(item,index) in showPageArr"
                       :page-map="item"
                       :key="item.articleUrl"
                       :data="item.articleUrl"
                       :theme-property="themeProperty"
              />
            </div>
          </div>
          <div class="tag-cloud"></div>
        </template>
        <template #page-center3>
          <Pagination @changePage="handleCurrentChange"
                      v-if="allPageMap.length !== 1"
                     :total="allPageMap.length"
                     :current-page="$store.state.currentTagNum"
                     :page-size="pageSize"/>
        </template>
      </BCenter>
    </template>
  </Common>
</template>

<script>
import TagCloud from "./child/tag/TagCloud.vue";
import TagItem from "./child/tag/TagItem.vue";
import TagPage from "./child/tag/TagPage.vue";
import Pagination from "./Pagination.vue";

import {useThemeData} from "../composables";

export default {
  name: "Tag",
  components: {
    TagItem,
    TagPage,
    TagCloud,
    Pagination
  },
  data() {
    return {
      //tagArr存放站点所有的标签
      tagArr: [],
      allPageMap: [],
      isActive: -1,
      isCategoriesActive: -1,
      tag: '',
      themeProperty: '',
      tagIndex: 0,
      pageSize: 5,
      showPageArr: [],
      isLoadingFinish: false
    }
  },
  created() {

    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }

    this.themeProperty = useThemeData().value
    let loadAllPageMap = setInterval(() => {
      if (this.$store.state.allPageMap.length !== 0) {
        clearInterval(loadAllPageMap)
        this.allPageMap = Array.from(this.$store.state.allPageMap)
        let currentTagNum = this.$store.state.currentTagNum
        if (currentTagNum === 1) {
          this.handleCurrentChange(1)
        }else {
          this.handleCurrentChange(currentTagNum)
        }
      }
    },50)

    setTimeout(() => {
      clearInterval(loadAllPageMap)
    },10000)
  },
  computed: {
    getRouteQuery() {
      return this.$route.query
    }
  },
  mounted() {
    this.isLoadingFinish = true
    this.autoScroll()
    setTimeout(() => {
      this.tagArr = this.$store.state.tagArr
      //this.allPageMap = this.$store.state.allPageMap
    },100)
  },
  watch: {
    isLoadingFinish() {
      this.autoScroll()
    },
    getRouteQuery(nV,oV) {
      if (this.$route.query.tag === undefined) {
        return
      }
      this.allPageMap = []
      // 从其他页面进入此tag页面，查看是否携带分类参数
      let tag = this.$route.query.tag
      this.tag = tag
      let loadAllPageMap = setInterval(() => {
        if (this.$store.state.allPageMap.length !== 0) {
          clearInterval(loadAllPageMap)
          let allPages = this.$store.state.allPageMap
          new Promise((resolve,reject) => {
            let temPage = new Set()
            for (let i = 0; i < allPages.length; i++) {
              let tagArr = allPages[i].tag
              let categoriesArr = allPages[i].categories
              for (let j = 0; j < tagArr.length; j++) {
                let pageTag = tagArr[j]
                if (this.tag === pageTag) {
                  temPage.add(allPages[i])
                }
              }

              for (let j = 0; j < categoriesArr.length; j++) {
                let pageTag = categoriesArr[j]
                if (this.tag === pageTag) {
                  temPage.add(allPages[i])
                }
              }
            }
            resolve(temPage)
          }).then((temPage) => {
            this.allPageMap = Array.from(temPage)
            this.handleCurrentChange(1)
          })
        }
      },50)

      setTimeout(() => {
        clearInterval(loadAllPageMap)
      },3000)
    }
  },
  methods: {
    handleCurrentChange(currentPageNum) {
      this.$store.commit("setCurrentTagNum", {
        currentTagNum: currentPageNum
      })
      let currentNum = currentPageNum === "" ? 1 : currentPageNum
      let start = (currentNum -1) * this.pageSize
      let end = start + this.pageSize
      this.showPageArr = this.allPageMap.slice(start,end)
      if (this.isLoadingFinish) {
        this.autoScroll()
      }
    },
    autoScroll() {
      import("smoothscroll-polyfill").then(module => {
        module.polyfill()
      })
      let autoScroll = setInterval(() => {
        if (document.querySelectorAll(".tag-bottom .tag-page").length !== 0) {
          clearInterval(autoScroll)
          document.querySelectorAll(".tag-bottom .tag-page")[0].scrollIntoView({behavior: "smooth"})
        }
      },30)
    },
    showTagCloud(e,index) {
      this.tagIndex = index
    },
    clickCloudTag(tagItem) {
      //当前鼠标点击的标签
      this.tag = tagItem.tagItem
      let allPages = this.$store.state.allPageMap
      new Promise((resolve,reject) => {
        let temPage = []
        for (let i = 0; i < allPages.length; i++) {
          let tagArr = allPages[i].tag
          for (let j = 0; j < tagArr.length; j++) {

            let pageTag = tagArr[j]
            if (this.tag === pageTag) {
              temPage.push(allPages[i])
            }
            continue
          }
        }
        resolve(temPage)
      }).then((temPage) => {
        this.allPageMap = temPage
        this.handleCurrentChange(1)
      })

      this.autoScroll()
    },
    clickCloudCategories(categories) {
      this.tag = categories.tagItem
      let allPages = this.$store.state.allPageMap
      new Promise((resolve,reject) => {
        let temPage = []
        for (let i = 0; i < allPages.length; i++) {
          let tagArr = allPages[i].categories
          for (let j = 0; j < tagArr.length; j++) {

            let pageTag = tagArr[j]
            if (this.tag === pageTag) {
              temPage.push(allPages[i])
            }
          }
        }
        resolve(temPage)
      }).then((temPage) => {
        this.allPageMap = temPage
        this.handleCurrentChange(1)
        this.$route.query = {
          tag: categories.tagItem
        }
      })

      this.autoScroll()
    },
    setIsActive(e,index,item) {
      this.isActive = index
      //当前鼠标点击的标签
      this.tag = item
      let allPages = this.$store.state.allPageMap
      new Promise((resolve,reject) => {
        let temPage = []
        for (let i = 0; i < allPages.length; i++) {
          let tagArr = allPages[i].tag
          for (let j = 0; j < tagArr.length; j++) {
            let pageTag = tagArr[j]
            if (this.tag === pageTag) {
              temPage.push(allPages[i])
            }
            continue
          }
        }
        resolve(temPage)
      }).then((temPage) => {
        this.allPageMap = temPage
        this.handleCurrentChange(1)
      })
      this.autoScroll()
    },
    setIsCategoriesActive(e,index,item) {
      this.isCategoriesActive = index
      //当前鼠标点击的标签
      this.tag = item
      let allPages = this.$store.state.allPageMap
      new Promise((resolve,reject) => {
        let temPage = []
        for (let i = 0; i < allPages.length; i++) {
          let tagArr = allPages[i].categories
          for (let j = 0; j < tagArr.length; j++) {

            let pageTag = tagArr[j]
            if (this.tag === pageTag) {
              temPage.push(allPages[i])
            }
            continue
          }
        }
        resolve(temPage)
      }).then((temPage) => {
        this.allPageMap = temPage
        this.handleCurrentChange(1)
      })
      this.autoScroll()
    }
  }
}
</script>