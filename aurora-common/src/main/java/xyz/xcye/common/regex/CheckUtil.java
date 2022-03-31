package xyz.xcye.common.regex;

import xyz.xcye.common.enums.RegexEnum;

import java.util.regex.Pattern;

/**
 * 这是和邮件相关的正则表达式工具类
 * @author qsyyke
 */


public class CheckUtil {

    /**
     * 检查邮箱是否正确
     * @param mail 待检查的邮箱号
     * @return boolean
     */
    public static boolean isMail(String mail) {
        return Pattern.matches(RegexEnum.MAIL_REGEX.getRegex(),mail);
    }

    /**
     * 检查电话号码是否正确
     * @param photoNumber 电话号码，不能为空，调用前先判断
     * @return boolean
     */
    public static boolean isPhotoNumber(String photoNumber) {
        return Pattern.matches(RegexEnum.PHOTO_NUMBER_REGEX.getRegex(), photoNumber);
    }

    /**
     * 检查域名地址是否正确，必须有http或者https前缀
     * @param domainName 域名
     * @return boolean
     */
    public static boolean isDomainName(String domainName) {
        return Pattern.matches(RegexEnum.DOMAIN_REGEX.getRegex(), domainName);
    }

    /**
     * 检查ip地址是否正确
     * @param ip ip地址 127.0.0.1
     * @return boolean
     */
    public static boolean isIp(String ip) {
        return Pattern.matches(RegexEnum.IP_REGEX.getRegex(), ip);
    }

    /**
     * 检查用户名是否正确 5-16位
     * @param username 用户名
     * @return boolean
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(RegexEnum.USERNAME_REGEX.getRegex(), username);
    }

    /**
     * 检查传入的是否是一个正整数
     * @param number 数字
     * @return boolean
     */
    public static boolean ischeckInteger(int number) {
        String numberStr = number + "";
        return Pattern.matches(RegexEnum.INTEGER_REGEX.getRegex(), numberStr);
    }
}
