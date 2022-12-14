package xyz.xcye.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.message.po.MailTemplate;
import xyz.xcye.message.dao.AuroraMailTemplateDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table mail_template <br/>
 * @description mail_template 数据表Service层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraMailTemplateService extends BaseService<MailTemplate> {
	@SuppressWarnings("unused")
	private AuroraMailTemplateDao mailTemplateDao;
	
	@Autowired
    public void setInfoDao(AuroraMailTemplateDao mailTemplateDao) {
        super.setBaseDao(mailTemplateDao);
        this.mailTemplateDao = mailTemplateDao;
    }
}