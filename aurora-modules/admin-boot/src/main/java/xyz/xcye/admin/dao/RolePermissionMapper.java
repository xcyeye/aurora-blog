package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.RolePermissionRelationship;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;

/**
* @author aurora
* @description 针对表【au_role_permission】的数据库操作Mapper
* @createDate 2022-05-04 16:17:47
* @Entity xyz.xcye.admin.po.RolePermissionRelationship
*/

@Mapper
public interface RolePermissionMapper {

    int deleteByUid(Long uid);

    int insertRolePermissionRelationship(RolePermissionRelationship relationship);


    int updateRolePermissionRelationship(RolePermissionRelationship relationship);

    /**
     * 其中uid => role_uid,otherUid => permission_uid
     * @param condition
     * @return
     */
    List<RolePermissionRelationship> selectAllRolePermissionRelationship(@Param("condition") Condition condition);

}
