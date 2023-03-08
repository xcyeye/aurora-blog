package xyz.xcye.auth.enums;

/**
 * 这是和token相关的常量
 * @author qsyyke
 */

public class TokenConstant {
    /** 生成token时使用的盐 **/
    public static final String JWT_SECRET_KEY = "https://aurora.xcye.xyz";

    /** 生成token时的subject **/
    public static final String JWT_SUBJECT = "https://aurora.xcye.xyz";

    /** 将token放置在请求头中的名称 **/
    public static final String JWT_HEADER_TOKEN_NAME = "authentication_token";

    /** 需要验证的路径放在redis里面的名称 **/
    public static final String REDIS_STORAGE_VERIFY_PATH_LIST_NAME = "verify_path_list";

    /** 需要验证的路径放在redis里面的最大存储时间 单位秒 **/
    public static final Integer REDIS_STORAGE_VERIFY_PATH_LIST_MAX_TIME = 3600 * 24 * 3;

    /** 需要验证的路径放在redis里面的最小存储时间 单位秒 **/
    public static final Integer REDIS_STORAGE_VERIFY_PATH_LIST_MIN_TIME = 3600 * 24;
}