import {defineStore} from "pinia";
import {SysSettingVo} from "@/theme/vo/admin/SysSettingVo";
import {getSysSetting} from "@/store/modules/sysSetting/helpers";
import {localStg} from "@/utils";

export const useSysSettingStore = defineStore('sys_setting', {
	state: () => {
		return {
			sysSettingMap: getSysSetting()
		}
	},
	actions: {
		setSysSetting(sysSettingList: Array<SysSettingVo>) {
			const sysSettingMapTemp: Map<string, SysSettingVo> = new Map<string, SysSettingVo>()
			sysSettingList.forEach((v, index) => {
				sysSettingMapTemp.set(v.paramCode!, v)
				if (index === sysSettingList.length -1) {
					localStg.set('sysSetting', sysSettingMapTemp);
				}
			})
		}
	}
})
