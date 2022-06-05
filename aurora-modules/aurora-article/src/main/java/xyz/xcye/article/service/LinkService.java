package xyz.xcye.article.service;

import org.springframework.validation.BindException;
import xyz.xcye.article.po.Link;
import xyz.xcye.article.vo.LinkVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 19:36
 */

public interface LinkService {

    int deleteByPrimaryKey(Long uid, String replyMessage) throws BindException;

    int insertSelective(Link record) throws BindException;

    PageData<LinkVO> selectByCondition(Condition<Long> condition);

    LinkVO selectByUid(Long uid);

    int updateByPrimaryKeySelective(Link record);

    /**
     * 修改友情链接的发布状态
     * @param uid
     * @param publish
     * @return
     */
    int updateLinkPublishStatus(long uid, boolean publish, String replyMessage) throws BindException;
}
