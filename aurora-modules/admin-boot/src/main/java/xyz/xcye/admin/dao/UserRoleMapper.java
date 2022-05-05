package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;

/**
* @author aurora
* @description 针对表【au_user_role】的数据库操作Mapper
* @createDate 2022-05-04 16:18:03
* @Entity UserRoleRelationship.UserRoleRelationship.UserRoleRelationship
*/

@Mapper
public interface UserRoleMapper {

    int deleteByUid(Long uid);

    int insertUserRoleRelationship(UserRoleRelationship relationship);

    /**
     * 其中uid => userUid，otherUid => roleUid
     * @param condition
     * @return
     */
    List<UserRoleRelationship> selectAllUserRoleRelationship(@Param("condition") Condition condition);

    int updateUserRoleRelationship(UserRoleRelationship relationship);
}
