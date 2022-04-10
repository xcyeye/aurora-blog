package xyz.xcye.message.manager.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.RabbitMQNameEnum;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.message.service.SendMailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费者
 * @author qsyyke
 */

@Slf4j
@Component
public class RabbitMQConsumer {
    @Autowired
    private SendMailService sendMailService;

    @RabbitListener(queues = RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME)
    public void receiveCommentNotice(String msgJson, Channel channel, Message message) throws MessagingException, BindException, IOException {
        log.info("消费者replyCommentNotice执行{}",msgJson);
        CommentDO commentDO = null;
        try {
            commentDO = JSON.parseObject(msgJson, CommentDO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }

        if (commentDO == null) {
            //转换失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            return;
        }

        try {
            ValidationUtils.valid(commentDO, Insert.class);
        } catch (BindException e) {
            e.printStackTrace();
            // 属性验证失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendReceiveCommentMail(commentDO, commentDO.getUserUid(), commentDO.getContent());

    }

    @RabbitListener(queues = RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME)
    public void replyCommentNotice(String msgJson, Channel channel, Message message) throws MessagingException, BindException, IOException {
        log.info("消费者replyCommentNotice执行{}",msgJson);
        CommentDO replyingCommentInfo = null;
        CommentDO repliedCommentInfo = null;
        try {
            JSONObject jsonObject = JSON.parseObject(msgJson);
            replyingCommentInfo = JSON.parseObject(jsonObject.getString("replyingCommentInfo"), CommentDO.class);
            repliedCommentInfo = JSON.parseObject(jsonObject.getString("repliedCommentInfo"), CommentDO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }

        if (replyingCommentInfo == null || repliedCommentInfo == null) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            return;
        }

        try {
            ValidationUtils.valid(replyingCommentInfo,Insert.class);
            ValidationUtils.valid(repliedCommentInfo,Insert.class);
        } catch (BindException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            return;
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        ModifyResult modifyResult = sendMailService.sendReplyCommentMail(replyingCommentInfo, repliedCommentInfo, repliedCommentInfo.getUserUid(), replyingCommentInfo.getContent());

    }

    @RabbitListener(queues = RabbitMQNameEnum.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME)
    public void deadLetterReplyCommentNotice(String msgJson,Channel channel,Message message) throws MessagingException, BindException, IOException {
        log.error("死信队列执行 {}",msgJson);
        replyCommentNotice(msgJson,channel,message);
    }

    @RabbitListener(queues = RabbitMQNameEnum.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME)
    public void deadLetterReceiveCommentNotice(String msgJson,Channel channel,Message message) throws MessagingException, BindException, IOException {
        log.error("死信队列执行 {}",msgJson);
        receiveCommentNotice(msgJson,channel,message);
    }
}
