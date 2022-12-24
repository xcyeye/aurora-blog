import RoleType = Auth.RoleType;
import {EmailVo} from "@/theme/vo/message/EmailVo";

interface UserInfo {
  user_uid: string;
  nickname: string;
  username: string;
  verify_email: boolean;
  authority: Array<string>;
  userRole: RoleType;
	emailInfo: EmailVo
}
interface OauthVo {
  access_token: string;
  token_type: string;
  refresh_token: string;
  expires_in: string;
  jti: string;
  scope: string;
  userInfo: UserInfo;
}

export { OauthVo, UserInfo };
