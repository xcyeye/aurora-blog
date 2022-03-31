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
 * 数据表au_tag
 * @author qsyyke
 */

@Data
public class Tag {

    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Update.class, Delete.class})
    private BigInteger uid;

    /**
     * 标签的标题 长度<100 不能为null
     */
    @ValidateString(value = "标签-标题",max = FieldLengthEnum.TITLE)
    private String title;

    /**
     * 标签的简介 长度<500 可以为null
     */
    @Length(max = FieldLengthEnum.SUMMARY,message = "标签-简介不能超过{max}")
    private String summary;

    /**
     * 该标签的删除状态 true：已删除 false：未删除 不能为null
     */
    private boolean deleteStatus;

    /**
     * 标签的创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "标签-创建时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 标签最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updatedAt;

    /**
     * 该标签的封面图片地址 因为图片是从文件服务里面获取的，所以可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthEnum.URL,message = "标签-封面图片地址不能超过{max}")
    private String coverUrl;
}
