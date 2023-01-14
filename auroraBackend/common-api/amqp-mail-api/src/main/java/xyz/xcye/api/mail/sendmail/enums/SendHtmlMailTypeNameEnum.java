package xyz.xcye.api.mail.sendmail.enums;

import lombok.Getter;

/**
 * 发送html邮件时的type值
 * @author qsyyke
 * @date Created in 2022/4/27 19:17
 */

@Getter
public enum SendHtmlMailTypeNameEnum {
    RECEIVE_COMMENT,
    REPLY_COMMENT,
    VERIFY_ACCOUNT,
    COMMON_NOTICE,
    FRIEND_LINK_NOTICE,
    CUSTOM_HTML,
    ADDITIONAL_DATA;
}
