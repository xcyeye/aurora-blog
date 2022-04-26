package xyz.xcye.admin.util;

/**
 * 和用户相关的工具类
 * @author qsyyke
 */


public class AccountInfoUtils {

    /**
     * 生成一个邮件中验证账户的链接，比如userUid为1111,则生成的应该是http://localhost:8088/admin/verifyAccount/email/1111
     * @param userUid
     * @param emailPrefixPath
     * @return
     */
    public static String generateVerifyAccountPath(long userUid, String emailPrefixPath) {
        emailPrefixPath = getCorrectPrefixPath(emailPrefixPath);
        return emailPrefixPath + "/" + userUid;
    }

    /**
     * 获取正确的邮件验证的地址前缀，如果传入的不是一个http|https地址，会返回null
     * @param emailPrefixPath
     * @return
     */
    private static String getCorrectPrefixPath(String emailPrefixPath) {
        // 此处没有进行网址的验证，因为如果在本地，会使用localhsot进行测试
        /*if (!Pattern.matches(RegexEnum.DOMAIN_REGEX.getRegex(), emailPrefixPath)) {
            // 地址不对
            return null;
        }*/

        // 判断是否以/结尾
        if (emailPrefixPath.endsWith("/")) {
            emailPrefixPath = emailPrefixPath.substring(0,emailPrefixPath.length() -1);
        }
        return emailPrefixPath;
    }
}
