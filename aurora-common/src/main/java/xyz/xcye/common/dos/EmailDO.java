package xyz.xcye.common.dos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_email
 * @author qsyyke
 */

@Data
public class EmailDO {
    /**
     * 唯一uid，不能为null，主键
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 此邮箱配置对应哪个用户 可以为null
     */
    @NotNull(message = "user_uid不能为null",groups = {Insert.class})
    private Long userUid;

    /**
     * 邮箱主机 可以为null
     * <p>length < 25</p>
     */
    @javax.validation.constraints.Email(message = "邮箱-不符合要求")
    @Length(max = FieldLengthConstant.EMAIL_NUMBER,message = "邮箱-长度不能超过25")
    private String emailHost;

    /**
     * 邮箱授权码 可以为null
     * <p>length < 50</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_PASSWORD,message = "邮箱-配置中的密码不能超过{max}")
    private String emailPassword;

    /**
     * 邮箱协议 可以为null
     * <p>length < 10</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_PROTOCOL,message = "邮箱-配置中的协议不能超过{max}")
    private String protocol;

    /**
     * 回复评论模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的回复评论模板",max = FieldLengthConstant.EMAIL_TEMPLATE,groups = {Insert.class})
    private String replyCommentTemplate;

    /**
     * 验证身份模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的验证身份模板",max = FieldLengthConstant.EMAIL_TEMPLATE,groups = {Insert.class})
    private String verifyAccountTemplate;

    /**
     * 普通的通知模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的普通通知模板",max = FieldLengthConstant.EMAIL_TEMPLATE,groups = {Insert.class})
    private String noticeTemplate;

    /**
     * 有用户评论的模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的有用户评论的模板",max = FieldLengthConstant.EMAIL_TEMPLATE,groups = {Insert.class})
    private String receiveCommentTemplate;

    /**
     * 删除状态
     */
    @NotNull(groups = Update.class)
    private Boolean delete;

    /**
     * 邮件服务的端口
     */
    private Integer port;

    /**
     * 邮箱号
     */
    @NotNull(groups = {Insert.class})
    @Length(max = FieldLengthConstant.EMAIL_NUMBER)
    @javax.validation.constraints.Email(message = "邮件的邮箱号不满足要求")
    private String email;

    /**
     * 默认回复评论的标题
     */
    @ValidateString(value = "邮箱-回复评论的标题",max = FieldLengthConstant.EMAIL_SUBJECT,groups = {Insert.class})
    private String replyCommentSubject;

    /**
     * 使用该邮箱发送验证账户时的默认标题
     */
    @ValidateString(value = "邮箱-验证账户的标题",max = FieldLengthConstant.EMAIL_SUBJECT,groups = {Insert.class})
    private String verifyAccountSubject;

    /**
     * 使用该邮箱发送收到评论时的默认标题
     */
    @ValidateString(value = "邮箱-收到评论的标题",max = FieldLengthConstant.EMAIL_SUBJECT,groups = {Insert.class})
    private String receiveCommentSubject;

    /**
     * 使用该邮箱发送普通通知时的默认标题
     */
    @ValidateString(value = "邮箱-普通通知的标题",max = FieldLengthConstant.EMAIL_SUBJECT,groups = {Insert.class})
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
