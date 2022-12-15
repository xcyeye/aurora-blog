package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.admin.pojo.UserRoleRelationshipPojo;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.data.entity.Condition;

import java.util.List;
import java.util.Objects;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:11
 */

@Service
public class UserRoleRelationshipService {
    @Autowired
    private AuroraUserRoleService auroraUserRoleService;

    public int deleteByUid(Long uid) {
        return auroraUserRoleService.deleteById(uid);
    }

    public void insertUserRoleRelationship(UserRoleRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        auroraUserRoleService.insert(BeanUtils.copyProperties(relationship, UserRoleRelationship.class));
    }

    public List<UserRoleRelationship> selectAllUserRoleRelationship(Condition<Long> condition) {
        return auroraUserRoleService.queryListByCondition(condition).getResult();
    }

    public int updateUserRoleRelationship(UserRoleRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        Objects.requireNonNull(relationship.getUid(), "需要传入一个uid");
        return auroraUserRoleService.updateById(BeanUtils.copyProperties(relationship, UserRoleRelationship.class));
    }

    private void relationshipIsFull(UserRoleRelationshipPojo relationship) {
        Objects.requireNonNull(relationship,"用户角色关系信息不能为null");
        Objects.requireNonNull(relationship.getRoleUid(),"用户角色的uid不能为null");
        Objects.requireNonNull(relationship.getUserUid(),"用户角色的用户uid不能为null");
    }
}
