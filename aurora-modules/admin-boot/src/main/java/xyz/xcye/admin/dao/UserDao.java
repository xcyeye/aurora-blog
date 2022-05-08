package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.User;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface UserDao {
    int insertUser(@Param("user") User user);

    int updateUser(@Param("user") User user);

    int deleteByUid(@Param("uid") long uid);

    /**
     * 根据条件查询用户信息
     * @param condition 查询条件，其中keyword => username,otherUid => user_account_uid,delete => delete
     * @return
     */
    List<User> queryAllByCondition(@Param("condition") Condition condition);
}