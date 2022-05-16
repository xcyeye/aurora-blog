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

@ApiModel(value="au_bulletin")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bulletin implements Serializable {
    /**
    * 唯一uid
    */
    @ApiModelProperty(value="唯一uid")
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
    * 公告的标题
    */
    @ApiModelProperty(value="公告的标题")
    @Length(max = FieldLengthConstant.TITLE)
    private String title;

    /**
    * 公告创建时间
    */
    @ApiModelProperty(value="公告创建时间")
    private String createTime;

    /**
    * 公告最后修改时间
    */
    @ApiModelProperty(value="公告最后修改时间")
    private String updateTime;

    /**
    * 发布此公告的用户uid
    */
    @ApiModelProperty(value="发布此公告的用户uid")
    private Long userUid;

    /**
    * 1: 显示公告 0： 不显示该公告
    */
    @ApiModelProperty(value="1: 显示公告 0： 不显示该公告")
    private Boolean show;

    /**
    * 1：定时发布 0： 不定时发布公告
    */
    @ApiModelProperty(value="1：定时发布 0： 不定时发布公告")
    private Boolean timing;

    /**
    * 定时发布公告的时间
    */
    @ApiModelProperty(value="定时发布公告的时间")
    private String timingPublishTime;

    /**
    * 1:删除 0：未删除
    */
    @ApiModelProperty(value="1:删除 0：未删除")
    private Boolean delete;

    /**
    * 公告内容
    */
    @ApiModelProperty(value="公告内容")
    @Length(max = FieldLengthConstant.CONTENT)
    @ValidateString(value = "公告内容", max = FieldLengthConstant.CONTENT, groups = Insert.class)
    private String content;

    private static final long serialVersionUID = 1L;
}