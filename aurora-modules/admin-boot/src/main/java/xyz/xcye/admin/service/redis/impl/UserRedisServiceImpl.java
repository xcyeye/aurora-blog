package xyz.xcye.admin.service.redis.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.constant.RedisConstant;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.api.mail.sendmail.util.AccountInfoUtils;
import xyz.xcye.core.back.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.core.exception.user.UserException;

import java.time.Duration;

/**
 * @author qsyyke
 */

@Slf4j
@Service
public class UserRedisServiceImpl implements UserRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    private static final Digester md5 = new Digester(DigestAlgorithm.MD5);

    @Override
    public void storageUserVerifyAccountInfo(EmailVerifyAccountDTO verifyAccount, long expirationTime) {
        redisTemplate.opsForValue().set(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + verifyAccount.getUserUid(),verifyAccount,
                Duration.ofSeconds(expirationTime));
        log.info("存储用户验证信息到redis，键:{}，值:{}，过期时间:{}",
                RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + verifyAccount.getUserUid(),verifyAccount,expirationTime);
    }

    @Override
    public boolean updateUserVerifyAccountInfo(long userUid, String secretKey) throws UserException {
        // 查询用户是否绑定
        User user = userService.queryByUidContainPassword(userUid);
        if (user == null || !StringUtils.hasLength(secretKey) || user.getUid() == null) {
            return false;
        }
        // 通过username+userUid+salt验证前后的秘钥是否一直
        String md5Str = md5.digestHex(user.getUsername() + user.getUid() + AccountInfoUtils.SALT_SECRET_KEY);

        if (!md5Str.equals(secretKey)) {
            // 密码错误
            return false;
        }

        if (user.getVerifyEmail()) {
            return true;
        }

        EmailVerifyAccountDTO storageVerifyAccountInfo = (EmailVerifyAccountDTO) redisTemplate.opsForValue().get(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + user.getUid());
        if (storageVerifyAccountInfo != null) {
            User updateUser = User.builder().verifyEmail(true).uid(storageVerifyAccountInfo.getUserUid()).build();
            int updateUserNum = userService.updateUser(updateUser);

            // 更新失败
            if (updateUserNum != 1) {
                return false;
            }

            // 从redis中清除
            redisTemplate.delete(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + user.getUid());
            return true;
        }

        return false;
    }
}
