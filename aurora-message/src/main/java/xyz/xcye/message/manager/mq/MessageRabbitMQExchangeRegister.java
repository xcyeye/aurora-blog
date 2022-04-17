package xyz.xcye.message.manager.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.enums.RabbitMQNameEnum;

/**
 * 注册队列以及交换机
 * @author qsyyke
 */

@Component
public class MessageRabbitMQExchangeRegister {
    // 注册一个接收错误消息的交换机
    @Bean
    public DirectExchange mistakeMessageExchange() {
        return new DirectExchange(RabbitMQNameEnum.MISTAKE_MESSAGE_EXCHANGE,true,false);
    }
}
