package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Email;

import java.util.List;

/**
 * 操作数据库中的au_email表，dao层
 * @author qsyyke
 */

@Mapper
public interface EmailDao {

    int insertEmail(@Param("email") Email email);
    int deleteEmailByUid(@Param("email") Email email);
    int updateEmailByUid(@Param("email") Email email);

    List<Email> queryAllEmail(@Param("email") Email email);

    Email queryByUid(@Param("uid") String uid);
}
