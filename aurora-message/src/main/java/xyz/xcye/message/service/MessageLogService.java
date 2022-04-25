package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.MessageLogVO;

import java.util.List;

/**
 * 操作消息中间件的消费消息日志
 * @author qsyyke
 */

public interface MessageLogService {
    /**
     * 插入一条mq发送的消息
     * @param messageLogDO
     * @return
     * @throws BindException 字段缺失或者某个字段不符合要求
     */
    ModifyResult insertMessageLog(MessageLogDO messageLogDO) throws BindException;

    /**
     * 根据uid删除对应的mq消息 直接删除，不需要先设置删除状态
     * @param uid
     * @return
     */
    ModifyResult deleteMessageLog(long uid);

    /**
     * 更新mq消息
     * @param messageLogDO
     * @return
     * @throws BindException 字段缺失或者不符合要求
     */
    ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException;

    /**
     * 根据分页条件和自定义条件查询所有满足要求的数据
     * @param condition 查询条件，其中keyword对应routingKey,status对应consume_status
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    List<MessageLogVO> queryAllMessageLog(ConditionDTO<Long> condition)
            throws InstantiationException, IllegalAccessException;

    /**
     * 根据uid查询对应mq消息
     * @param uid
     * @return
     */
    MessageLogVO queryByUid(long uid) throws InstantiationException, IllegalAccessException;
}
