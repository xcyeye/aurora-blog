package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.common.entity.table.EmailTemplateDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.vo.EmailTemplateVO;
import xyz.xcye.message.dao.EmailTemplateDao;
import xyz.xcye.message.service.EmailTemplateService;

import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    private EmailTemplateDao emailTemplateDao;

    @Override
    public ModifyResult insertEmailTemplate(EmailTemplateDO emailTemplate) {
        emailTemplate.setCreateTime(DateUtils.format());
        int insertEmailTemplateNum = emailTemplateDao.insertEmailTemplate(emailTemplate);
        return ModifyResult.operateResult(insertEmailTemplateNum,"插入邮件发送模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), insertEmailTemplateNum);
    }

    @Override
    public ModifyResult deleteEmailTemplateByUid(long uid) {
        int deleteEmailTemplateNum = emailTemplateDao.deleteEmailTemplate(uid);
        return ModifyResult.operateResult(deleteEmailTemplateNum, "删除邮件发送模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public ModifyResult updateEmailTemplate(EmailTemplateDO emailTemplate) {
        int updateEmailTemplateNum = emailTemplateDao.updateEmailTemplate(emailTemplate);
        return ModifyResult.operateResult(updateEmailTemplateNum,"更新邮件发送模板",
                ResponseStatusCodeEnum.SUCCESS.getCode(), emailTemplate.getUid());
    }

    @Override
    public List<EmailTemplateVO> queryAllEmailTemplate(ConditionDTO<Long> condition) throws InstantiationException, IllegalAccessException {
        condition = condition.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return BeanUtils.copyList(emailTemplateDao.queryAllEmailTemplate(condition),EmailTemplateVO.class);
    }

    @Override
    public EmailTemplateVO queryEmailTemplateByUid(long uid) throws InstantiationException, IllegalAccessException {
        ConditionDTO<Object> condition = new ConditionDTO<>();
        condition.setUid(uid);
        return BeanUtils.getSingleObjFromList(emailTemplateDao.queryAllEmailTemplate(condition), EmailTemplateVO.class);
    }

    @Override
    public EmailTemplateVO queryEmailTemplateByUserUid(long userUid) throws InstantiationException, IllegalAccessException {
        ConditionDTO<Object> condition = new ConditionDTO<>();
        condition.setOtherUid(userUid);
        return BeanUtils.getSingleObjFromList(emailTemplateDao.queryAllEmailTemplate(condition), EmailTemplateVO.class);
    }
}
