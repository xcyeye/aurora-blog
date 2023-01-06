import {defineStore} from "pinia";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {userApi} from "@/service";
import {StringUtil} from "@/utils";

const userInfoMap: Map<string, UserVo> = new Map<string, UserVo>()
export const useUserInfo = defineStore('userInfo', {
  state: () => {
    return {
      userInfoMap
    }
  },
  getters: {
    getUserInfo: state => (userUid: string): UserVo => {
      if (!StringUtil.haveLength(userUid)) return {}
      if (state.userInfoMap.get(userUid) && state.userInfoMap.get(userUid)!.uid) {
        return state.userInfoMap.get(userUid) as UserVo
      }

      // 先查询，后存储
      const useSite = useUserInfo()
      let userInfo: UserVo = {}
      userApi.queryOneDataByUid({uid: userUid}).then(result => {
        if (result.data) {
          useSite.setUserInfo(userUid, userInfo)
        }
      })
      return userInfo
    }
  },
  actions: {
    setUserInfo(userUid: string, userInfo?: UserVo) {
      if (userInfo) {
        this.userInfoMap.set(userUid, userInfo)
      }else {
        userApi.queryOneDataByUid({uid: userUid}).then(result => {
          if (result.data) {
            this.userInfoMap.set(userUid, result.data)
          }
        })
      }
    }
  }
})