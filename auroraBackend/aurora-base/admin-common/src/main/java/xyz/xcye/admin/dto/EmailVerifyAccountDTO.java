package xyz.xcye.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 邮箱验证账户实体
 * @author qsyyke
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVerifyAccountDTO {
    /**
     * 验证邮箱的地址
     */
    @ValidateString(value = "验证账户的地址",max = FieldLengthConstant.URL)
    private String verifyAccountUrl;

    /**
     * 接受者的邮箱
     */
    @ValidateString(value = "验证账户的收件人邮箱地址",max = FieldLengthConstant.EMAIL_NUMBER)
    private String receiverEmail;

    /**
     * 此验证链接失效时间
     */
    @NotNull
    private Long expirationTime;

    private String expirationTimeStr;

    /**
     * 此userUid需要保证在数据库中存在验证账户的邮件发送模板，推荐填写管理员的userUid
     */
    @NotNull
    private Long userUid;

    /**
     * 邮件发送的标题
     */
    @Length(max = FieldLengthConstant.EMAIL_SUBJECT)
    private String subject;
}
