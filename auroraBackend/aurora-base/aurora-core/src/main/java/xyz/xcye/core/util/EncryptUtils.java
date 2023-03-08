package xyz.xcye.core.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 密码加密，将明文密码使用md5进行加密
 * @author qsyyke
 */


public class EncryptUtils {
    public static String encrypt(String password,String salt) {
        password = password + "-" + salt;
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(password);
    }
}
