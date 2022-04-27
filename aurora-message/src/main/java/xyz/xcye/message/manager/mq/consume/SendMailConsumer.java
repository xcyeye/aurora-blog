package xyz.xcye.message.manager.mq.consume;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.vo.MessageLogVO;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.web.common.manager.mq.MistakeMessageSendService;

import javax.mail.MessagingException;
import javax.validation.groups.Default;
import java.io.IOException;

/**
 * 发送邮件的mq消费者
 * @author qsyyke
 * @date Created in 2022/4/27 14:30
 */

@Slf4j
@Component
public class SendMailConsumer {

    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageLogService messageLogService;
    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;
    @Autowired
    private ParseMessage parseMessage;

    /**
     * 消费发送html邮件的消费者
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = RabbitMQNameConstant.SEND_HTML_MAIL_QUEUE_NAME, ackMode = "MANUAL")
    public void sendHtmlMailConsumer(String msgJson, Channel channel, Message message)
            throws IOException, ReflectiveOperationException, BindException, MessagingException {
        // 待替换的key和value的map
        StorageSendMailInfo storageSendMailInfo = parseMessage.getStorageSendMailInfoFromMsg(msgJson,channel,message);
        if (!isLegitimateHtmlData(storageSendMailInfo)) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        }

        try {
            ValidationUtils.valid(storageSendMailInfo, Default.class);
        } catch (BindException e) {
            // 属性验证失败
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,false,"xyz.xcye.common.dto.StorageSendMailInfo.java对象中的属性字段不满足要求");
        }

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendHtmlMail(storageSendMailInfo);

        updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,true,null);
    }

    /**
     * 消费发送邮件的死信队列
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = RabbitMQNameConstant.SEND_HTML_MAIL_DEAD_LETTER_QUEUE_NAME, ackMode = "MANUAL")
    public void sendHtmlMailDeadLetterConsumer(String msgJson, Channel channel, Message message)
            throws ReflectiveOperationException, MessagingException, BindException, IOException {
        sendHtmlMailConsumer(msgJson,channel,message);
    }

    /**
     * 消费发送简单文本邮件的消费者
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = RabbitMQNameConstant.SEND_SIMPLE_TEXT_MAIL_QUEUE_NAME, ackMode = "MANUAL")
    public void sendSimpleTextMailConsumer(String msgJson, Channel channel, Message message) throws IOException, ReflectiveOperationException, BindException, MessagingException {
        // 从mq发送的消息中，解析出邮件发送的相关数据
        StorageSendMailInfo storageSendMailInfo = parseMessage.getStorageSendMailInfoFromMsg(msgJson,channel,message);
        if(!isLegitimateSimpleTextData(storageSendMailInfo)) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        }

        // 判断是否有标题，没有也不影响
        if (!StringUtils.hasLength(storageSendMailInfo.getSubject())) {
            storageSendMailInfo.setSubject("Aurora主题");
        }

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendSimpleMail(storageSendMailInfo.getReceiverEmail(),
                storageSendMailInfo.getSubject(), storageSendMailInfo.getSimpleText());

        updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,true,null);
    }

    /**
     * 消费发送简单文本邮件的死信队列
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = RabbitMQNameConstant.SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_QUEUE_NAME, ackMode = "MANUAL")
    public void sendSimpleTextMailDeadLetterConsumer(String msgJson, Channel channel, Message message)
            throws ReflectiveOperationException, MessagingException, BindException, IOException {
        sendSimpleTextMailConsumer(msgJson,channel,message);
    }

    /**
     * 更新数据库中的mq消息的信息
     * @param correlationDataId
     * @param ackStatus
     * @param consumeStatus
     * @param errorMessage
     * @throws BindException
     */
    private void updateMessageLogInfo(String correlationDataId, boolean ackStatus,
                                      boolean consumeStatus, String errorMessage)
            throws BindException, ReflectiveOperationException {
        MessageLogVO messageLogVO = messageLogService.queryByUid(Long.parseLong(correlationDataId));

        if (messageLogVO == null) {
            return;
        }
        messageLogVO.setAckStatus(ackStatus);
        messageLogVO.setConsumeStatus(consumeStatus);
        messageLogVO.setErrorMessage(errorMessage);
        messageLogService.updateMessageLog(BeanUtils.copyProperties(messageLogVO, MessageLogDO.class));
    }

    /**
     * 判断传入对象，是否是一个合法的发送简单文本邮件的对象
     * @param storageSendMailInfo
     * @return true是合法的，反之
     */
    private boolean isLegitimateSimpleTextData(StorageSendMailInfo storageSendMailInfo) {
        // 此方法是接收发送简单文本邮件的方法，没有简单文本内容，则判断为错误消息，可能不是我们发送的，不需要修改mysql中的相关记录
        if (storageSendMailInfo == null || !StringUtils.hasLength(storageSendMailInfo.getSimpleText())) {
            return false;
        }

        if (!StringUtils.hasLength(storageSendMailInfo.getReceiverEmail())) {
            return false;
        }
        return true;
    }

    /**
     * 判断传入对象是否是一个合法的发送html邮件的对象
     * @param storageSendMailInfo
     * @return
     */
    private boolean isLegitimateHtmlData(StorageSendMailInfo storageSendMailInfo) {
        if (storageSendMailInfo == null) {
            return false;
        }

        if (storageSendMailInfo.getReceiverEmail() == null) {
            return false;
        }

        return true;
    }
}
