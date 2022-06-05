package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.Social;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface SocialMapper {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(long uid);

    /**
     * insertWhiteUrl record to table selective
     * @param record the record
     * @return insertWhiteUrl count
     */
    int insertSelective(Social record);

    /**
     * select by primary key
     * @param condition 查询参数，keyword->social_name(不是模糊查询)
     * @return object by primary key
     */
    List<Social> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Social record);
}