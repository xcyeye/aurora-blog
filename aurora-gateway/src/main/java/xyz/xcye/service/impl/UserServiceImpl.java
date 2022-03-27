package xyz.xcye.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.dao.UserDao;
import xyz.xcye.entity.table.User;
import xyz.xcye.service.UserService;

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
