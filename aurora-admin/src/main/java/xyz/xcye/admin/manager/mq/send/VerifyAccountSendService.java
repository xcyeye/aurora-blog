package xyz.xcye.admin.manager.mq.send;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;
import xyz.xcye.web.common.service.mq.MessageLogService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class VerifyAccountSendService {

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

    @Value("${aurora.admin.verify.account.expiration-time}")
    private int emailVerifyAccountExpirationTime;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageLogFeignService messageLogFeignService;
    @Autowired
    private MessageLogService messageLogService;
    @Autowired
    private UserRedisService userRedisService;

    public void sendVerifyAccount(EmailVerifyAccountDTO verifyAccountInfo) throws BindException {
        // 生成一个唯一uid 此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(workerId, datacenterId);
        CorrelationData correlationData = new CorrelationData(uid + "");

        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("correlationDataId",correlationData.getId());
        messageMap.put("verifyAccountInfo",verifyAccountInfo);

        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ConvertObjectUtils.jsonToString(messageMap);
        messageLogService.remoteInsertMessageLog(jsonToString,uid,
                RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE,"",
                RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY,false,0,
                "topic",false,"");

        // 存储验证信息
        rabbitTemplate.send(RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)),correlationData);
        log.info("发送mq验证账户消息，消息信息{}，验证账户信息{}",jsonToString,verifyAccountInfo);
        // 发送成功 向redis中插入待验证的账户信息
        userRedisService.storageUserVerifyAccountInfo(verifyAccountInfo,emailVerifyAccountExpirationTime);
    }
}
