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
                  <h2 class="tag-scroll">标签</h2>
                </div>
                <div class="tag-select-icomoon">
                  <span :class="{tagCloudControl: tagIndex === 0}" @click="showTagCloud(event,0)"
                        class="aurora-iconfont-common aurora-tag-left"></span>
                  <span :class="{tagListControl: tagIndex === 1}" @click="showTagCloud(event,1)"
                        class="aurora-iconfont-common aurora-tag-right"></span>
                </div>
              </div>

              <!--这里显示的是标签-->
              <div class="tag-no-show-common" :class="{tagListActive: tagIndex === 1}">
                <TagItem
                    :class="isCategoriesActive === index ? 'active' : ''"
                    v-for="(item,index) in $store.state.categories"
                    @click="setIsCategoriesActive($event,index,item)"
                    :theme-property="themeProperty"
                    :is-categories="true"
                    :key="index" :tag="item"/>
              </div>

            <!-- 显示标签云 -->
              <TagCloud class="tag-no-show-common" :class="{tagCloudActive: tagIndex === 0}"
                        @click-cloud-tag="clickCloudCategories"
                        :theme-property="themeProperty"
                        :tag-arr="$store.state.categories"/>
            </div>
            <div style="clear: both"></div>

            <!--这里是显示类别-->
            <div class="tag-top">
              <div class="tag-center-title">
                <h2>类别</h2>
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
                       :key="index"
                       :index="index"
                       :style="$store.state.opacityStyle"
                       :theme-property="themeProperty"
              />
            </div>
          </div>
          <div class="tag-cloud"></div>
        </template>
        <template #page-center2>
          <cute-page @changePage="changePage"
                     :total="allPageMap.length"
                     :page-size="pageSize"/>
        </template>
      </BCenter>
    </template>
  </Common>
</template>

<script>
import TagCloud from "./child/tag/TagCloud";
import TagItem from "./child/tag/TagItem";
import TagPage from "./child/tag/TagPage";
import CutePage from "./child/side/CutePage";
import {useThemeData} from "../composables";

export default {
  name: "Tag",
  components: {
    TagItem,
    TagPage,
    TagCloud,
    CutePage
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
      showPageArr: []
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

    // 从其他页面进入此tag页面，查看是否携带分类参数
    let tag = this.$route.query.tag

    if (tag !== undefined && tag !== null && tag !== "") {
      this.tag = tag
      setTimeout(() => {
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
          this.changePage(1)
        })
      },200)
    }
  },
  mounted() {
    this.autoScroll()
    setTimeout(() => {
      this.tagArr = this.$store.state.tagArr
      //this.allPageMap = this.$store.state.allPageMap
    },100)
  },
  methods: {
    changePage(currentPageNum) {
      let currentNum = currentPageNum === "" ? 1 : currentPageNum
      let start = (currentNum -1) * this.pageSize
      let end = start + this.pageSize
      this.showPageArr = this.allPageMap.slice(start,end)
    },
    autoScroll() {
      let autoScroll = setInterval(() => {
        if (document.querySelectorAll(".tag-bottom .tag-page").length !== 0) {
          clearInterval(autoScroll)
          document.querySelectorAll(".tag-bottom .tag-page")[0].scrollIntoView()
        }
      },30)
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
        this.changePage(1)
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
            continue
          }
        }
        resolve(temPage)
      }).then((temPage) => {
        this.allPageMap = temPage
        this.changePage(1)
      })

      this.autoScroll()
    },
    showTagCloud(e,index) {
      this.tagIndex = index
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
        this.changePage(1)
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
        this.changePage(1)
      })
      this.autoScroll()
    }
  }
}
</script>