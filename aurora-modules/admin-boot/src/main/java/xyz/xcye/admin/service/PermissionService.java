package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Permission;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:16
 */

public interface PermissionService {
    int deleteByUid(long uid);

    int insertPermission(Permission permission);

    int updatePermission(Permission permission);

    PageData<Permission> selectAllPermission(Condition<Long> condition);

    Permission selectByUid(long uid);
}
