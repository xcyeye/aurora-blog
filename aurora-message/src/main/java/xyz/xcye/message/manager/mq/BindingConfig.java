package xyz.xcye.message.manager.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.enums.RabbitMQNameEnum;

/**
 * @author qsyyke
 */

@Component
public class BindingConfig {

    //====================普通队列和普通交换机绑定
    /**
     * 将普通通知队列和普通邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailCommonNoticeBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.MAIL_COMMON_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE))
                .with(RabbitMQNameEnum.MAIL_COMMON_NOTICE_ROUTING_KEY);
    }

    /**
     * 将回复评论通知队列和普通邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailReplyCommentNoticeBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE))
                .with(RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY);
    }

    /**
     * 收到评论通知队列和普通邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailReceiveCommentNoticeBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE))
                .with(RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY);
    }

    /**
     * 将验证账户通知队列和普通邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailVerifyAccountNoticeBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE))
                .with(RabbitMQNameEnum.MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY);
    }

    // ===================死信队列和死信交换机绑定

    /**
     * 将普通邮件通知队列和死信邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailCommonNoticeDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.DEAD_LETTER_MAIL_COMMON_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE))
                .with(RabbitMQNameEnum.DEAD_LETTER_MAIL_COMMON_NOTICE_ROUTING_KEY);
    }

    /**
     * 将回复评论通知队列和死信邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailReplyCommentNoticeDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE))
                .with(RabbitMQNameEnum.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY);
    }

    /**
     * 将收到评论通知队列和死信邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailReceiveCommentNoticeDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE))
                .with(RabbitMQNameEnum.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY);
    }

    /**
     * 将验证账户通知队列和死信邮件发送交换机绑定起来
     * @return
     */
    @Bean
    public Binding mailVerifyAccountNoticeDeadLetterBinding() {
        return BindingBuilder.bind(new Queue(RabbitMQNameEnum.MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME))
                .to(new TopicExchange(RabbitMQNameEnum.AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE))
                .with(RabbitMQNameEnum.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY);
    }
}
