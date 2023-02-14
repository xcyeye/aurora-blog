package xyz.xcye.message.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @description email_log数据表的POJO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailLogPojo {

    /**
     * 唯一uid 自增 主键 不使用雪花算法
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 用户uid
     */
    private Long userUid;

    /**
     * 邮件发送的标题 不能为null
     * <p>length < 50</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_TEMPLATE)
    @ValidateString(value = "邮件日志-发送的标题", max = FieldLengthConstant.EMAIL_TEMPLATE, groups = {Insert.class})
    private String subject;

    /**
     * 邮件发送的内容 不能为null 长度随意
     */
    @Length(max = FieldLengthConstant.CONTENT)
    @ValidateString(value = "邮件日志-发送的内容", max = FieldLengthConstant.CONTENT, groups = {Insert.class})
    private String content;

    /**
     * 接收者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "必须是邮箱号")
    @NotBlank(groups = {Insert.class})
    @Length(max = FieldLengthConstant.EMAIL_NUMBER)
    private String receiver;

    /**
     * 发送者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "必须是邮箱号")
    @NotBlank(groups = {Insert.class})
    @Length(max = FieldLengthConstant.EMAIL_NUMBER)
    private String sender;

    /**
     * 邮件发送的状态 true：已成功发送 false：未发送成功
     */
    @NotNull(groups = {Insert.class,Update.class})
    private Boolean send;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    @Length(max = FieldLengthConstant.TIME_FORMAT,message = "邮件日志-邮件发送时间长度不能超过{max}")
    private String updateTime;

}