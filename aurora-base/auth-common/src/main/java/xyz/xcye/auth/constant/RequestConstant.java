package xyz.xcye.auth.constant;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 16:23
 */


public class RequestConstant {
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

    public static final String AMQP_MESSAGE_PROPERTIES_WHITE_URL_FLAG = "amqp_message_properties_white_url";
    public static final String CONTEXT_AMQP_MESSAGE_PROPERTIES_WHITE_URL_FLAG = "context_amqp_message_properties_white_url";

    public static final String AMQP_REQUEST_REQUEST_JWT_USER_INFO_NAME = "amqp_request_jwt_user_info";
}
