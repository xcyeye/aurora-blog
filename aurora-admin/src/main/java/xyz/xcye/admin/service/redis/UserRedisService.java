package xyz.xcye.admin.service.redis;

import xyz.xcye.common.dos.UserDO;
import xyz.xcye.common.exception.user.UserException;

/**
 * 这是使用redis操作用户的service层
 * @author qsyyke
 */

public interface UserRedisService {
    void storageUserVerifyAccountInfo(UserDO userDO, long expirationTime);
    boolean updateUserVerifyAccountInfo(long userUid) throws UserException;
}
