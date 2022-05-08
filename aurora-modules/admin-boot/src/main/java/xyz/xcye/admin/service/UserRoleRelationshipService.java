package xyz.xcye.admin.service;

import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:10
 */


public interface UserRoleRelationshipService {

    int deleteByUid(Long uid);

    int insertUserRoleRelationship(UserRoleRelationship relationship);

    /**
     * 其中uid => userUid，otherUid => roleUid
     * @param condition
     * @return
     */
    List<UserRoleRelationship> selectAllUserRoleRelationship(Condition<Long> condition);

    int updateUserRoleRelationship(UserRoleRelationship relationship);
}
