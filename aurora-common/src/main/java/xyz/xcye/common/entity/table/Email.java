package xyz.xcye.common.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.enums.FieldLengthEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_email
 * @author qsyyke
 */

@Data
public class Email {
    /**
     * 唯一uid，不能为null，主键
     */
    @NotNull(groups = {Update.class, Delete.class})
    private BigInteger uid;

    /**
     * 此邮箱配置对应哪个用户 可以为null
     */
    private BigInteger userUid;

    /**
     * 邮箱主机 可以为null
     * <p>length < 25</p>
     */
    @javax.validation.constraints.Email(message = "邮箱-不符合要求")
    @Length(max = FieldLengthEnum.EMAIL_NUMBER,message = "邮箱-长度不能超过25")
    private String emailHost;

    /**
     * 邮箱授权码 可以为null
     * <p>length < 50</p>
     */
    @Length(max = FieldLengthEnum.EMAIL_PASSWORD,message = "邮箱-配置中的密码不能超过{max}")
    private String emailPassword;

    /**
     * 邮箱协议 可以为null
     * <p>length < 10</p>
     */
    @Length(max = FieldLengthEnum.EMAIL_PROTOCOL,message = "邮箱-配置中的协议不能超过{max}")
    private String protocol;

    /**
     * 发送者的名称 不能为null
     * <p>length < 50</p>
     */
    @ValidateString(value = "邮箱-配置中的发送者名称",max = FieldLengthEnum.USERNAME)
    private String senderName;

    /**
     * 发送者的标题 不能为null
     * <p>length < 50</p>
     */
    @ValidateString(value = "邮箱-配置中的发送者标题",max = FieldLengthEnum.EMAIL_SUBJECT)
    private String subjectTitle;

    /**
     * 回复评论模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的回复评论模板",max = FieldLengthEnum.EMAIL_TEMPLATE)
    private String replyCommentTemplate;

    /**
     * 验证身份模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的验证身份模板",max = FieldLengthEnum.EMAIL_TEMPLATE)
    private String verifyInfoTemplate;

    /**
     * 普通的通知模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的普通通知模板",max = FieldLengthEnum.EMAIL_TEMPLATE)
    private String noticeTemplate;

    /**
     * 有用户评论的模板 不能为null 长度没有限制
     */
    @ValidateString(value = "邮箱-配置中的有用户评论的模板",max = FieldLengthEnum.EMAIL_TEMPLATE)
    private String receiveCommentTemplate;

    private boolean deleteStatus;
}
