package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.core.dto.Condition;
import xyz.xcye.message.vo.EmailLogVO;

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
    int insertEmailLog(EmailLog emailLog);

    /**
     * 更新一条邮件发送日志
     */
    int updateEmailLog(EmailLog emailLog) throws BindException;

    /**
     * 根据uid直接删除对应的邮件发送日志记录
     */
    int deleteEmailLog(long uid);

    /**
     * 查询所有满足要求的邮件发送日志
     * @param condition 查询条件 其中keyword为接收者邮箱号,发送状态为status
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    List<EmailLogVO> queryAll(Condition<Long> condition) throws ReflectiveOperationException;

    /**
     * 查询uid所对应的邮件发送日志
     */
    EmailLogVO queryByUid(long uid) throws ReflectiveOperationException;
}
