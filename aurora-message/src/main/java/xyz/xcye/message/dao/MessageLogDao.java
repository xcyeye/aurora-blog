package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.dto.ConditionDTO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface MessageLogDao {
    int insertMessageLog(@Param("messageDO") MessageLogDO messageLogDO);
    int deleteMessageLog(@Param("uid") long uid);
    int updateMessageLog(@Param("messageDO") MessageLogDO messageLogDO);

    /**
     *
     * @param condition 查询条件，其中keyword对应routingKey,status对应consume_status
     * @return
     */
    List<MessageLogDO> queryAllMessageLog(@Param("condition") ConditionDTO condition);

}
