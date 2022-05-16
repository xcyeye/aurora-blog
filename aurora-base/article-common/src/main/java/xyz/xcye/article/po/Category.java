package xyz.xcye.article.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@ApiModel(value="au_category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    /**
    * 唯一uid
    */
    @ApiModelProperty(value="唯一uid")
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
    * 此类别的标题
    */
    @ApiModelProperty(value="此类别的标题")
    @Length(max = FieldLengthConstant.TITLE)
    @ValidateString(value = "类别的标题", max = FieldLengthConstant.TITLE, groups = Insert.class)
    private String title;

    /**
    * 此类别的简介
    */
    @ApiModelProperty(value="此类别的简介")
    @Length(max = FieldLengthConstant.SUMMARY)
    private String summary;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    @Null(groups = Insert.class)
    private String createTime;

    /**
    * 最后修改时间
    */
    @ApiModelProperty(value="最后修改时间")
    @Null(groups = Insert.class)
    private String updateTime;

    /**
    * 此类别的封面图地址
    */
    @ApiModelProperty(value="此类别的封面图地址")
    @Length(max = FieldLengthConstant.URL)
    private String coverUrl;

    /**
    * 1: 删除 ，0：未删除
    */
    @ApiModelProperty(value="1: 删除 ，0：未删除")
    private Boolean delete;

    /**
    * 用户的userUid
    */
    @ApiModelProperty(value="用户的userUid")
    private Long userUid;

    private static final long serialVersionUID = 1L;
}