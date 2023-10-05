package xyz.xcye.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.message.dao.AuroraEmailDao;
import xyz.xcye.message.po.Email;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table email <br/>
 * @description email 数据表Service层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 */

@Service
public class AuroraEmailService extends BaseService<Email> {
    @SuppressWarnings("unused")
    private AuroraEmailDao emailDao;

    @Autowired
    public void setInfoDao(AuroraEmailDao emailDao) {
        super.setBaseDao(emailDao);
        this.emailDao = emailDao;
    }
}