<template>
  <Common :is-show-top-img="true" :is-show-side-bar="false">
    <template #center1>
      <BCenter>
        <template #page-center1>
          <div class="tag" id="tag">

            <div class="tag-top">
              <div class="tag-center-title">
                <h2>标签</h2>
              </div>
              <TagItem
                  :class="isCategoriesActive === index ? 'active' : ''"
                  v-for="(item,index) in $store.state.categories"
                  @click="setIsCategoriesActive($event,index)"
                  :theme-property="themeProperty"
                  :is-categories="true"
                  :key="index" :tag="item"/>
            </div>
            <div style="clear: both"></div>

            <div class="tag-top">
              <div class="tag-center-title">
                <h2>类别</h2>
              </div>
              <TagItem
                  :class="isActive === index ? 'active' : ''"
                  v-for="(item,index) in tagArr"
                  @click="setIsActive($event,index)"
                  :theme-property="themeProperty"
                  :key="index" :tag="item"/>
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
        </template>
      </BCenter>
    </template>
  </Common>
</template>

<script>
import TagItem from "./child/TagItem";
import TagPage from "./child/TagPage";
import $ from "jquery";
import myData from '@temp/my-data'

export default {
  name: "Tag",
  components: {
    TagItem,
    TagPage,
  },
  data() {
    return {
      //tagArr存放站点所有的标签
      tagArr: [],
      allPageMap: [],
      isActive: -1,
      isCategoriesActive: -1,
      tag: '',
      themeProperty: null
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
  },
  mounted() {
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
    },
    setIsCategoriesActive(e,index) {
      this.isCategoriesActive = index
      //当前鼠标点击的标签
      let splitTag = e.target.innerText
      this.tag = splitTag.split(this.themeProperty.split)[0]
      console.log("--------this.tag----------")
      console.log(this.tag)

      let allPages = this.$store.state.allPageMap
      new Promise((resolve,reject) => {
        let temPage = []
        for (let i = 0; i < allPages.length; i++) {
          let tagArr = allPages[i].categories
          for (let j = 0; j < tagArr.length; j++) {

            let pageTag = tagArr[j]
            if (this.tag === pageTag) {
              temPage.push(allPages[i])
              console.log(temPage)
            }
            continue
          }
        }
        resolve(temPage)
      }).then((temPage) => {
        this.allPageMap = temPage
        console.log(this.allPageMap)
      })
    }
  }
}
</script>

<style scoped>
  .active {
    font-size: 21px;
  }
</style>