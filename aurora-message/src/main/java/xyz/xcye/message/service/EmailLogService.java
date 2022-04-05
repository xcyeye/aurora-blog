package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.EmailLog;

import java.math.BigInteger;
import java.util.List;

/**
 * 操作au_email_log数据表的service层
 * @author qsyyke
 */

public interface EmailLogService {
    ModifyResult insertEmailLog(EmailLog emailLog);
    ModifyResult updateEmailLog(EmailLog emailLog) throws BindException;
    ModifyResult deleteEmailLog(BigInteger uid);
    List<EmailLog> queryAll(EmailLog emailLog, Pagination pagination);
    EmailLog queryByUid(BigInteger uid);
}
