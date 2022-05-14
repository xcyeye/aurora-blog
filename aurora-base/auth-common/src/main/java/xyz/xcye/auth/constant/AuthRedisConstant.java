package xyz.xcye.auth.constant;

/**
 * 认证中心中，将信息存入redis中的键名
 * @author qsyyke
 * @date Created in 2022/5/14 13:45
 */


public class AuthRedisConstant {
    /** redis中存储用户登录失败次数的键名前缀 **/
    public static final String USER_LOGIN_FAILURE_NUMBER_PREFIX = "login_failure_number:";
}
