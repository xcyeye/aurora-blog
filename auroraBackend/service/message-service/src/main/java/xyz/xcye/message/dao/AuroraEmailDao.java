package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.message.po.Email;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table email <br/>
 * @description email 数据表DAO层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 */

@Mapper
public interface AuroraEmailDao extends BaseDao<Email> {

}