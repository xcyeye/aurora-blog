package xyz.xcye.wg.service;

import xyz.xcye.common.dos.UserDO;
import xyz.xcye.wg.dto.SecurityUserDTO;

/**
 * 用户service层，在网关中，只有查询用户的操作
 * @author qsyyke
 */

public interface UserService {

    /**
     * 通过唯一uid查询用户的信息
     * @param username 用户名
     * @return usershiti
     */
    SecurityUserDTO queryUserByUsername(String username);
}
