package xyz.xcye.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table comment <br/>
 * @description comment 数据表DAO层 <br/>
 * @date 2022-12-14 21:35:45 <br/>
 */

@Mapper
public interface AuroraCommentDao extends BaseDao<Comment> {

}