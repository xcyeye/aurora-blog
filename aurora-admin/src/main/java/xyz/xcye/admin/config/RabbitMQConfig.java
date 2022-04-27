package xyz.xcye.admin.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.web.common.manager.mq.MistakeMessageSendService;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import javax.annotation.PostConstruct;

/**
 * rabbitmq相关的配置
 * */

@Component
@Slf4j
public class RabbitMQConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private MessageLogFeignService messageLogFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 设置消息确认
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.error("发布确认了-> ack:{},id:{}",ack,correlationData.getId());
        if (ack) {
            MessageLogDO messageLogDO = null;
            try {
                messageLogDO = getMessageLogDO(correlationData);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (messageLogDO == null) {
                return;
            }

            //更新消息投递状态
            messageLogDO.setAckStatus(true);
            try {
                messageLogFeignService.updateMessageLog(messageLogDO);
            } catch (BindException e) {
                e.printStackTrace();
                // 可能因为服务未开启或者是网络不可用，造成的异常
            }
        } else {
            /*
             * 可能是由于routingKey错误或者是交换机的名字错误，导致交换机没有找到
             * 如果在生产者那里，交换机的名字写错了，那么此处ack的值会为false和returnedMessage都会执行，但是如果只是由于routingKey错误，
             * 交换机正常，那么就只会导致ack的值为false
             * */
            MessageLogDO messageLogDO = null;
            try {
                messageLogDO = getMessageLogDO(correlationData);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (messageLogDO == null) {
                return;
            }

            //更新消息投递状态
            messageLogDO.setAckStatus(false);
            //设置错误消息
            messageLogDO.setErrorMessage(cause);
            try {
                messageLogFeignService.updateMessageLog(messageLogDO);
            } catch (BindException e) {
                e.printStackTrace();
                // 可能会由于网络不可用，造成异常
            }
        }
    }


    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage());

        // 如果消息投递失败，那么优先使用此处的错误信息更新数据库中的记录

    }

    /**
     * 初始化rabbitmqTemplate
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 从数据库中获取mq消息
     * @param correlationDataId
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private MessageLogDO getMessageLogFromDb(String correlationDataId) throws InstantiationException, IllegalAccessException {
        if (correlationDataId == null) {
            return null;
        }

        long uid = 0L;
        try {
            uid = Long.parseLong(correlationDataId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

        R r = messageLogFeignService.queryMessageLogByUid(uid);
        String json = ConvertObjectUtils.jsonToString(r);
        // 从json中获取data
        MessageLogDO messageLogDO = null;
        try {
            messageLogDO = JSON.parseObject(JSON.parseObject(json).getString("data"), MessageLogDO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageLogDO;
    }

    private MessageLogDO getMessageLogDO(CorrelationData correlationData) throws InstantiationException, IllegalAccessException {
        if (correlationData == null) {
            //生产者发送的消息不规范，不做任何处理
            return null;
        }

        String correlationDataId = correlationData.getId();
        //向au_message_log表中插入信息
        MessageLogDO messageLogDO = getMessageLogFromDb(correlationDataId);

        //如果messageLogDO为null，则可能是在生产消息的时候，出现什么错误，没有将数据添加到数据库中
        if (messageLogDO == null || messageLogDO.getUid() == null) {
            return null;
        }
        
        return messageLogDO;
    }

    @Bean
    public MistakeMessageSendService mistakeMessageSendService() {
        return new MistakeMessageSendService(rabbitTemplate);
    }
}
