package xyz.xcye.file.enums;

/**
 * 这是和token相关的常量
 * @author qsyyke
 */

public class TokenEnum {
    /** 生成token时使用的盐 **/
    public static final String JWT_SECRET_KEY = "https://aurora.xcye.xyz";

    /** 生成token时的subject **/
    public static final String JWT_SUBJECT = "https://aurora.xcye.xyz";

    /** 将token放置在请求头中的名称 **/
    public static final String JWT_HEADER_TOKEN_NAME = "authentication_token";
}
