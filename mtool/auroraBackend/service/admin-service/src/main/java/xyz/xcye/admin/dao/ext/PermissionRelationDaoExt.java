package xyz.xcye.admin.dao.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.dto.RolePermissionDTO;
import xyz.xcye.admin.po.Role;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/10 13:09
 */


@Mapper
public interface PermissionRelationDaoExt {
    /**
     * 根据用户uid，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]
     * @param userUid
     * @return
     */
    List<RolePermissionDTO> loadPermissionByUserUid(@Param("userUid") long userUid);

    /**
     * 根据角色名称，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]
     * @param roleName
     * @return
     */
    List<RolePermissionDTO> loadPermissionByRoleName(@Param("roleName") String roleName);

    List<Role> loadAllRoleByUserUid(@Param("uid") long uid);

    /**
     * 加载所有的角色-权限关系信息
     * @return
     */
    List<RolePermissionDTO> loadAllRolePermission(@Param("condition") Condition condition);

    List<RolePermissionDTO> loadRolePermissionRelByRoleUid(Long roleUid);

    /**
     * 根据permissionPath，查询哪些角色可以访问
     * @param permissionPath
     * @return
     */
    List<RolePermissionDTO> queryRoleByPermissionPath(@Param("permissionPath") String permissionPath);
}
