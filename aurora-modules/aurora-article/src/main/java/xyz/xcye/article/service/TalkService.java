package xyz.xcye.article.service;

import xyz.xcye.article.po.Talk;
import xyz.xcye.article.vo.TalkVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:40
 */

public interface TalkService {

    /**
     * 逻辑删除
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * 物理删除
     * @param uid
     * @return
     */
    int physicsDeleteByUid(Long uid);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Talk record);

    /**
     * select by primary key
     * @param condition 查询条件 其中keyword->content(模糊查询),status->is_show_comment,show->is_show
     * @return object by primary key
     */
    PageData<TalkVO> selectByCondition(Condition<Long> condition);

    /**
     * 返回一条数据
     * @param uid
     * @return
     */
    TalkVO selectByUid(Long uid);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Talk record);
}
