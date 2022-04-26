package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.table.UserDO;

import java.util.List;

@Mapper
public interface UserDao {
    int insertUser(@Param("userDO") UserDO userDO);

    int updateUser(@Param("userDO") UserDO userDO);

    int deleteByUid(@Param("uid") long uid);

    /**
     * 根据条件查询用户信息
     * @param condition 查询条件，其中keyword为username，status为verifAccount,otherUid为user_account_uid
     * @return
     */
    List<UserDO> queryAllByCondition(@Param("condition") ConditionDTO condition);
}