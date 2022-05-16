package xyz.xcye.admin.po;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 这是白名单地址
 * @TableName au_white_url
 */
@Data
public class WhiteUrl implements Serializable {
    /**
     * 唯一uid
     */
    @NotNull(groups = {Update.class})
    private Integer uid;

    /**
     * 白名单地址
     */
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "白名单地址", max = FieldLengthConstant.URL, groups = {Insert.class})
    private String url;

    /**
     * 创建时间，自动插入
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}