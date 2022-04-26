package xyz.xcye.comment.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.CommentDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.vo.CommentVO;

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
    ModifyResult insertComment(CommentDO commentDO)
            throws BindException, ReflectiveOperationException;

    /**
     * 根据uid删除对应的记录，是真正的从数据库中删除此条记录
     * @param uid
     * @return
     */
    ModifyResult deleteComment(Long uid);

    /**
     * 修改一条评论数据，根据commentDTO对象
     * @param commentDO
     * @return
     */
    ModifyResult updateComment(CommentDO commentDO);

    /**
     * 根据传入的arrayCommentUid评论uid数据，获取对应的所有评论节点数据
     * @param arrayCommentUid
     * @return
     */
    CommentVO queryArticleComments(long[] arrayCommentUid) throws ReflectiveOperationException;

    /**
     * 根据传入的arrayCommentUid评论uid数据，获取对应的所有评论节点数据
     * @param condition
     * @return
     */
    List<CommentDTO> queryAllComments(ConditionDTO<Long> condition);

    CommentDTO queryByUid(long uid) throws ReflectiveOperationException;
}
