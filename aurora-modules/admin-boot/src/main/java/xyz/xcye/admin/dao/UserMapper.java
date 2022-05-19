package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.User;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(User record);

    /**
     * select by primary key
     * @param condition primary key
     * @return object by primary key
     */
    List<User> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(User record);
}