package xyz.xcye.common.enums;

import lombok.Getter;

/**
 * 这是和rabbitmq相关的队列长度限制枚举
 * @author qsyyke
 */

@Getter
public enum RabbitMQQueueLengthEnum {
    //===================普通配置
    MAIL_COMMON_NOTICE(200),
    MAIL_REPLY_COMMENT_NOTICE(200),
    MAIL_RECEIVE_COMMENT_NOTICE(200),
    MAIL_VERIFY_ACCOUNT_NOTICE(200),

    //===================死信配置
    DEAD_LETTER_MAIL_COMMON_NOTICE(200),
    DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE(200),
    DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE(200),
    DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE(200);


    private int queueLength;

    RabbitMQQueueLengthEnum(int queueLength) {
        this.queueLength = queueLength;
    }
}
