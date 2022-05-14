package xyz.xcye.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.auth.vo.LoginInfoVO;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface LoginInfoMapper {
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
    int insertSelective(LoginInfo record);

    /**
     * select by primary key
     * @param uid primary key
     * @return object by primary key
     */
    List<LoginInfoVO> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(LoginInfo record);
}