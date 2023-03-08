import {SysSettingVo} from "@/bean/vo/admin/SysSettingVo";
import {cleanLocalStorage, getLocalStorage} from "@/utils";

/** 获取sysSetting */
export function getSysSetting(): Map<string, SysSettingVo> {
	if (getLocalStorage('sysSetting') === null || getLocalStorage('sysSetting') === undefined) return new Map<string, SysSettingVo>()
	const sysSetting: Map<string, SysSettingVo> = new Map<string, SysSettingVo>(Object.entries(getLocalStorage('sysSetting') as object));
	return sysSetting
}

/** 去除sysSetting相关缓存 */
export function clearSysSettingStorage() {
  cleanLocalStorage('sysSetting');
}
