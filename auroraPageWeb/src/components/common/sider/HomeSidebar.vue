<!--侧边栏组件，手机端菜单组件为MobileSidebar.vue-->
<template>
  <div v-if="showSidebar">
		<!--这是页面右侧的侧边栏，和默认主题的侧边栏不同-->
		<div ref="sidebar-top" class="sidebar-cqy"></div>
		<div :style="getHomeSidebarStyle" id="stickSidebar"
				 :class="{'sidebar-single-enter-animate': showEnterAnimate}">
			<!---->
			<div :class="{sidebarScroll: isShowCatalog}" :style="setHomeSidebarStyle" class="home-sidebar" id="home-sidebar">
			
				<!--头像信息-->
				<aurora-card :show-linear-gradient="false" class="sidebar-aurora-card" :id="customId" v-if="showPersonInfo">
					<!--<div class="home-sidebar-avatar">-->
					<!--	&lt;!&ndash;<img id="home-sidebar-avatar-img" :src="useUserInfo().getUserInfo(userUid).avatar" alt="">&ndash;&gt;-->
					<!--	<n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" :size="60" round/>-->
					<!--</div>-->
					<aurora-random-picture :pc-height="10" :mobile-height="10" custom-picture-class="aurora-sidebar-random-picture" :user-uid="userUid">
						<aurora-typing :typing-content="useUserInfo().getUserInfo(userUid).userSummary" class="aurora-sidebar-random-picture-summary"/>
					</aurora-random-picture>
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
						<div @click="goComment" class="sidebar-page-common">
							<div>评论</div>
							<div>{{totalCommentNumber}}</div>
						</div>
					</div>
					<a target="_blank" :href="getGithubUrl">
						<div class="sidebar-github">Github</div>
					</a>
					<slot name="sidebar-son1"/>
				</aurora-card>
				<slot name="sidebar1"></slot>
			
				<!--&lt;!&ndash;时间&ndash;&gt;-->
				<!--<div :id="customId" v-if="getShowSidebarSocial" class="sidebar-single-common">-->
				<!--  <div class="sidebar-time">-->
				<!--    <span>12:36:57</span>-->
				<!--  </div>-->
				<!--</div>-->
			
				<!--社交-->
				<aurora-card  custom-style="padding: 0rem;" class="sidebar-aurora-card" :id="customId" v-if="getShowSidebarSocial">
					<div class="sidebar-social">
						<HomeSidebarSocialItem :sidebar-row-var="sidebarRowVar"
																	 :sidebar-width-var="sidebarWidthVar"
																	 :social-item="item" :data="item.aHref" :key="item.aHref"
																	 v-for="(item,index) in socialsArr"/>
						<slot name="sidebar-son2"/>
					</div>
				</aurora-card>
				<slot name="sidebar2"></slot>
			
				<aurora-card :title="showTalkInfo.title" icon="fa:comments" class="sidebar-aurora-card" v-if="talkArr.length > 0 && showTalk">
					<div @click="goTalk($event, showTalkInfo)">
						<n-ellipsis :line-clamp="3" :tooltip="false">
							<aurora-typing :typing-content="showTalkInfo.content"/>
						</n-ellipsis>
					</div>
					<div style="font-size: .3rem" class="sidebar-page-time">
						<span>{{showTalkInfo.createTime}}</span>
					</div>
				</aurora-card>
			
				<!--侧边栏友情链接-->
				<aurora-card icon="fa:paper-plane" title="朋友圈" class="sidebar-aurora-card" :id="customId" v-if="getShowSidebarLink">
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
				</aurora-card>
			
				<!--顶部导航-->
				<aurora-card class="sidebar-aurora-card" :id="customId" v-if="showNavbar" :class="{'sidebar-single-enter-animate': showEnterAnimate}">
					<mobile-sidebar-nav :is-article-page="isArticlePage" :user-uid="userUid" />
					<slot name="sidebar-son3"/>
				</aurora-card>
				<slot name="sidebar3"></slot>
			
				<!--搜索-->
				<aurora-card class="sidebar-aurora-card" :id="customId" v-if="showSearch" :class="{'sidebar-single-enter-animate': showEnterAnimate}">
					<!--<SearchBox/>-->
					<slot name="sidebar-son4"/>
				</aurora-card>
				<slot name="sidebar4"></slot>
			
				<!--文章-->
				<aurora-card icon="fa:history" :id="customId" v-if="showArticle" :class="{'sidebar-single-enter-animate': showEnterAnimate}" class="sidebar-single-page sidebar-aurora-card">
					<template #header>
						<div class="change-page">
							<div v-if="isShowCatalog" class="catalog-page change-page-common">
								<span :class="{changePageActive: changePageIndex === '1'}" index="1" @click="changePage">文章目录</span>
							</div>
							<div :style="getSinglePageStyle" class="latest-page change-page-common">
								<span :class="{changePageActive: 'changePageIndex' === '2'}" @click="changePage" index="2">最新文章</span>
							</div>
						</div>
					</template>
					<!--文章目录-->
					<catalog :is-show-catalog="isShowCatalog" v-show="changePageIndex === '1'" />
				
					<!--最新文章-->
					<div v-show="changePageIndex === '2'">
						<div v-for="item in articleArr" :key="item.uid" :data="item.uid" class="sidebar-page-item sidebar-hover-bg-common">
							<div class="sidebar-page-title">
								<router-link :to="`/article/${item.userUid}/${item.uid}`">
									<span @click="goRead($event,item)">{{item.title ? item.title :getRecommendNoTitle}}</span>
								</router-link>
							</div>
							<div class="sidebar-page-time">
								<span>{{ item.createTime }}</span>
							</div>
						</div>
					</div>
					<slot name="sidebar-son5"/>
				</aurora-card>
				<slot name="sidebar5"></slot>
			
				<!--公告-->
				<aurora-card class="sidebar-aurora-card"
										 :id="customId"
										 @click="goBulletin(bulletinArr[0])"
										 v-if="bulletinArr.length > 0"
										 :class="{'sidebar-single-enter-animate': showEnterAnimate}"
										 :title="bulletinArr[0].title ? bulletinArr[0].title : '公告'" icon="bi:messenger">
					<n-ellipsis :line-clamp="3" :tooltip="false">
						<span>{{bulletinArr[0].content}}</span>
					</n-ellipsis>
					<slot name="sidebar-son6"/>
				</aurora-card>
				<slot name="sidebar6"></slot>
			
				<!--标签-->
				<aurora-card class="sidebar-aurora-card" :id="customId" v-if="showTagCloud" :class="{'sidebar-single-enter-animate': showEnterAnimate}" title="标签" icon="bi:tag-fill">
				
					<!--<div v-if="isMobileSidebar" class="change-page">-->
					<!--	<div class="catalog-page change-page-common">-->
					<!--		<span class="home-menu-ico" style="&#45;&#45;homeIcoCode: '\e7b5'"></span>-->
					<!--		<span>标签</span>-->
					<!--	</div>-->
					<!--	<div class="latest-page change-page-common">-->
					<!--		<span></span>-->
					<!--		<span></span>-->
					<!--	</div>-->
					<!--</div>-->
				
					<div class="sidebar-tag-item">
						<router-link :key="index" v-for="(item,index) in tagArr" :to="`/tag/${item.userUid}/${item.title}`">
							<div class="sidebar-tag-single">
								<span class="home-sidebar-tag-hover" :key="index" :style="setTagItemStyle(index)">{{item.title}}</span>
							</div>
						</router-link>
					</div>
					<slot name="sidebar-son7"/>
				</aurora-card>
				<slot name="sidebar7"></slot>
			
				<!--站点-->
				<aurora-card class="sidebar-aurora-card" :id="customId" v-if="showSite" :class="{'sidebar-single-enter-animate': showEnterAnimate}">
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
				</aurora-card>
				<slot name="sidebar8"></slot>
			</div>
		</div>
	</div>
