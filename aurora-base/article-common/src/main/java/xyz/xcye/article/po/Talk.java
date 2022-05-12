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
import java.io.Serializable;

@ApiModel(value="au_talk")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Talk implements Serializable {
    /**
    * 唯一uid
    */
    @ApiModelProperty(value="唯一uid")
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
    * 发布此说说的用户uid
    */
    @ApiModelProperty(value="发布此说说的用户uid")
    private Long userUid;

    /**
    * 此说说是否显示评论 1： 显示 0： 不显示
    */
    @ApiModelProperty(value="此说说是否显示评论 1： 显示 0： 不显示")
    private Boolean showComment;

    /**
    * 此说说的评论uid集合
    */
    @ApiModelProperty(value="此说说的评论uid集合")
    private String commentUids;

    /**
    * 最后更新时间
    */
    @ApiModelProperty(value="最后更新时间")
    private String updatedTime;

    /**
    * 此说说发布时间
    */
    @ApiModelProperty(value="此说说发布时间")
    private String createdTime;

    /**
    * 此说说标题
    */
    @ApiModelProperty(value="此说说标题")
    @Length(max = FieldLengthConstant.TITLE)
    private String title;

    /**
    * 1： 显示说说，0： 不显示说说
    */
    @ApiModelProperty(value="1： 显示说说，0： 不显示说说")
    private Boolean show;

    /**
    * 此说说的点赞数
    */
    @ApiModelProperty(value="此说说的点赞数")
    private Integer likeNumber;

    /**
    * 此说说对应的图片uid集合
    */
    @ApiModelProperty(value="此说说对应的图片uid集合")
    private String pictureUids;

    /**
    * 1: 已删除
    */
    @ApiModelProperty(value="1: 已删除")
    private Boolean delete;

    /**
    * 此说说的内容
    */
    @ApiModelProperty(value="此说说的内容")
    @ValidateString(value = "此说说的内容", max = FieldLengthConstant.CONTENT, groups = Insert.class)
    private String content;

    private static final long serialVersionUID = 1L;
}