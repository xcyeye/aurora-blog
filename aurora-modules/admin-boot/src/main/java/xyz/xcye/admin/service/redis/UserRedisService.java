package xyz.xcye.admin.service.redis;

import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.exception.user.UserException;

/**
 * 这是使用redis操作用户的service层
 * @author qsyyke
 */

public interface UserRedisService {
    void storageUserVerifyAccountInfo(EmailVerifyAccountDTO verifyAccount, long expirationTime);
    boolean updateUserVerifyAccountInfo(long userUid, String secretKey) throws UserException, ReflectiveOperationException;
}
