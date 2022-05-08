package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.vo.MessageLogVO;

/**
 * 操作消息中间件的消费消息日志
 * @author qsyyke
 */

public interface MessageLogService {
    /**
     * 插入一条mq发送的消息
     * @param messageLog
     * @return
     * @throws BindException 字段缺失或者某个字段不符合要求
     */
    int insertMessageLog(MessageLog messageLog) throws BindException;

    /**
     * 根据uid删除对应的mq消息 直接删除，不需要先设置删除状态
     * @param uid
     * @return
     */
    int deleteMessageLog(long uid);

    /**
     * 更新mq消息
     * @param messageLog
     * @return
     * @throws BindException 字段缺失或者不符合要求
     */
    int updateMessageLog(MessageLog messageLog) throws BindException;

    /**
     * 根据分页条件和自定义条件查询所有满足要求的数据
     * @param condition 查询条件，其中keyword对应routingKey,status对应consume_status
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    PageData<MessageLogVO> queryAllMessageLog(Condition<Long> condition);

    /**
     * 根据uid查询对应mq消息
     * @param uid
     * @return
     */
    MessageLogVO queryByUid(long uid);
}
