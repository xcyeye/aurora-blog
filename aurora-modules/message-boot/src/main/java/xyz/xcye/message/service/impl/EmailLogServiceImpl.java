package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.PageUtils;
import xyz.xcye.core.util.ValidationUtils;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.message.dao.EmailLogDao;
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.vo.EmailLogVO;

import javax.validation.groups.Default;
import java.util.Date;

/**
 * @author qsyyke
 */

@Service
public class EmailLogServiceImpl implements EmailLogService {

    @Autowired
    private EmailLogDao emailLogDao;

    @Override
    public int insertEmailLog(EmailLog emailLog) {
        //因为au_email_log表中的uid是自增的，可以不用设置uid
        //设置创建时间
        emailLog.setCreateTime(DateUtils.format(new Date()));
        return emailLogDao.insertEmailLog(emailLog);
    }

    @Override
    public int updateEmailLog(EmailLog emailLog) throws BindException {
        //参数验证
        ValidationUtils.valid(emailLog, Insert.class, Default.class);
        return emailLogDao.updateEmailLog(emailLog);
    }

    @Override
    public int deleteEmailLog(long uid) {
        return emailLogDao.deleteEmailLog(uid);
    }

    @Override
    public PageData<EmailLogVO> queryAll(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.pageList(BeanUtils.copyList(emailLogDao.queryAll(condition),EmailLogVO.class));
    }

    @Override
    public EmailLogVO queryByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        return BeanUtils.getSingleObjFromList(emailLogDao.queryAll(condition), EmailLogVO.class);
    }
}
