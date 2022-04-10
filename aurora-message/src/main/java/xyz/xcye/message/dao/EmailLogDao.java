package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.EmailLogDO;

import java.math.BigInteger;
import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface EmailLogDao {
    int insertEmailLog(@Param("emailLog") EmailLogDO emailLog);
    int updateEmailLog(@Param("emailLog") EmailLogDO emailLog);
    int deleteEmailLog(@Param("uid") long uid);
    List<EmailLogDO> queryAll(@Param("emailLog") EmailLogDO emailLog);
    EmailLogDO queryByUid(@Param("uid") long uid);
}
