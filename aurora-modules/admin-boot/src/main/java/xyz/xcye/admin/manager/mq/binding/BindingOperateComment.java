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
 * 将消费评论的交换机和队列绑定起来
 * @author qsyyke
 */

@Component
public class BindingOperateComment {

    //====================普通队列和普通交换机绑定
    /**
     * 将收到文章评论的交换机和队列绑定起来
     * @return
     */
    @Bean
    public Binding receiveArticleCommentBinding() {
        return BindingBuilder.bind(new Queue(AmqpQueueNameConstant.PAGE_COMMENT_QUEUE))
                .to(new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_COMMENT_EXCHANGE))
                .with(AmqpQueueNameConstant.PAGE_COMMENT_ROUTING_KEY);
    }

    /**
     * 将发送评论死信交换机和收到文章评论死信队列绑定起来
     * @return
     */
    @Bean
    public Binding receiveArticleCommentDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(AmqpQueueNameConstant.PAGE_COMMENT_DEAD_LETTER_QUEUE))
                .to(new TopicExchange(AmqpExchangeNameConstant.AURORA_SEND_COMMENT_DEAD_LETTER_EXCHANGE))
                .with(AmqpQueueNameConstant.PAGE_COMMENT_DEAD_LETTER_ROUTING_KEY);
    }
}
