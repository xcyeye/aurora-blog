package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.ValidationUtils;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;
import xyz.xcye.message.dao.EmailLogDao;
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.vo.EmailLogVO;

import javax.validation.groups.Default;

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
        return emailLogDao.insertEmailLog(emailLog);
    }

    @Override
    public int updateEmailLog(EmailLog emailLog) throws BindException {
        //参数验证
        ValidationUtils.valid(emailLog, Update.class, Default.class);
        return emailLogDao.updateEmailLog(emailLog);
    }

    @Override
    public int deleteEmailLog(long uid) {
        return emailLogDao.deleteEmailLog(uid);
    }

    @Override
    public PageData<EmailLogVO> queryAll(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.pageList(condition, t -> BeanUtils.copyList(emailLogDao.queryAll(condition),EmailLogVO.class));
    }

    @Override
    public EmailLogVO queryByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        return BeanUtils.getSingleObjFromList(emailLogDao.queryAll(condition), EmailLogVO.class);
    }
}
