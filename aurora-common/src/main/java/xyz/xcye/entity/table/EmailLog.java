package xyz.xcye.entity.table;

import lombok.Data;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;

/**
 * 数据表 au_email_log
 * @author qsyyke
 */

@Data
public class EmailLog {

    /**
     * 唯一uid 自增 主键 不使用雪花算法
     */
    private int uid;

    /**
     * 邮件发送的标题 不能为null
     * <p>length < 50</p>
     */
    @ValidateString(value = "邮件日志-发送的标题",max = FieldLengthEnum.EMAIL_TEMPLATE)
    private String subject;

    /**
     * 邮件发送的内容 不能为null 长度随意
     */
    @ValidateString(value = "邮件日志-发送的内容",max = FieldLengthEnum.CONTENT)
    private String content;

    /**
     * 接收者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    @ValidateString(value = "邮件日志-接受者的邮箱号",max = FieldLengthEnum.EMAIL_NUMBER)
    private String receiver;

    /**
     * 发送者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    @ValidateString(value = "邮件日志-发送者的邮箱号",max = FieldLengthEnum.EMAIL_NUMBER)
    private String sender;

    /**
     * 邮件发送时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "邮件日志-邮件发送时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 邮件发送的状态 1：已成功发送 0：未发送成功
     */
    @Status(value = "邮件日志-发送成功的状态")
    private int sendStatus;
}
