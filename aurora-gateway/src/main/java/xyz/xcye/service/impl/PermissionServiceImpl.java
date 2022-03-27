package xyz.xcye.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.dao.PermissionDao;
import xyz.xcye.entity.table.Permission;
import xyz.xcye.service.PermissionService;

import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission insertPermission(Permission permission) {
        int insertUid = permissionDao.insertPermission(permission);
        permission.setUid(insertUid);
        return permission;
    }

    @Override
    public Permission updatePermissionByUid(Permission permission) {
        return permission;
    }

    @Override
    public boolean deletePermissionByUid(int uid) {
        int i = permissionDao.deletePermissionByUid(uid);
        return i != 0;
    }

    @Override
    public List<Permission> queryAllRole() {

        List<Permission> permissions = permissionDao.queryAllRole();
        return permissions;
    }
}
