package xyz.xcye.amqp.api;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.amqp.config.service.MistakeMessageSendService;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.feign.config.service.MessageLogFeignService;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.pojo.MessageLogPojo;
import xyz.xcye.message.vo.MessageLogVO;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 12:27
 */

@Component
public class ConfirmUpdateMessageAckStatus {

    @Autowired
    private MessageLogFeignService messageLogFeignService;
    @Autowired
    private MessageLogFeignService.UpdateMessageLog updateMessageLog;
    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;

    public void updateConfirmMessageAckStatus(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData == null) {
            // 不合法的消息，不做任何处理
            return;
        }
        if (ack) {
            successAck(correlationData);
            return;
        }
        failureAck(correlationData, cause);
    }

    private void successAck(CorrelationData correlationData) {
        MessageLogPojo messageLogPojo = getMessageLog(correlationData);
        if (messageLogPojo == null) {
            return;
        }

        //更新消息投递状态
        messageLogPojo.setAckStatus(true);
        try {
            messageLogFeignService.updateMessageLog(messageLogPojo);
        } catch (BindException e) {
            e.printStackTrace();
            // 可能因为服务未开启或者是网络不可用，造成的异常
        }
    }

    private void failureAck(CorrelationData correlationData, String cause) {
        /*
         * 可能是由于routingKey错误或者是交换机的名字错误，导致交换机没有找到
         * 如果在生产者那里，交换机的名字写错了，那么此处ack的值会为false和returnedMessage都会执行，但是如果只是由于routingKey错误，
         * 交换机正常，那么就只会导致ack的值为false
         * */
        MessageLogPojo messageLogPojo = getMessageLog(correlationData);
        if (messageLogPojo == null) {
            return;
        }

        //更新消息投递状态
        messageLogPojo.setAckStatus(false);
        //设置错误消息
        messageLogPojo.setErrorMessage(cause);
        try {
            messageLogFeignService.updateMessageLog(messageLogPojo);
        } catch (BindException e) {
            e.printStackTrace();
            // 可能会由于网络不可用，造成异常
        }
    }

    private MessageLogPojo getMessageLog(CorrelationData correlationData) {
        String correlationDataId = correlationData.getId();
        //向au_message_log表中插入信息
        MessageLogVO messageLog = getMessageLogFromRemote(correlationDataId);
        //如果messageLogDO为null，则可能是在生产消息的时候，出现什么错误，没有将数据添加到数据库中
        if (messageLog == null || messageLog.getUid() == null) {
            return null;
        }
        return BeanUtils.copyProperties(messageLog, MessageLogPojo.class);
    }

    /**
     * 从数据库中获取mq消息
     * @param correlationDataId
     * @return
     */
    private MessageLogVO getMessageLogFromRemote(String correlationDataId) {
        long uid = 0L;
        try {
            uid = Long.parseLong(correlationDataId);
        } catch (NumberFormatException e) {
            LogUtils.logExceptionInfo(e);
            return null;
        }

        MessageLogVO messageLogVO = null;
        try {
            MessageLogPojo pojo = new MessageLogPojo();
            pojo.setUid(uid);
            R r = messageLogFeignService.queryMessageLogByUid(pojo);
            messageLogVO = JSONUtils.parseObjFromResult(r, "data", MessageLogVO.class);
        } catch (Exception e) {
            return null;
        }
        return messageLogVO;
    }
}
