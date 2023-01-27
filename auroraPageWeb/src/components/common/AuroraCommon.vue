<template>
  <div class="common">
    <Navbar :user-uid="userUid" :show-header-bg="showHeaderBg" v-if="true"></Navbar>
    <!--<mobile-sidebar :show-navbar="isHomePage"/>-->
    <!--<social-spin/>-->
    <style-menu
        :user-uid="userUid"
        @setIsFitter="setIsFitter"
        @setBodyStyle="getBodyStyle"
        @setBodyWallpaper="setBodyWallpaper"
        :is-show-ico="true"
        custom-class="custom-about"/>
    <slot name="top1"></slot>
    <slot name="top2"></slot>
    <slot name="top3"></slot>
    <slot name="top4"></slot>
    <slot name="top5"></slot>
    <slot name="top6"></slot>
    <div
        class="theme-container"
        @touchstart="onTouchStart"
        @touchend="onTouchEnd"
        :style="colorFontStyle"
        :class="{'sidebar-single-enter-animate': showSidebarAnimateClass}"
    >
      <div class="page-sidebar">
				<slot name="topImageSlot"/>
        <top-image :is-show-top-img="isShowTopImg"
									 :tag-or-category="tagOrCategory"
									 :current-site-info="currentSiteInfo"
									 :background-image="topBackgroundImage"
									 :article-info="articleInfo"
                   :is-show-head-line="isShowHeadLine"
                   :show-mood-edit="showMoodEdit"
                   :head-line="headLine">
        </top-image>

        <div :class="{'content': !isHomePage}">
          <div :id="getArticleId" :class="{'article-page-parent-pro': !isHomePage}" class="article-page-parent">
            <div :class="{noShowSidebar: getNoShowSidebar}" id="page-sidebar-left"
                 class="page-sidebar-left">
              <slot name="center1"></slot>
              <slot name="center2"></slot>
              <slot name="center3"></slot>
              <slot name="center4"></slot>
              <slot name="center5"></slot>
              <slot name="center6"></slot>
              <slot name="center7"></slot>
              <slot name="center8"></slot>
              <slot name="center9"></slot>
            </div>
            <div id="page-sidebar-right" v-if="!isHomePage" v-show="showSidebar" class="page-sidebar-right">
              <div class="stickSidebar" v-if="mobilePageSidebar">
                <HomeSidebar :show-navbar="showNavbar"
														 :user-uid="userUid"
														 :is-article-page="isArticlePage"
                             :sidebar-width-var="0.92"
														 :show-sidebar="showSidebar"
                             :show-sidebar-social="true"
                             :show-sidebar-link="showSidebarLink"
                             :sidebar-row-var="sidebarRowVar"
                             :is-sticky-sidebar="isStickySidebar"
                             :show-tag-cloud="showTagCloud"
                             :is-show-catalog="isShowCatalog">
                </HomeSidebar>
              </div>
            </div>
          </div>
        </div>
      </div>
      <slot name="bottom1"></slot>
      <slot name="bottom2"></slot>
      <slot name="bottom3"></slot>
      <slot name="bottom4"></slot>
      <Footer :current-site-info="currentSiteInfo"
              :is-home="isHomePage"
              :is-show-footer="isShowFooter">
      </Footer>
    </div>
    <set-bg :user-uid="userUid"/>
		<!--<aurora-snow/>-->
  </div>
</template>
<script lang="ts">

//配置导入
import {defineComponent, PropType, Transition,} from 'vue';
import {useSiteInfo} from "@/stores";
import blogConfig from '@/config/blogConfig.json';
import {getRandomNum} from "@/utils";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {TagVo} from "@/bean/vo/article/TagVo";
import {CategoryVo} from "@/bean/vo/article/CategoryVo";

