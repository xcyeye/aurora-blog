package xyz.xcye.message.service;

import xyz.xcye.common.entity.table.EmailTemplateDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.EmailTemplateVO;

import java.util.List;

/**
 * @author qsyyke
 */

public interface EmailTemplateService {
    /**
     * 向数据库中插入一条emailTemplate
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
     * 根据uid修改记录 修改的内容在emailTemplate对象中
     */
    ModifyResult updateEmailTemplate(EmailTemplateDO emailTemplate);

    /**
     * 根据自定义条件查询对应的内容
     * @param condition 查询条件,只需要设置uid和create_time
     */
    List<EmailTemplateVO> queryAllEmailTemplate(ConditionDTO<Long> condition) throws InstantiationException, IllegalAccessException;

    /**
     * 通过uid查询邮件发送模板
     * @param uid
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    EmailTemplateVO queryEmailTemplateByUid(long uid) throws InstantiationException, IllegalAccessException;

    /**
     * 通过userUid查询
     * @param userUid
     * @return
     */
    EmailTemplateVO queryEmailTemplateByUserUid(long userUid) throws InstantiationException, IllegalAccessException;
}
