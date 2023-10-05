package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.AdminRouter;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table admin_router <br/>
 * @description admin_router 数据表DAO层 <br/>
 * @date 2022-12-30 22:47:42 <br/>
 */

@Mapper
public interface AdminRouterDao extends BaseDao<AdminRouter> {

}