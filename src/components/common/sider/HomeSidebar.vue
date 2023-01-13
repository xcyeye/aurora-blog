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
          <!--<img id="home-sidebar-avatar-img" :src="useUserInfo().getUserInfo(userUid).avatar" alt="">-->
        	<n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" :size="60" round/>
				</div>
        <div class="home-sidebar-info-desc">
          <span v-if="getLogoTitle !== ''" v-html="getLogoTitle"></span>
        </div>
        <div class="home-sidebar-info-page">
          <div class="sidebar-page-common">
            <div>文章</div>
            <div>{{articleNumber}}</div>
          </div>
          <div class="sidebar-page-common">
            <div>标签</div>
            <div>{{tagNumber}}</div>
          </div>
          <div class="sidebar-page-common">
            <div>类别</div>
            <div>{{categoryNumber}}</div>
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
                                 :social-item="item" :data="item.aHref" :key="item.aHref"
																 v-for="(item,index) in socialsArr"/>
          <slot name="sidebar-son2"/>
        </div>
      </div>
      <slot name="sidebar2"></slot>
	
			<div class="sidebar-single-common" v-if="talkArr.length > 0 && showTalk">
				<div class="sidebar-social" @click="goTalk">
					<div>
						<n-ellipsis :line-clamp="3" :tooltip="false">
							<svg-icon icon="mdi:message-fast"/> &nbsp;&nbsp;{{talkArr[0].content}}
						</n-ellipsis>
					</div>
					<div style="font-size: .3rem" class="sidebar-page-time">
						<span>{{talkArr[0].createTime}}</span>
					</div>
				</div>
			</div>

      <!--侧边栏友情链接-->
      <div :id="customId" v-if="getShowSidebarLink" class="sidebar-single-common">
        <div class="sidebar-link">
          <a :href="item.linkUrl" target="_blank" :data="item.linkTitle" :key="item.uid" v-for="(item,index) in friendLinks">
            <div class="sidebar-link-single">
              <div class="sidebar-link-avatar">
                <img :origin-src="item.linkLogo" :src="showFriendAvatar ? item.linkLogo : homePageLazyLoadingImg" alt="">
              </div>
              <div :dat="item.linkTitle" class="sidebar-link-title">
                <span>{{item.linkTitle}}</span>
              </div>
            </div>
          </a>
        </div>
      </div>

      <!--顶部导航-->
      <div :id="customId" v-if="showNavbar" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <mobile-sidebar-nav :is-article-page="isArticlePage" :user-uid="userUid" />
        <slot name="sidebar-son3"/>
      </div>
      <slot name="sidebar3"></slot>

      <!--搜索-->
      <div :id="customId" v-if="showSearch" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <!--<SearchBox/>-->
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
          <div v-for="item in articleArr" :key="item.uid" :data="item.uid" class="sidebar-page-item sidebar-hover-bg-common">
            <div class="sidebar-page-title">
              <router-link :to="`/article/${item.uid}`">
                <span @click="goRead($event,item)">{{item.title ? item.title :getRecommendNoTitle}}</span>
              </router-link>
            </div>
            <div class="sidebar-page-time">
              <span>{{ item.createTime }}</span>
            </div>
          </div>
        </div>
        <slot name="sidebar-son5"/>
      </div>
      <slot name="sidebar5"></slot>

      <!--公告-->
      <div :id="customId" v-if="bulletinArr" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-common">
        <div class="sidebar-page">
          <span class="aurora-iconfont-common aurora-sidebar-message"></span>
          <span>公告</span>
        </div>
        <div class="sidebar-message">
          <li id="sidebar-message" :key="item.uid" v-for="(item,index) in bulletinArr" class="sidebar-hover-bg-common">
            <span v-html="item.content"></span>
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
          <router-link :key="index" v-for="(item,index) in tagArr" :to="'/tag?tag=' + item.uid">
            <div class="sidebar-tag-single">
              <span class="home-sidebar-tag-hover" :key="index" :style="setTagItemStyle(index)">{{item.title}}</span>
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

<script lang="ts">

import {useSiteInfo, useUserInfo} from "@/stores";
import {articleApi, bulletinApi, categoryApi, linkApi, tagApi, talkApi} from "@/service";
import {LinkVo} from "@/bean/vo/article/LinkVo";
import {getLocalTime, getRandomNum, StringUtil} from "@/utils";
import blogConfig from '@/config/blogConfig.json';
import {BulletinVo} from "@/bean/vo/article/BulletinVo";
import {TagVo} from "@/bean/vo/article/TagVo";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {CategoryVo} from "@/bean/vo/article/CategoryVo";
import {useRouterPush} from "@/composables";
import {TalkVo} from "@/bean/vo/article/TalkVo";

const currentSiteInfo: SiteSettingInfo = {}
const friendLinks: Array<LinkVo> = []
const socialsArr: Array<SocialInfo> = []
const bulletinArr: Array<BulletinVo> = []
const tagArr: Array<TagVo> = []
const talkArr: Array<TalkVo> = []
const categoryArr: Array<CategoryVo> = []
const articleArr: Array<ArticleVo> = []
const routerPush = useRouterPush()

