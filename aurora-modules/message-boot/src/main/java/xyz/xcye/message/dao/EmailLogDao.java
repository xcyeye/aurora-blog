package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface EmailLogDao {
    int insertEmailLog(@Param("emailLog") EmailLog emailLog);
    int updateEmailLog(@Param("emailLog") EmailLog emailLog);
    int deleteEmailLog(@Param("uid") long uid);

    /**
     *
     * @param condition 查询条件 其中keyword为接收者邮箱号,发送状态为status
     * @return
     */
    List<EmailLog> queryAll(@Param("condition") Condition condition);
}
