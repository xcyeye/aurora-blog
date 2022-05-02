package xyz.xcye.core.back.common.dto;

import lombok.Data;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.validator.ValidateString;

/**
 * 普通的邮件通知内容
 * @author qsyyke
 */

@Data
public class EmailCommonNoticeDTO {

    /**
     * 通知的内容
     */
    @ValidateString(value = "发布普通通知的内容",max = FieldLengthConstant.CONTENT, groups = {Insert.class})
    private String noticeContent;

    /**
     * 通知的时间
     */
    private String noticeTime;

    /**
     * 发布此通知的用户名
     */
    @ValidateString(value = "发布普通通知的用户名",max = FieldLengthConstant.USERNAME,groups = Insert.class)
    private String publishNoticeUsername;

    /**
     * 接收者的邮箱
     */
    @ValidateString(value = "发布普通通知的接收者邮件地址",max = FieldLengthConstant.EMAIL_NUMBER)
    private String receiverEmail;

    /**
     * 此通知对应的地址
     */
    private String noticePath;

}
