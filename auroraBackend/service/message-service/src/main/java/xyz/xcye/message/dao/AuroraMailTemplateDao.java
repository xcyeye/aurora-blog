package xyz.xcye.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.message.po.MailTemplate;
import xyz.xcye.service.base.BaseDao;

/**
 * @table mail_template <br/>
 * @description mail_template 数据表DAO层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraMailTemplateDao extends BaseDao<MailTemplate> {

}