package xyz.xcye.article.service;

import xyz.xcye.article.po.Tag;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:30
 */

public interface TagService {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    int physicsDeleteByUid(Long uid);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Tag record);

    /**
     * select by primary key
     * @param condition 查询条件，其中keyword->title(模糊查询)
     * @return object by primary key
     */
    PageData<TagVO> selectByCondition(Condition<Long> condition);

    TagVO selectByUid(Long uid);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Tag record);
}
