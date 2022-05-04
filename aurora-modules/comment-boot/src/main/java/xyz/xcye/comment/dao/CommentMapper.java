package xyz.xcye.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.mybatis.entity.Condition;

import java.util.List;

/**
 * 评论的dao层
 * @author qsyyke
 */

@Mapper
public interface CommentMapper {
    /**
     * 插入一条评论数据，根据Comment对象
     * @param comment
     * @return
     */
    int insertComment(Comment comment);

    /**
     * 根据uid删除对应的记录，是真正的从数据库中删除此条记录
     * @param uid
     * @return
     */
    int deleteComment(@Param("uid") Long uid);

    /**
     * 修改一条评论数据，根据Comment对象
     * @param comment
     * @return
     */
    int updateComment(Comment comment);

    int updateDeleteStatus(@Param("comment") Comment comment);

    /**
     * 根据传入的Comment，查询多条评论数据
     * @param condition 其中keyword为username,otherUid为userUid
     * @return
     */
    List<Comment> queryAllComment(@Param("condition") Condition condition);
}
