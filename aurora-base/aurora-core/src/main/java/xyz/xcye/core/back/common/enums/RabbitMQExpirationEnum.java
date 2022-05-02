package xyz.xcye.core.back.common.enums;

import lombok.Getter;

/**
 * 这是和rabbitmq相关的ttl枚举 单位s
 * @author qsyyke
 */

@Getter
public enum RabbitMQExpirationEnum {
    //===================普通配置
    MAIL_COMMON_NOTICE(2000000),
    MAIL_REPLY_COMMENT_NOTICE(20000),
    MAIL_RECEIVE_COMMENT_NOTICE(20000),
    MAIL_VERIFY_ACCOUNT_NOTICE(20000),

    //===================死信配置
    DEAD_LETTER_MAIL_COMMON_NOTICE(20000),
    DEAD_LETTER_MAIL_REPLY_COMMENT_NOTICE(20000),
    DEAD_LETTER_MAIL_VERIFY_ACCOUNT_NOTICE(20000),
    DEAD_LETTER_MAIL_RECEIVE_COMMENT_NOTICE(20000);


    private int ttl;

    RabbitMQExpirationEnum(int ttl) {
        this.ttl = ttl;
    }
}
