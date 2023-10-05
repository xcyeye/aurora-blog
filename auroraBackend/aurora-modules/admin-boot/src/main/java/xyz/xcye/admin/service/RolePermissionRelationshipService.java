package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.RolePermissionRelationship;
import xyz.xcye.admin.pojo.RolePermissionRelationshipPojo;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.data.entity.Condition;

import java.util.List;
import java.util.Objects;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:22
 */

@Service
public class RolePermissionRelationshipService {

    @Autowired
    private AuroraRolePermissionService auroraRolePermissionService;

    public int deleteByUid(Long uid) {
        Objects.requireNonNull(uid, "需要传入uid");
        return auroraRolePermissionService.deleteById(uid);
    }

    public void insertRolePermissionRelationship(RolePermissionRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        auroraRolePermissionService.insert(BeanUtils.copyProperties(relationship, RolePermissionRelationship.class));
    }

    public int updateRolePermissionRelationship(RolePermissionRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        Objects.requireNonNull(relationship.getUid(), "需要传入uid");
        return auroraRolePermissionService.updateById(BeanUtils.copyProperties(relationship, RolePermissionRelationship.class));
    }

    public List<RolePermissionRelationship> selectAllRolePermissionRelationship(Condition<Long> condition) {
        return auroraRolePermissionService.queryListByCondition(condition).getResult();
    }

    private void relationshipIsFull(RolePermissionRelationshipPojo relationship) {
        Objects.requireNonNull(relationship, "用户角色关系信息不能为null");
        Objects.requireNonNull(relationship.getRoleUidArr(), "用户角色的uid不能为null");
        Objects.requireNonNull(relationship.getPermissionUidArr(), "用户角色的用户uid不能为null");
    }
}
