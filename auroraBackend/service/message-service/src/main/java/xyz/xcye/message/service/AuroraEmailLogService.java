package xyz.xcye.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.message.dao.AuroraEmailLogDao;
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table email_log <br/>
 * @description email_log 数据表Service层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 */

@Service
public class AuroraEmailLogService extends BaseService<EmailLog> {
    @SuppressWarnings("unused")
    private AuroraEmailLogDao emailLogDao;

    @Autowired
    public void setInfoDao(AuroraEmailLogDao emailLogDao) {
        super.setBaseDao(emailLogDao);
        this.emailLogDao = emailLogDao;
    }
}