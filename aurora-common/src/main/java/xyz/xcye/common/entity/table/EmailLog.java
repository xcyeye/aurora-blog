package xyz.xcye.common.entity.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.enums.FieldLengthEnum;
import xyz.xcye.common.enums.RegexEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;

/**
 * 数据表 au_email_log
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailLog {

    /**
     * 唯一uid 自增 主键 不使用雪花算法
     */
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 邮件发送的标题 不能为null
     * <p>length < 50</p>
     */
    @ValidateString(value = "邮件日志-发送的标题",max = FieldLengthEnum.EMAIL_TEMPLATE,groups = {Insert.class})
    private String subject;

    /**
     * 邮件发送的内容 不能为null 长度随意
     */
    @ValidateString(value = "邮件日志-发送的内容",max = FieldLengthEnum.CONTENT,groups = {Insert.class})
    private String content;

    /**
     * 接收者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "必须是邮箱号")
    @NotBlank(groups = {Insert.class})
    @Length(max = FieldLengthEnum.EMAIL_NUMBER)
    private String receiver;

    /**
     * 发送者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "必须是邮箱号")
    @NotBlank(groups = {Insert.class})
    @Length(max = FieldLengthEnum.EMAIL_NUMBER)
    private String sender;

    /**
     * 邮件发送时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @Length(max = FieldLengthEnum.TIME_FORMAT,message = "邮件日志-邮件发送时间长度不能超过{max}")
    private String createdAt;

    /**
     * 邮件发送的状态 true：已成功发送 false：未发送成功
     */
    private boolean sendStatus;
}
