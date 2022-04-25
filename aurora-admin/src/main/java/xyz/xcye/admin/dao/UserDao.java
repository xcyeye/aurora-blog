package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.table.UserDO;

import java.util.List;

@Mapper
public interface UserDao {
    int insertUser(@Param("userDO") UserDO userDO);

    int updateUser(@Param("userDO") UserDO userDO);

    int updateDeleteStatus(@Param("userDO") UserDO userDO);

    int deleteByUid(@Param("uid") long uid);

    List<UserDO> queryAll(@Param("userDO") UserDO userDO);

    UserDO queryByUid(@Param("uid") long uid);

    UserDO queryByUsername(@Param("username") String username);
}