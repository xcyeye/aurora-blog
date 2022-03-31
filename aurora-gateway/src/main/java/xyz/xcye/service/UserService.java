package xyz.xcye.service;

import xyz.xcye.common.entity.table.User;

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
    User queryUserByUsername(String username);
}
