package xyz.xcye.message.vo;

import lombok.Data;

@Data
public class MailTemplateVO {

    /**
     * 唯一uid
     */
    private Long uid;

    /**
     * 此模板是哪个用户创建的
     */
    private Long userUid;

    private String type;

    /**
     * 邮件html模板
     */
    private String template;

    /**
     * 默认的发送标题，如果没有指定的话
     */
    private String subject;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;
}