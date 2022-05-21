package xyz.xcye.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(title="说说")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalkVO implements Serializable {
    /**
    * 唯一uid
    */
    @Schema(title = "唯一uid")
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
    private String updatedTime;

    /**
    * 此说说发布时间
    */
    @Schema(title = "此说说发布时间")
    private String createdTime;

    /**
    * 此说说标题
    */
    @Schema(title = "此说说标题")
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
    private String pictureUids;

    /**
    * 1: 已删除
    */
    @Schema(title = "1: 已删除")
    private Boolean delete;

    /**
    * 此说说的内容
    */
    @Schema(title = "此说说的内容")
    private String content;

    private static final long serialVersionUID = 1L;
}