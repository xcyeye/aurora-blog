package xyz.xcye.comment.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xcye <br/>
 * @table comment <br/>
 * @description TODO <br/>
 * @date 2022-12-14 21:35:45 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "comment数据表的实体类")
public class Comment implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 唯一uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "唯一uid")
    private Long uid;

    /**
     * 此评论的用户名
     */
    @Schema(title = "此评论的用户名")
    private String username;

    /**
     * 此评论这的头像uid
     */
    @Schema(title = "此评论这的头像uid")
    private String avatar;

    /**
     * 此评论者的博客地址
     */
    @Schema(title = "此评论者的博客地址")
    private String site;

    /**
     * 此评论这的邮箱地址
     */
    @Schema(title = "此评论这的邮箱地址")
    private String email;

    /**
     * 此评论的创时间
     */
    @Schema(title = "此评论的创时间")
    private String createTime;

    /**
     * 此评论最后修改时间
     */
    @Schema(title = "此评论最后修改时间")
    private String updateTime;

    /**
     * 评论者的ip地址
     */
    @Schema(title = "评论者的ip地址")
    private String commentIp;

    /**
     * 评论者的浏览器版本
     */
    @Schema(title = "评论者的浏览器版本")
    private String operationSystemInfo;

    /**
     * 是否显示此评论 1： 显示 0： 不显示
     */
    @Schema(title = "是否显示此评论 1： 显示 0： 不显示")
    private Boolean showComment;

    /**
     * 此评论是回复哪个评论的
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "此评论是回复哪个评论的")
    private Long replyCommentUid;

    /**
     * 如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知
     */
    @Schema(title = "如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知")
    private Boolean emailNotice;

    /**
     * 在哪个地址发布评论
     */
    @Schema(title = "在哪个地址发布评论")
    private String path;

    /**
     * 此评论的所有下一条集合
     */
    @Schema(title = "此评论的所有下一条集合")
    private String nextCommentUidArray;

    /**
     * 评论内容
     */
    @Schema(title = "评论内容")
    private String content;

    /**
     * 此评论是属于哪个用户的
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "此评论是属于哪个用户的")
    private Long userUid;

    /**
     * 1：删除 0：未删除
     */
    @Schema(title = "1：删除 0：未删除")
    private Boolean delete;

    /**
     * 此评论是在哪种页面发布的
     */
    @Schema(title = "此评论是在哪种页面发布的")
    private String pageType;

}