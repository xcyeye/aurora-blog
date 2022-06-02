package xyz.xcye.comment.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author qsyyke
 * @date Created in 2022/6/2 08:23
 */

@Data
public class CommentVO {
    /**
     * 唯一uid 不能为null 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 此评论对应注册用户中的哪个用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userUid;

    /**
     * 是否逻辑删除
     */
    private Boolean delete;

    /**
     * 评论这的用户名 不能为null
     * <p>length < 15</p>
     */
    private String username;

    /**
     * 头像地址 可以为null
     * <p>length < 255</p>
     */
    private String avatar;

    /**
     * 站点地址 不能为null
     * <p>length < 255</p>
     */
    private String site;

    /**
     * 邮箱地址 不能为null 用于接收邮件
     * <p>length < 32</p>
     */
    private String email;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String createTime;

    /**
     * 最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updateTime;

    /**
     * 评论者的ip 可以为null
     * <p>length < 12</p>
     */
    private String commentIp;

    /**
     * 评论者的操作系统信息 可以为null
     * <p>length < 200</p>
     */
    private String operationSystemInfo;

    /**
     * 是否显示此条评论 true：显示 false：不显示
     */
    private Boolean showComment;

    /**
     * 此条评论是回复哪条评论的 不能为null
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long replyCommentUid;

    /**
     * 是否发送邮件通知
     */
    private Boolean emailNotice;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 此评论在哪个页面上的评论
     */
    private String path;

    /**
     * 此评论所对应的所有子评论集合，使用,分割开的
     */
    private String nextCommentUidArray;

    /**
     * 此评论是在哪种类型的页面上发布的，可以是说说，文章等
     */
    private String pageType;

    /**
     * 如果此评论是在说说发布的，那么此pageUid就表示说说的uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pageUid;
}
