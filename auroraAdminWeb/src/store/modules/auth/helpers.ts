import { localStg } from '@/utils';
import type { UserInfo } from '@/bean/vo/auth/OauthVo';

/** 获取token */
export function getToken() {
  return localStg.get('token') || '';
}

/** 获取用户信息 */
export function getUserInfo() {
  const emptyInfo: UserInfo = {
		userDetailInfo:{},
		emailInfo: {},
    user_uid: '',
    username: '',
    userRole: 'user',
    nickname: '',
    verify_email: false,
    authority: ['ROLE_user']
  };
  const userInfo: UserInfo = localStg.get('userInfo') || emptyInfo;

  return userInfo;
}

/** 去除用户相关缓存 */
export function clearAuthStorage() {
  localStg.remove('token');
  localStg.remove('refreshToken');
  localStg.remove('userInfo');
}
