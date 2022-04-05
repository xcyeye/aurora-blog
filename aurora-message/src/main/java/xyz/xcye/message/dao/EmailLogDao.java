package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.EmailLog;

import java.math.BigInteger;
import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface EmailLogDao {
    int insertEmailLog(@Param("emailLog") EmailLog emailLog);
    int updateEmailLog(@Param("emailLog") EmailLog emailLog);
    int deleteEmailLog(@Param("uid") BigInteger uid);
    List<EmailLog> queryAll(@Param("emailLog") EmailLog emailLog);
    EmailLog queryByUid(@Param("uid") BigInteger uid);
}
