<template>
  <!--这是页面右侧的侧边栏，和默认主题的侧边栏不同-->
  <div ref="sidebar-top" class="sidebar-cqy"></div>
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" id="stickSidebar" class="sidebar-single-enter-animate" :class="{stickSidebar: stickSidebar}">
    <!---->
    <div :class="{sidebarScroll: isShowCatalog}" class="home-sidebar" id="home-sidebar">

      <!--头像信息-->
      <div :id="customId" v-if="showPersonInfo" class="sidebar-single-common">
        <div class="home-sidebar-avatar">
          <img id="home-sidebar-avatar-img" :src="getAvatar" alt="">
        </div>
        <div class="home-sidebar-info-desc">
          <span>{{getLogoTitle}}</span>
        </div>
        <div class="home-sidebar-info-page">
          <div class="sidebar-page-common">
            <div>文章</div>
            <div>{{$store.state.pageNum}}</div>
          </div>
          <div class="sidebar-page-common">
            <div>标签</div>
            <div>{{$store.state.categories.length}}</div>
          </div>
          <div class="sidebar-page-common">
            <div>类别</div>
            <div>{{$store.state.tagArr.length}}</div>
          </div>
        </div>
        <a target="_blank" :href="getGithubUrl">
          <div class="sidebar-github">Github</div>
        </a>

        <div class="home-sidebar-info-social">
          <div class="home-sidebar-social-single" v-for="item in themeProperty.socials">
            <div v-if="item.sidebar" class="home-social-item">
              <a target="_blank" :href="item.aHref" data-v-84deb35e="">
                <img :src="item.imgSrc" alt="" data-v-84deb35e="">
              </a>
            </div>
          </div>
          <!--<div class="sidebar-social-single">
            <div class="home-sidebar-social-single" v-for="item in themeProperty.socials">
              <div v-if="item.sidebar" class="home-social-item">
                <a target="_blank" :href="item.aHref" data-v-84deb35e="">
                  <img :src="item.imgSrc" alt="" data-v-84deb35e="">
                </a>
              </div>
            </div>
          </div>-->
        </div>
        <slot name="sidebar-son1"/>
      </div>
      <slot name="sidebar1"></slot>

      <!--顶部导航-->
      <div :id="customId" v-if="showNavbar" class="sidebar-single-enter-animate sidebar-single-common">
        <mobile-sidebar-nav />
        <slot name="sidebar-son2"/>
      </div>
      <slot name="sidebar2"></slot>

      <!--搜索-->
      <div :id="customId" v-if="showSearch" class="sidebar-single-enter-animate sidebar-single-common">
        <SearchBox/>
        <slot name="sidebar-son3"/>
      </div>
      <slot name="sidebar3"></slot>

      <!--文章-->
      <div :id="customId" v-if="showArticle" class="sidebar-single-enter-animate sidebar-single-common">
        <div class="change-page">
          <div v-if="isShowCatalog" class="catalog-page change-page-common">
            <span :class="{changePageActive: changePageIndex === '1'}" index="1" @click="changePage">文章目录</span>
          </div>
          <div :style="getSinglePageStyle" class="latest-page change-page-common">
            <span v-if="!isShowCatalog" class="icon-history"></span>
            <span :style="setChangePageStyle" :class="{changePageActive: changePageIndex === '2'}" @click="changePage" index="2">最新文章</span>
          </div>
        </div>
        <!--文章目录-->
        <catalog :is-show-catalog="isShowCatalog" v-show="changePageIndex === '1'" />
        <!--最新文章-->
        <div v-show="changePageIndex === '2'">
          <div v-for="item in getLatestPage" class="sidebar-page-item sidebar-hover-bg-common">
            <div class="sidebar-page-title">
              <a :href="item.articleUrl">
                <span>{{item.title === "" ? themeProperty.recommendNoTitle : item.title}}</span>
              </a>
            </div>
            <div class="sidebar-page-time">
              <span>{{getLocalTime(item.data.git.updatedTime)}}</span>
            </div>
          </div>
        </div>
        <slot name="sidebar-son4"/>
      </div>
      <slot name="sidebar4"></slot>

      <!--公告-->
      <div :id="customId" v-if="showMessage" class="sidebar-single-enter-animate sidebar-single-common">
        <div class="sidebar-page">
          <span class="home-menu-ico home-menu-message-ico" style="--homeIcoCode: '\e9a9'"></span>&nbsp;
          <span>公告</span>
        </div>
        <div class="sidebar-message">
          <li id="sidebar-message" v-for="(item,index) in themeProperty.message" class="sidebar-hover-bg-common">
            <span v-html="item"></span>
          </li>
        </div>
        <slot name="sidebar-son5"/>
      </div>
      <slot name="sidebar5"></slot>

      <!--标签-->
      <div :id="customId" v-if="showTagCloud" class="sidebar-single-enter-animate sidebar-single-common">
        <div v-if="!isMobileSidebar" id="home-tag-sidebar" class="sidebar-page">
          <span class="tag-label sidebar-tag"></span>
          <span>标签</span>
        </div>

        <div v-if="isMobileSidebar" class="change-page">
          <div class="catalog-page change-page-common">
            <span class="tag-label sidebar-tag"></span>
            <span>标签</span>
          </div>
          <div class="latest-page change-page-common">
            <span></span>
            <span></span>
          </div>
        </div>

        <div class="sidebar-tag-item">
          <router-link v-for="(item,index) in $store.state.categories" :to="'/tag?tag=' + item">
            <div class="sidebar-tag-single">
              <span class="home-sidebar-tag-hover" :key="index" :style="setTagItemStyle(index)">{{item}}</span>
            </div>
          </router-link>
        </div>
        <slot name="sidebar-son6"/>
      </div>
      <slot name="sidebar6"></slot>
      <!--站点-->
      <div :id="customId" v-if="showSite" id="sidebar-single-common" class="sidebar-single-enter-animate sidebar-single-common">
        <div class="sidebar-page">
          <span class="home-menu-ico" style="--homeIcoCode: '\e99c'"></span>&nbsp;
          <span>site</span>
        </div>

        <div class="sidebar-site">
          <div class="sidebar-site-single" v-for="item in 5">
            <div class="sidebar-site-name">
              <span>访客</span>
            </div>
            <div class="sidebar-site-desc">
              <span>1000</span>
            </div>
          </div>
        </div>
        <slot name="sidebar-son7"/>
      </div>
      <slot name="sidebar7"></slot>
    </div>
  </div>

