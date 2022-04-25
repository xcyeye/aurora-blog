package xyz.xcye.common.dos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 数据表 au_article
 * @author qsyyke
 */

@Data
public class ArticleDO {
    /**
     * 唯一uid 不为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * true：展示评论 false：不显示评论
     */
    private Boolean showComment;

    /**
     * 文章中如果有附件的话，存放附件集合 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.STRING_ARRAY,message = "文章-附件下载集合长度必须小于{max}")
    private String downloadFileArr;

    /**
     * 文章类别的uid集合 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.STRING_ARRAY,message = "文章-类别集合长度必须小于{max}")
    private String categoryArr;

    /**
     * 文章的标签uid集合 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.STRING_ARRAY,message = "文章-标签集合长度必须小于{max}")
    private String tagArr;

    /**
     * 文章是否发布 true：已发布 false：未发布 不能为null
     */
    private Boolean publish;

    /**
     * 该篇文章是哪个用户发布的 可以为null
     */
    private Long userUid;

    /**
     * 是否是原创 true：原创 false：不是原创
     */
    private Boolean originalArticle;

    /**
     * 如果不是原创的话 该篇文章的真是地址
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "文章-路径长度必须小于{max}")
    private String originalArticleUrl;

    /**
     * 该篇文章对应的评论uid集合 可以为null
     * <p>mysql -> text</p>
     */
    @Length(max = FieldLengthConstant.STRING_ARRAY,message = "文章-对应评论集合")
    private String commentArr;

    /**
     * 最后修改时间，不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(max = FieldLengthConstant.TIME_FORMAT,value = "文章-最后更新时间")
    private String updateTime;

    /**
     * 文章发布时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "文章-创建时间",max = FieldLengthConstant.TIME_FORMAT)
    private String createTime;

    /**
     * 文章的内容 不能为null
     * <p>mysql -> longtext</p>
     */
    @ValidateString(value = "文章-内容",max = FieldLengthConstant.CONTENT)
    private String content;

    /**
     * 文章点赞数 不能为null
     */
    @Pattern(regexp = "^[0-9]?\\d*$",message = "文章-点赞数量，必须是整数")
    private Integer likeNumber;

    /**
     * 文章的标题 不能为null
     * <p>length < 100</p>
     */
    @ValidateString(value = "文章-标题",max = FieldLengthConstant.TITLE)
    private String title;

    /**
     * 文章的简介 可以为null
     * <p>length < 500</p>
     */
    @Length(max = FieldLengthConstant.SUMMARY,message = "文章-简介必须小于等于{max}")
    private String summary;

    /**
     * 是否定时发布 true：定时 false：不定时 不能为null
     */
    private Boolean timing;

    /**
     * 如果是定时发布的话，定时发布的时间 可以为null
     * <p>mysql -> datetime</p>
     */
    @Length(message = "文章-定时发布时间，只能是未来",max = FieldLengthConstant.TIME_FORMAT)
    private String timingPublish;

    /**
     * 文章的状态 true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 文章的封面url 不可以为null
     * <p>length < 255</p>
     */
    @ValidateString(value = "文章-封面图",max = FieldLengthConstant.URL)
    private String coverPicture;
}
