/*
package xyz.xcye.common.test;

import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.constant.RabbitMQNameConstant;

*/
/**
 * 注册队列以及交换机
 * @author qsyyke
 *//*


@Component
public class RabbitMQExchangeRegister {

    */
/**
     * 使用mq发送普通邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailCommonNoticeExchange() {
        return new CustomExchange(RabbitMQNameConstant.MAIL_COMMON_NOTICE.getExchangeName(),
                RabbitMQNameConstant.MAIL_COMMON_NOTICE.getType(),RabbitMQNameConstant.MAIL_COMMON_NOTICE.isDurable(),false);
    }

    */
/**
     * 使用mq发送回复评论邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailReplyCommentNoticeExchange() {
        return new CustomExchange(RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.getType(),RabbitMQNameConstant.MAIL_REPLY_COMMENT_NOTICE.isDurable(),false);
    }

    */
/**
     * 使用mq发送收到评论邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailReceiveCommentNoticeExchange() {
        return new CustomExchange(RabbitMQNameConstant.MAIL_RECEIVE_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameConstant.MAIL_RECEIVE_COMMENT_NOTICE.getType(),RabbitMQNameConstant.MAIL_RECEIVE_COMMENT_NOTICE.isDurable(), false);
    }

    */
/**
     * 使用mq发送验证账户邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailVerifyAccountNoticeExchange() {
        return new CustomExchange(RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE.getExchangeName(),
                RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE.getType(),RabbitMQNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE.isDurable(), false);
    }

    */
/**
     * 普通通知的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailCommonNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.getExchangeName(),
                RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.getType(),RabbitMQNameConstant.DEAD_LETTER_MAIL_COMMON_NOTICE.isDurable(), false);
    }

    */
/**
     * 回复评论的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailReplyCommentNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getType(),RabbitMQNameConstant.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.isDurable(), false);
    }

    */
/**
     * 收到评论的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailReceiveCommentNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getType(),RabbitMQNameConstant.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.isDurable(), false);
    }

    */
/**
     * 验证账户的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailVerifyAccountNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getExchangeName(),
                RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getType(),RabbitMQNameConstant.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.isDurable(), false);
    }

}
*/