</template>

<script lang="ts">

import {useSiteInfo, useUserInfo} from "@/stores";
import {articleApi, bulletinApi, categoryApi, commentApi, linkApi, tagApi, talkApi} from "@/service";
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
const showTalkInfo: TalkVo = {}
const categoryArr: Array<CategoryVo> = []
const articleArr: Array<ArticleVo> = []
const routerPush = useRouterPush()

export default {
  name: "HomeSidebar",
  components: {
  },
  data() {
    return {
			totalCommentNumber: 0,
			articleNumber: 0,
			tagNumber: 0,
			categoryNumber: 0,
			articleArr,
			showTalkInfo,
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
    },
		showSidebar: {
			type: Boolean,
			default() {
				return true;
			}
		}
  },
  created() {
		this.currentSiteInfo = useSiteInfo().getSiteInfo(this.userUid)
    if (this.isShowCatalog) {
      this.changePageIndex = '1'
    }

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
		articleApi.queryTotalCount({userUid: this.userUid, delete: false}).then(result => {
			if (result.data) {
				this.articleNumber = result.data
			}
		})
		
		commentApi.queryTotalCount({userUid: this.userUid}).then(result => {
			if (result.data) {
				this.totalCommentNumber = result.data
			}
		})
		
		talkApi.queryListDataByCondition({otherUid: this.userUid, delete: false, show: true, orderBy: 'create_time desc'}).then(result => {
			if (result.data && result.data.result) {
				this.talkArr = result.data.result
				this.showTalkInfo = this.talkArr[getRandomNum(0, this.talkArr.length)]
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
		goComment() {
			this.$router.push({
				path: `/comment/${this.userUid}`
			})
		},
		goBulletin(bulletin: BulletinVo) {
			this.$router.push({
				path: `/bulletin/${this.userUid}/${bulletin.uid}`
			})
		},
		goTalk(e: Event, talk: TalkVo) {
			this.$router.push({
				path: `/shareSpace/${this.userUid}/${talk.uid}`
				// path: `/shareSpace-page/${this.userUid}/1614847608245198848`
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
