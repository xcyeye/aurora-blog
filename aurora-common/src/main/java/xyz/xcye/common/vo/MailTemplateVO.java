package xyz.xcye.common.vo;

import lombok.Builder;
import lombok.Data;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

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
     * 替换模板中的特定字符的字符串集合
     */
    private String replaceArr;

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