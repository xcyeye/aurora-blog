import {defineStore} from "pinia";
import {UserVo} from "@/bean/vo/admin/UserVo";
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
      return {}
    }
  },
  actions: {
    setUserInfo(userUid: string, userInfo: UserVo) {
      this.userInfoMap.set(userUid, userInfo)
    }
  }
})