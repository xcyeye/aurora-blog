<template>
  <div class="">
    <div class="change-page">
      <div class="catalog-page change-page-common">
        <span class="aurora-iconfont-common aurora-sidebar-menu"></span>
        <span :class="{changePageActive: changePageIndex === 1}" @click="changePage(event,1)">Menu</span>
      </div>
      <div class="latest-page change-page-common">
        <span class="aurora-iconfont-common aurora-sidebar-nav"></span>
        <span :class="{changePageActive: changePageIndex === 2}" @click="changePage(event,2)">导航</span>
      </div>
			<!--TODO 重做-->
      <!--原始<div v-if="showMobileCatalogStatus && !frontmatter.home" class="latest-page change-page-common">-->
      <div v-if="showMobileCatalogStatus" class="latest-page change-page-common">
        <span class="aurora-iconfont-common aurora-sidebar-catalog"></span>
        <span :class="{changePageActive: changePageIndex === 3}" @click="changePage(event,3)">目录</span>
      </div>
    </div>

    <!--这是菜单栏-->
    <div v-show="changePageIndex === 1" class="mobile-sidebar-catalog sidebar-menu">
      <!--首页-->
      <div>
        <div class="sidebar-menu-item">
          <div class="menu-item-left">
            <span class="aurora-iconfont-common aurora-sidebar-nav-home"></span>
          </div>
          <div class="menu-item-right">
            <router-link to="/">
              <span class="">Home</span>
            </router-link>
          </div>
        </div>
      </div>

			<!--TODO 重做 下面的是注释的-->
      <!--<div v-for="(menuItem,index) in navbarLinks" :key="index">-->
      <!--  <div v-if="menuItem.link" class="sidebar-menu-item">-->
      <!--    <div class="menu-item-left">-->
      <!--      &lt;!&ndash;<span :style="setHomeNavIco(menuItem.link)" :class="setHomeClass(menuItem.link)" class="aurora-iconfont-common"></span>&ndash;&gt;-->
      <!--      <span class="aurora-nav-font aurora-font" :class="setHomeClass(menuItem)"></span>-->
      <!--    </div>-->
      <!--    <div class="menu-item-right">-->
			
      <!--      <a v-if="getIsOuterLink(menuItem.link)" target="_blank" :key="index" :href="menuItem.link">-->
      <!--        <span>{{menuItem.text}}</span>-->
      <!--      </a>-->
      <!--      <router-link v-else :to="menuItem.link">-->
      <!--        <span>{{menuItem.text}}</span>-->
      <!--      </router-link>-->
      <!--    </div>-->
      <!--  </div>-->
      <!--</div>-->
    </div>

		<!--TODO 重做 下面被我注释的-->
    <!--这是是导航栏-->
    <!--<div v-show="changePageIndex === 2" class="mobile-sidebar-catalog sidebar-nav">-->
    <!--  <div style="overflow: hidden" :key="itemLevel1Index" v-for="(itemLevel1,itemLevel1Index) in navbarLinks">-->
    <!--    &lt;!&ndash;这是顶部导航的文章目录 一级标题&ndash;&gt;-->
    <!--    <div v-if="itemLevel1.children"-->
    <!--         id="catalog-single" :class="getCatalogOpenStatus(itemLevel1Index)"-->
    <!--         class="catalog-single sidebar-catalog-item">-->
    <!--      &lt;!&ndash;展示一级标题&ndash;&gt;-->
    <!--      <div v-if="itemLevel1.children" class="page-catalog-parent">-->
    <!--        <div class="catalog-page-title">-->
    <!--          <span class="content-single-show">{{itemLevel1.text}}</span>-->
    <!--          &lt;!&ndash;<span>-->
    <!--            <span>&nbsp;-->
    <!--              <svg class="icon outbound" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" x="0px" y="0px" viewBox="0 0 100 100" width="15" height="15">-->
    <!--                <path fill="currentColor" d="M18.8,85.1h56l0,0c2.2,0,4-1.8,4-4v-32h-8v28h-48v-48h28v-8h-32l0,0c-2.2,0-4,1.8-4,4v56C14.8,83.3,16.6,85.1,18.8,85.1z"></path>-->
    <!--                <polygon fill="currentColor" points="45.7,48.7 51.3,54.3 77.2,28.5 77.2,37.2 85.2,37.2 85.2,14.9 62.8,14.9 62.8,22.9 71.5,22.9"></polygon>-->
    <!--              </svg>-->
    <!--              <span class="sr-only">open in new window</span>-->
    <!--            </span></span>&ndash;&gt;-->
    <!--          &lt;!&ndash;<span v-if="!itemLevel1.link">{{itemLevel1.text}}</span>&ndash;&gt;-->
    <!--        </div>-->
    <!--        <div v-if="itemLevel1.children" @click="changeCurrentLevel1Active(event,itemLevel1Index)" class="catalog-page-spread">-->
    <!--          <span class="home-menu-ico" :style="getSpreadClass(itemLevel1Index)"></span>-->
    <!--        </div>-->
    <!--      </div>-->
		
    <!--      &lt;!&ndash;展示二级标题&ndash;&gt;-->
    <!--      <div v-if="itemLevel1.children" class="page-catalog-children-parent">-->
    <!--        <div class="page-catalog-children">-->
    <!--          <div :key="itemLevel2Index" v-for="(itemLevel2,itemLevel2Index) in itemLevel1.children"-->
    <!--               :class="{catalogChildrenActive: catalogChildrenActive === itemLevel2Index }"-->
    <!--               class="catalog-page-children-item">-->
    <!--            <div class="catalog-page-children-title">-->
    <!--              <router-link v-if="itemLevel2.link" :to="itemLevel2.link">-->
    <!--                <span>{{itemLevel2.text}}</span>-->
    <!--              </router-link>-->
    <!--              <span v-if="!itemLevel2.link">{{itemLevel2.text}}</span>-->
    <!--            </div>-->
		
    <!--            &lt;!&ndash;展示三级标题&ndash;&gt;-->
    <!--            <div class="page-catalog-children-level3-parent">-->
    <!--              <div :style="setLevel3Style(itemLevel1Index)" class="page-catalog-children-level3">-->
    <!--                <div :key="itemLevel3Index" v-for="(item,itemLevel3Index) in itemLevel2.children"-->
    <!--                     class="page-catalog-children-level3-title">-->
    <!--                  <span v-if="!getIsOuterLink(item.link)">-->
    <!--                    <router-link v-if="item.link" :da="item.link" :data="getIsOuterLink(item.link)" :to="item.link">-->
    <!--                      <span>{{item.text}}</span>-->
    <!--                    </router-link>-->
    <!--                  </span>-->
    <!--                  <span v-if="getIsOuterLink(item.link)">-->
    <!--                    <a target="_blank" v-if="item.link" :da="item.link" :data="getIsOuterLink(item.link)" :href="item.link">-->
    <!--                      <span>{{item.text}}</span>-->
    <!--                    </a>-->
    <!--                  </span>-->
    <!--                  <span class="content-single-show" v-if="!item.link">{{item.text}}</span>-->
    <!--                </div>-->
    <!--              </div>-->
    <!--            </div>-->
    <!--          </div>-->
    <!--        </div>-->
    <!--      </div>-->
    <!--    </div>-->
    <!--  </div>-->
    <!--</div>-->

		<!--TODO 重做 被我注释-->
    <!-- 这是是目录 -->
    <!--<catalog @get-current-catalog-object="getCurrentCatalogObject"-->
    <!--         class="mobile-sidebar-catalog" :is-show-catalog="true"-->
    <!--         v-show="changePageIndex === 3" />-->

  </div>
