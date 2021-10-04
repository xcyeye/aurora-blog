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
                  <span :class="{tagCloudControl: tagIndex === 0}" @click="showTagCloud(event,0)" style="--homeIcoCode: '\e92f'" class="home-menu-ico"></span>
                  <span :class="{tagListControl: tagIndex === 1}" @click="showTagCloud(event,1)" style="--homeIcoCode: '\e9bd'" class="home-menu-ico"></span>
                </div>
              </div>

              <!--这里显示的是标签-->
              <div class="tag-no-show-common" :class="{tagListActive: tagIndex === 1}">
                <TagItem
                    :class="isCategoriesActive === index ? 'active' : ''"
                    v-for="(item,index) in $store.state.categories"
                    @click="setIsCategoriesActive($event,index)"
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
                    @click="setIsActive($event,index)"
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
              <TagPage v-for="(item,index) in allPageMap"
                       :page-map="item"
                       :key="index"
                       :style="$store.state.opacityStyle"
                       :theme-property="themeProperty"
              />
            </div>
          </div>
          <div class="tag-cloud">

          </div>
        </template>
      </BCenter>
    </template>
  </Common>
</template>

<script>
import TagCloud from "./child/tag/TagCloud";
import TagItem from "./child/tag/TagItem";
import TagPage from "./child/tag/TagPage";
import $ from "jquery";
import myData from '@temp/my-data'

export default {
  name: "Tag",
  components: {
    TagItem,
    TagPage,
    TagCloud
  },
  data() {
    return {
      //tagArr存放站点所有的标签
      tagArr: [],
      allPageMap: [],
      isActive: -1,
      isCategoriesActive: -1,
      tag: '',
      themeProperty: null,
      tagIndex: 0
    }
  },
  created() {
    // 从其他页面进入此tag页面，查看是否携带分类参数
    console.log("已进入tag")
    console.log(this.$route.query);
    let tag = this.$route.query.tag;
    if (tag !== undefined || tag !== null) {
      this.tag = tag
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
      })
    }

    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
      resolve()
    })
  },
  mounted() {
    this.autoScroll()
    setTimeout(() => {
      this.tagArr = this.$store.state.tagArr
      //this.allPageMap = this.$store.state.allPageMap
    },100)


  },
  beforeMount() {
    let content = $("#content")
    if (content.length > 1) {
      $(content[0]).css("display",'none')
    }
  },
  methods: {
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
      })

      this.autoScroll()
    },
    showTagCloud(e,index) {
      this.tagIndex = index
    },
    setIsActive(e,index) {
      this.isActive = index
      //当前鼠标点击的标签
      let splitTag = e.target.innerText
      this.tag = splitTag.split(this.themeProperty.split)[0]

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
      })
      this.autoScroll()
    },
    setIsCategoriesActive(e,index) {
      this.isCategoriesActive = index
      //当前鼠标点击的标签
      let splitTag = e.target.innerText
      this.tag = splitTag.split(this.themeProperty.split)[0]

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
      })
      this.autoScroll()
    }
  }
}
</script>

<style scoped>
  .active {
    font-size: 21px;
  }
</style>