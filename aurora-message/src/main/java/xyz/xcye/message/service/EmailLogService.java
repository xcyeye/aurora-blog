package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.entity.table.EmailLogDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.EmailLogVO;

import java.util.List;

/**
 * 操作au_email_log数据表的service层
 * @author qsyyke
 */

public interface EmailLogService {
    /**
     * 插入一条邮件发送日志
     * @param emailLog
     * @return
     */
    ModifyResult insertEmailLog(EmailLogDO emailLog);

    /**
     * 更新一条邮件发送日志
     */
    ModifyResult updateEmailLog(EmailLogDO emailLog) throws BindException;

    /**
     * 根据uid直接删除对应的邮件发送日志记录
     */
    ModifyResult deleteEmailLog(long uid);

    /**
     * 查询所有满足要求的邮件发送日志
     * @param condition 查询条件 其中keyword为接收者邮箱号,发送状态为status
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    List<EmailLogVO> queryAll(ConditionDTO<Long> condition) throws InstantiationException, IllegalAccessException;

    /**
     * 查询uid所对应的邮件发送日志
     */
    EmailLogVO queryByUid(long uid) throws InstantiationException, IllegalAccessException;
}
