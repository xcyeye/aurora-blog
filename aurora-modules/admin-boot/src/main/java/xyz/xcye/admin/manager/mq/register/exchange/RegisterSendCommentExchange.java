package xyz.xcye.admin.manager.mq.register.exchange;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;

/**
 * 注册发送评论的交换机
 * @author qsyyke
 */

@Component
public class RegisterSendCommentExchange {
    /**
     * 注册一个普通发送评论的交换机
     * @return
     */
    @Bean
    public Exchange commonSendCommentExchange() {
        return new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_COMMENT_EXCHANGE,
                true,true);
    }

    /**
     * 注册一个死信发送评论的交换机
     * @return
     */
    @Bean
    public Exchange deadLetterSendCommentExchange() {
        return new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_COMMENT_DEAD_LETTER_EXCHANGE,
                true, false);
    }
}
