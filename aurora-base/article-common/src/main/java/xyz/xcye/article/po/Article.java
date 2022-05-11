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

@ApiModel(value="au_article")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    /**
    * 唯一uid
    */
    @ApiModelProperty(value="唯一uid")
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
    * 文章是否展示评论，0：否，1：是
    */
    @ApiModelProperty(value="文章是否展示评论，0：否，1：是")
    private Boolean showComment;

    /**
    * 文章有附件的话，附件的uid集合
    */
    @ApiModelProperty(value="文章有附件的话，附件的uid集合")
    private String accessoryUids;

    /**
    * 文章类别uid集合
    */
    @ApiModelProperty(value="文章类别uid集合")
    private String categoryUids;

    /**
    * 文章标签uid集合
    */
    @ApiModelProperty(value="文章标签uid集合")
    private String tagUids;

    /**
    * 文章是否发布，1：发布，0：不发布
    */
    @ApiModelProperty(value="文章是否发布，1：发布，0：不发布")
    private Boolean publish;

    /**
    * 发布此篇文章的用户uid
    */
    @ApiModelProperty(value="发布此篇文章的用户uid")
    @NotNull(groups = {Insert.class})
    private Long userUid;

    /**
    * 是否原创，1：原创 0：不是原创
    */
    @ApiModelProperty(value="是否原创，1：原创 0：不是原创")
    private Boolean originalArticle;

    /**
    * 如果是原创，则原创链接
    */
    @ApiModelProperty(value="如果是原创，则原创链接")
    @Length(max = FieldLengthConstant.URL)
    private String originalArticleUrl;

    /**
    * 文章封面对应的图片uid
    */
    @ApiModelProperty(value="文章封面对应的图片uid")
    @Length(max = FieldLengthConstant.URL)
    private String coverPictureUrl;

    /**
    * 文章标题
    */
    @ApiModelProperty(value="文章标题")
    @ValidateString(value = "文章标题", max = FieldLengthConstant.TITLE, groups = {Insert.class})
    private String title;

    /**
    * 文章简介
    */
    @Length(max = FieldLengthConstant.SUMMARY)
    @ApiModelProperty(value="文章简介")
    private String summary;

    /**
    * 是否定时发布 0： 不定时，1：定时
    */
    @ApiModelProperty(value="是否定时发布 0： 不定时，1：定时")
    private Boolean timing;

    /**
    * 定时发布时间
    */
    @ApiModelProperty(value="定时发布时间")
    private String timingPublishTime;

    /**
    * 文章点赞数
    */
    @ApiModelProperty(value="文章点赞数")
    private Integer likeNumber;

    /**
    * 阅读量
    */
    @ApiModelProperty(value="阅读量")
    private Integer readNumber;

    /**
    * 此篇文章最后修改的时间
    */
    @ApiModelProperty(value="此篇文章最后修改的时间")
    private String updateTime;

    /**
    * 文章发布时间
    */
    @ApiModelProperty(value="文章发布时间")
    private String createTime;

    /**
    * 是否删除 逻辑删除 1： 已删除
    */
    @ApiModelProperty(value="是否删除 逻辑删除 1： 已删除")
    private Boolean delete;

    /**
    * 文章内容
    */
    @ApiModelProperty(value="文章内容")
    @ValidateString(value = "文章内容", max = FieldLengthConstant.CONTENT, groups = Insert.class)
    private String content;

    /**
    * 此篇文章对应的评论uid集合，只需要设置所有父评论的uid
    */
    @ApiModelProperty(value="此篇文章对应的评论uid集合，只需要设置所有父评论的uid")
    private String commentUids;

    private static final long serialVersionUID = 1L;
}