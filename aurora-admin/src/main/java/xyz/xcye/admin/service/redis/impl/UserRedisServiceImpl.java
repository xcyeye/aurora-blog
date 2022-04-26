package xyz.xcye.admin.service.redis.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.constant.RedisConstant;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.UserDO;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void storageUserVerifyAccountInfo(EmailVerifyAccountDTO verifyAccount, long expirationTime) {
        redisTemplate.opsForValue().set(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + verifyAccount.getUserUid(),verifyAccount,
                Duration.ofSeconds(expirationTime));
        log.info("存储用户验证信息到redis，键:{}，值:{}，过期时间:{}",
                RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + verifyAccount.getUserUid(),verifyAccount,expirationTime);
    }

    @Override
    public boolean updateUserVerifyAccountInfo(String username, String password)
            throws UserException, ReflectiveOperationException {
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            return false;
        }
        // 查询用户是否绑定
        UserDO userDO = userService.queryByUidContainPassword(username);
        // 进行密码匹配
        boolean matches = passwordEncoder.matches(password, userDO.getPassword());
        if (!matches) {
            // 密码错误
            log.error("{},{}的密码错误，",username,password);
            return false;
        }

        if (userDO.getVerifyEmail()) {
            return true;
        }

        EmailVerifyAccountDTO storageVerifyAccountInfo = (EmailVerifyAccountDTO) redisTemplate.opsForValue().get(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + userDO.getUid());
        if (storageVerifyAccountInfo != null) {
            UserDO updateUserDO = UserDO.builder().verifyEmail(true).uid(storageVerifyAccountInfo.getUserUid()).build();
            ModifyResult modifyResult = userService.updateUser(updateUserDO);

            // 判断更新状态
            if (modifyResult.getUid() != storageVerifyAccountInfo.getUserUid()) {
                return false;
            }

            // 从redis中清楚
            redisTemplate.delete(RedisConstant.STORAGE_VERIFY_ACCOUNT_PREFIX + userDO.getUid());
            return true;
        }

        return false;
    }
}
