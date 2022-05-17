package xyz.xcye.admin.manager.mq.register.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;

/**
 * 注册操作用户的队列
 * @author qsyyke
 */

@Component
public class RegisterOperateUserQueue {
    /**
     * 注册一个普通的操作用户-绑定邮箱的队列，并将其和死信操作用户的交换机绑定起来
     * @return
     */
    @Bean
    public Queue bindingEmailQueue() {
        return QueueBuilder.durable(AmqpQueueNameConstant.OPERATE_USER_BINDING_EMAIL_QUEUE)
                .deadLetterExchange(AmqpExchangeNameConstant.AURORA_SEND_OPERATE_USER_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(AmqpQueueNameConstant.DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_ROUTING_KEY).build();
    }

    /**
     * 注册一个死信操作用户-绑定邮箱的队列
     * @return
     */
    @Bean
    public Queue bindingEmailDeadLetterQueue() {
        return new Queue(AmqpQueueNameConstant.DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_QUEUE, true);
    }


    /**
     * 注册一个锁住用户账户的队列，并将其和死信操作用户的交换机绑定起来
     * @return
     */
    @Bean
    public Queue lockAccountQueue() {
        return QueueBuilder.durable(AmqpQueueNameConstant.OPERATE_USER_LOCK_ACCOUNT_QUEUE)
                .deadLetterExchange(AmqpExchangeNameConstant.AURORA_SEND_OPERATE_USER_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(AmqpQueueNameConstant.DEAD_LETTER_OPERATE_USER_LOCK_ACCOUNT_ROUTING_KEY).build();
    }

    /**
     * 注册一个死信操作用户-锁住账户的队列
     * @return
     */
    @Bean
    public Queue lockAccountDeadLetterQueue() {
        return new Queue(AmqpQueueNameConstant.DEAD_LETTER_OPERATE_USER_LOCK_ACCOUNT_QUEUE, true);
    }
}
