package xyz.xcye.admin.manager.mq.register.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.RabbitMQNameConstant;

/**
 * 注册操作其他的队列
 * @author qsyyke
 */

@Component
public class RegisterOtherTypeQueue {
    /**
     * 注册一个生产者生产错误消息的队列
     * @return
     */
    @Bean
    public Queue mistakeMessageQueue() {
        return new Queue(RabbitMQNameConstant.MISTAKE_MESSAGE_QUEUE,true,true,false);
    }


}
