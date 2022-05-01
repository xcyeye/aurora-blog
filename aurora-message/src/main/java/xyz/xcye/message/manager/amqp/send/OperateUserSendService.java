package xyz.xcye.message.manager.amqp.send;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.entity.table.EmailDO;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 此类暂时不用
 */

@Slf4j
@Component
public class OperateUserSendService {
    @Autowired
    private AuroraProperties auroraProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /*@Autowired
    private MessageLogService messageLogService;*/

    /**
     * 当用户向数据库中插入邮箱记录之后，会调用此发送服务向AURORA_SEND_OPERATE_USER_EXCHANGE交换机发送绑定用户账户信息的mq消息
     * 在aurora-admin模块中，和此OPERATE_USER_BINDING_EMAIL_ROUTING_KEY绑定的队列的消费和能够更新数据库中，此userUid所对应的记录，然后
     * ，当更新成功之后，再从aurora-admin模块发送验证账户的信息到AURORA_SEND_EMAIL_COMMON_EXCHANGE交换机
     * <p>因为是跨服务进行操作，所以需要绑定seata的xid，以确保数据的一致性</p>
     * @param emailDO
     * @throws BindException
     */
    public void sendBindingEmail(EmailDO emailDO, String xid) throws BindException {
        // 生成一个唯一uid 此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(), auroraProperties.getSnowFlakeDatacenterId());
        CorrelationData correlationData = new CorrelationData(uid + "");

        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("xid",xid);
        messageMap.put("correlationDataId",correlationData.getId());
        messageMap.put("emailDO",emailDO);

        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ConvertObjectUtils.jsonToString(messageMap);
        log.error("sendBindingEmail恢复全局事务{}", RootContext.getXID());
        RootContext.bind(xid);
        insertMessageLog(jsonToString,uid);
        log.error("sendBindingEmail挂起全局事务{}", RootContext.getXID());
        RootContext.unbind();
        // 存储验证信息
        rabbitTemplate.send(RabbitMQNameConstant.AURORA_SEND_OPERATE_USER_EXCHANGE,
                RabbitMQNameConstant.OPERATE_USER_BINDING_EMAIL_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)),correlationData);
    }

    private void insertMessageLog(String jsonToString, long uid) throws BindException {
        /*//向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(jsonToString, uid, RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE, "",
                RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY, false, 0, "topic",
                false, "");

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        //messageLogService.insertMessageLog(messageLogDO);*/
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
