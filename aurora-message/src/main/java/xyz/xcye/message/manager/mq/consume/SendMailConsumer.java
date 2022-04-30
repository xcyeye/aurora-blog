package xyz.xcye.message.manager.mq.consume;

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
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.vo.MessageLogVO;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.web.common.manager.amqp.MistakeMessageSendService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RabbitListener(queues = RabbitMQNameConstant.SEND_HTML_MAIL_QUEUE_NAME, ackMode = "MANUAL")
    public void sendHtmlMailConsumer(String msgJson, Channel channel, Message message)
            throws IOException, ReflectiveOperationException, BindException, MessagingException, EmailException {
        // 待替换的key和value的map
        StorageSendMailInfo storageSendMailInfo = parseMessage.getStorageSendMailInfoFromMsg(msgJson,channel,message);
        if (!isLegitimateHtmlData(storageSendMailInfo)) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        }

        if (storageSendMailInfo.getUserUid() == null) {
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            updateMessageLogInfo(storageSendMailInfo.getCorrelationDataId(),true,false,"邮件发送中，没有传入userUid");
        }

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendHtmlMail(storageSendMailInfo);

        isReplyComment(storageSendMailInfo);
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
            throws ReflectiveOperationException, MessagingException, BindException, IOException, EmailException {
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
        CommentDO commentDO = JSON.parseObject(jsonObject.getString(SendHtmlMailTypeNameEnum.ADDITIONAL_DATA.getKeyName()), CommentDO.class);

        StorageSendMailInfo receiveCommentMailInfo = new StorageSendMailInfo();
        // 设置属性
        receiveCommentMailInfo.setUserUid(commentDO.getUserUid());
        receiveCommentMailInfo.setSubject(commentDO.getContent());
        receiveCommentMailInfo.setSendType(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT);
        receiveCommentMailInfo.setCorrelationDataId(storageSendMailInfo.getCorrelationDataId());

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT.getKeyName(), commentDO);
        list.add(map);

        ConvertObjectUtils.generateMailInfo(receiveCommentMailInfo, list);
        sendMailService.sendHtmlMail(receiveCommentMailInfo);
    }
}
