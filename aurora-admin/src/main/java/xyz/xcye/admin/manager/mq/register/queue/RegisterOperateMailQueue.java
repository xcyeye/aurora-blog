package xyz.xcye.admin.manager.mq.register.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.constant.RabbitMQNameConstant;

/**
 * 注册操作邮件的队列
 * @author qsyyke
 */

@Component
public class RegisterOperateMailQueue {
    /**
     * 发送普通邮件通知的队列
     * @return
     */
    @Bean
    public Queue mailCommonNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_COMMON_NOTICE_QUEUE_NAME)
                .deadLetterExchange(RabbitMQNameConstant.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE_ROUTING_KEY).build();
    }

    /**
     * 发送回复评论邮件通知的队列
     * @return
     */
    @Bean
    public Queue mailReplyCommentNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME)
                .deadLetterExchange(RabbitMQNameConstant.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY).build();
    }

    /**
     * 发送收到评论提醒的邮件通知队列
     * @return
     */
    @Bean
    public Queue mailReceiveCommentNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME)
                .deadLetterExchange(RabbitMQNameConstant.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY).build();
    }

    /**
     * 发送验证账户提醒的邮件通知队列
     * @return
     */
    @Bean
    public Queue mailVerifyAccountNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME)
                .deadLetterExchange(RabbitMQNameConstant.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY).build();
    }

    /**
     * 发送收到死信通知的邮件通知队列
     * @return
     */
    @Bean
    public Queue mailCommonNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE_QUEUE_NAME, true);
    }

    /**
     * 回复评论邮件通知的死信队列
     * @return
     */
    @Bean
    public Queue mailReplyCommentNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME, true);
    }

    /**
     * 收到评论邮件通知的死信队列
     * @return
     */
    @Bean
    public Queue mailReceiveCommentNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME, true);
    }

    /**
     * 验证账户的邮件通知的死信队列
     * @return
     */
    @Bean
    public Queue mailVerifyAccountNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME, true);
    }
}
