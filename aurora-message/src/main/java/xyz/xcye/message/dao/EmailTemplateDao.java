package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.table.EmailTemplateDO;
import xyz.xcye.common.dto.ConditionDTO;

import java.util.List;

/**
 * 操作数据库中的au_email表，dao层
 * @author qsyyke
 */

@Mapper
public interface EmailTemplateDao {
    /**
     * 插入邮件发送模板
     * @param emailTemplate
     * @return
     */
    int insertEmailTemplate(@Param("emailTemplate") EmailTemplateDO emailTemplate);

    /**
     * 更新邮件发送模板
     * @param emailTemplate
     * @return
     */
    int updateEmailTemplate(@Param("emailTemplate") EmailTemplateDO emailTemplate);

    /**
     * 根据uid删除邮件发送模板
     * @param uid
     * @return
     */
    int deleteEmailTemplate(@Param("uid") long uid);

    /**
     * 查询所有的邮件发送模板，查询的条件只能是删除状态的true或者false
     * @param condition 查询条件,只需要设置uid和create_time
     * @return
     */
    List<EmailTemplateDO> queryAllEmailTemplate(@Param("condition") ConditionDTO condition);
}
