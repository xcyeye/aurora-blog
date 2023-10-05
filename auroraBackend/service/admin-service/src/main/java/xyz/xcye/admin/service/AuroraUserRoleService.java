package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.AuroraUserRoleDao;
import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table user_role <br/>
 * @description user_role 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 */

@Service
public class AuroraUserRoleService extends BaseService<UserRoleRelationship> {
    @SuppressWarnings("unused")
    private AuroraUserRoleDao userRoleDao;

    @Autowired
    public void setInfoDao(AuroraUserRoleDao userRoleDao) {
        super.setBaseDao(userRoleDao);
        this.userRoleDao = userRoleDao;
    }
}