import {defineStore} from "pinia";
import {OauthVo} from "@/bean/vo/auth/OauthVo";
import {cleanLocalStorage, createLocalStorage} from "@/utils";
import {getAuthInfo} from "@/stores/modules/auth/helpers";

export const useAuthStore = defineStore('auth_info', {
  state: () => {
    return {
      authInfo: getAuthInfo()
    }
  },
  actions: {
    setAuthInfo(authInfo: OauthVo) {
      cleanLocalStorage('auth_info')
      createLocalStorage('auth_info', authInfo)
    }
  }
})