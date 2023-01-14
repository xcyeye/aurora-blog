package xyz.xcye.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.comment.dao.AuroraCommentDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table comment <br/>
 * @description comment 数据表Service层 <br/>
 * @date 2022-12-14 21:35:45 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraCommentService extends BaseService<Comment> {
	@SuppressWarnings("unused")
	private AuroraCommentDao commentDao;
	
	@Autowired
    public void setInfoDao(AuroraCommentDao commentDao) {
        super.setBaseDao(commentDao);
        this.commentDao = commentDao;
    }
}