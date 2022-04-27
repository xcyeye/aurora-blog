package xyz.xcye.message.service;

import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.MailTemplateDO;
import xyz.xcye.common.vo.MailTemplateVO;

import java.util.List;

/**
 * 操作邮件模板的service层
 * @author qsyyke
 * @date Created in 2022/4/27 12:30
 */

public interface MailTemplateService {
    /**
     * 插入新的邮件模板
     * @param mailTemplate
     * @return
     */
    ModifyResult insertMailTemplate(MailTemplateDO mailTemplate);

    /**
     * 根据uid更新邮件模板 不提供修改userUid操作
     * @param mailTemplate
     * @return
     */
    ModifyResult updateMailTemplate(MailTemplateDO mailTemplate);

    /**
     * 根据uid删除邮件模板
     * @param uid
     * @return
     */
    ModifyResult deleteMailTemplate(long uid);

    /**
     * 根据特定条件，查询邮件模板，其中keyword为replace_arr
     * @param condition
     * @return
     */
    List<MailTemplateVO> queryAllMailTemplate(ConditionDTO<Long> condition) throws ReflectiveOperationException;

    /**
     * 根据uid查询特定的邮件模板
     * @param uid
     * @return
     */
    MailTemplateVO queryMailTemplateByUid(long uid) throws ReflectiveOperationException;

    /**
     * 根据userUid查询模板
     */
    MailTemplateVO queryMailTemplateByUserUid(long userUid) throws ReflectiveOperationException;
}
