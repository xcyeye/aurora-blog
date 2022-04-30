package xyz.xcye.common.enums;

import lombok.Getter;

/**
 * 发送html邮件时的type值
 * @author qsyyke
 * @date Created in 2022/4/27 19:17
 */

@Getter
public enum SendHtmlMailTypeNameEnum {
    RECEIVE_COMMENT("receiveComment"),
    REPLY_COMMENT("replyComment"),
    VERIFY_ACCOUNT("verifyAccount"),
    COMMON_NOTICE("commonNotice"),
    ADDITIONAL_DATA("additional");

    /**
     * 在发送html邮件中，存放替换数据的map集合的键前缀，最终会形如receiveComment:Username
     */
    private String keyName;

    SendHtmlMailTypeNameEnum(String keyName) {
        this.keyName = keyName;
    }
}
