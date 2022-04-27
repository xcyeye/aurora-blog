package xyz.xcye.comment.manager.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.util.ObjectConvertJson;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class CommentRabbitMQSendService {

    /**
     * 当前机器的id
     */
    @Value("${aurora.snow-flake.workerId}")
    private int workerId;

    /**
     * 该台机器对应的数据中心id
     */
    @Value("${aurora.snow-flake.datacenterId}")
    private int datacenterId;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MessageLogFeignService messageLogFeignService;

    /**
     * 如果收到新评论，则会将新评论的信息发送到交换机中，最终在message模块中，被消费，然后发送邮件通知作者
     * @param receiveCommentInfo
     * @throws BindException
     */
    public void sendReceiveCommentNotice(CommentDO receiveCommentInfo) throws BindException {
        // 生成一个唯一uid 此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("correlationDataId",correlationData.getId());
        messageMap.put("receiveCommentInfo",receiveCommentInfo);

        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ObjectConvertJson.jsonToString(messageMap);

        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(jsonToString, uid, RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE, "",
                RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY, false, 0, "topic",
                false, "");

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        messageLogFeignService.insertMessageLog(messageLogDO);

        rabbitTemplate.send(RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)),correlationData);
    }

    /**
     * 如果有人回复某个评论，则会将此评论信息发送到交换机中，然后最终被消费者消费，最终通知双方(被回复评论的用户以及作者)
     * @param replyingCommentInfo
     * @param repliedCommentInfo
     * @return
     */
    public void sendReplyCommentNotice(CommentDO replyingCommentInfo,CommentDO repliedCommentInfo) throws BindException {
        //将发送的回复评论数据组装成一个map集合
        Map<String,Object> commentMap = new HashMap<>();

        //此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        // 组装评论对象
        commentMap.put("replyingCommentInfo",replyingCommentInfo);
        commentMap.put("repliedCommentInfo",repliedCommentInfo);
        commentMap.put("correlationDataId",correlationData.getId());

        // 将组装的map集合转换成json字符串，发送到交换机
        String commentJson = ObjectConvertJson.jsonToString(commentMap);

        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(commentJson, uid, RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE, "",
                RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY, false, 0, "topic",
                false, "");

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO,Insert.class,Default.class);
        messageLogFeignService.insertMessageLog(messageLogDO);

        rabbitTemplate.send(RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY,
                new Message(commentJson.getBytes(StandardCharsets.UTF_8)));
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
