package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Role;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 20:59
 */


public interface RoleService {
    int insertRole(Role role);

    /**
     * 更新角色的状态
     * @param uid
     * @return
     */
    int updateRoleStatus(long uid, boolean status);

    int updateRole(Role role);

    int deleteByUid(long uid);

    PageData<Role> selectAllRole(Condition<Long> condition);

    Role selectByUid(long uid);
}