</template>

<script lang="ts">
import {getRandomNum} from "@/utils";

export default {
  name: "MobileSidebarNav",
  data() {
    return {
      catalogActive: 0,
      catalogChildrenActive: -1,
      clickSpreadStatus: false,
      catalogOpen: false,
      clickCatalogNum: 0,
      catalogOpenStatus: {
        openStatus: true,
        index: -1
      },
      changePageIndex: 1,
      menuArr: [],
      navArr: [],
      showMobileCatalogStatus: false
    }
  },
  props: {
    isShowCatalog: {
      type: Boolean,
      default() {
        return false
      }
    },
  },
  computed: {
    getIsOuterLink() {
      return (item: string) => {
        let reg=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
        return reg.test(item)
      }
    },
		// TODO 需要重做
    setHomeNavIco() {
      return (href: string) => {
        if ("/link".search(href) !== -1) {
          return ""
        }
        if ("/tag".search(href) !== -1) {
          return ""
        }
        if ("/mood".search(href) !== -1) {
          return ""
        }
        if ("/photo".search(href) !== -1) {
          return ""
        }
        if ("/about".search(href) !== -1) {
          return ""
        }
        if (href !== undefined && href.search("github") !== -1) {
          return ""
        }

        // 如果没有，那么使用默认的
        let icoArr = ['e62e','e848','e9f7','e8e4','e701','e7ad','e635','e60a','e618','e65c','e675','e615','e616','e617','e684','e7b9','e93d','e60c','e66f']
        return "--aurora-sidebar-nav-other-ico: '\\" + icoArr[getRandomNum(0,icoArr.length)] + "'"
      }
    },
		// TODO 需要重做
    setHomeClass() {
      return (item) => {
        // if (item.iconClass !== undefined && item.iconClass !== "") {
        //   return item.iconClass
        // }else {
        //   if (item.link !== undefined && item.link.search("github") !== -1) {
        //     if (useThemeData().value.repoIconClass !== undefined) {
        //       return useThemeData().value.repoIconClass
        //     }else {
        //       return "aurora-sidebar-nav-github"
        //     }
        //   }
        // }
				return "aurora-sidebar-nav-github"
      }
    },
		// TODO 重做
    getMenuIco() {
      return (href: string) => {
        if ("/aurora".search(href) !== -1) {
          return 'icon-home'
        }
        if ("/link".search(href) !== -1) {
          return 'icon-link2'
        }
        if ("/tag".search(href) !== -1) {
          return 'tag-label'
        }
        if ("/mood".search(href) !== -1) {
          return 'icon-bubbles4'
        }
        if ("/photo".search(href) !== -1) {
          return 'icon-camera1'
        }
        if ("/about".search(href) !== -1) {
          return 'icon-pacman'
        }

        if (href !== undefined && href.search("github") !== -1) {
          return 'icon-github1'
        }

        //如果都不是上面几个，那么就随机获取一些



      }
    },
    setChangePageStyle() {
      return 'cursor: auto;color: var(--fontColor);'
    },
    getSinglePageStyle() {
      return 'text-align: left;padding-left: 5px;'
    },
    isBlank() {
      return (href: string) => {
        if (href.search("http") !== -1) {
          return '_blank'
        }else {
          return ''
        }
      }
    },
    isOuterHref() {
      return (href: string) => {
        if (href.search("http") !== -1) {
          return true
        }else {
          return false
        }
      }
    },
    setLevel3Style() {
      return (index: number) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return "display: block;"
          }
        }
      }
    },
    getCatalogOpenStatus() {
      return (index: number) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return 'catalogActive'
          }
        }
      }
    },
    getSpreadClass() {
      return (index: number) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return "--homeIcoCode: '\\e631'"
          }else {
            return "--homeIcoCode: '\\e630'"
          }
        }else {
          return "--homeIcoCode: '\\e630'"
        }
      }
    }
  },
  methods: {
    getCurrentCatalogObject(currentCatalog) {
      if (currentCatalog.currentCatalog.length !== 0) {
        this.showMobileCatalogStatus = true
      }
    },
    changePage(e,index) {
      this.changePageIndex = index
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
  }
}
</script>

<style scoped>

</style>