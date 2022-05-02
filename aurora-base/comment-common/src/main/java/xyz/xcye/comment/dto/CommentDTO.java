package xyz.xcye.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author qsyyke
 */

@Data
public class CommentDTO {
    /**
     * 唯一uid 不能为null 主键
     */
    private Long uid;

    /**
     * 评论这的用户名 不能为null
     * <p>length < 15</p>
     */
    private String username;

    /**
     * 逻辑删除
     */
    private Boolean delete;

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
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String createTime;

    /**
     * 评论者的操作系统信息 可以为null
     * <p>length < 200</p>
     */
    private String operationSystemInfo;

    /**
     * 此条评论是回复哪条评论的 不能为null
     */
    @NotNull
    private Long replyCommentUid;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 此评论对应的页面地址
     */
    private String path;

    /**
     * 此评论对应此页面的用户
     */
    private Long userUid;

    private List<CommentDTO> sonCommentList;

    private String nextCommentUidArray;
}
