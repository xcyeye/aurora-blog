import {StringUtil} from "@/utils";
import {useSysSettingStore} from "@/store";

export const isNotEmptyObject = (obj: any): boolean => {
	if (typeof obj === "object") {
		if (Object.keys(obj).length > 0) {
			return true;
		}
	}
	return false;
}

export const setLazyImg = () => {
	let sysSetting: SysSetting = useSysSettingStore().sysSettingMap.get('lazy-loading-img')!
	if (!sysSetting || isNotEmptyObject(sysSetting)) {
		return 'https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/other/lazyLoadImg.png'
	}
	if (StringUtil.haveLength(sysSetting.paramValue)) {
		return sysSetting.paramValue
	}else {
		return 'https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/other/lazyLoadImg.png'
	}
}
