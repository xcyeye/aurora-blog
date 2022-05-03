package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface MessageLogDao {
    int insertMessageLog(@Param("messageDO") MessageLog messageLog);
    int deleteMessageLog(@Param("uid") long uid);
    int updateMessageLog(@Param("messageDO") MessageLog messageLog);

    /**
     *
     * @param condition 查询条件，其中keyword对应routingKey,status对应consume_status
     * @return
     */
    List<MessageLog> queryAllMessageLog(@Param("condition") Condition condition);

}
