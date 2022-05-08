package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.Permission;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
* @author aurora
* @description 针对表【au_permission】的数据库操作Mapper
* @createDate 2022-05-04 16:18:15
* @Entity Permission.Permission.Permission
*/

@Mapper
public interface PermissionMapper {

    int deleteByUid(Long uid);

    int insertPermission(Permission permission);

    int updatePermission(Permission record);

    /**
     * uid => uid, path => keyword
     * @param condition
     * @return
     */
    List<Permission> queryAllPermission(@Param("condition") Condition condition);

}
