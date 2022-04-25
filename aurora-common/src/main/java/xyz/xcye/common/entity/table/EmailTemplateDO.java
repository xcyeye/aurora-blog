package xyz.xcye.common.dos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_email_template
 * 设置邮件发送模板
 * @author qsyyke
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailTemplateDO {
    /**
     * 唯一uid，不能为null，主键
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 此邮件发送模板是哪个用户创建的
     */
    @NotNull(groups = {Insert.class,Update.class})
    private Long userUid;

    /**
     * 回复评论模板 不能为null 长度没有限制
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_TEMPLATE, value = "邮箱-配置中的回复评论模板")
    private String replyCommentTemplate;

    /**
     * 验证身份模板 不能为null 长度没有限制
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_TEMPLATE,value = "邮箱-配置中的验证账户信息的模板")
    private String verifyAccountTemplate;

    /**
     * 普通的通知模板 不能为null 长度没有限制
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_TEMPLATE,value = "邮箱-配置中的提醒通知模板")
    private String noticeTemplate;

    /**
     * 有用户评论的模板 不能为null 长度没有限制
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_TEMPLATE,value = "邮箱-配置中的收到评论模板")
    private String receiveCommentTemplate;

    /**
     * 默认回复评论的标题
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_SUBJECT,value = "邮箱-回复评论的标题")
    private String replyCommentSubject;

    /**
     * 使用该邮箱发送验证账户时的默认标题
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_SUBJECT,value = "邮箱-验证账户的标题")
    private String verifyAccountSubject;

    /**
     * 使用该邮箱发送收到评论时的默认标题
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_SUBJECT,value = "邮箱-收到评论的标题")
    private String receiveCommentSubject;

    /**
     * 使用该邮箱发送普通通知时的默认标题
     */
    @ValidateString(max = FieldLengthConstant.EMAIL_SUBJECT,value = "邮箱-普通通知的标题")
    private String noticeSubject;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
