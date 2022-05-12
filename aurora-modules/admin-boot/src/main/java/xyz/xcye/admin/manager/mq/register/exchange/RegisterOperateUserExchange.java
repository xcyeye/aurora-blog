package xyz.xcye.admin.manager.mq.register.exchange;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;

/**
 * 注册一个操作用户的交换机
 * @author qsyyke
 */

@Component
public class RegisterOperateUserExchange {


    /**
     * 注册一个操作用户的交换机
     * @return
     */
    @Bean
    public TopicExchange operateUserExchange() {
        return new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_OPERATE_USER_EXCHANGE,true,false);
    }

    /**
     * 注册一个操作用户的交换机
     * @return
     */
    @Bean
    public TopicExchange operateUserDeadLetterExchange() {
        return new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_OPERATE_USER_DEAD_LETTER_EXCHANGE,true,false);
    }
}
