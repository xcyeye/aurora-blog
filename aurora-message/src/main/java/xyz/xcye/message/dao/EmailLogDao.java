package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.table.EmailLogDO;
import xyz.xcye.common.dto.ConditionDTO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface EmailLogDao {
    int insertEmailLog(@Param("emailLog") EmailLogDO emailLog);
    int updateEmailLog(@Param("emailLog") EmailLogDO emailLog);
    int deleteEmailLog(@Param("uid") long uid);

    /**
     *
     * @param condition 查询条件 其中keyword为接收者邮箱号,发送状态为status
     * @return
     */
    List<EmailLogDO> queryAll(@Param("condition") ConditionDTO condition);
}
