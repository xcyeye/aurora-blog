package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.RolePermissionMapper;
import xyz.xcye.admin.po.RolePermissionRelationship;
import xyz.xcye.admin.service.RolePermissionRelationshipService;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;
import java.util.Objects;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:22
 */

@Service
public class RolePermissionRelationshipServiceImpl implements RolePermissionRelationshipService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public int deleteByUid(Long uid) {
        Objects.requireNonNull(uid, "需要传入uid");
        return rolePermissionMapper.deleteByUid(uid);
    }

    @Override
    public int insertRolePermissionRelationship(RolePermissionRelationship relationship) {
        relationshipIsFull(relationship);
        return rolePermissionMapper.insertRolePermissionRelationship(relationship);
    }

    @Override
    public int updateRolePermissionRelationship(RolePermissionRelationship relationship) {
        relationshipIsFull(relationship);
        Objects.requireNonNull(relationship.getUid(), "需要传入uid");
        return rolePermissionMapper.updateRolePermissionRelationship(relationship);
    }

    @Override
    public List<RolePermissionRelationship> selectAllRolePermissionRelationship(Condition<Long> condition) {
        return rolePermissionMapper.selectAllRolePermissionRelationship(condition);
    }

    private void relationshipIsFull(RolePermissionRelationship relationship) {
        Objects.requireNonNull(relationship,"用户角色关系信息不能为null");
        Objects.requireNonNull(relationship.getRoleUid(),"用户角色的uid不能为null");
        Objects.requireNonNull(relationship.getPermissionUid(),"用户角色的用户uid不能为null");
    }
}
