package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.UserRoleMapper;
import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.admin.service.UserRoleRelationshipService;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;
import java.util.Objects;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:11
 */

@Service
public class UserRoleRelationshipServiceImpl implements UserRoleRelationshipService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int deleteByUid(Long uid) {
        return userRoleMapper.deleteByUid(uid);
    }

    @Override
    public int insertUserRoleRelationship(UserRoleRelationship relationship) {
        relationshipIsFull(relationship);
        return userRoleMapper.insertUserRoleRelationship(relationship);
    }

    @Override
    public List<UserRoleRelationship> selectAllUserRoleRelationship(Condition<Long> condition) {
        return userRoleMapper.selectAllUserRoleRelationship(condition);
    }

    @Override
    public int updateUserRoleRelationship(UserRoleRelationship relationship) {
        relationshipIsFull(relationship);
        Objects.requireNonNull(relationship.getUid(), "需要传入一个uid");
        return userRoleMapper.updateUserRoleRelationship(relationship);
    }

    private void relationshipIsFull(UserRoleRelationship relationship) {
        Objects.requireNonNull(relationship,"用户角色关系信息不能为null");
        Objects.requireNonNull(relationship.getRoleUid(),"用户角色的uid不能为null");
        Objects.requireNonNull(relationship.getUserUid(),"用户角色的用户uid不能为null");
    }
}
