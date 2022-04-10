package xyz.xcye.comment.service;

import org.apache.ibatis.annotations.Param;
import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;

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
    ModifyResult insertComment(CommentDO commentDO);

    /**
     * 修改此uid对应的记录的delete状态，没有真正的从数据库中删除
     * @param uid
     * @return
     */
    ModifyResult setCommentDeleteStatus(Long uid);

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
    CommentVO queryArticleComments(Long[] arrayCommentUid,Long articleUid,Integer pageType);

    /**
     * 根据传入的arrayCommentUid评论uid数据，获取对应的所有评论节点数据
     * @param commentDO
     * @return
     */
    List<CommentDTO> queryAllComments(CommentDO commentDO, PaginationDTO pagination);
}
