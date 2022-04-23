package xyz.xcye.admin.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.constant.RedisConstant;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.common.dos.UserDO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.exception.user.UserException;

import java.time.Duration;

/**
 * @author qsyyke
 */

@Service
public class UserRedisServiceImpl implements UserRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public void storageUserVerifyAccountInfo(UserDO userDO, long expirationTime) {
        redisTemplate.opsForValue().set(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + userDO.getUid(),userDO,
                Duration.ofMillis(expirationTime));
    }

    @Override
    public boolean updateUserVerifyAccountInfo(long userUid) throws UserException {
        UserDO storageUserDO = (UserDO) redisTemplate.opsForValue().get(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + userUid);
        if (storageUserDO != null) {
            UserDO userDO = UserDO.builder().verifyEmail(true).uid(storageUserDO.getUid()).build();
            ModifyResult modifyResult = userService.updateUser(userDO);

            // 判断更新状态
            if (modifyResult.getUid() != storageUserDO.getUid()) {
                return false;
            }

            return true;
        }

        return false;
    }
}
