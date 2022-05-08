package xyz.xcye.admin.service;

import xyz.xcye.admin.po.RolePermissionRelationship;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:22
 */

public interface RolePermissionRelationshipService {
    int deleteByUid(Long uid);

    int insertRolePermissionRelationship(RolePermissionRelationship relationship);


    int updateRolePermissionRelationship(RolePermissionRelationship relationship);

    /**
     * 其中uid => role_uid,otherUid => permission_uid
     * @param condition
     * @return
     */
    List<RolePermissionRelationship> selectAllRolePermissionRelationship(Condition<Long> condition);
}
