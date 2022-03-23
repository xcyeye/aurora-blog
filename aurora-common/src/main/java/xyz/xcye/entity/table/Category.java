package xyz.xcye.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_category
 * @author qsyyke
 */

@Data
public class Category {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Update.class, Delete.class})
    private BigInteger uid;

    /**
     * 此类别的标题 不能为null
     * <p>length < 100</p>
     */
    @ValidateString(value = "类别-标题",max = FieldLengthEnum.TITLE)
    private String title;

    /**
     * 此类别的简介 可以为null
     * <p>length < 500</p>
     */
    @Length(max = FieldLengthEnum.SUMMARY,message = "类别-简介长度不能超过{max}")
    private String summary;

    /**
     * 1：已删除 0：未删除 不能为null
     */
    @Status("类别-删除")
    private int deleteStatus;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "类别-创建时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updatedAt;

    /**
     * 此类别的封面图地址 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthEnum.URL,message = "类别-封面图片地址不能超过{max}")
    private String coverUrl;
}
