package xyz.xcye.article.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.article.po.Link;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface LinkMapper {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Link record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Link record);

    /**
     * select by primary key
     * @param condition 其中keyword->link_url(不是模糊查询), status->is_publish
     * @return object by primary key
     */
    List<Link> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Link record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Link record);
}