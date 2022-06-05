package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Role;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 20:59
 */


public interface RoleService {

    /**
     * 插入角色信息
     * @param role
     * @return
     */
    int insertRole(Role role);

    /**
     * 更新角色的状态
     * @param uid
     * @return
     */
    int updateRoleStatus(long uid, boolean status);

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 根据uid删除角色信息
     * @param uid
     * @return
     */
    int deleteByUid(long uid);

    /**
     * 根据自定义条件，查询满足要求的角色信息，只包含角色，不包含权限信息
     * @param condition
     * @return
     */
    PageData<Role> selectAllRole(Condition<Long> condition);

    /**
     * 根据uid查询
     * @param uid
     * @return
     */
    Role selectByUid(long uid);
}
