package xyz.xcye.message.manager.mq.consume;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.web.common.manager.mq.MistakeMessageSendService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.groups.Default;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 消费者
 * @author qsyyke
 */

@Slf4j
@Component
public class RabbitMQConsumer {
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageLogService messageLogService;
    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;

    /**
     * 消费收到评论的mq消息
     * @param msgJson
     * @param channel
     * @param message
     * @throws MessagingException
     * @throws BindException
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQNameConstant.MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME,ackMode = "MANUAL")
    public void receiveCommentNotice(String msgJson, Channel channel, Message message) throws MessagingException, BindException, IOException {
        log.info("消费者replyCommentNotice执行{}",msgJson);
        // 获取唯一id
        String correlationDataId = null;
        CommentDO receiveCommentInfo = null;
        try {
            JSONObject jsonObject = JSON.parseObject(msgJson);
            correlationDataId = JSON.parseObject(jsonObject.getString("correlationDataId"), String.class);
            receiveCommentInfo = JSON.parseObject(jsonObject.getString("receiveCommentInfo"), CommentDO.class);
        } catch (Exception e) {
            e.printStackTrace();
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        } finally {}

        try {
            ValidationUtils.valid(receiveCommentInfo, Insert.class);
        } catch (BindException e) {
            e.printStackTrace();
            // 属性验证失败
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            updateMessageLogInfo(correlationDataId,true,false,"commentDO对象中的属性字段不满足要求");
        }

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendReceiveCommentMail(receiveCommentInfo,
                receiveCommentInfo.getUserUid(), receiveCommentInfo.getContent());

        updateMessageLogInfo(correlationDataId,true,true,null);
    }

    /**
     * 消费回复评论的mq消息
     * @param msgJson
     * @param channel
     * @param message
     * @throws MessagingException
     * @throws BindException
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME,ackMode = "MANUAL")
    public void replyCommentNotice(String msgJson, Channel channel, Message message) throws MessagingException, BindException, IOException {
        log.info("消费者replyCommentNotice执行{}",msgJson);
        CommentDO replyingCommentInfo = null;
        CommentDO repliedCommentInfo = null;
        String correlationDataId = null;
        try {
            JSONObject jsonObject = JSON.parseObject(msgJson);
            correlationDataId = JSON.parseObject(jsonObject.getString("correlationDataId"), String.class);
            replyingCommentInfo = JSON.parseObject(jsonObject.getString("replyingCommentInfo"), CommentDO.class);
            repliedCommentInfo = JSON.parseObject(jsonObject.getString("repliedCommentInfo"), CommentDO.class);
        } catch (Exception e) {
            e.printStackTrace();
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        }finally {}

        try {
            ValidationUtils.valid(replyingCommentInfo,Insert.class, Default.class);
            ValidationUtils.valid(repliedCommentInfo,Insert.class,Default.class);
        } catch (BindException e) {
            e.printStackTrace();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            updateMessageLogInfo(correlationDataId,true,false,"commentDO对象中的属性字段不满足要求");
            return;
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendReplyCommentMail(replyingCommentInfo, repliedCommentInfo, repliedCommentInfo.getUserUid(), replyingCommentInfo.getContent());
        updateMessageLogInfo(correlationDataId,true,true,null);
    }

    @RabbitListener(queues = RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME,ackMode = "MANUAL")
    public void verifyAccountNotice(String msgJson, Channel channel, Message message) throws MessagingException, BindException, IOException {
        log.info("mq消费者接收到消息:{}",msgJson);
        // 获取唯一id
        String correlationDataId = null;
        EmailVerifyAccountDTO verifyAccountInfo = null;
        try {
            JSONObject jsonObject = JSON.parseObject(msgJson);
            correlationDataId = JSON.parseObject(jsonObject.getString("correlationDataId"), String.class);
            verifyAccountInfo = JSON.parseObject(jsonObject.getString("verifyAccountInfo"), EmailVerifyAccountDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        } finally {}

        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendVerifyAccountMail(verifyAccountInfo, verifyAccountInfo.getUserUid(), verifyAccountInfo.getSubject());
        log.info("发送邮件到{}验证账户,userUid:{},结果:{}",verifyAccountInfo.getReceiverEmail(),verifyAccountInfo.getUserUid(),modifyResult.isSuccess());
        updateMessageLogInfo(correlationDataId,true,true,null);
    }

    /**
     * 消费死信回复评论的mq消息
     * @param msgJson
     * @param channel
     * @param message
     * @throws MessagingException
     * @throws BindException
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME,ackMode = "MANUAL")
    public void deadLetterReplyCommentNotice(String msgJson,Channel channel,Message message) throws MessagingException, BindException, IOException {
        log.error("死信队列执行 {}",msgJson);
        replyCommentNotice(msgJson,channel,message);
    }

    /**
     * 消费死信的收到评论的mq消息
     * @param msgJson
     * @param channel
     * @param message
     * @throws MessagingException
     * @throws BindException
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME,ackMode = "MANUAL")
    public void deadLetterReceiveCommentNotice(String msgJson,Channel channel,Message message) throws MessagingException, BindException, IOException {
        log.error("死信队列执行 {}",msgJson);
        receiveCommentNotice(msgJson,channel,message);
    }

    /**
     * 更新数据库中的mq消息的信息
     * @param correlationDataId
     * @param ackStatus
     * @param consumeStatus
     * @param errorMessage
     * @throws BindException
     */
    private void updateMessageLogInfo(String correlationDataId, boolean ackStatus, boolean consumeStatus, String errorMessage) throws BindException {
        MessageLogDO messageLogDO = messageLogService.queryByUid(Long.parseLong(correlationDataId));

        if (messageLogDO == null) {
            return;
        }

        messageLogDO.setAckStatus(ackStatus);
        messageLogDO.setConsumeStatus(consumeStatus);
        messageLogDO.setErrorMessage(errorMessage);
        messageLogService.updateMessageLog(messageLogDO);
    }
}
