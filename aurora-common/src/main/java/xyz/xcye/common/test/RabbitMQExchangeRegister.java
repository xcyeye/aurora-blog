/*
package xyz.xcye.common.test;

import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.xcye.common.enums.RabbitMQNameEnum;

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
        return new CustomExchange(RabbitMQNameEnum.MAIL_COMMON_NOTICE.getExchangeName(),
                RabbitMQNameEnum.MAIL_COMMON_NOTICE.getType(),RabbitMQNameEnum.MAIL_COMMON_NOTICE.isDurable(),false);
    }

    */
/**
     * 使用mq发送回复评论邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailReplyCommentNoticeExchange() {
        return new CustomExchange(RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE.getType(),RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE.isDurable(),false);
    }

    */
/**
     * 使用mq发送收到评论邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailReceiveCommentNoticeExchange() {
        return new CustomExchange(RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE.getType(),RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE.isDurable(), false);
    }

    */
/**
     * 使用mq发送验证账户邮件通知
     * @return
     *//*

    @Bean
    public Exchange mailVerifyAccountNoticeExchange() {
        return new CustomExchange(RabbitMQNameEnum.MAIL_VERIFY_ACCOUNT_NOTICE.getExchangeName(),
                RabbitMQNameEnum.MAIL_VERIFY_ACCOUNT_NOTICE.getType(),RabbitMQNameEnum.MAIL_VERIFY_ACCOUNT_NOTICE.isDurable(), false);
    }

    */
/**
     * 普通通知的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailCommonNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameEnum.DEAD_LETTER_MAIL_COMMON_NOTICE.getExchangeName(),
                RabbitMQNameEnum.DEAD_LETTER_MAIL_COMMON_NOTICE.getType(),RabbitMQNameEnum.DEAD_LETTER_MAIL_COMMON_NOTICE.isDurable(), false);
    }

    */
/**
     * 回复评论的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailReplyCommentNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameEnum.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameEnum.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.getType(),RabbitMQNameEnum.DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE.isDurable(), false);
    }

    */
/**
     * 收到评论的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailReceiveCommentNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameEnum.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getExchangeName(),
                RabbitMQNameEnum.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.getType(),RabbitMQNameEnum.DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE.isDurable(), false);
    }

    */
/**
     * 验证账户的死信交换机
     * @return
     *//*

    @Bean
    public Exchange mailVerifyAccountNoticeDeadLetterExchange() {
        return new CustomExchange(RabbitMQNameEnum.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getExchangeName(),
                RabbitMQNameEnum.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.getType(),RabbitMQNameEnum.DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE.isDurable(), false);
    }

}
*/
