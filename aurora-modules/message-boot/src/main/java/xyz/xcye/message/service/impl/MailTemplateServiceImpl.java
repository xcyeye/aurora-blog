package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.core.dto.Condition;
import xyz.xcye.core.entity.result.ModifyResult;
import xyz.xcye.message.po.MailTemplate;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.message.vo.MailTemplateVO;
import xyz.xcye.message.dao.MailTemplateDao;
import xyz.xcye.message.service.MailTemplateService;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/4/27 12:33
 */

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Override
    public ModifyResult insertMailTemplate(MailTemplate mailTemplate) {
        mailTemplate.setCreateTime(DateUtils.format());
        int insertMailTemplate = mailTemplateDao.insertMailTemplate(mailTemplate);
        return ModifyResult.operateResult(insertMailTemplate, "插入邮件模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), mailTemplate.getUid());
    }

    @Override
    public ModifyResult updateMailTemplate(MailTemplate mailTemplate) {
        mailTemplate.setUpdateTime(DateUtils.format());
        int updateMailTemplate = mailTemplateDao.updateMailTemplate(mailTemplate);
        return ModifyResult.operateResult(updateMailTemplate, "更新邮件模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), mailTemplate.getUid());
    }

    @Override
    public ModifyResult deleteMailTemplate(long uid) {
        int deleteMailTemplate = mailTemplateDao.deleteMailTemplate(uid);
        return ModifyResult.operateResult(deleteMailTemplate, "删除邮件模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public List<MailTemplateVO> queryAllMailTemplate(Condition<Long> condition)
            throws ReflectiveOperationException {
        condition.init(condition);
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy());
        return BeanUtils.copyList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    @Override
    public MailTemplateVO queryMailTemplateByUid(long uid) throws ReflectiveOperationException {
        Condition<Long> condition = Condition.instant(uid, Long.class, true);
        return BeanUtils.getSingleObjFromList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    @Override
    public MailTemplateVO queryMailTemplateByUserUid(long userUid) throws ReflectiveOperationException {
        Condition<Long> condition = Condition.instant(userUid, Long.class, false);
        return BeanUtils.getSingleObjFromList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    private String getCorrectReplaceArrStr(String replaceArrStr) {
        String[] splitArr = replaceArrStr.split(",");
        StringBuilder builder = new StringBuilder();
        for (String splitStr : splitArr) {
            builder.append(splitStr);
            builder.append(",");
        }

        return builder.length() > 0 ? builder.substring(0,builder.length() -1) : builder.toString();
    }
}
