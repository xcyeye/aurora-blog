package xyz.xcye.message.service;

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
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.message.pojo.EmailLogPojo;
import xyz.xcye.message.vo.EmailLogVO;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@Service
public class EmailLogService {

    @Autowired
    private AuroraEmailLogService auroraEmailLogService;

    public void insertEmailLog(EmailLogPojo emailLog) {
        //因为au_email_log表中的uid是自增的，可以不用设置uid
        auroraEmailLogService.insert(BeanUtils.copyProperties(emailLog, EmailLog.class));
    }

    public int updateEmailLog(EmailLogPojo emailLog) throws BindException {
        //参数验证
        ValidationUtils.valid(emailLog, Update.class, Default.class);
        return auroraEmailLogService.updateById(BeanUtils.copyProperties(emailLog, EmailLog.class));
    }

    public int physicalDeleteEmailLog(long uid) {
        return auroraEmailLogService.deleteById(uid);
    }

    public PageData<EmailLogVO> queryListEmailLogByCondition(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.copyPageDataResult(auroraEmailLogService.queryListByCondition(condition),EmailLogVO.class);
    }

    public EmailLogVO queryByUid(long uid) {
        return BeanUtils.copyProperties(auroraEmailLogService.queryById(uid), EmailLogVO.class);
    }
}
