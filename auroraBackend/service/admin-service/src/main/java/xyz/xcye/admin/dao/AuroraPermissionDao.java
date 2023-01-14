package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.Permission;
import xyz.xcye.service.base.BaseDao;

/**
 * @table permission <br/>
 * @description permission 数据表DAO层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraPermissionDao extends BaseDao<Permission> {

}