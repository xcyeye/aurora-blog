import {cleanLocalStorage, getLocalStorage} from "@/utils";
import {OauthVo} from "@/bean/vo/auth/OauthVo";

/** 获取authInfo */
export function getAuthInfo(): OauthVo {
	if (getLocalStorage('auth_info') === null || getLocalStorage('auth_info') === undefined) return {}
	const authInfo: OauthVo = getLocalStorage('auth_info')
	return authInfo
}

/** 去除sysSetting相关缓存 */
export function clearSysSettingStorage() {
  cleanLocalStorage('auth_info');
}
