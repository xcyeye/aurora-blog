package xyz.xcye.common.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_category
 * @author qsyyke
 */

@Data
public class CategoryDO {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 此类别的标题 不能为null
     * <p>length < 100</p>
     */
    @ValidateString(value = "类别-标题",max = FieldLengthConstant.TITLE)
    private String title;

    /**
     * 此类别的简介 可以为null
     * <p>length < 500</p>
     */
    @Length(max = FieldLengthConstant.SUMMARY,message = "类别-简介长度不能超过{max}")
    private String summary;

    /**
     * true：已删除 false：未删除 不能为null
     */
    private Boolean delete;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "类别-创建时间",max = FieldLengthConstant.TIME_FORMAT)
    private String createTime;

    /**
     * 最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updateTime;

    /**
     * 此类别的封面图地址 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "类别-封面图片地址不能超过{max}")
    private String coverUrl;
}
