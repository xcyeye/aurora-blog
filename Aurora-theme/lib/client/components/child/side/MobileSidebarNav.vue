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
      <div v-if="showMobileCatalogStatus && !frontmatter.home" class="latest-page change-page-common">
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

      <div v-for="(menuItem,index) in navbarLinks" :key="index">
        <div v-if="menuItem.link" class="sidebar-menu-item">
          <div class="menu-item-left">
            <!--<span :style="setHomeNavIco(menuItem.link)" :class="setHomeClass(menuItem.link)" class="aurora-iconfont-common"></span>-->
            <span class="aurora-nav-font aurora-font" :class="setHomeClass(menuItem)"></span>
          </div>
          <div class="menu-item-right">

            <a v-if="getIsOuterLink(menuItem.link)" target="_blank" :key="index" :href="menuItem.link">
              <span>{{menuItem.text}}</span>
            </a>
            <router-link v-else :to="menuItem.link" :data="getNavHref(menuItem.link)">
              <span>{{menuItem.text}}</span>
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!--这是是导航栏-->
    <div v-show="changePageIndex === 2" class="mobile-sidebar-catalog sidebar-nav">
      <div style="overflow: hidden" :key="itemLevel1Index" v-for="(itemLevel1,itemLevel1Index) in navbarLinks">
        <!--这是顶部导航的文章目录 一级标题-->
        <div v-if="itemLevel1.children"
             id="catalog-single" :class="getCatalogOpenStatus(itemLevel1Index)"
             class="catalog-single sidebar-catalog-item">
          <!--展示一级标题-->
          <div v-if="itemLevel1.children" class="page-catalog-parent">
            <div class="catalog-page-title">
              <span class="content-single-show">{{itemLevel1.text}}</span>
              <!--<span>
                <span>&nbsp;
                  <svg class="icon outbound" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" x="0px" y="0px" viewBox="0 0 100 100" width="15" height="15">
                    <path fill="currentColor" d="M18.8,85.1h56l0,0c2.2,0,4-1.8,4-4v-32h-8v28h-48v-48h28v-8h-32l0,0c-2.2,0-4,1.8-4,4v56C14.8,83.3,16.6,85.1,18.8,85.1z"></path>
                    <polygon fill="currentColor" points="45.7,48.7 51.3,54.3 77.2,28.5 77.2,37.2 85.2,37.2 85.2,14.9 62.8,14.9 62.8,22.9 71.5,22.9"></polygon>
                  </svg>
                  <span class="sr-only">open in new window</span>
                </span></span>-->
              <!--<span v-if="!itemLevel1.link">{{itemLevel1.text}}</span>-->
            </div>
            <div v-if="itemLevel1.children" @click="changeCurrentLevel1Active(event,itemLevel1Index)" class="catalog-page-spread">
              <span class="home-menu-ico" :style="getSpreadClass(itemLevel1Index)"></span>
            </div>
          </div>

          <!--展示二级标题-->
          <div v-if="itemLevel1.children" class="page-catalog-children-parent">
            <div class="page-catalog-children">
              <div :key="itemLevel2Index" v-for="(itemLevel2,itemLevel2Index) in itemLevel1.children"
                   :class="{catalogChildrenActive: catalogChildrenActive === itemLevel2Index }"
                   class="catalog-page-children-item">
                <div class="catalog-page-children-title">
                  <router-link v-if="itemLevel2.link" :to="itemLevel2.link">
                    <span>{{itemLevel2.text}}</span>
                  </router-link>
                  <span v-if="!itemLevel2.link">{{itemLevel2.text}}</span>
                </div>

                <!--展示三级标题-->
                <div class="page-catalog-children-level3-parent">
                  <div :style="setLevel3Style(itemLevel1Index)" class="page-catalog-children-level3">
                    <div :key="itemLevel3Index" v-for="(item,itemLevel3Index) in itemLevel2.children"
                         class="page-catalog-children-level3-title">
                      <span v-if="!getIsOuterLink(item.link)">
                        <router-link v-if="item.link" :da="item.link" :data="getIsOuterLink(item.link)" :to="item.link">
                          <span>{{item.text}}</span>
                        </router-link>
                      </span>
                      <span v-if="getIsOuterLink(item.link)">
                        <a target="_blank" v-if="item.link" :da="item.link" :data="getIsOuterLink(item.link)" :href="item.link">
                          <span>{{item.text}}</span>
                        </a>
                      </span>
                      <span class="content-single-show" v-if="!item.link">{{item.text}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 这是是目录 -->
    <catalog @get-current-catalog-object="getCurrentCatalogObject"
             class="mobile-sidebar-catalog" :is-show-catalog="true"
             v-show="changePageIndex === 3" />

  </div>
</template>

<script lang="ts">
import {computed} from "vue";
import { computed, defineComponent } from 'vue'
import type { ComputedRef } from 'vue'
import { useRouter } from 'vue-router'
import {usePageFrontmatter, useRouteLocale, useSiteLocaleData} from '@vuepress/client'
import { isLinkHttp, isString } from '@vuepress/shared'
import type { NavbarItem, NavbarGroup, ResolvedNavbarItem } from '../../../../shared'
import { useNavLink, useThemeLocaleData } from '../../../composables'
import { resolveRepoType } from '../../../utils'

/**
 * Get navbar config of select language dropdown
 */
const useNavbarSelectLanguage = (): ComputedRef<ResolvedNavbarItem[]> => {
  const router = useRouter()
  const routeLocale = useRouteLocale()
  const siteLocale = useSiteLocaleData()
  const themeLocale = useThemeLocaleData()

  return computed<ResolvedNavbarItem[]>(() => {
    const localePaths = Object.keys(siteLocale.value.locales)
    // do not display language selection dropdown if there is only one language
    if (localePaths.length < 2) {
      return []
    }
    const currentPath = router.currentRoute.value.path
    const currentFullPath = router.currentRoute.value.fullPath

    const languageDropdown: ResolvedNavbarItem = {
      text: themeLocale.value.selectLanguageText ?? 'unkown language',
      ariaLabel: themeLocale.value.selectLanguageAriaLabel ?? 'unkown language',
      children: localePaths.map((targetLocalePath) => {
        // target locale config of this langauge link
        const targetSiteLocale =
            siteLocale.value.locales?.[targetLocalePath] ?? {}
        const targetThemeLocale =
            themeLocale.value.locales?.[targetLocalePath] ?? {}
        const targetLang = `${targetSiteLocale.lang}`

        const text = targetThemeLocale.selectLanguageName ?? targetLang
        let link

        if (targetLang === siteLocale.value.lang) {
          // if the target language is current language
          // stay at current link
          link = currentFullPath
        } else {
          // if the target language is not current language
          // try to link to the corresponding page of current page
          // or fallback to homepage
          const targetLocalePage = currentPath.replace(
              routeLocale.value,
              targetLocalePath
          )
          if (
              router.getRoutes().some((item) => item.path === targetLocalePage)
          ) {
            link = targetLocalePage
          } else {
            link = targetThemeLocale.home ?? targetLocalePath
          }
        }

        return {
          text,
          link,
        }
      }),
    }

    return [languageDropdown]
  })
}

/**
 * Get navbar config of repository link
 */
const useNavbarRepo = (): ComputedRef<ResolvedNavbarItem[]> => {
  const themeLocale = useThemeLocaleData()

  const repo = computed(() => themeLocale.value.repo)
  const repoType = computed(() =>
      repo.value ? resolveRepoType(repo.value) : null
  )

  const repoLink = computed(() => {
    if (repo.value && !isLinkHttp(repo.value)) {
      return `https://github.com/${repo.value}`
    }

    return repo.value
  })

  const repoLabel = computed(() => {
    if (!repoLink.value) return null
    if (themeLocale.value.repoLabel) return themeLocale.value.repoLabel
    if (repoType.value === null) return 'Source'
    return repoType.value
  })

  return computed(() => {
    if (!repoLink.value || !repoLabel.value) {
      return []
    }

    return [
      {
        text: repoLabel.value,
        link: repoLink.value,
      },
    ]
  })
}

const resolveNavbarItem = (
    item: NavbarItem | NavbarGroup | string
): ResolvedNavbarItem => {
  if (isString(item)) {
    return useNavLink(item)
  }
  if ((item as NavbarGroup).children) {
    return {
      ...item,
      children: (item as NavbarGroup).children.map(resolveNavbarItem),
    }
  }
  return item as ResolvedNavbarItem
}

const useNavbarConfig = (): ComputedRef<ResolvedNavbarItem[]> => {
  const themeLocale = useThemeLocaleData()
  return computed(() => (themeLocale.value.navbar || []).map(resolveNavbarItem))
}
import Catalog from "../Catalog.vue";
import {DefaultThemePageFrontmatter} from "../../../../shared";
import {useThemeData} from '../../../composables'
export default {
  name: "MobileSidebarNav",
  components: {Catalog},
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
    getNavHref() {
      return (link) => {
        let re = new RegExp("^"+this.$site.base);
        if (re.test(link)) {
          //link中已经带有base
          return link
        }else {
          return this.$site.base + link
        }
      }
    },
    getIsOuterLink() {
      return (item) => {
        let reg=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
        return reg.test(item)
      }
    },
    setHomeNavIco() {
      return (href) => {
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
        return "--aurora-sidebar-nav-other-ico: '\\" + icoArr[this.getRandomInt(0,icoArr.length)] + "'"
      }
    },
    setHomeClass() {
      return (item) => {
        if (item.iconClass !== undefined && item.iconClass !== "") {
          return item.iconClass
        }else {
          if (item.link !== undefined && item.link.search("github") !== -1) {
            if (useThemeData().value.repoIconClass !== undefined) {
              return useThemeData().value.repoIconClass
            }else {
              return "aurora-sidebar-nav-github"
            }
          }
        }
      }
    },
    getMenuIco() {
      return (href) => {
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
      return (href) => {
        if (href.search("http") !== -1) {
          return '_blank'
        }else {
          return ''
        }
      }
    },
    isOuterHref() {
      return (href) => {
        if (href.search("http") !== -1) {
          return true
        }else {
          return false
        }
      }
    },
    setLevel3Style() {
      return (index) => {
        if (this.catalogOpenStatus.index === index) {
          if (this.catalogOpenStatus.openStatus) {
            return "display: block;"
          }
        }
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
    getSpreadClass() {
      return (index) => {
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
  setup() {
    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
    const navbarConfig = useNavbarConfig()
    const navbarSelectLanguage = useNavbarSelectLanguage()
    const navbarRepo = useNavbarRepo()

    const navbarLinks = computed(() => [
      ...navbarConfig.value,
      ...navbarSelectLanguage.value,
      // ...navbarRepo.value,
    ])
    return {
      frontmatter,
      navbarLinks,
    }
  },
  methods: {
    getCurrentCatalogObject(currentCatalog) {
      if (currentCatalog.currentCatalog.length !== 0) {
        this.showMobileCatalogStatus = true
      }
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
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