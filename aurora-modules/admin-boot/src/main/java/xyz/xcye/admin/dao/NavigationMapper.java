package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.Navigation;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface NavigationMapper {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * insertWhiteUrl record to table selective
     * @param record the record
     * @return insertWhiteUrl count
     */
    int insertSelective(Navigation record);

    /**
     * select by primary key
     * @param condition 查询参数，keyword->title(不是模糊查询)
     * @return object by primary key
     */
    List<Navigation> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Navigation record);
}