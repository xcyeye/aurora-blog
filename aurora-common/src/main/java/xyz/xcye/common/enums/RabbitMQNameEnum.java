package xyz.xcye.common.enums;

import lombok.Getter;

/**
 * 这是和rabbitmq相关的枚举
 * @author qsyyke
 */

@Getter
public class RabbitMQNameEnum {
    //===================普通配置
    //public static final String MAIL_COMMON_NOTICE_EXCHANGE_NAME = "mail_common_notice_exchange";
    public static final String MAIL_COMMON_NOTICE_QUEUE_NAME = "mail.common.notice.queue";
    public static final String MAIL_COMMON_NOTICE_ROUTING_KEY = "mail.common.notice.routing";

    //public static final String MAIL_REPLY_COMMENT_NOTICE_EXCHANGE_NAME = "mail_reply_comment_exchange";
    public static final String MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME = "mail.reply.comment.queue";
    public static final String MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY = "mail.reply.comment.routing";

    //public static final String MAIL_RECEIVE_COMMENT_NOTICE_EXCHANGE_NAME = "mail_receive_comment_exchange";
    public static final String MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME = "mail.receive.comment.queue";
    public static final String MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY = "mail.receive.comment.routing";

    //public static final String MAIL_VERIFY_ACCOUNT_NOTICE_EXCHANGE_NAME = "mail_verify_account_exchange";
    public static final String MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME = "mail.verify.account.queue";
    public static final String MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY = "mail.verify.account.routing";

    //public static final String DEAD_LETTER_MAIL_COMMON_NOTICE_EXCHANGE_NAME = "mail_common_notice_dead_letter_exchange";
    public static final String DEAD_LETTER_MAIL_COMMON_NOTICE_QUEUE_NAME = "mail.common.notice.dead.letter.queue";
    public static final String DEAD_LETTER_MAIL_COMMON_NOTICE_ROUTING_KEY = "mail.common.notice.dead.letter.routing";

    //public static final String DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_EXCHANGE_NAME = "mail_reply_comment_dead_letter_exchange";
    public static final String DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_QUEUE_NAME = "mail.reply.comment.dead.letter.queue";
    public static final String DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY = "mail.reply.comment.dead.letter.routing";

    //public static final String DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE_EXCHANGE_NAME = "mail_verify_account_dead_letter_exchange";
    public static final String DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE_QUEUE_NAME = "mail.verify.account.dead.letter.queue";
    public static final String DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY = "mail.verify.account.dead.letter.routing";

    //public static final String DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_EXCHANGE_NAME = "mail_receive_comment_dead_letter_exchange";
    public static final String DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_QUEUE_NAME = "mail.receive.comment.dead.letter.queue";
    public static final String DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY = "mail.receive.comment.dead.letter.routing";

    /** 发送邮件的普通交换机名称 **/
    public static final String AURORA_SEND_EMAIL_COMMON_EXCHANGE = "aurora.send.email.common.exchange";
    /** 发送邮件的死信交换机名称 **/
    public static final String AURORA_SEND_EMAIL_DEAD_LETTER_EXCHANGE = "aurora.send.email.dead.letter.exchange";
}
