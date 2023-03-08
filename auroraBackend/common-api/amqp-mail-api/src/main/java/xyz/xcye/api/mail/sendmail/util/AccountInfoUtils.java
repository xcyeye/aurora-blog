package xyz.xcye.api.mail.sendmail.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import xyz.xcye.message.constant.VerifyUrlConstant;

/**
 * 和用户相关的工具类
 * @author qsyyke
 */


public class AccountInfoUtils {

    public static final String SALT_SECRET_KEY = "[aurora]";

    private static final Digester md5 = new Digester(DigestAlgorithm.MD5);

    /**
     * 生成一个邮件中验证账户的链接，比如userUid为1111,则生成的应该是http://localhost:8088/verifyPrefixPath/xxxx
     */
    public static String generateVerifyUrl(long userUid, String redisKey, Integer hashValue, String verifyPrefixPath) {
        verifyPrefixPath = getCorrectPrefixPath(verifyPrefixPath);
        // 对redisKey:verifyValue:userUid进行base64编码
        String encodeVerifyValue = encodeVerifyValue(userUid, redisKey, hashValue);
        return verifyPrefixPath + "/" + encodeVerifyValue;
    }

    /**
     * 获取正确的邮件验证的地址前缀，如果传入的不是一个http|https地址，会返回null
     * @param verifyPrefixPath
     * @return
     */
    private static String getCorrectPrefixPath(String verifyPrefixPath) {
        // 此处没有进行网址的验证，因为如果在本地，会使用localhsot进行测试
        /*if (!Pattern.matches(RegexEnum.DOMAIN_REGEX.getRegex(), emailPrefixPath)) {
            // 地址不对
            return null;
        }*/

        // 判断是否以/结尾
        if (verifyPrefixPath.endsWith("/")) {
            verifyPrefixPath = verifyPrefixPath.substring(0,verifyPrefixPath.length() -1);
        }
        return verifyPrefixPath;
    }

    private static String encodeVerifyValue(long userUid, String redisKey, Integer hashValue) {
        return Base64.encode(redisKey + VerifyUrlConstant.VERIFY_URL_CONCAT_KEY_VALUE +
                hashValue + VerifyUrlConstant.VERIFY_URL_CONCAT_KEY_VALUE + userUid);
    }
}
