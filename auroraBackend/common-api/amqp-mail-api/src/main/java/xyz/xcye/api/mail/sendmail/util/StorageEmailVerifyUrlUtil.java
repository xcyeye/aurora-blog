package xyz.xcye.api.mail.sendmail.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.AuroraSpringUtils;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.message.constant.VerifyUrlConstant;

import java.time.Duration;

/**
 * @author qsyyke
 */

public class StorageEmailVerifyUrlUtil {

    private static final Digester md5 = new Digester(DigestAlgorithm.MD5);

    public static boolean storageVerifyUrlToRedis(String key, Integer hashValue, long expirationSeconds, long userUid) {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        AuroraProperties.AuroraAuthProperties authProperties = getAuthProperties();

        // 无论redis中有没有，都重新设置
        // 进行md5编码
        String digestHex = md5.digestHex(hashValue + "", authProperties.getVerifySecretKey());
        redisTemplate.opsForValue().set(generateRedisKey(userUid, key), digestHex, Duration.ofSeconds(expirationSeconds));
        return checkKey(key, redisTemplate, userUid);
    }

    public static boolean verifyUrl(String incomingSecretKey) throws UserException {
        if (!StringUtils.hasLength(incomingSecretKey)) {
            return false;
        }

        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        // 对传入的incomingSecretKey进行base64解码 获取key和明文value
        String decodeStr = decodeStr(incomingSecretKey);
        // 获取key和value
        String key = getKey(decodeStr);
        String originValue = getOriginValue(decodeStr);
        long userUid = getUserUidFromVerifyPath(incomingSecretKey);

        // 查看redis中是否含有该key
        Boolean hasKey = redisTemplate.hasKey(generateRedisKey(userUid, key));
        if (Boolean.FALSE.equals(hasKey) || originValue == null) {
            return false;
        }
        // 将originValue值进行md5编码，和redis中的进行比较
        return verify(key, originValue, redisTemplate, getAuthProperties(), userUid);
    }

    public static long getUserUidFromVerifyPath(String incomingSecretKey) {
        Long userUid = getUserUid(decodeStr(incomingSecretKey));
        return userUid == null ? 0 : userUid;
    }

    public static boolean deleteKey(String incomingSecretKey) {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        String decodeStr = decodeStr(incomingSecretKey);
        String key = getKey(decodeStr);
        long userUid = getUserUidFromVerifyPath(incomingSecretKey);
        String redisKey = generateRedisKey(userUid, key);
        return Boolean.TRUE.equals(redisTemplate.delete(redisKey));
    }

    private static String getKey(String decodeStr) {
        return decodeStr.split(VerifyUrlConstant.VERIFY_URL_CONCAT_KEY_VALUE)[0];
    }

    /**
     * 获取原始的值，原始的值是一个hash值
     * @param decodeStr
     * @return
     */
    private static String getOriginValue(String decodeStr) {
        String[] strings = decodeStr.split(VerifyUrlConstant.VERIFY_URL_CONCAT_KEY_VALUE);
        return strings.length > 1 ? strings[1] : null;
    }

    private static Long getUserUid(String decodeStr) {
        String[] strings = decodeStr.split(VerifyUrlConstant.VERIFY_URL_CONCAT_KEY_VALUE);

        // userUid存储在第三个位置
        if (strings.length < 3) {
            return null;
        }
        return Long.parseLong(strings[2]);
    }

    private static String decodeStr(String incomingSecretKey) {
        return Base64.decodeStr(incomingSecretKey);
    }

    private static boolean verify(String key, String originValue, RedisTemplate<String, Object> redisTemplate,
                           AuroraProperties.AuroraAuthProperties auroraAuthProperties, long userUid) {
        String digestHex = md5.digestHex(originValue, auroraAuthProperties.getVerifySecretKey());
        String redisStorageValue = (String) redisTemplate.opsForValue().get(generateRedisKey(userUid, key));
        return redisStorageValue != null && redisStorageValue.equals(digestHex);
    }

    private static boolean checkKey(String key, RedisTemplate<String, Object> redisTemplate, long userUid) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(generateRedisKey(userUid, key)));
    }

    private static RedisTemplate<String, Object> getRedisTemplate() {
        return AuroraSpringUtils.getBean("redisTemplate", RedisTemplate.class);
    }

    private static AuroraProperties.AuroraAuthProperties getAuthProperties() {
        return AuroraSpringUtils.getBean(AuroraProperties.AuroraAuthProperties.class);
    }

    private static String generateRedisKey(long userUid, String key) {
        return VerifyUrlConstant.REDIS_STORAGE_VERIFY_URL_KEY_PREFIX + key + VerifyUrlConstant.VERIFY_URL_CONCAT_KEY_VALUE + userUid;
    }
}
