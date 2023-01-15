import type {App} from 'vue';
import {createPinia, PiniaPluginContext} from 'pinia';
import {createLocalStorage, getLocalStorage} from "@/utils";
import {UserVo} from "@/bean/vo/admin/UserVo";

/** setup vue store plugin: pinia. - [安装vue状态管理插件：pinia] */
export function setupStore(app: App) {
  const piniaPlugin = (context: PiniaPluginContext) => {
    const siteInfoMap: Map<string, SiteSettingInfo> = new Map<string, SiteSettingInfo>(Object.entries(getLocalStorage('userSiteInfoMap')))
    const userInfoMap: Map<string, UserVo> = new Map<string, UserVo>(Object.entries(getLocalStorage('userInfoMap')))
    const {store} = context
    store.$subscribe((mutation, type) => {
      if (mutation.storeId === 'siteInfo') {
        const siteInfoMap: Map<string, SiteSettingInfo> = type.siteInfoMap
        createLocalStorage('userSiteInfoMap', siteInfoMap)
      }

      if (mutation.storeId === 'userInfo') {
        const userInfoMap: Map<string, UserVo> = type.userInfoMap
        createLocalStorage('userInfoMap', userInfoMap)
      }
    })
    return {
      siteInfoMap: siteInfoMap,
      userInfoMap: userInfoMap
    }
  }
  const store = createPinia();
  store.use(piniaPlugin)
  app.use(store);
}

export * from './modules';