const currentSiteInfo: SiteSettingInfo = {}
const useSite = useSiteInfo()
export default defineComponent({
  name: 'Common',
  components: {
    Transition
  },
  data() {
    return {
      showHeaderBg: true,
      aboutOption: [],
      sidebarRowVar: 5,
      obj: {
        output: '',
        isEnd: false,
        speed: 80,
        singleBack: false,
        sleep: 1700,
        type: 'normal',
        backSpeed: 70,
        sentencePause: false,
      },
      ico: null,
      colorStyle: '',
      fontStyle: '',
      isShowFooter: '',
      colorFontStyle: '',
      isFitter: false,
			currentSiteInfo,
      //首页壁纸数组
      homeWps: [],
      mobilePageSidebar: true,
      pageYOffset: 0,
      width: 0,
    }
  },
  props: {
		tagOrCategory: {
			type: Object as PropType<TagVo> | Object as PropType<CategoryVo>
		},
		topBackgroundImage: {
			type: String
		},
		articleInfo: {
			type: Object as PropType<ArticleVo>
		},
		isArticlePage: {
			type: Boolean,
			default() {
				return false
			}
		},
		showNavbar: {
			type: Boolean,
			default() {
				return true
			}
		},
		userUid: {
			type: String
		},
    showSidebarAnimateClass: {
      type: Boolean,
      default() {
        return true
      }
    },
    isHomePage: {
      type: Boolean,
      default() {
        return false
      }
    },
    showSidebarLink: {
      type: Boolean,
      default() {
        return true
      }
    },
    isStickySidebar: {
      type: Boolean,
      default() {
        return false;
      }
    },
    isShowCatalog: {
      type: Boolean,
      default() {
        return false
      }
    },
    showTagCloud: {
      type: Boolean,
      default() {
        return true
      }
    },
    showSidebar: {
      type: Boolean,
      default() {
        return true
      }
    },
    showMoodEdit: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowTopImg: {
      type: Boolean,
      default() {
        return false
      }
    },
    isShowHeadLine: {
      type: Boolean,
      default() {
        return false
      }
    },
    headLine: {
      type: String,
      default() {
        return ""
      }
    },
    isShowSideBar: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  computed: {
    getArticleId() {
      if (!this.isHomePage) {
        return 'article-page-parent'
      }else {
        //如果是首页，并且不是手机端
        if (this.width > 719) {
          return 'article-page-parent'
        }else {
          return 'article-page-parent-mobile'
        }
      }
    },
    getNoShowSidebar() {
      // showSidebar
      if (this.isHomePage) {
        return false
      }else {
        return this.showSidebar
      }
    },
    getIndex() {
      return (index: number, length: number)=> {
        if (index === 0 && length === 1) {
          return ""
        }
        return index+1 + ". "
      }
    },
    setSpanStyle() {
      return (score: number) => {
        let newScore = score * 0.8
        let background_color = blogConfig.randomColor[
            getRandomNum(0,blogConfig.randomColor.length -1)]
        return 'width: '+ newScore + "%;" + "background-color: "+background_color + ";"
      }
    },
    setIco() {
      return 'background-image: url('+this.ico+');'
    },
    setBodyStyle() {
    }
  },
  methods: {
		setDefaultInfo() {
			this.currentSiteInfo = useSite.getSiteInfo(this.userUid)
			//在v1.3.2之后，就已经移除通过docs/readme.md中配置favicon，转为在config中进行配置
			// let metaKey = $('<link rel="shortcut icon" href=\"'+this.themeProperty.faviconIco+'\">')
			// $("head").get(0).appendChild(metaKey.get(0))
			
			//从配置文件中，获取首页壁纸
			// let homeWps = []
			// if (this.themeProperty.homeWps === undefined || this.themeProperty.homeWps == null) {
			//   homeWps.push("https://picoss.cco.vin/animate/wall/404901.png")
			// }else {
			//   homeWps = this.themeProperty.homeWps
			// }
			//
			// if (homeWps.length === 0) {
			//   homeWps.push("https://picoss.cco.vin/animate/wall/404901.png")
			// }
			//
			// this.homeWps = homeWps
			//
			// if (this.aboutOption !== undefined) {
			//   this.aboutOption = this.themeProperty.about
			// }
			//
			// try {
			//   this.ico = this.themeProperty.ico.aboutIco
			// }catch (e) {
			//   this.ico = "https://ooszy.cco.vin/img/ico/cat.svg"
			// }
			//
			// setTag(this,this.themeProperty).then(() => {
			//   this.$store.commit('setTagStatus',{
			//     isSuccess:  true
			//   })
			// })
			if (this.currentSiteInfo.footerInfo) {
				this.isShowFooter = this.currentSiteInfo.footerInfo.enable
			}
			
			this.colorFontStyle = this.colorStyle + " "+ this.fontStyle
		},
    setHomeBg() {
			// TODO 重做
      // let base = ""
      // if (this.$site.base !== "/") {
      //   base = this.$site.base
      // }
      // new Promise((resolve,reject) => {
      //   let homeWpsSet = new Set()
      //   for (let i = 0; i < this.homeWps.length; i++) {
      //     // homeWpsSet.add(withBase(this.homeWps[i]))
      //     homeWpsSet.add(base + this.homeWps[i])
      //   }
      //   resolve(homeWpsSet)
      // }).then((homeWpsSet) => {
      //   this.homeWps = Array.from(homeWpsSet)
      //   let backgroundUrl = ''
      //   if (this.$store.state.homeWps === "") {
      //     //将首页壁纸设置为配置文件数组中的第一张图片
      //     // backgroundUrl = this.homeWps[0]
      //     backgroundUrl = this.homeWps[this.getRandomInt(0,this.homeWps.length -1)]
      //   }else {
      //     //将首页壁纸设置为配置文件数组中的第一张图片
      //     backgroundUrl = this.$store.state.homeWps
      //   }
			//
      //   this.$store.commit("setHomeWps",{
      //     homeWps: backgroundUrl
      //   })
      // })
    },
    handleScroll() {
      this.showHeaderBg = window.pageYOffset <= this.pageYOffset;
      this.pageYOffset = window.pageYOffset
    },
    getBodyStyle() {
    },
    setBodyWallpaper() {
    },
    setIsFitter(isFitter: boolean) {
      this.isFitter = isFitter
    },
  },
  created() {
		this.setDefaultInfo()
  },
	watch: {
		userUid(nv, ov) {
			this.setDefaultInfo()
		}
	},
  mounted() {
    this.width = document.body.clientWidth

    if (document.documentElement.clientWidth < 719) {
      this.sidebarRowVar = 6
    }

    // 滚动条的获取
    window.addEventListener('scroll', this.handleScroll, true)

    //手机端壁纸
    let screen = document.body.clientWidth
    if (screen < 500) {
      // if (this.themeProperty.homeWpsMobile !== undefined) {
      //   try {
      //     if (this.themeProperty.homeWpsMobile.length !== 0) {
      //       this.homeWps = this.themeProperty.homeWpsMobile
      //     }
      //   }catch (e) {
      //   }
      // }
    }

    this.setHomeBg()
		this.setDefaultInfo()

    if (document.body.clientWidth < 550 && this.currentSiteInfo.mobilePageSidebar !== undefined) {
      this.mobilePageSidebar = this.currentSiteInfo.mobilePageSidebar
    }
  }
})
</script>

