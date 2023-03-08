import {EmailVo} from "@/bean/vo/message/EmailVo";
import {UserVo} from "@/bean/vo/admin/UserVo";

interface UserInfo {
  user_uid: string;
  avatar: string,
  nickname: string;
  username: string;
  verify_email: boolean;
  authority: Array<string>;
  userRole: string;
	emailInfo: EmailVo;
	userDetailInfo: UserVo
}
interface OauthVo {
  access_token?: string;
  token_type?: string;
  refresh_token?: string;
  expires_in?: string;
  jti?: string;
  scope?: string;
  userInfo?: UserInfo;
}

export { OauthVo, UserInfo };
