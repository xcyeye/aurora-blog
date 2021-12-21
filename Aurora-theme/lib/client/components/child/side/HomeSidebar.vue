<!--侧边栏组件，手机端菜单组件为MobileSidebar.vue-->
<template>
  <!--这是页面右侧的侧边栏，和默认主题的侧边栏不同-->
  <div ref="sidebar-top" class="sidebar-cqy"></div>
  <div :style="getHomeSidebarStyle" id="stickSidebar"
       :class="{'sidebar-single-enter-animate': showEnterAnimate}">
    <!---->
    <div :class="{sidebarScroll: isShowCatalog}" :style="setHomeSidebarStyle" class="home-sidebar" id="home-sidebar">

      <!--头像信息-->
      <div :id="customId" v-if="showPersonInfo" class="sidebar-single-common">
        <div class="home-sidebar-avatar">
          <img id="home-sidebar-avatar-img" :src="$withBase(getAvatar)" alt="">
        </div>
        <div class="home-sidebar-info-desc">
          <span v-if="getLogoTitle !== ''" v-html="getLogoTitle"></span>
        </div>
        <div class="home-sidebar-info-page">
          <div class="sidebar-page-common">
            <div>文章</div>
            <div>{{$store.state.pageNum}}</div>
          </div>
          <div class="sidebar-page-common">
            <div>标签</div>
            <div>{{$store.state.tagArr.length}}</div>
          </div>
          <div class="sidebar-page-common">
            <div>类别</div>
            <div>{{$store.state.categories.length}}</div>
          </div>
        </div>
        <a target="_blank" :href="getGithubUrl">
          <div class="sidebar-github">Github</div>
        </a>
        <slot name="sidebar-son1"/>
      </div>
      <slot name="sidebar1"></slot>

      <!--时间-->
      <!--<div :id="customId" v-if="getShowSidebarSocial" class="sidebar-single-common">
        <div class="sidebar-time">
          <span>12:36:57</span>
        </div>
      </div>-->

      <!--社交-->
      <div :id="customId" v-if="getShowSidebarSocial" class="sidebar-single-common">
        <div class="sidebar-social">
          <HomeSidebarSocialItem :sidebar-row-var="sidebarRowVar"
                                 :sidebar-width-var="sidebarWidthVar"
                                 :social-item="item" :data="item.aHref" :key="item.aHref" v-for="(item,index) in socialsArr"/>
          <slot name="sidebar-son2"/>
        </div>
      </div>
      <slot name="sidebar2"></slot>

      <!--侧边栏友情链接-->
      <div :id="customId" v-if="getShowSidebarLink" class="sidebar-single-common">
        <div class="sidebar-link">
          <a :href="item.url" target="_blank" :data="item.url" :key="item.url" v-for="(item,index) in friendLinks">
            <div class="sidebar-link-single">
              <div class="sidebar-link-avatar">
                <img :origin-src="item.logo" :src="showFriendAvatar ? item.logo : homePageLazyLoadingImg" alt="">
              </div>
              <div :dat="item.title" class="sidebar-link-title">
                <span>{{item.title}}</span>
              </div>
            </div>
          </a>
        </div>
      </div>

      <!--顶部导航-->
      <div :id="customId" v-if="showNavbar" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <mobile-sidebar-nav />
        <slot name="sidebar-son3"/>
      </div>
      <slot name="sidebar3"></slot>

      <!--搜索-->
      <div :id="customId" v-if="showSearch" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <SearchBox/>
        <slot name="sidebar-son4"/>
      </div>
      <slot name="sidebar4"></slot>

      <!--文章-->
      <div :id="customId" v-if="showArticle" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-page sidebar-single-common">
        <div class="change-page">
          <div v-if="isShowCatalog" class="catalog-page change-page-common">
            <span :class="{changePageActive: changePageIndex === '1'}" index="1" @click="changePage">文章目录</span>
          </div>
          <div :style="getSinglePageStyle" class="latest-page change-page-common">
            <span v-if="!isShowCatalog" class="aurora-iconfont-common aurora-sidebar-newest-page"></span>
            <span :style="setChangePageStyle" :class="{changePageActive: changePageIndex === '2'}" @click="changePage" index="2">最新文章</span>
          </div>
        </div>
        <!--文章目录-->
        <catalog :is-show-catalog="isShowCatalog" v-show="changePageIndex === '1'" />

        <!--最新文章-->
        <div v-show="changePageIndex === '2'">
          <div v-for="item in getLatestPage" :key="item.articleUrl" :data="item.articleUrl" class="sidebar-page-item sidebar-hover-bg-common">
            <div class="sidebar-page-title">
              <router-link :to="item.articleUrl">
                <span>{{item.title === "" ? getRecommendNoTitle : item.title}}</span>
              </router-link>
            </div>
            <div class="sidebar-page-time">
              <span>{{getLocalTime(item.pageCreateTime)}}</span>
            </div>
          </div>
        </div>
        <slot name="sidebar-son5"/>
      </div>
      <slot name="sidebar5"></slot>

      <!--公告-->
      <div :id="customId" v-if="getShowMessage" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <div class="sidebar-page">
          <span class="aurora-iconfont-common aurora-sidebar-message"></span>
          <span>公告</span>
        </div>
        <div class="sidebar-message">
          <li id="sidebar-message" :key="item" v-for="(item,index) in themeProperty.message" class="sidebar-hover-bg-common">
            <span v-html="item"></span>
          </li>
        </div>
        <slot name="sidebar-son6"/>
      </div>
      <slot name="sidebar6"></slot>

      <!--标签-->
      <div :id="customId" v-if="showTagCloud" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <div v-if="!isMobileSidebar" id="home-tag-sidebar" class="sidebar-page">
          <span class="aurora-iconfont-common aurora-sidebar-tag"></span>
          <span>标签</span>
        </div>

        <div v-if="isMobileSidebar" class="change-page">
          <div class="catalog-page change-page-common">
            <span class="home-menu-ico" style="--homeIcoCode: '\e7b5'"></span>
            <span>标签</span>
          </div>
          <div class="latest-page change-page-common">
            <span></span>
            <span></span>
          </div>
        </div>

        <div class="sidebar-tag-item">
          <router-link :key="index" v-for="(item,index) in getSidebarTagArr" :to="'/tag?tag=' + item">
            <div class="sidebar-tag-single">
              <span class="home-sidebar-tag-hover" :key="index" :style="setTagItemStyle(index)">{{item}}</span>
            </div>
          </router-link>
        </div>
        <slot name="sidebar-son7"/>
      </div>
      <slot name="sidebar7"></slot>

      <!--站点-->
      <div :id="customId" v-if="showSite" id="sidebar-single-common" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class=" sidebar-single-common">
        <div class="sidebar-page">
          <span class="aurora-iconfont-common aurora-sidebar-site"></span>
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
        <slot name="sidebar-son8"/>
      </div>
      <slot name="sidebar8"></slot>
    </div>
  </div>
