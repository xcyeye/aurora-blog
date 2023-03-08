package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.dao.AuroraUserDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table user <br/>
 * @description user 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraUserService extends BaseService<User> {
	@SuppressWarnings("unused")
	private AuroraUserDao userDao;
	
	@Autowired
    public void setInfoDao(AuroraUserDao userDao) {
        super.setBaseDao(userDao);
        this.userDao = userDao;
    }
}