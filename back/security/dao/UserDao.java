package xyz.xcye.wg.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.wg.dto.SecurityUserDTO;

/**
 * @author qsyyke
 */

@Mapper
public interface UserDao {
    /**
     * 通过唯一uid查询用户的信息
     * @param username 用户名
     * @return usershiti
     */
    SecurityUserDTO queryUserByUsername(@Param("username")String username);
}
