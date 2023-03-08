import { EnumContentType } from '@/enum';
import { mockRequest, request } from '@/service/request';
import type { OauthVo } from '@/bean/vo/auth/OauthVo';
import type { OauthPasswordPo } from '@/bean/pojo/auth/oauthPassword';

function _baseApi() {
  /** 登录 */
  function loginByPassword(data: OauthPasswordPo) {
    return request.post<OauthVo>('/oauth/token', data, { headers: { 'Content-Type': EnumContentType.formUrlencoded } });
  }

  function logoutOut() {
    return mockRequest.post<boolean>('/auth/logout/out');
  }

  return { loginByPassword, logoutOut };
}

export const authApi = _baseApi();
