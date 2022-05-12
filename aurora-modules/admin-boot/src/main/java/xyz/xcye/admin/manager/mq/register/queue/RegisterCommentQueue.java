package xyz.xcye.admin.manager.mq.register.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;

/**
 * 注册接收评论的队列，比如在文章页面评论，那么在文章部分会消费此消息
 * @author qsyyke
 */

@Component
public class RegisterCommentQueue {
    /**
     * 收到文章评论的普通队列
     * @return
     */
    @Bean
    public Queue receiveArticleCommentQueue() {
        return QueueBuilder.durable(AmqpQueueNameConstant.ARTICLE_COMMENT_QUEUE)
                .deadLetterExchange(AmqpExchangeNameConstant.AURORA_SEND_COMMENT_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(AmqpQueueNameConstant.ARTICLE_COMMENT_DEAD_LETTER_ROUTING_KEY).build();
    }

    /**
     * 收到说说评论的普通队列
     * @return
     */
    @Bean
    public Queue receiveTalkCommentQueue() {
        return QueueBuilder.durable(AmqpQueueNameConstant.TALK_COMMENT_QUEUE)
                .deadLetterExchange(AmqpExchangeNameConstant.AURORA_SEND_COMMENT_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(AmqpQueueNameConstant.TALK_COMMENT_DEAD_LETTER_ROUTING_KEY).build();
    }


    /**
     * 收到文章评论的死信队列
     * @return
     */
    @Bean
    public Queue receiveArticleCommentDeadLetterQueue() {
        return new Queue(AmqpQueueNameConstant.ARTICLE_COMMENT_DEAD_LETTER_QUEUE, true);
    }

    /**
     * 收到说说评论的死信队列
     * @return
     */
    @Bean
    public Queue receiveTalkCommentDeadLetterQueue() {
        return new Queue(AmqpQueueNameConstant.TALK_COMMENT_DEAD_LETTER_QUEUE, true);
    }
}
