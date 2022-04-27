package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.MailTemplateDO;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.vo.MailTemplateVO;
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
    public ModifyResult insertMailTemplate(MailTemplateDO mailTemplate) {
        mailTemplate.setCreateTime(DateUtils.format());
        int insertMailTemplate = mailTemplateDao.insertMailTemplate(mailTemplate);
        return ModifyResult.operateResult(insertMailTemplate, "插入邮件模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), mailTemplate.getUid());
    }

    @Override
    public ModifyResult updateMailTemplate(MailTemplateDO mailTemplate) {
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
    public List<MailTemplateVO> queryAllMailTemplate(ConditionDTO<Long> condition)
            throws ReflectiveOperationException {
        condition.init(condition);
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy());
        return BeanUtils.copyList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    @Override
    public MailTemplateVO queryMailTemplateByUid(long uid) throws ReflectiveOperationException {
        ConditionDTO<Long> condition = ConditionDTO.instant(uid, Long.class, true);
        return BeanUtils.getSingleObjFromList(mailTemplateDao.queryAllMailTemplate(condition), MailTemplateVO.class);
    }

    @Override
    public MailTemplateVO queryMailTemplateByUserUid(long userUid) throws ReflectiveOperationException {
        ConditionDTO<Long> condition = ConditionDTO.instant(userUid, Long.class, false);
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
