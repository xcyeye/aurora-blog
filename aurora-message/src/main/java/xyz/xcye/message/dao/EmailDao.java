package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.EmailDO;

import java.util.List;

/**
 * 操作数据库中的au_email表，dao层
 * @author qsyyke
 */

@Mapper
public interface EmailDao {

    int insertEmail(@Param("email") EmailDO email);

    int deleteEmailByUid(@Param("uid") long uid);

    int updateDeleteStatus(@Param("email") EmailDO email);

    int updateEmail(@Param("email") EmailDO email);

    List<EmailDO> queryAllEmail(@Param("email") EmailDO email);

    EmailDO queryByUid(@Param("uid") long uid);
    EmailDO queryByUserUid(@Param("userUid") long userUid);
    EmailDO queryByEmail(@Param("email") String email);
}
