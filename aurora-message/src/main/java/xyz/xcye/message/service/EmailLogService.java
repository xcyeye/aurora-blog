package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.dos.EmailLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;

import java.math.BigInteger;
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
     */
    List<EmailLogDO> queryAll(EmailLogDO emailLog, PaginationDTO pagination);

    /**
     * 查询uid所对应的邮件发送日志
     */
    EmailLogDO queryByUid(long uid);
}
