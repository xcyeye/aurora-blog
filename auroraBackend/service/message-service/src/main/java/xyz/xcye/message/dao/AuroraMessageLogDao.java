package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table message_log <br/>
 * @description message_log 数据表DAO层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 */

@Mapper
public interface AuroraMessageLogDao extends BaseDao<MessageLog> {

}