package xyz.xcye.auth.constant;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 16:23
 */


public class RequestConstant {
    /** 把一个已经验证的用户向下层服务传递时，存放在请求头中的用户信息名字 **/
    public static final String REQUEST_TOKEN_NAME = "authentication-user";
    /** 把一个已经验证的用户向下层服务传递时，存放在请求头中的jwt名字 **/
    public static final String REQUEST_JWT_TOKEN_NAME = "authentication-jwt";
    /** 网关处向下层服务传递时，判断当前请求地址是否为白名单，并将此标识设置到请求头上，类型为true或者false **/
    public static final String REQUEST_WHITE_URL_FLAG_NAME = "white-url-flag";

    /** 如果一个查询密码的请求是从认证中心发出的，那么会在请求头中添加一个标识，字符串的boolean值 **/
    public static final String REQUEST_OAUTH_SERVER_QUERY_PASSWORD = "oauth-query-pwd";
    /** 在网关层已认证的用户信息，用于将用户信息存储在RequestContextHolder **/
    public static final String REQUEST_STORAGE_JWT_USER_INFO_NAME = "authentication-jwt-user-info";
    /** 将白名单的状态放入消息中间件的message中 **/
    public static final String AMQP_MESSAGE_PROPERTIES_WHITE_URL_STATUS = "amqp-message-properties-white-url";
    /** 将白名单的状态存储在RequestContextHolder **/
    public static final String CONTEXT_WHITE_URL_STATUS = "context-amqp-message-properties-white-url";
    /** 将白名单的状态放入请求中，在网关的时候 **/
    public static final String REQUEST_WHITE_URL_STATUS = "request-amqp-message-properties-white-url";

    public static final String AMQP_REQUEST_REQUEST_JWT_USER_INFO_NAME = "amqp-request-jwt-user-info";
    /** 在认证服务器中，在请求头中存储生成的uid的名字 **/
    public static final String AUTH_SERVER_STORAGE_LOGIN_UID_NAME= "auth_storage_login_uid";
    public static final String AUTH_SERVER_STORAGE_LOGIN_USERNAME_NAME= "auth_storage_login_username";


    //
    public static final String REQUEST_OPEN_FEIGN_HEADER = "request-feign-header";
}
