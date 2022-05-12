package xyz.xcye.comment.service;

import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * 评论的service层
 * @author qsyyke
 */

public interface CommentService {
    /**
     * 插入一条评论数据，根据commentDTO对象
     * @return
     */
    int insertComment(Comment comment) throws Throwable;

    /**
     * 根据uid删除对应的记录，是真正的从数据库中删除此条记录
     * @param uid
     * @return
     */
    int deleteComment(Long uid);

    /**
     * 修改一条评论数据，根据commentDTO对象
     * @param comment
     * @return
     */
    int updateComment(Comment comment);

    int updateDeleteStatus(Comment comment);

    /**
     * 根据传入的arrayCommentUid评论uid数据，获取对应的所有评论节点数据
     * @param arrayCommentUid
     * @return
     */
    CommentVO queryArticleComments(long[] arrayCommentUid);

    /**
     * 根据传入的arrayCommentUid评论uid数据，获取对应的所有评论节点数据
     * @param condition
     * @return
     */
    List<CommentDTO> queryAllComments(Condition<Long> condition);

    CommentDTO queryByUid(long uid);
}
