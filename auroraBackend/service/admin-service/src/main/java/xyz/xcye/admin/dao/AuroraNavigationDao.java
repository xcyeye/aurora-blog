package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.Navigation;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table navigation <br/>
 * @description navigation 数据表DAO层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 */

@Mapper
public interface AuroraNavigationDao extends BaseDao<Navigation> {

}