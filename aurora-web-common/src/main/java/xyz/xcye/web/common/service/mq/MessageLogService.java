package xyz.xcye.web.common.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class MessageLogService {

    @Autowired
    private MessageLogFeignService messageLogFeignService;

    public void remoteInsertMessageLog(String message,long uid,String exchange,
                                        String queue,String routingKey,boolean ackStatus,
                                        int tryCount,String exchangeType,boolean consumeStatus,
                                        String errorMessage) throws BindException {
        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(message, uid, exchange, queue,
                routingKey, ackStatus, tryCount, exchangeType,
                consumeStatus, errorMessage);

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        ModifyResult modifyResult = messageLogFeignService.insertMessageLog(messageLogDO);
        log.info("使用feign插入mq消息发送日志: {}",modifyResult);
    }

    private MessageLogDO setMessageLogDO(String message,long uid,String exchange,
                                         String queue,String routingKey,boolean ackStatus,
                                         int tryCount,String exchangeType,boolean consumeStatus,
                                         String errorMessage) {
        MessageLogDO messageLogDO = new MessageLogDO();
        messageLogDO.setMessage(message);
        messageLogDO.setUid(uid);
        messageLogDO.setExchange(exchange);
        messageLogDO.setQueue(queue);
        messageLogDO.setRoutingKey(routingKey);
        messageLogDO.setAckStatus(ackStatus);
        messageLogDO.setTryCount(tryCount);
        messageLogDO.setExchangeType(exchangeType);
        messageLogDO.setConsumeStatus(consumeStatus);
        messageLogDO.setErrorMessage(errorMessage);
        return messageLogDO;
    }
}
