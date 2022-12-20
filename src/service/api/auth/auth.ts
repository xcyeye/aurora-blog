import { EnumContentType } from '@/enum';
import { mockRequest, request } from '@/service/request';
import type { OauthVo } from '@/theme/vo/auth/OauthVo';
import type { OauthPasswordPo } from '@/theme/pojo/auth/oauthPassword';

function _baseApi() {
	/** 登录 */
	function loginByPassword(data: OauthPasswordPo) {
		return request.post<OauthVo>('/oauth/token', data, { headers: { 'Content-Type': EnumContentType.formUrlencoded } });
	}

	/** 获取用户信息 */
	function fetchUserInfo() {
		return mockRequest.get<ApiAuth.UserInfo>('/getUserInfo');
	}

	/**
	 * 获取用户路由数据
	 * @param userId - 用户id
	 * @description 后端根据用户id查询到对应的角色类型，并将路由筛选出对应角色的路由数据返回前端
	 */
	function fetchUserRoutes(userId: string) {
		return mockRequest.post<ApiRoute.Route>('/getUserRoutes', { userId });
	}

	/**
	 * 刷新token
	 * @param refreshToken
	 */
	function fetchUpdateToken(refreshToken: string) {
		return mockRequest.post<ApiAuth.Token>('/updateToken', { refreshToken });
	}

	/**
	 * 获取验证码
	 * @param phone - 手机号
	 * @returns - 返回boolean值表示是否发送成功
	 */
	function fetchSmsCode(phone: string) {
		return mockRequest.post<boolean>('/getSmsCode', { phone });
	}

	function logoutOut() {
		return mockRequest.post<boolean>('/auth/logout/out');
	}

	return { loginByPassword, fetchSmsCode, fetchUpdateToken, fetchUserRoutes, fetchUserInfo, logoutOut}

}

export const authApi = _baseApi();
