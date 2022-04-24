package xyz.xcye.admin.manager.mq.binding;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.constant.RabbitMQNameConstant;

/**
 * 将操作用户的交换机和队列绑定起来
 * @author qsyyke
 */

@Component
public class BindingOperateUser {
//====================普通队列和普通交换机绑定
    /**
     * 将普通通知队列和普通邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding bindingEmailBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameConstant.OPERATE_USER_BINDING_EMAIL_QUEUE))
                .to(new TopicExchange(RabbitMQNameConstant.AURORA_SEND_OPERATE_USER_EXCHANGE))
                .with(RabbitMQNameConstant.OPERATE_USER_BINDING_EMAIL_ROUTING_KEY);
    }

    /**
     * 将验证账户通知队列和死信邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding bindingEmailDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameConstant.DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_QUEUE))
                .to(new TopicExchange(RabbitMQNameConstant.AURORA_SEND_OPERATE_USER_DEAD_LETTER_EXCHANGE))
                .with(RabbitMQNameConstant.DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_ROUTING_KEY);
    }
}
