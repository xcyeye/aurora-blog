package xyz.xcye.auth.constant;

/**
 * oauth的相关常量
 * @author qsyyke
 * @date Created in 2022/5/8 23:00
 */


public class OauthJwtConstant {
    /** 设置jwt中存储额外数据的键名 **/
    public static final String USERNAME = "username";
    public static final String USER_UID = "user_uid";
    public static final String NICKNAME = "nickname";
    public static final String VERIFY_EMAIL = "verify_email";
    public static final String AUTHORITY = "authority";

    /** 超级管理员的角色名 **/
    public static final String SUPER_ADMINISTRATOR_ROLE_NAME = "ROLE_root";

    /** 处理登录操作的url **/
    public static final String LOGIN_PROCESS_URL = "/auth/login";

    /** 请求中，在cookie里存储用户登录状态的键名 **/
    public static final String COOKIE_STORAGE_LOGIN_SUCCESS_STATUS = "login_status";

    /** cookie中存储cookie的秘钥 **/
    public static final String STORAGE_COOKIE_SECRET_KEY = "aurora.cookie.secrey";

    /** 静态白名单资源 **/
    public static final String[] PUBLIC_STATIC_RESOURCE = {"css","js","html", "png"};
}
