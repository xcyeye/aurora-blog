package xyz.xcye.admin.service.redis.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.constant.RedisConstant;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.common.entity.table.UserDO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.exception.user.UserException;

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

    @Override
    public void storageUserVerifyAccountInfo(EmailVerifyAccountDTO verifyAccount, long expirationTime) {
        redisTemplate.opsForValue().set(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + verifyAccount.getUserUid(),verifyAccount,
                Duration.ofMillis(expirationTime));
        log.info("存储用户验证信息到redis，键:{}，值:{}，过期时间:{}",
                RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + verifyAccount.getUserUid(),verifyAccount,expirationTime);
    }

    @Override
    public boolean updateUserVerifyAccountInfo(long userUid) throws UserException {
        EmailVerifyAccountDTO storageVerifyAccountInfo = (EmailVerifyAccountDTO) redisTemplate.opsForValue().get(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + userUid);
        if (storageVerifyAccountInfo != null) {
            UserDO userDO = UserDO.builder().verifyEmail(true).uid(storageVerifyAccountInfo.getUserUid()).build();
            ModifyResult modifyResult = userService.updateUser(userDO);

            // 判断更新状态
            if (modifyResult.getUid() != storageVerifyAccountInfo.getUserUid()) {
                return false;
            }

            return true;
        }

        return false;
    }
}
