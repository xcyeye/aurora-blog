package xyz.xcye.admin.manager.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.admin.constant.RedisConstant;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dos.UserDO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.util.ObjectConvertJson;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class AdminRabbitMQSendService {

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
    @Autowired
    private UserRedisService userRedisService;

    public void sendVerifyAccount(EmailVerifyAccountDTO verifyAccountInfo, UserDO userDO) throws BindException {
        // 生成一个唯一uid 此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("correlationDataId",correlationData.getId());
        messageMap.put("verifyAccountInfo",verifyAccountInfo);

        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ObjectConvertJson.jsonToString(messageMap);

        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(jsonToString, uid, RabbitMQNameConstant.AURORA_SEND_EMAIL_COMMON_EXCHANGE, "",
                RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY, false, 0, "topic",
                false, "");

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        messageLogFeignService.insertMessageLog(messageLogDO);

        // 存储验证信息
        userRedisService.storageUserVerifyAccountInfo(userDO, RedisConstant.STORAGE_VERIFY_ACCOUNT_EXPIRATION_TIME);

        rabbitTemplate.send(RabbitMQNameConstant.AURORA_SEND_EMAIL_COMMON_EXCHANGE,
                RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)),correlationData);
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
