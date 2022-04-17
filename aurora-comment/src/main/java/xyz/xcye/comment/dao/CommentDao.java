package xyz.xcye.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.CommentDO;

import java.util.List;

/**
 * 评论的dao层
 * @author qsyyke
 */

@Mapper
public interface CommentDao {
    /**
     * 插入一条评论数据，根据commentDO对象
     * @param commentDO
     * @return
     */
    int insertComment(@Param("commentDO") CommentDO commentDO);

    /**
     * 修改此uid对应的记录的delete状态，没有真正的从数据库中删除
     * @param uid
     * @return
     */
    int setCommentDeleteStatus(@Param("uid") Long uid);

    /**
     * 根据uid删除对应的记录，是真正的从数据库中删除此条记录
     * @param uid
     * @return
     */
    int deleteComment(@Param("uid") Long uid);

    /**
     * 修改一条评论数据，根据commentDO对象
     * @param commentDO
     * @return
     */
    int updateComment(@Param("commentDO") CommentDO commentDO);

    /**
     * 根据传入的commentDO，查询多条评论数据
     * @param commentDO
     * @return
     */
    List<CommentDO> queryAllComment(@Param("commentDO") CommentDO commentDO);

    /**
     * 根据uid查询一条评论数据
     * @param uid
     * @return
     */
    CommentDO querySingleByUid(@Param("uid") Long uid);

    //List<CommentDO> queryAllCommentByCondition(@Param("commentDO") CommentDO commentDO);
}
