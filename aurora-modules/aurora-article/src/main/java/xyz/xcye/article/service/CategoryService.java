package xyz.xcye.article.service;

import xyz.xcye.article.po.Category;
import xyz.xcye.article.vo.CategoryVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:21
 */

public interface CategoryService {

    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * 通过uid物理删除
     * @param uid
     * @return
     */
    int physicsDeleteByUid(Long uid);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Category record);

    /**
     * select by primary key
     * @param condition 查询条件，其中keyword->title(模糊查询)
     * @return object by primary key
     */
    PageData<CategoryVO> selectByCondition(Condition<Long> condition);

    CategoryVO selectByUid(Long uid);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Category record);
}
