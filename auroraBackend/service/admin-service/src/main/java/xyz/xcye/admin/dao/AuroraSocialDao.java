package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.Social;
import xyz.xcye.service.base.BaseDao;

/**
 * @table social <br/>
 * @description social 数据表DAO层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraSocialDao extends BaseDao<Social> {

}