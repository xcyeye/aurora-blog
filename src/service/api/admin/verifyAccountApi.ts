import { baseAxiosRequestConfig } from "@/constants";
import { request } from "@/service/request";

/** 接口前缀 */
const apiPrefix = "/admin/verifyAccount/";

function _baseApi() {

	/**
	 * 绑定账户
	 * @param incomingSecretKey 系统生成的秘钥
	 */
	function emailVerifyAccount(incomingSecretKey: string) {
		return request.get<string>(apiPrefix + `/bindEmail/emailVerifyAccount/${incomingSecretKey}`, baseAxiosRequestConfig);
	}

	/**
	 * 启用账户
	 * @param incomingSecretKey 系统生成的秘钥
	 */
	function enableAccount(incomingSecretKey: string) {
		return request.get<string>(apiPrefix + `/enable/enableAccount/${incomingSecretKey}`, baseAxiosRequestConfig);
	}

	return { emailVerifyAccount, enableAccount }
}

export const verifyAccountApi = _baseApi();
