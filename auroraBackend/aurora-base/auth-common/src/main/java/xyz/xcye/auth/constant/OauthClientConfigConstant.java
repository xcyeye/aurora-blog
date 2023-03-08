package xyz.xcye.auth.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author xcye
 * @description
 * @date 2022-12-25 13:59:23
 */

public class OauthClientConfigConstant {
    /** 认证中心支持的授权类型 **/
    public static final List<String> AUTHORIZED_GRANT_TYPES =
            Arrays.asList("authorization_code","client_credentials","implicit","refresh_token","password");

}
