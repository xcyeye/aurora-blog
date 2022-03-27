package xyz.xcye.util.id.generator;

import xyz.xcye.enums.FieldLengthEnum;

import java.util.Random;

/**
 * 生成用户名
 * @author qsyyke
 */


public class UsernameGenerator {
    /**
     * 根据提供的用户名前缀，把当前时间戳添加到用户名前缀后面，返回一个符合长度的用户名
     * @param usernamePrefix 用户名前缀
     * @return 合法的用户名
     */
    public static String getUsername(String usernamePrefix) {
        //用户名前缀长度大于最大的用户名长度
        if (usernamePrefix.length() >= FieldLengthEnum.USERNAME) {
            return usernamePrefix.substring(0,FieldLengthEnum.USERNAME);
        }

        //用户名前缀小于最大的用户名长度
        int suffixLength = FieldLengthEnum.USERNAME - usernamePrefix.length();
        String timeMillisStr = System.currentTimeMillis() + "";

        //截取的将时间戳和用户名前缀进行凭借
        return usernamePrefix + timeMillisStr.substring(0,new Random().nextInt(suffixLength));
    }
}
