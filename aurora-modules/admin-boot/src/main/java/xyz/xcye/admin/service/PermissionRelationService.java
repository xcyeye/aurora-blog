package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.vo.UserVO;

import java.util.Map;
import java.util.Set;

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
    Set<Map<String,String>> loadPermissionByUserUid(long userUid);

    /**
     * 根据角色名称，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]
     * @param roleName
     * @return
     */
    Set<Map<String,String>> loadPermissionByRoleName(String roleName);

    /**
     * 根据permissionPath，查询哪些角色可以访问
     * @param permissionPath
     * @return
     */
    Set<Role> queryRoleByPermissionPath(String permissionPath);

    /**
     * 根据此permissionPath，查询哪些用户可以访问
     * @param permissionPath
     * @return
     */
    Set<UserVO> queryUserByPermissionPath(String permissionPath);

    int insertUserRoleBatch(long[] userUidArr, long roleUid);
    int deleteUserRoleBatch(long userUid, long[] roleUidArr);
    int updateUserRole(long userUid, long originRoleUid, long newRoleUid);

    int insertRolePermissionBatch(long[] roleUidArr, long permissionUid);
    int deleteRolePermissionBatch(long roleUid, long[] permissionUidArr);
    int updateRolePermission(long roleUid, long originPermissionUid, long newPermissionUid);
}
