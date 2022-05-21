package xyz.xcye.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(title="公告")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BulletinVO implements Serializable {
    /**
    * 唯一uid
    */
    @Schema(title = "唯一uid")
    private Long uid;

    /**
    * 公告的标题
    */
    @Schema(title = "公告的标题")
    private String title;

    /**
    * 公告创建时间
    */
    @Schema(title = "公告创建时间")
    private String createTime;

    /**
    * 公告最后修改时间
    */
    @Schema(title = "公告最后修改时间")
    private String updateTime;

    /**
    * 发布此公告的用户uid
    */
    @Schema(title = "发布此公告的用户uid")
    private Long userUid;

    /**
    * 1: 显示公告 0： 不显示该公告
    */
    @Schema(title = "1: 显示公告 0： 不显示该公告")
    private Boolean show;

    /**
    * 1：定时发布 0： 不定时发布公告
    */
    @Schema(title = "1：定时发布 0： 不定时发布公告")
    private Boolean timing;

    /**
    * 定时发布公告的时间
    */
    @Schema(title = "定时发布公告的时间")
    private String timingPublishTime;

    /**
    * 1:删除 0：未删除
    */
    @Schema(title = "1:删除 0：未删除")
    private Boolean delete;

    /**
    * 公告内容
    */
    @Schema(title = "公告内容")
    private String content;

    private static final long serialVersionUID = 1L;
}