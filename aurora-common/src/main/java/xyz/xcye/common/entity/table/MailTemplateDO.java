package xyz.xcye.common.entity.table;

import lombok.Data;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 对应数据表au_mail_template
 * @author qsyyke
 * @date Created in 2022/4/27 12:23
 */

@Data
public class MailTemplateDO {

    /**
     * 唯一uid
     */
    @NotNull(groups = Update.class)
    private Long uid;

    /**
     * 此模板是哪个用户创建的
     */
    @NotNull(groups = Insert.class)
    private Long userUid;

    @ValidateString(value = "此邮件模板是验证哪种类型，比如收到评论", max = FieldLengthConstant.EMAIL_TEMPLATE_TYPE)
    private String typeName;

    /**
     * 邮件html模板
     */
    @ValidateString(value = "邮件发送模板", max = FieldLengthConstant.EMAIL_TEMPLATE, groups = {Insert.class})
    private String template;

    /**
     * 默认的发送标题，如果没有指定的话
     */
    @ValidateString(value = "邮件-邮件默认发送标题", max = FieldLengthConstant.EMAIL_SUBJECT, groups = Insert.class)
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
