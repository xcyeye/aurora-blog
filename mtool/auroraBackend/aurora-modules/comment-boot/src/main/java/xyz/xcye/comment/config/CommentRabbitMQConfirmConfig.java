package xyz.xcye.comment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.xcye.amqp.api.ConfirmUpdateMessageAckStatus;

import javax.annotation.PostConstruct;

/**
 * 这是全局的rabbitmq的发布确认
 * */

@Component
@Slf4j
public class CommentRabbitMQConfirmConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfirmUpdateMessageAckStatus confirmUpdateMessageAckStatus;

    /**
     * 设置消息确认
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        confirmUpdateMessageAckStatus.updateConfirmMessageAckStatus(correlationData, ack, cause);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", returned.getExchange(),
                returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage());
        // 如果消息投递失败，那么优先使用此处的错误信息更新数据库中的记录

    }

    /**
     * 初始化rabbitmqTemplate
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }
}
