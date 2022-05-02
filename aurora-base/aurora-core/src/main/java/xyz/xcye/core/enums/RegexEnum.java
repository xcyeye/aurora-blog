package xyz.xcye.core.enums;


/**
 * 正则表达式的枚举，存放所有需要的正则表达式
 * @author qsyyke
 */

public enum RegexEnum {

    /**
     * 验证邮箱 只允许英文字母、数字、下划线、英文句号、以及中划线组成
     */
    MAIL_REGEX("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"),

    /**
     * 电话号码 不能是12xxx这种形式
     */
    PHOTO_NUMBER_REGEX("^1(3|4|5|6|7|8|9)\\d{9}$"),

    /**
     * 域名 后缀的范围为2-6
     */
    DOMAIN_REGEX("^((http:)|(https:))?//([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}/*"),

    /**
     * IP地址
     */
    IP_REGEX("((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))"),

    /**
     * 用户名 字母开头，允许5-16字节，允许字母数字下划线
     */
    USERNAME_REGEX("^[a-zA-Z][a-zA-Z0-9_]{4,15}$"),

    /**
     * 正整数
     */
    INTEGER_REGEX("^[1-9]\\d*$");

    /**
     * 正则表达式
     */
    private final String regex;

    private RegexEnum(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
