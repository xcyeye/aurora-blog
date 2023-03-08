import {defineStore} from "pinia";
import {UserVo} from "@/bean/vo/admin/UserVo";
import {StringUtil} from "@/utils";

const currentUserInfo: UserVo = {}
export const useCurrentUser = defineStore('currentUser', {
  state: () => {
    return {
      currentUserInfo
    }
  },
  getters: {
    getCurrentUserInfo: state => {
      if (!StringUtil.haveLength(state.currentUserInfo.uid)) return {}
      return state.currentUserInfo
    }
  },
  actions: {
    setCurrentUserInfo(userInfo: UserVo) {
      this.currentUserInfo = userInfo
    }
  }
})