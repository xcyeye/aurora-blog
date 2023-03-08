import {localStg} from '@/utils';
import {SysSettingVo} from "@/bean/vo/admin/SysSettingVo";

/** 获取sysSetting */
export function getSysSetting(): Map<string, SysSettingVo> {
	if (localStg.get('sysSetting') === null || localStg.get('sysSetting') === undefined) return new Map<string, SysSettingVo>()
	const sysSetting: Map<string, SysSettingVo> = new Map<string, SysSettingVo>(Object.entries(localStg.get('sysSetting') as object));
	return sysSetting
}

/** 去除sysSetting相关缓存 */
export function clearSysSettingStorage() {
  localStg.remove('sysSetting');
}
