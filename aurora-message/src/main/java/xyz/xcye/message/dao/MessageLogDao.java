package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.MessageLogDO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface MessageLogDao {
    int insertMessageLog(@Param("messageDO") MessageLogDO messageLogDO);
    int deleteMessageLog(@Param("uid") long uid);
    int updateMessageLog(@Param("messageDO") MessageLogDO messageLogDO);
    List<MessageLogDO> queryAllMessageLog(@Param("messageDO") MessageLogDO messageLogDO);
    MessageLogDO queryByUid(@Param("uid") long uid);

}
