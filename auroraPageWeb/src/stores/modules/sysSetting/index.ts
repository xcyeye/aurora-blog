import {defineStore} from "pinia";
import {SysSettingVo} from "@/bean/vo/admin/SysSettingVo";
import {createLocalStorage} from "@/utils";
import {getSysSetting} from "@/stores/modules/sysSetting/helpers";

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
					createLocalStorage('sysSetting', sysSettingMapTemp);
				}
			})
		}
	}
})
