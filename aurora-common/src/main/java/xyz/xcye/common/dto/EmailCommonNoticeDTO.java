package xyz.xcye.common.dto;

import lombok.Data;

/**
 * 普通的邮件通知内容
 * @author qsyyke
 */

@Data
public class EmailCommonNoticeDTO {

    /**
     * 通知的内容
     */
    private String noticeContent;

    /**
     * 通知的时间
     */
    private String noticeTime;

    /**
     * 发布此通知的用户名
     */
    private String publishNoticeUsername;

    /**
     * 接收者的邮箱
     */
    private String receiverEmail;

    /**
     * 此通知对应的地址
     */
    private String noticePath;

}
