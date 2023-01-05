import {defineStore} from "pinia";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";
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

      // 先查询，后存储
      const useSite = useSiteInfo()
      let siteInfo: SiteSettingInfo = {}
      siteSettingApi.queryOneDataByUserUid({userUid: userUid}).then(result => {
        if (result.data && result.data.paramValue) {
          siteInfo = JSON.parse(result.data.paramValue)
          useSite.setSiteInfo(userUid, siteInfo)
        }else {
          siteInfo = defaultSiteSettingInfo
          useSite.setSiteInfo(userUid, siteInfo)
        }
      })
      return siteInfo
    }
  },
  actions: {
    setSiteInfo(userUid: string, siteInfo?: SiteSettingInfo) {
      if (siteInfo) {
        setDefaultProperties(siteInfo, defaultSiteSettingInfo).then(r => {
          this.siteInfoMap.set(userUid, siteInfo)
        })
      }else {
        siteSettingApi.queryOneDataByUserUid({userUid: userUid}).then(result => {
          if (result.data) {
            const querySiteInfo: SiteSettingInfo = JSON.parse(result.data.paramValue!)
            this.siteInfoMap.set(userUid, querySiteInfo)
          }
        })
      }
    }
  }
})