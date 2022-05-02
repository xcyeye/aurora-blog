package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.core.dto.Condition;
import xyz.xcye.message.po.MailTemplate;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/4/27 12:33
 */

@Mapper
public interface MailTemplateDao {
    /**
     * 插入一条邮件模板
     * @param mailTemplate
     * @return
     */
    int insertMailTemplate(@Param("mailTemplate") MailTemplate mailTemplate);

    /**
     * 根据uid更新邮件模板
     * @param mailTemplate
     * @return
     */
    int updateMailTemplate(@Param("mailTemplate") MailTemplate mailTemplate);

    /**
     * 根据uid删除邮件模板
     * @param uid
     * @return
     */
    int deleteMailTemplate(@Param("uid") long uid);

    /**
     * 根据特定条件，查询邮件模板
     * @param condition 其中keyword为type，不是模糊查询
     * @return
     */
    List<MailTemplate> queryAllMailTemplate(@Param("condition") Condition condition);
}
