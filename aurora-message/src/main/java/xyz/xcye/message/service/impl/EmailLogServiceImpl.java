package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Email;
import xyz.xcye.common.entity.table.EmailLog;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.common.util.NameUtil;
import xyz.xcye.common.util.ValidationUtil;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.message.dao.EmailDao;
import xyz.xcye.message.dao.EmailLogDao;
import xyz.xcye.message.service.EmailLogService;

import javax.validation.Valid;
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
    public ModifyResult insertEmailLog(EmailLog emailLog) {
        //因为au_email_log表中的uid是自增的，可以不用设置uid

        //设置创建时间
        emailLog.setCreatedAt(DateUtil.format(new Date()));
        int insertEmailLogNum = emailLogDao.insertEmailLog(emailLog);

        String msg = insertEmailLogNum == 1 ? "插入数据成功" : "插入数据失败";

        return new ModifyResult(insertEmailLogNum,insertEmailLogNum == 1,
                msg,insertEmailLogNum == 1 ? emailLog : null);
    }

    @Override
    public ModifyResult updateEmailLog(EmailLog emailLog) throws BindException {

        //参数验证
        ValidationUtil.valid(emailLog, Insert.class, Default.class);

        int updateEmailLogNum = emailLogDao.updateEmailLog(emailLog);

        String msg = updateEmailLogNum == 1 ? "修改数据成功" : "修改数据失败";

        if (updateEmailLogNum == 1) {
            //修改数据成功，调用查询，返回最新的数据
            emailLog = queryByUid(emailLog.getUid());
        }
        return new ModifyResult(updateEmailLogNum,updateEmailLogNum == 1,msg,emailLog);
    }

    @Override
    public ModifyResult deleteEmailLog(BigInteger uid) {
        int deleteEmailLogNum = emailLogDao.deleteEmailLog(uid);
        String msg = deleteEmailLogNum == 1 ? "删除成功" : "删除失败";

        return new ModifyResult(deleteEmailLogNum,deleteEmailLogNum == 1,msg,null);
    }

    @Override
    public List<EmailLog> queryAll(EmailLog emailLog, Pagination pagination) {
        pagination = Pagination.initPagination(pagination,defaultPageNum,defaultPageSize);
        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());

        List<EmailLog> emailLogs = emailLogDao.queryAll(emailLog);
        PageInfo<EmailLog> emailLogPageInfo = new PageInfo<>(emailLogs);
        return emailLogPageInfo.getList();
    }

    @Override
    public EmailLog queryByUid(BigInteger uid) {
        return emailLogDao.queryByUid(uid);
    }
}
