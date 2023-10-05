package xyz.xcye.amqp.api;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.feign.FeignException;
import xyz.xcye.core.util.ValidationUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.feign.config.service.MessageLogFeignService;
import xyz.xcye.message.pojo.MessageLogPojo;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 这个是发送mq消息的类
 *
 * @author qsyyke
 * @date Created in 2022/5/12 18:35
 */

@Component
public class AmqpSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${aurora.snow-flake-worker-id:12}")
    private int snowFlakeWorkerId;

    @Value("${aurora.snow-flake-datacenter-id:12}")
    private int snowFlakeDatacenterId;

    @Resource
    private MessageLogFeignService messageLogFeignService;

    /**
     * 发送mq消息 因为目前把消息发送到交换机中，都是同步的，也就是RequestContextHolder中一定存在数据
     *
     * @param msgJson
     * @param exchangeName
     * @param routingKey
     * @param exchangeType
     * @throws BindException
     */
    @Transactional
    public void sendMQMsg(String msgJson, String exchangeName, String routingKey, String exchangeType) throws BindException {
        boolean whiteUrlStatus = AuroraRequestUtils.getWhiteUrlFlag();
        // 生成一个唯一correlationDataId
        String correlationDataId = getCorrelationDataId();
        CorrelationData correlationData = new CorrelationData(correlationDataId);

        // 调用feign向数据库中插入mq消息
        insertMessageLogData(correlationDataId, msgJson, exchangeName, routingKey, exchangeType);
        JwtUserInfo currentUserInfo = UserUtils.getCurrentUser();
        // if (!whiteUrlStatus && currentUserInfo == null) {
        //    throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        //}

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationDataId);

        // 如果是白名单，可以不用加入请求头信息，但是必须将白名单状态传递到消费者中
        Optional.ofNullable(currentUserInfo).ifPresent(t -> messageProperties.setHeader(RequestConstant.AMQP_REQUEST_REQUEST_JWT_USER_INFO_NAME,
                currentUserInfo.getRequestHeadMap()));

        // 传递白名单状态
        messageProperties.setHeader(RequestConstant.AMQP_MESSAGE_PROPERTIES_WHITE_URL_STATUS, whiteUrlStatus + "");
        Message message = new Message(msgJson.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send(exchangeName, routingKey, message, correlationData);
    }

    /**
     * 生成一个唯一correlationDataId
     */
    private String getCorrelationDataId() {
        return GenerateInfoUtils.generateUid(snowFlakeWorkerId, snowFlakeDatacenterId) + "";
    }

    /**
     * mq消息入库
     *
     * @param correlationDataId
     * @param msgJson
     * @param exchangeName
     * @param routingKey
     * @param exchangeType
     * @throws BindException
     */
    private void insertMessageLogData(String correlationDataId, String msgJson, String exchangeName,
                                      String routingKey, String exchangeType) throws BindException {
        // 向au_message_log表中插入生产信息
        MessageLogPojo pojo = getMessageLog(msgJson, Long.parseLong(correlationDataId), exchangeName, "",
                routingKey, false, 0, exchangeType, false, "");
        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(pojo, Insert.class, Default.class);
        R r = messageLogFeignService.insertMessageLog(pojo);
        if (!r.getSuccess()) {
            throw new FeignException(ResponseStatusCodeEnum.REQUEST_REMOTE_MESSAGE_LOG_SERVICE_FAILURE);
        }
    }

    /**
     * 设置mq消息入库的对象
     *
     * @param message
     * @param uid
     * @param exchange
     * @param queue
     * @param routingKey
     * @param ackStatus
     * @param tryCount
     * @param exchangeType
     * @param consumeStatus
     * @param errorMessage
     * @return
     */
    private MessageLogPojo getMessageLog(String message, long uid, String exchange,
                                         String queue, String routingKey, boolean ackStatus,
                                         int tryCount, String exchangeType, boolean consumeStatus,
                                         String errorMessage) {
        MessageLogPojo pojo = new MessageLogPojo();
        pojo.setMessage(message);
        pojo.setUid(uid);
        pojo.setExchange(exchange);
        pojo.setRoutingKey(routingKey);
        pojo.setAckStatus(ackStatus);
        pojo.setTryCount(tryCount);
        pojo.setExchangeType(exchangeType);
        pojo.setConsumeStatus(consumeStatus);
        pojo.setErrorMessage(errorMessage);
        return pojo;
    }
}
