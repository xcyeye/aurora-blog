package xyz.xcye.comment.manager.mq;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.comment.service.MessageLogFeignService;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.enums.RabbitMQNameEnum;
import xyz.xcye.common.util.ObjectConvertJson;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class RabbitMQSendService {

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

    @GlobalTransactional
    public void sendReceiveCommentNotice(CommentDO receiveCommentInfo) throws BindException {
        // 生成一个唯一uid 此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("correlationDataId",correlationData.getId());
        messageMap.put("receiveCommentInfo",receiveCommentInfo);

        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ObjectConvertJson.jsonToString(messageMap);

        String xid = RootContext.getXID();

        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(jsonToString, uid, RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE, "",
                RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY, false, 0, "topic",
                false, "");

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        messageLogFeignService.insertMessageLog(messageLogDO,xid);

        rabbitTemplate.send(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE,
                RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)),correlationData);
    }

    /**
     *
     * @param replyingCommentInfo
     * @param repliedCommentInfo
     * @return
     */
    @GlobalTransactional
    public void sendReplyCommentNotice(CommentDO replyingCommentInfo,CommentDO repliedCommentInfo) throws BindException {
        //将发送的回复评论数据组装成一个map集合
        Map<String,Object> commentMap = new HashMap<>();

        //此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        commentMap.put("replyingCommentInfo",replyingCommentInfo);
        commentMap.put("repliedCommentInfo",repliedCommentInfo);
        commentMap.put("correlationDataId",correlationData.getId());

        String commentJson = ObjectConvertJson.jsonToString(commentMap);

        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(commentJson, uid, RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE, "",
                RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY, false, 0, "topic",
                false, "");

        String xid = RootContext.getXID();

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO,Insert.class,Default.class);
        messageLogFeignService.insertMessageLog(messageLogDO,xid);

        rabbitTemplate.send(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE,
                RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY,
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
