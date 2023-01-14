package xyz.xcye.article.pojo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * @description category数据表的POJO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPojo {

    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 此类别的标题
     */
    @Schema(title = "此类别的标题")
    @Length(max = FieldLengthConstant.TITLE)
    @ValidateString(value = "类别的标题", max = FieldLengthConstant.TITLE, groups = Insert.class)
    private String title;

    /**
     * 此类别的简介
     */
    @Schema(title = "此类别的简介")
    @Length(max = FieldLengthConstant.SUMMARY)
    private String summary;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private String createTime;

    /**
     * 最后修改时间
     */
    @Schema(title = "最后修改时间")
    private String updateTime;

    /**
     * 此类别的封面图地址
     */
    @Schema(title = "此类别的封面图地址")
    @Length(max = FieldLengthConstant.URL)
    private String coverUrl;

    /**
     * 1: 删除 ，0：未删除
     */
    @Schema(title = "1: 删除 ，0：未删除")
    private Boolean delete;

    /**
     * 用户的userUid
     */
    @Schema(title = "用户的userUid")
    private Long userUid;

}