package xyz.xcye.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(title="分类")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagVO implements Serializable {
    /**
    * 唯一uid
    */
    @Schema(title = "唯一uid")
    private Long uid;

    /**
    * 此标签的标题
    */
    @Schema(title = "此标签的标题")
    private String title;

    /**
    * 此标签的简介
    */
    @Schema(title = "此标签的简介")
    private String summary;

    /**
    * 创建时间
    */
    @Schema(title = "创建时间")
    private String createdTime;

    /**
    * 最后修改时间
    */
    @Schema(title = "最后修改时间")
    private String updatedTime;

    /**
    * 此类别的封面图uid
    */
    @Schema(title = "此类别的封面图uid")
    private String coverUrl;

    /**
    * 1: 删除，0：未删除
    */
    @Schema(title = "1: 删除，0：未删除")
    private Boolean delete;

    /**
    * 用户的userUid
    */
    @Schema(title = "用户的userUid")
    private Long userUid;

    private static final long serialVersionUID = 1L;
}