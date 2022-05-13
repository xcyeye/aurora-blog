package xyz.xcye.core.constant.oauth;

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

    /** 超级管理员的角色名 **/
    public static final String SUPER_ADMINISTRATOR_ROLE_NAME = "ROLE_root";

    /** 把一个已经验证的用户向下层服务传递时，存放在请求头中的用户信息名字 **/
    public static final String REQUEST_TOKEN_NAME = "authentication_user";
    /** 把一个已经验证的用户向下层服务传递时，存放在请求头中的jwt名字 **/
    public static final String REQUEST_JWT_TOKEN_NAME = "authentication_jwt";
    /** 网关处向下层服务传递时，判断当前请求地址是否为白名单，并将此标识设置到请求头上，类型为true或者false **/
    public static final String REQUEST_WHITE_URL_FLAG_NAME = "white_url_flag";
    /** 如果一个查询密码的请求是从认证中心发出的，那么会在请求头中添加一个标识，字符串的boolean值 **/
    public static final String REQUEST_OAUTH_SERVER_QUERY_PASSWORD = "oauth_query_pwd";
    /** 在网关层已认证的用户信息，用于将用户信息存储在RequestContextHolder **/
    public static final String REQUEST_STORAGE_JWT_USER_INFO_NAME = "authentication_jwt_user_info";

    public static final String AMQP_REQUEST_REQUEST_JWT_USER_INFO_NAME = "amqp_request_jwt_user_info";
}