</template>

<script>
import Catalog from "../Catalog.vue";
import MobileSidebarNav from "./MobileSidebarNav.vue";
import HomeSidebarSocialItem from "./HomeSidebarSocialItem.vue";


import {useThemeData} from "../../../composables";
export default {
  name: "HomeSidebar",
  components: {
    MobileSidebarNav,
    Catalog,
    HomeSidebarSocialItem
  },
  data() {
    return {
      showFriendAvatar: false,
      themeProperty: '',
      allSortPageArr: [],
      latestPageSize: 6,
      changePageIndex: '2',
      stickSidebar: false,
      socialsArr: [],
      friendLinks: [],
      homePageLazyLoadingImg: 'https://ooszy.cco.vin/img/blog-public/ljz.gif'
    }
  },
  props: {
    showEnterAnimate: {
      type: Boolean,
      default() {
        return true
      }
    },
    sidebarWidthVar: {
      type: Number,
      default() {
        return 0.8
      }
    },
    sidebarRowVar: {
      type: Number,
      default() {
        return 5
      }
    },
    showSidebarSocial: {
      type: Boolean,
      default() {
        return true
      }
    },
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
    showSidebarLink: {
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
        return false
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

    this.themeProperty = useThemeData().value

    if (this.themeProperty.homePageLazyLoadingImg !== undefined) {
      this.homePageLazyLoadingImg = this.themeProperty.homePageLazyLoadingImg
    }

    if (this.themeProperty.latestPageSize !== undefined) {
      this.latestPageSize = this.themeProperty.latestPageSize
    }

    if (this.themeProperty.friendLinks !== undefined && this.themeProperty.friendLinks != null) {
      let friendArrs = []
      new Promise((resolve,reject) => {
        for (let i = 0; i < this.themeProperty.friendLinks.length; i++) {
          let links = this.themeProperty.friendLinks[i].links;
          friendArrs = friendArrs.concat(links)
        }
        resolve()
      }).then(() => {
        this.shuffleArray(friendArrs).then((arr) => {
          this.friendLinks = arr
        })
      })
    }

    let socials = this.themeProperty.socials
    let setArr = new Set()
    if (socials !== undefined) {
      new Promise((resolve,reject) => {
        for (let i = 0; i < socials.length; i++) {
          if (socials[i].sidebar) {
            setArr.add(socials[i])
          }
        }
        resolve()
      }).then(() => {
        this.socialsArr = Array.from(setArr)
      })
    }
  },
  computed: {
    setHomeSidebarStyle() {
      if (this.isMobileSidebar) {
        return "border-radius: 0px;"
      }
    },
    getHomeSidebarStyle() {
      if (!this.isMobileSidebar) {
        return this.$store.state.borderRadiusStyle + this.$store.state.opacityStyle
      }
      return this.$store.state.opacityStyle
    },
    getShowSidebarLink() {
      if (!this.showSidebarLink) {
        return false
      }

      if (this.themeProperty.friendLinks === undefined) return false

      return this.themeProperty.friendLinks.length !== 0;
    },
    getShowSidebarSocial() {
      if (!this.showSidebarSocial) {
        return false
      }

      return this.socialsArr.length !== 0;

    },
    getShowMessage() {
      if (!this.showMessage) {
        return false
      }

      if (this.themeProperty.message === undefined) {
        return false
      }

      return this.themeProperty.message.length !== 0;

    },
    getSidebarTagArr() {
      let sidebarTag = this.themeProperty.sidebarTag
      if (sidebarTag === undefined || sidebarTag == null) {
        return this.$store.state.tagArr
      }

      if (sidebarTag === "categories") {
        return this.$store.state.categories
      }else {
        return this.$store.state.tagArr
      }
    },
    getRecommendNoTitle() {
      let recommendNoTitle = '`╮(￣▽￣)╭`'
      if (this.themeProperty.recommendNoTitle !== undefined && this.themeProperty.recommendNoTitle != null) {
        recommendNoTitle = this.themeProperty.recommendNoTitle
      }
      return recommendNoTitle
    },
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
      if (this.themeProperty.sidebarDesc !== undefined) {
        return this.themeProperty.sidebarDesc
      }
    },
    getAvatar() {

      let sidebarAvatar = "https://ooszy.cco.vin/img/blog-public/avatar.jpg"
      if (this.themeProperty.sidebarAvatar !== undefined) {
        sidebarAvatar = this.themeProperty.sidebarAvatar
      }else {
        if (this.themeProperty.heroImg !== undefined) {
          sidebarAvatar = this.themeProperty.heroImg
        }
      }
      return sidebarAvatar
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
        let color = ''
        if (this.themeProperty.randomColor !== undefined) {
          color = this.themeProperty.randomColor[
              this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        }else {
          color = this.$store.state.defaultRandomColors[
              this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
        }

        let fontSize = this.getRandomInt(10,35)
        return "color: " + color + "; font-size: " + fontSize + "px;";
      }
    }
  },
  methods: {
    shuffleArray(array) {
      return new Promise((resolve,reject) => {
        for (var i = array.length - 1; i > 0; i--) {
          var j = Math.floor(Math.random() * (i + 1));
          var temp = array[i];
          array[i] = array[j];
          array[j] = temp;
        }
        resolve(array)
      })
    },
    shuffle(arr){
      let _arr = arr.slice()
      for (let i = 0; i < _arr.length; i++) {
        let j = this.getRandomInt(0, i)
        let t = _arr[i]
        _arr[i] = _arr[j]
        _arr[j] = t
      }
      return _arr
    },
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
        var value1  = object1.pageCreateTime;
        var value2  = object2.pageCreateTime;
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

      if (distance_top < 210) {
        this.showFriendAvatar = true
      }
    }
  },
  mounted() {
     window.addEventListener('scroll', this.handleScroll, true)
  }

}
</script>
