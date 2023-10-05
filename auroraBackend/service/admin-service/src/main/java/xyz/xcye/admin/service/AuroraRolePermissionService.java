package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.AuroraRolePermissionDao;
import xyz.xcye.admin.po.RolePermissionRelationship;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table role_permission <br/>
 * @description role_permission 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 */

@Service
public class AuroraRolePermissionService extends BaseService<RolePermissionRelationship> {
    @SuppressWarnings("unused")
    private AuroraRolePermissionDao rolePermissionDao;

    @Autowired
    public void setInfoDao(AuroraRolePermissionDao rolePermissionDao) {
        super.setBaseDao(rolePermissionDao);
        this.rolePermissionDao = rolePermissionDao;
    }
}