</template>

<script>
import Catalog from "../Catalog";
import MobileSidebarNav from "./MobileSidebarNav";
import myData from '@temp/my-data'
export default {
  name: "HomeSidebar",
  components: {
    MobileSidebarNav,
    Catalog
  },
  data() {
    return {
      themeProperty: null,
      allSortPageArr: [],
      latestPageSize: 6,
      changePageIndex: '2',
      stickSidebar: false
    }
  },
  props: {
    customId: {
      type: String,
      default() {
        return ''
      }
    },
    showPersonInfo: {
      type: Boolean,
      default() {
        return true
      }
    },
    showNavbar: {
      type: Boolean,
      default() {
        return true
      }
    },
    showSearch: {
      type: Boolean,
      default() {
        return false
      }
    },
    showArticle: {
      type: Boolean,
      default() {
        return true
      }
    },
    showMessage: {
      type: Boolean,
      default() {
        return true
      }
    },
    showTagCloud: {
      type: Boolean,
      default() {
        return true
      }
    },
    showSite: {
      type: Boolean,
      default() {
        return true
      }
    },
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
    },
    isStickySidebar: {
      type: Boolean,
      default() {
        return false;
      }
    }
  },
  created() {
    if (this.isShowCatalog) {
      this.changePageIndex = '1'
    }
    const loadAllPageMap = setInterval(() => {
      if (this.$store.state.allPageMap.length !== 0) {
        clearInterval(loadAllPageMap)
        this.setShowAllPage(this.$store.state.allPageMap)
      }
    },50)
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter

          if (myData[i].frontmatter.latestPageSize !== undefined || myData[i].frontmatter.latestPageSize !== null) {
            this.latestPageSize = myData[i].frontmatter.latestPageSize
          }
        }
      }
      resolve()
    })
  },
  computed: {
    setChangePageStyle() {
      if (!this.isShowCatalog) {
        return 'cursor: auto;color: var(--fontColor);'
      }
    },
    getSinglePageStyle() {
      if (!this.isShowCatalog) {
        return 'text-align: left;padding-left: 5px;'
      }
    },
    getLogoTitle() {
      let logoTitle = this.themeProperty.logoTitle
      if (logoTitle === undefined || logoTitle === null) {
        logoTitle = "ccds"
      }
      return logoTitle
    },
    getAvatar() {
      let src = this.themeProperty.heroLogo
      if (src === undefined) {
        console.log("you need to set the logo field value,the default is: \nhttps://ooszy.cco.vin/img/blog-public/avatar.jpg")
        return "https://ooszy.cco.vin/img/blog-public/avatar.jpg"
      }else {
        return src
      }
    },
    getLatestPage() {
      return this.allSortPageArr.slice(0,this.latestPageSize)
    },
    isShow() {
      return (item) => {
      }
    },
    getGithubUrl() {
      return this.themeProperty.githubUrl === undefined || this.themeProperty.githubUrl == null ?
          "https://github.com/qsyyke" : this.themeProperty.githubUrl
    },
    setTagItemStyle() {
      return (index) => {
        let color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        let fontSize = this.getRandomInt(12,30)
        return "color: " + color + "; font-size: " + fontSize + "px;";
      }
    }
  },
  methods: {
    changePage(e) {
      this.changePageIndex = e.target.getAttribute("index")
    },
    getLocalTime(time) {
      let date = new Date(time);
      let day = date.getDate()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let hours = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
      return year + "-" + month + "-" + day + " "
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
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
    setShowAllPage(allPageMaps) {
      this.allSortPageArr = []
      let tempArr = allPageMaps.sort(this.compare("updatedTime"))
      for (let i = tempArr.length; i > 0; i--) {
        this.allSortPageArr.push(tempArr[i - 1])
      }
    },
    handleScroll() {
      let distance_top = 0
      try {
        distance_top = this.$refs["sidebar-top"].getBoundingClientRect().top
      }catch (e) {
      }

      if (this.isStickySidebar) {
        if (distance_top < 98) {
          this.stickSidebar = true
        }else {
          this.stickSidebar = false
        }
      }
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll, true)
  }

}
</script>

<style scoped>

</style>