package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.EmailLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.message.dao.EmailLogDao;
import xyz.xcye.message.service.EmailLogService;

import javax.validation.groups.Default;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class EmailLogServiceImpl implements EmailLogService {

    /**
     * 查询时默认的初始页数
     */
    @Value("${aurora.pagination.pageNum}")
    private int defaultPageNum;

    /**
     * 查询时默认的返回数目
     */
    @Value("${aurora.pagination.pageSize}")
    private int defaultPageSize;

    @Autowired
    private EmailLogDao emailLogDao;

    @Override
    public ModifyResult insertEmailLog(EmailLogDO emailLog) {
        //因为au_email_log表中的uid是自增的，可以不用设置uid
        //设置创建时间
        emailLog.setCreateTime(DateUtils.format(new Date()));
        int insertEmailLogNum = emailLogDao.insertEmailLog(emailLog);
        return ModifyResult.operateResult(insertEmailLogNum,"插入邮件发送日志",emailLog);
    }

    @Override
    public ModifyResult updateEmailLog(EmailLogDO emailLog) throws BindException {
        //参数验证
        ValidationUtils.valid(emailLog, Insert.class, Default.class);

        int updateEmailLogNum = emailLogDao.updateEmailLog(emailLog);

        if (updateEmailLogNum == 1) {
            //修改数据成功，调用查询，返回最新的数据
            emailLog = queryByUid(emailLog.getUid());
        }
        return ModifyResult.operateResult(updateEmailLogNum,"修改邮件发送日志",emailLog);
    }

    @Override
    public ModifyResult deleteEmailLog(long uid) {
        int deleteEmailLogNum = emailLogDao.deleteEmailLog(uid);
        return ModifyResult.operateResult(deleteEmailLogNum,"删除" + uid + "对应的邮件发送日志",null);
    }

    @Override
    public List<EmailLogDO> queryAll(EmailLogDO emailLog, PaginationDTO pagination) {
        pagination = PaginationDTO.initPagination(pagination,defaultPageNum,defaultPageSize);
        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());

        List<EmailLogDO> emailLogs = emailLogDao.queryAll(emailLog);
        PageInfo<EmailLogDO> emailLogPageInfo = new PageInfo<>(emailLogs);
        return emailLogPageInfo.getList();
    }

    @Override
    public EmailLogDO queryByUid(long uid) {
        return emailLogDao.queryByUid(uid);
    }
}
