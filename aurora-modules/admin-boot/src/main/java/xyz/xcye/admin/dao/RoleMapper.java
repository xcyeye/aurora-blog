package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.Role;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;

/**
* @author aurora
* @description 针对表【au_role】的数据库操作Mapper
* @createDate 2022-05-04 16:16:53
* @Entity Role.Role.Role
*/

@Mapper
public interface RoleMapper {

    int deleteByUid(Long uid);

    int insertRole(Role role);

    /**
     * 其中name => keyword，status => status
     * @param condition
     * @return
     */
    List<Role> selectAllRole(@Param("condition") Condition condition);

    int updateRole(Role role);

}
