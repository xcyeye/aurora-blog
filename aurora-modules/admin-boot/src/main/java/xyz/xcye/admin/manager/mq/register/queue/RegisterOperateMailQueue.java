package xyz.xcye.admin.manager.mq.register.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;

/**
 * 注册操作邮件的队列，只注册一个就行了
 * @author qsyyke
 */

@Component
public class RegisterOperateMailQueue {
    /**
     * 发送html邮件的队列
     * @return
     */
    @Bean
    public Queue mailSendHtmlQueue() {
        return QueueBuilder.durable(AmqpQueueNameConstant.SEND_HTML_MAIL_QUEUE_NAME)
                .deadLetterExchange(AmqpExchangeNameConstant.AURORA_SEND_MAIL_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(AmqpQueueNameConstant.SEND_HTML_MAIL_DEAD_LETTER_ROUTING_KEY).build();
    }

    /**
     * 发送简单文本邮件的队列
     * @return
     */
    @Bean
    public Queue mailSendSimpleTextQueue() {
        return QueueBuilder.durable(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_QUEUE_NAME)
                .deadLetterExchange(AmqpExchangeNameConstant.AURORA_SEND_MAIL_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_ROUTING_KEY).build();
    }


    /**
     * 发送html邮件的死信队列
     * @return
     */
    @Bean
    public Queue sendHtmlMailDeadLetterQueue() {
        return new Queue(AmqpQueueNameConstant.SEND_HTML_MAIL_DEAD_LETTER_QUEUE_NAME, true);
    }

    /**
     * 发送简单文本邮件的死信队列
     * @return
     */
    @Bean
    public Queue sendSimpleTextMailDeadLetterQueue() {
        return new Queue(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_QUEUE_NAME, true);
    }
}
