import {defineStore} from "pinia";
import {defaultSiteSettingInfo} from "@/field";
import {setDefaultProperties} from "@/utils/business";

const siteInfoMap: Map<string, SiteSettingInfo> = new Map<string, SiteSettingInfo>()
export const useSiteInfo = defineStore('siteInfo', {
  state: () => {
    return {
      siteInfoMap
    }
  },
  getters: {
    getSiteInfo: state => (userUid: string): SiteSettingInfo => {
      if (state.siteInfoMap.get(userUid)) {
        return state.siteInfoMap.get(userUid) as SiteSettingInfo
      }
      return {}
    }
  },
  actions: {
    setSiteInfo(userUid: string, siteInfo?: SiteSettingInfo) {
      setDefaultProperties(siteInfo, defaultSiteSettingInfo).then(r => {
        this.siteInfoMap.set(userUid, siteInfo)
      })
    }
  }
})