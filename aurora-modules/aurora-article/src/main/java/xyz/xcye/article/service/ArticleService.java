package xyz.xcye.article.service;

import xyz.xcye.article.po.Article;
import xyz.xcye.article.vo.ArticleVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 17:01
 */

public interface ArticleService {

    /**
     * 这是一个逻辑删除
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * 根据主键物理删除
     * @param uid
     * @return
     */
    int physicsDeleteByUid(Long uid);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Article record);

    /**
     * select by primary key
     * @param condition 查询条件，其中keyword-> content,status->is_publish,show->is_show_comment,delete->is_delete
     * @return object by primary key
     */
    PageData<ArticleVO> selectByCondition(Condition<Long> condition);

    /**
     * 通过uid查询对应的文章数据
     * @param uid
     * @return
     */
    ArticleVO selectByUid(Long uid);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Article record);
}
