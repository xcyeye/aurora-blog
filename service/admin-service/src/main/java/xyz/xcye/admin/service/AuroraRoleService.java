package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.dao.AuroraRoleDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table role <br/>
 * @description role 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraRoleService extends BaseService<Role> {
	@SuppressWarnings("unused")
	private AuroraRoleDao roleDao;
	
	@Autowired
    public void setInfoDao(AuroraRoleDao roleDao) {
        super.setBaseDao(roleDao);
        this.roleDao = roleDao;
    }
}