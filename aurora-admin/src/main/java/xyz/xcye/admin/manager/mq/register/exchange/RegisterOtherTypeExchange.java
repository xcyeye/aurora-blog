package xyz.xcye.admin.manager.mq.register.exchange;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.constant.RabbitMQNameConstant;

/**
 * @author qsyyke
 */

@Component
public class RegisterOtherTypeExchange {
    /**
     * 注册一个接收错误消息的交换机
     * @return
     */
    @Bean
    public DirectExchange mistakeMessageExchange() {
        return new DirectExchange(RabbitMQNameConstant.MISTAKE_MESSAGE_EXCHANGE,true,false);
    }
}
