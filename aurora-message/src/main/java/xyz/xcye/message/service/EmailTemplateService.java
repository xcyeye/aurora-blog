package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.dos.EmailTemplateDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.common.vo.EmailTemplateVO;

import java.util.List;

/**
 * @author qsyyke
 */

public interface EmailTemplateService {
    /**
     * 向数据库中插入一条email
     * @param emailTemplate email对象
     * @return
     */
    ModifyResult insertEmailTemplate(EmailTemplateDO emailTemplate);

    /**
     * 根据uid删除一条记录
     * @param uid
     * @return
     */
    ModifyResult deleteEmailTemplateByUid(long uid);

    /**
     * 根据uid修改记录 修改的内容在email对象中
     */
    ModifyResult updateEmailTemplate(EmailTemplateDO emailTemplate);

    /**
     * 根据自定义条件查询对应的内容
     * @param condition 存放查询条件
     */
    List<EmailTemplateVO> queryAllEmailTemplate(ConditionDTO<Long> condition) throws InstantiationException, IllegalAccessException;

    EmailTemplateVO queryEmailTemplateByUid(long uid) throws InstantiationException, IllegalAccessException;
}
