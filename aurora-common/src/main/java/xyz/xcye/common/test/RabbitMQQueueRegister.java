/*
package xyz.xcye.common.test;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import RabbitMQNameConstant;

*/
/**
 * 注册队列以及交换机
 * @author qsyyke
 *//*


@Component
public class RabbitMQQueueRegister {

    */
/**
     * 使用mq发送普通邮件通知
     * @return
     *//*

    @Bean
    public Queue mailCommonNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.getQueueName())
                .deadLetterExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.getExchangeName())
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.getRoutingKey()).build();
    }

    */
/**
     * 使用mq发送回复评论邮件通知
     * @return
     *//*

    @Bean
    public Queue mailReplyCommentNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.getQueueName())
                .deadLetterExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getExchangeName())
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getRoutingKey()).build();
    }

    */
/**
     * 使用mq发送收到评论邮件通知
     * @return
     *//*

    @Bean
    public Queue mailReceiveCommentNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.getQueueName())
                .deadLetterExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getExchangeName())
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getRoutingKey()).build();
    }

    */
/**
     * 使用mq发送验证账户邮件通知
     * @return
     *//*

    @Bean
    public Queue mailVerifyAccountNoticeQueue() {
        return QueueBuilder.durable(RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.getQueueName())
                .deadLetterExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getExchangeName())
                .deadLetterRoutingKey(RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getRoutingKey()).build();
    }

    */
/**
     * 普通通知的死信交换机
     * @return
     *//*

    @Bean
    public Queue mailCommonNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.getQueueName(),RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.isDurable());
    }

    */
/**
     * 回复评论的死信交换机
     * @return
     *//*

    @Bean
    public Queue mailReplyCommentNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getQueueName(),RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.isDurable());
    }

    */
/**
     * 收到评论的死信交换机
     * @return
     *//*

    @Bean
    public Queue mailReceiveCommentNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getQueueName(),RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.isDurable());
    }

    */
/**
     * 验证账户的死信交换机
     * @return
     *//*

    @Bean
    public Queue mailVerifyAccountNoticeDeadLetterQueue() {
        return new Queue(RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getQueueName(),RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.isDurable());
    }

}*/
