package xyz.xcye.article.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * @description talk数据表的POJO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkPojo {

    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 发布此说说的用户uid
     */
    @Schema(title = "发布此说说的用户uid")
    private Long userUid;

    /**
     * 此说说是否显示评论 1： 显示 0： 不显示
     */
    @Schema(title = "此说说是否显示评论 1： 显示 0： 不显示")
    private Boolean showComment;

    /**
     * 此说说的评论uid集合
     */
    @Schema(title = "此说说的评论uid集合")
    private String commentUids;

    /**
     * 最后更新时间
     */
    @Schema(title = "最后更新时间")
    private String updateTime;

    /**
     * 此说说发布时间
     */
    @Schema(title = "此说说发布时间")
    private String createTime;

    /**
     * 此说说标题
     */
    @Schema(title = "此说说标题")
    @Length(max = FieldLengthConstant.TITLE)
    private String title;

    /**
     * 1： 显示说说，0： 不显示说说
     */
    @Schema(title = "1： 显示说说，0： 不显示说说")
    private Boolean show;

    /**
     * 此说说的点赞数
     */
    @Schema(title = "此说说的点赞数")
    private Integer likeNumber;

    /**
     * 此说说对应的图片uid集合
     */
    @Schema(title = "此说说对应的图片uid集合")
    private String pictureSrcList;

    /**
     * 1: 已删除
     */
    @Schema(title = "1: 已删除")
    private Boolean delete;

    /**
     * 此说说的内容
     */
    @Schema(title = "此说说的内容")
    @Length(max = FieldLengthConstant.CONTENT)
    @ValidateString(value = "此说说的内容", max = FieldLengthConstant.CONTENT, groups = Insert.class)
    private String content;

}