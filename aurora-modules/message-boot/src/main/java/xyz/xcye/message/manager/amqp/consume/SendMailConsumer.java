package xyz.xcye.message.manager.amqp.consume;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.amqp.config.service.MistakeMessageSendService;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.util.StorageMailUtils;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.message.vo.MessageLogVO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

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
    @RabbitListener(queues = AmqpQueueNameConstant.SEND_HTML_MAIL_QUEUE_NAME, ackMode = "MANUAL")
    public void sendHtmlMailConsumer(String msgJson, Channel channel, Message message)
            throws Exception {
        // 待替换的key和value的map
        StorageSendMailInfo storageSendMailInfo = parseMessage.getStorageSendMailInfoFromMsg(msgJson,channel,message);
        if (!isLegitimateHtmlData(storageSendMailInfo)) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message,
                    AmqpExchangeNameConstant.MISTAKE_MESSAGE_EXCHANGE, AmqpQueueNameConstant.MISTAKE_MESSAGE_ROUTING_KEY);
            return;
        }

        if (storageSendMailInfo.getUserUid() == null) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message,
                    AmqpExchangeNameConstant.MISTAKE_MESSAGE_EXCHANGE, AmqpQueueNameConstant.MISTAKE_MESSAGE_ROUTING_KEY);
            updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,false,"邮件发送中，没有传入userUid");
        }

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        try {
            sendMailService.sendHtmlMail(storageSendMailInfo);
        } catch (MessagingException | ReflectiveOperationException | IOException | EmailException e) {
            // 如果发送html的过程中，发生异常，那么修改mq的消息记录
            updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,true,
                    Optional.ofNullable(e.getMessage()).orElse("发送html邮件过程中，发生了异常"));
            throw new Exception(e.getMessage());
        }

        isReplyComment(storageSendMailInfo);
        updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,true,null);
    }

    /**
     * 消费发送邮件的死信队列
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = AmqpQueueNameConstant.SEND_HTML_MAIL_DEAD_LETTER_QUEUE_NAME, ackMode = "MANUAL")
    public void sendHtmlMailDeadLetterConsumer(String msgJson, Channel channel, Message message)
            throws Exception {
        sendHtmlMailConsumer(msgJson,channel,message);
    }

    /**
     * 消费发送简单文本邮件的消费者
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_QUEUE_NAME, ackMode = "MANUAL")
    public void sendSimpleTextMailConsumer(String msgJson, Channel channel, Message message) throws IOException, ReflectiveOperationException, BindException, MessagingException {
        // 从mq发送的消息中，解析出邮件发送的相关数据
        StorageSendMailInfo storageSendMailInfo = parseMessage.getStorageSendMailInfoFromMsg(msgJson,channel,message);
        if(!isLegitimateSimpleTextData(storageSendMailInfo)) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message,
                    AmqpExchangeNameConstant.MISTAKE_MESSAGE_EXCHANGE, AmqpQueueNameConstant.MISTAKE_MESSAGE_ROUTING_KEY);
            return;
        }

        // 判断是否有标题，没有也不影响
        if (!StringUtils.hasLength(storageSendMailInfo.getSubject())) {
            storageSendMailInfo.setSubject("Aurora主题");
        }

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        sendMailService.sendSimpleMail(storageSendMailInfo.getReceiverEmail(),
                storageSendMailInfo.getSubject(), storageSendMailInfo.getSimpleText());

        updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,true,null);
    }

    /**
     * 消费发送简单文本邮件的死信队列
     * @param msgJson
     * @param channel
     * @param message
     */
    @RabbitListener(queues = AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_QUEUE_NAME, ackMode = "MANUAL")
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
        messageLogService.updateMessageLog(BeanUtils.copyProperties(messageLogVO, MessageLog.class));
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

        return true;
    }

    /**
     * 判断是否是回复评论的mq消息，如果是，那么还需要发送消息给被回复的这个评论的用户
     * @param storageSendMailInfo
     * @return
     */
    private void isReplyComment(StorageSendMailInfo storageSendMailInfo)
            throws ReflectiveOperationException, MessagingException, EmailException, IOException {
        if (storageSendMailInfo.getSendType() != SendHtmlMailTypeNameEnum.REPLY_COMMENT) {
            return;
        }

        // 是回复评论，则组装一个新的数据，用于发送收到评论的邮件
        Map<String, Object> additionalData = storageSendMailInfo.getAdditionalData();
        if (additionalData == null) {
            // 可能会存在用户消息乱发送请求，则直接跳过
            return;
        }

        JSONObject jsonObject = JSON.parseObject(ConvertObjectUtils.jsonToString(additionalData));
        Comment comment = JSON.parseObject(jsonObject.getString(SendHtmlMailTypeNameEnum.ADDITIONAL_DATA.getKeyName()), Comment.class);

        StorageSendMailInfo receiveCommentMailInfo = new StorageSendMailInfo();
        // 设置属性
        receiveCommentMailInfo.setUserUid(comment.getUserUid());
        receiveCommentMailInfo.setSubject(comment.getContent());
        receiveCommentMailInfo.setSendType(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT);
        receiveCommentMailInfo.setCorrelationDataId(storageSendMailInfo.getCorrelationDataId());

        List<Map<SendHtmlMailTypeNameEnum,Object>> list = new ArrayList<>();
        Map<SendHtmlMailTypeNameEnum,Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT, comment);
        list.add(map);

        receiveCommentMailInfo = StorageMailUtils.generateMailInfo(receiveCommentMailInfo, list);
        sendMailService.sendHtmlMail(receiveCommentMailInfo);
    }
}
