package xyz.xcye.admin.service;

import xyz.xcye.admin.dto.RolePermissionDTO;
import xyz.xcye.admin.po.Role;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * 权限关系service层
 * @author qsyyke
 * @date Created in 2022/5/4 22:46
 */

public interface PermissionRelationService {
    /**
     * 根据用户uid，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]
     * @param userUid
     * @return
     */
    List<RolePermissionDTO> loadPermissionByUserUid(long userUid);

    /**
     * 查询用户所拥有的角色
     */
    List<Role> loadAllRoleByUsername(String username);

    /**
     * 根据用户名，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]
     * @param username
     * @return
     */
    List<RolePermissionDTO> loadPermissionByUsername(String username);

    /**
     * 根据角色名称，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]
     * @param roleName
     * @return
     */
    List<RolePermissionDTO> loadPermissionByRoleName(String roleName);

    /**
     * 加载所有的角色-权限关系信息
     * @return
     */
    List<RolePermissionDTO> loadAllRolePermission(Condition<Long> condition);

    /**
     * 根据permissionPath，查询哪些角色可以访问
     * @param permissionPath
     * @return
     */
    List<RolePermissionDTO> queryRoleByPermissionPath(String permissionPath);

    int insertUserRoleBatch(long[] userUidArr, long roleUid);
    int deleteUserRoleBatch(long userUid, long[] roleUidArr);
    int updateUserRole(long userUid, long originRoleUid, long newRoleUid);

    int insertRolePermissionBatch(long[] roleUidArr, long permissionUid);
    int deleteRolePermissionBatch(long roleUid, long[] permissionUidArr);
    int updateRolePermission(long roleUid, long originPermissionUid, long newPermissionUid);
}