export default {
  name: "HomeSidebar",
  components: {
  },
  data() {
    return {
			articleNumber: 0,
			tagNumber: 0,
			categoryNumber: 0,
			articleArr,
			talkArr,
			categoryArr,
			tagArr,
			bulletinArr,
			currentSiteInfo,
      showFriendAvatar: false,
      latestPageSize: 6,
      changePageIndex: '2',
      stickSidebar: false,
      socialsArr,
			friendLinks,
      homePageLazyLoadingImg: 'https://picture.xcye.xyz/img/blog-public/ljz.gif'
    }
  },
  props: {
		isArticlePage: {
			type: Boolean,
			default() {
				return false
			}
		},
		userUid: {
			type: String
		},
    showEnterAnimate: {
      type: Boolean,
      default() {
        return true
      }
    },
		showTalk: {
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
		this.currentSiteInfo = useSiteInfo().getSiteInfo(this.userUid)
    if (this.isShowCatalog) {
      this.changePageIndex = '1'
    }
    const loadAllPageMap = setInterval(() => {
      if (this.$store.state.allPageMap.length !== 0) {
        clearInterval(loadAllPageMap)
        this.setShowAllPage(this.$store.state.allPageMap)
      }
    },50)
		

    if (this.currentSiteInfo.homePageLazyLoadingImg !== undefined) {
      this.homePageLazyLoadingImg = this.currentSiteInfo.homePageLazyLoadingImg
    }

    if (this.currentSiteInfo.latestPageSize !== undefined) {
      this.latestPageSize = this.currentSiteInfo.latestPageSize
    }
		
		tagApi.queryListDataByCondition({pageSize: 300, delete: false, otherUid: this.userUid}).then(result => {
			if (result.data && result.data.result) {
				this.tagArr = result.data.result
				this.tagNumber = result.data.total
			}
		})
		
		bulletinApi.queryListDataByCondition({pageSize: 300, delete: false, show: true, otherUid: this.userUid}).then(result => {
			if (result.data && result.data.result) {
				this.bulletinArr = result.data.result
			}
		})
		
		linkApi.queryListDataByCondition({otherUid: this.userUid, status: true, pageSize: 300}).then(result => {
			if (result.data && result.data.result) {
				this.shuffleArray(result.data.result).then((arr: Array<LinkVo>) => {
					this.friendLinks = arr
				})
			}
		})
		// latestPageSize
		articleApi.queryListDataByCondition({otherUid: this.userUid, delete: false, status: true, pageSize: this.latestPageSize}).then(result => {
			if (result.data && result.data.result) {
				this.articleArr = result.data.result
				this.articleNumber = result.data.total
			}
		})
		
		talkApi.queryListDataByCondition({delete: false, show: true, orderBy: 'create_time desc'}).then(result => {
			if (result.data && result.data.result) {
				this.talkArr = result.data.result
			}
		})
	
		categoryApi.queryListDataByCondition({otherUid: this.userUid, delete: false, pageSize: 300}).then(result => {
			if (result.data && result.data.result) {
				this.categoryArr = result.data.result
				this.categoryNumber = result.data.total
			}
		})

    let socials = this.currentSiteInfo.socialsArr
    let setArr = new Set()
    if (socials !== undefined) {
      new Promise((resolve,reject) => {
        for (let i = 0; i < socials.length; i++) {
          if (socials[i].sidebar) {
            setArr.add(socials[i])
          }
					if (i === socials?.length - 1) {
						resolve(null)
					}
        }
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

      return this.friendLinks.length > 0
    },
    getShowSidebarSocial() {
      if (!this.showSidebarSocial) {
        return false
      }

      return this.socialsArr.length !== 0;

    },
    getRecommendNoTitle() {
			return '`╮(￣▽￣)╭`'
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
      if (this.currentSiteInfo.sidebarPersonDesc !== undefined) {
        return this.currentSiteInfo.sidebarPersonDesc
      }
    },
    isShow() {
      return (item: any) => {
      }
    },
    getGithubUrl() {
      return StringUtil.haveLength(this.currentSiteInfo.githubUrl) ? this.currentSiteInfo.githubUrl : "https://github.com/xcyeye"
    },
    setTagItemStyle() {
      return (index: number) => {
        let color = blogConfig.randomColor[
					getRandomNum(0,blogConfig.randomColor.length -1)]
        let fontSize = getRandomNum(10,35)
        return "color: " + color + "; font-size: " + fontSize + "px;";
      }
    }
  },
  methods: {
		goTalk() {
			routerPush.routerPush({
				path: `/shareSpace/${this.userUid}`
			})
		},
		goRead(e: any, articleInfo: ArticleVo) {
			routerPush.routerPush({
				path: '/article',
				params: {
					uid: articleInfo.uid
				}
			})
		},
		getLocalTime() {
			return getLocalTime
		},
		useUserInfo,
    shuffleArray(array: Array<LinkVo>) {
      return new Promise((resolve,reject) => {
        for (let i = array.length - 1; i > 0; i--) {
          let j = Math.floor(Math.random() * (i + 1));
          let temp = array[i];
          array[i] = array[j];
          array[j] = temp;
        }
        resolve(array)
      })
    },
    shuffle(arr: any){
      let _arr = arr.slice()
      for (let i = 0; i < _arr.length; i++) {
        let j = getRandomNum(0, i)
        let t = _arr[i]
        _arr[i] = _arr[j]
        _arr[j] = t
      }
      return _arr
    },
		// @ts-ignore
    changePage(e) {
      this.changePageIndex = e.target.getAttribute("index")
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
