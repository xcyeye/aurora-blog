package xyz.xcye.admin.manager.mq.binding;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;

/**
 * 将操作邮件相关的队列和交换机绑定起来
 * @author qsyyke
 */

@Component
public class BindingOperateMail {

    //====================普通队列和普通交换机绑定
    /**
     * 将发送html邮件的交换机和队列绑定起来
     * @return
     */
    @Bean
    public Binding sendHtmlMailBinding() {
        return BindingBuilder.bind(new Queue(AmqpQueueNameConstant.SEND_HTML_MAIL_QUEUE_NAME))
                .to(new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE))
                .with(AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY);
    }

    /**
     * 将发送html邮件的死信交换机和死信队列绑定起来
     * @return
     */
    @Bean
    public Binding sendHtmlMailDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(AmqpQueueNameConstant.SEND_HTML_MAIL_DEAD_LETTER_QUEUE_NAME))
                .to(new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_MAIL_DEAD_LETTER_EXCHANGE))
                .with(AmqpQueueNameConstant.SEND_HTML_MAIL_DEAD_LETTER_ROUTING_KEY);
    }

    /**
     * 将发送简单文本邮件的交换机和队列绑定起来
     * @return
     */
    @Bean
    public Binding sendSimpleTextBinding() {
        return BindingBuilder.bind(new Queue(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_QUEUE_NAME))
                .to(new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE))
                .with(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_ROUTING_KEY);
    }

    /**
     * 将发送简单文本邮件的死信交换机和死信队列绑定起来
     * @return
     */
    @Bean
    public Binding sendSimpleTextDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_QUEUE_NAME))
                .to(new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_MAIL_DEAD_LETTER_EXCHANGE))
                .with(AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_ROUTING_KEY);
    }
}
