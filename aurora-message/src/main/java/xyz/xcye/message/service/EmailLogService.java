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
    ModifyResult insertEmailLog(EmailLogDO emailLog);
    ModifyResult updateEmailLog(EmailLogDO emailLog) throws BindException;
    ModifyResult deleteEmailLog(long uid);
    List<EmailLogDO> queryAll(EmailLogDO emailLog, PaginationDTO pagination);
    EmailLogDO queryByUid(long uid);
}
