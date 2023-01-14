package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.service.base.BaseDao;

/**
 * @table user_role <br/>
 * @description user_role 数据表DAO层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraUserRoleDao extends BaseDao<UserRoleRelationship> {

}