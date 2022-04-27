package xyz.xcye.comment.manager.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.enums.SendHtmlMailKeyNameEnum;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        StorageSendMailInfo storageSendMailInfo = new StorageSendMailInfo();
        storageSendMailInfo.setCorrelationDataId(uid + "");
        storageSendMailInfo.setSubject(receiveCommentInfo.getContent());
        storageSendMailInfo.setUserUid(receiveCommentInfo.getUserUid());
        storageSendMailInfo.setSendType(SendHtmlMailKeyNameEnum.RECEIVE_COMMENT);

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put(SendHtmlMailKeyNameEnum.RECEIVE_COMMENT.getKeyName(),receiveCommentInfo);
        list.add(map);
        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ConvertObjectUtils.generateMailJson(storageSendMailInfo, list);

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
        //此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        // 组装评论对象
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setSubject(replyingCommentInfo.getContent());
        mailInfo.setUserUid(repliedCommentInfo.getUserUid());
        mailInfo.setCorrelationDataId(uid + "");

        // 如果不是回复评论的话，则直接传入userUid便可以，会通过此userUid查询对应的email，但是如果是回复评论，则需要在此处进行设置收件人邮箱
        // 优先级：receiverEmail > 通过userUid查询到的email
        mailInfo.setReceiverEmail(repliedCommentInfo.getEmail());
        mailInfo.setSendType(SendHtmlMailKeyNameEnum.REPLY_COMMENT);

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put(SendHtmlMailKeyNameEnum.RECEIVE_COMMENT.getKeyName(),replyingCommentInfo);
        map.put(SendHtmlMailKeyNameEnum.REPLY_COMMENT.getKeyName(), repliedCommentInfo);
        list.add(map);

        // 将组装的map集合转换成json字符串，发送到交换机
        mailInfo = ConvertObjectUtils.generateMailInfo(mailInfo, list);

        // 组装一个存放被回复评论对象的数据
        Map<String,Object> repliedMap = new HashMap<>();
        repliedMap.put(SendHtmlMailKeyNameEnum.ADDITIONAL_DATA.getKeyName(), replyingCommentInfo);
        mailInfo.setAdditionalData(repliedMap);
        String commentJson = ConvertObjectUtils.jsonToString(mailInfo);

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
