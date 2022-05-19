package xyz.xcye.article.service;

import org.apache.ibatis.annotations.Param;
import xyz.xcye.article.po.Bulletin;
import xyz.xcye.article.vo.BulletinVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:05
 */

public interface BulletinService {

    /**
     * 这是一个逻辑删除
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * 通过主键物理删除公告
     * @param uid
     * @return
     */
    int physicsDeleteByUid(Long uid);

    /**
     * insertArticle record to table selective
     * @param record the record
     * @return insertArticle count
     */
    int insertSelective(Bulletin record);

    /**
     * select by primary key
     * @param condition 查询条件,其中keyword->content(like模糊查询),show->is_show, status->is_timing
     * @return object by primary key
     */
    PageData<BulletinVO> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * 通过uid查询一条公告
     * @param uid
     * @return
     */
    BulletinVO selectByUid(Long uid);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Bulletin record);
}
