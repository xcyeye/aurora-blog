package xyz.xcye.wg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.common.entity.table.User;
import xyz.xcye.wg.dao.UserDao;
import xyz.xcye.wg.service.UserService;

/**
 * @author qsyyke
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User queryUserByUsername(String username) {
        return userDao.queryUserByUsername(username);
    }
}
