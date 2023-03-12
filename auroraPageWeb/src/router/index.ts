import {createRouter, createWebHistory, Router} from 'vue-router';
import {useCurrentUser, useSiteInfo, useSysSettingStore, useThemeStore, useUserInfo} from "@/stores";
import {isNotEmptyObject} from "@/utils/business";
import {userApi} from "@/service";
import {defaultSiteSettingInfo} from "@/field";
import {StringUtil} from "@/utils";
import {App} from "vue";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
import {sysSettingApi} from "@/service/api/admin/sysSettingApi";
import {getSysSetting} from "@/stores/modules/sysSetting/helpers";

const isPersonalBlog = import.meta.env.VITE_PERSONAL_BLOG === 'Y';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/:pathMatch(.*)',
      name: 'error404',
      component: () => import('../views/404/index.vue')
    },
    {
      path: "/",
      name: 'home',
      component: () => import('../views/home/index.vue')
    },
    {
      path: '/user/:userUid',
      name: 'userHome',
      component: () => import('../views/user-home/index.vue')
    },
    {
      path: '/friendLink/:userUid',
      name: 'friendLink',
      component: () => import('../views/friend-link/index.vue')
    },
    {
      path: '/archive/:userUid',
      name: 'archive',
      component: () => import('../views/archive/index.vue')
    },
    {
      path: '/tag/:userUid/:tagName?',
      name: 'tag',
      component: () => import('../views/tag/index.vue')
    },
    {
      path: '/category/:userUid/:tagName?',
      name: 'category',
      component: () => import('../views/category/index.vue')
    },
    {
      path: '/about/:userUid',
      name: 'about',
      component: () => import('../views/about/index.vue')
    },
    {
      path: '/about-old/:userUid',
      name: 'about-old',
      component: () => import('../views/about-old/index.vue')
    },
    {
      path: '/shareSpace/:userUid/:talkUid?',
      name: '说说1',
      component: () => import('../views/share-space/swiperShareSpace.vue')
    },
    {
      path: '/shareSpace-page/:userUid/:talkUid?',
      name: '说说2',
      component: () => import('../views/share-space/commonShareSpace.vue')
    },
    {
      path: '/bulletin/:userUid/:bulletinUid?',
      name: '公告',
      component: () => import('../views/bulletin/index.vue')
    },
    {
      path: '/article/:userUid/:pageUid',
      name: 'article',
      component: () => import('../views/article/index.vue')
    },
    {
      path: '/photo/:userUid',
      name: 'photo',
      component: () => import('../views/photo/index.vue')
    },
    {
      path: '/comment/:userUid',
      name: 'comment',
      component: () => import('../views/comment/index.vue')
    },
    {
      path: '/test',
      name: 'test',
      component: () => import('../views/test/index.vue')
    }
  ]
})

const setMobileOpenStatus = () => {
  // 如果手机端侧边栏打开，则关闭
  const currentTheme = useThemeStore().currentTheme
  if (currentTheme.mobileOpenStatus) {
    currentTheme.mobileOpenStatus = !currentTheme.mobileOpenStatus
    useThemeStore().setCurrentThemeStore(currentTheme)
  }
}

const loadSysSetting = () => {
  const sysSettingStore = useSysSettingStore()
  // 获取系统配置
  if (!getSysSetting() || getSysSetting().size === 0) {
    sysSettingApi.queryListDataByCondition({pageSize: 999}).then(result => {
      if (result.data && result.data.result) {
        // TODO bug，需要刷新才会生效
        sysSettingStore.setSysSetting(result.data.result)
      }else {
        sysSettingStore.setSysSetting([])
      }
    })
  }
}

function createRouterGuard(router: Router) {
  router.beforeEach((to, from, next) => {
    // 从路由中查询userUid，如果不存在siteInfo,userInfo的话，则获取
    const userUid: string = to.params.userUid as string;
    setMobileOpenStatus()

    // 查询系统配置
    loadSysSetting()

    if (StringUtil.haveLength(userUid)) {
      // 存储用户信息
      if (!isNotEmptyObject(useUserInfo().getUserInfo(userUid))) {
        useCurrentUser().setCurrentUserInfo({uid: userUid})
        userApi.queryOneDataByUid({uid: userUid}).then(result => {
          if (result.data && isNotEmptyObject(result.data)) {
            useUserInfo().setUserInfo(userUid, result.data)

            // 查询该用户的站点信息
            // 存储用户站点信息
            const userSiteInfo = useSiteInfo().getSiteInfo(userUid)
            if (!userSiteInfo || !isNotEmptyObject(userSiteInfo)) {
              // useSiteInfo().setSiteInfo(userUid, defaultSiteSettingInfo)
              // next()
              siteSettingApi.queryListDataByCondition({otherUid: userUid, keyword: `${userUid}SiteInfo`}).then(result => {
                if (result.data && result.data.result) {
                  const siteInfo = result.data.result[0]
                  if (siteInfo && StringUtil.haveLength(siteInfo.paramValue)) {
                    useSiteInfo().setSiteInfo(userUid, JSON.parse(siteInfo.paramValue!))
                    next()
                    // siteSettingInfo.value = JSON.parse(result.data.paramValue)
                    // siteSettingInfo.value = JSON.parse(result.data.paramValue)
                    // useSite.setSiteInfo(userUid.value, defaultSiteSettingInfo)
                  }else {
                    // 用户没有任何信息，则使用默认的
                    console.error('该用户没有配置任何站点信息，将使用默认值')
                    useSiteInfo().setSiteInfo(userUid, defaultSiteSettingInfo)
                    next()
                  }
                }else {
                  // 用户没有任何信息，则使用默认的
                  console.error('该用户没有配置任何站点信息，将使用默认值')
                  useSiteInfo().setSiteInfo(userUid, defaultSiteSettingInfo)
                  next()
                }
              })
            }else {
              next()
            }
          }else {
            // 用户不存在
            console.error("此用户不存在");
            window.$message?.error('此用户不存在')
            next()
            router.push({
              path: '/notFound'
            })
          }
        })
      }else {
        next()
      }
    }else {
      next()
    }
  })
}

/** setup vue router. - [安装vue路由] */
export async function setupRouter(app: App) {
  app.use(router);
  createRouterGuard(router);
  await router.isReady();
}


// export default router