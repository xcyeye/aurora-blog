import { EnumContentType } from '@/enum';
import { mockRequest, request } from '@/service/request';
import type { OauthVo } from '@/theme/vo/auth/OauthVo';
import type { OauthPasswordPo } from '@/theme/pojo/auth/oauthPassword';

/** 登录 */
export function fetchLoginByPassword(data: OauthPasswordPo) {
  return request.post<OauthVo>('/oauth/token', data, { headers: { 'Content-Type': EnumContentType.formUrlencoded } });
}

/** 获取用户信息 */
export function fetchUserInfo() {
  return mockRequest.get<ApiAuth.UserInfo>('/getUserInfo');
}

/**
 * 获取用户路由数据
 * @param userId - 用户id
 * @description 后端根据用户id查询到对应的角色类型，并将路由筛选出对应角色的路由数据返回前端
 */
export function fetchUserRoutes(userId: string) {
  return mockRequest.post<ApiRoute.Route>('/getUserRoutes', { userId });
}

/**
 * 刷新token
 * @param refreshToken
 */
export function fetchUpdateToken(refreshToken: string) {
  return mockRequest.post<ApiAuth.Token>('/updateToken', { refreshToken });
}

/**
 * 获取验证码
 * @param phone - 手机号
 * @returns - 返回boolean值表示是否发送成功
 */
export function fetchSmsCode(phone: string) {
  return mockRequest.post<boolean>('/getSmsCode', { phone });
}
