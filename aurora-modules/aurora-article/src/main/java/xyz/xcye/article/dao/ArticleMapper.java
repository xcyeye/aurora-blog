package xyz.xcye.article.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.article.po.Article;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * insertArticle record to table selective
     * @param record the record
     * @return insertArticle count
     */
    int insertSelective(Article record);

    /**
     * select by primary key
     * @param condition 查询条件，其中keyword-> content,status->is_publish,show->is_show_comment,delete->is_delete
     * @return object by primary key
     */
    List<Article> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Article record);
}