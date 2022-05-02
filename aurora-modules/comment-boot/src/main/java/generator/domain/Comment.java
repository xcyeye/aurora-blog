package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName au_comment
 */
@TableName(value ="au_comment")
@Data
public class Comment implements Serializable {
    /**
     * 唯一uid
     */
    @TableId(value = "uid")
    private Long uid;

    /**
     * 此评论的用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 此评论这的头像uid
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 此评论者的博客地址
     */
    @TableField(value = "site")
    private String site;

    /**
     * 此评论这的邮箱地址
     */
    @TableField(value = "email")
    private String email;

    /**
     * 此评论的创时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 此评论最后修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 评论者的ip地址
     */
    @TableField(value = "comment_ip")
    private String commentIp;

    /**
     * 评论者的浏览器版本
     */
    @TableField(value = "operation_system_info")
    private String operationSystemInfo;

    /**
     * 是否显示此评论 1： 显示 0： 不显示
     */
    @TableField(value = "is_show_comment")
    private Integer isShowComment;

    /**
     * 此评论是回复哪个评论的
     */
    @TableField(value = "reply_comment_uid")
    private Long replyCommentUid;

    /**
     * 如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知
     */
    @TableField(value = "is_email_notice")
    private Integer isEmailNotice;

    /**
     * 在哪个地址发布评论
     */
    @TableField(value = "path")
    private String path;

    /**
     * 此评论的所有下一条集合
     */
    @TableField(value = "next_comment_uid_array")
    private String nextCommentUidArray;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "user_uid")
    private Long userUid;

    /**
     * 
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}