package xyz.xcye.message.service;

import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;
import xyz.xcye.message.po.MailTemplate;
import xyz.xcye.message.vo.MailTemplateVO;

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
    int insertMailTemplate(MailTemplate mailTemplate);

    /**
     * 根据uid更新邮件模板 不提供修改userUid操作
     * @param mailTemplate
     * @return
     */
    int updateMailTemplate(MailTemplate mailTemplate);

    /**
     * 根据uid删除邮件模板
     * @param uid
     * @return
     */
    int deleteMailTemplate(long uid);

    /**
     * 根据特定条件，查询邮件模板，其中keyword为replace_arr
     * @param condition
     * @return
     */
    PageData<MailTemplateVO> queryAllMailTemplate(Condition<Long> condition);

    /**
     * 根据uid查询特定的邮件模板
     * @param uid
     * @return
     */
    MailTemplateVO queryMailTemplateByUid(long uid);

    /**
     * 根据userUid查询模板
     */
    MailTemplateVO queryMailTemplateByUserUid(long userUid);
}
