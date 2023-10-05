package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.AuroraSiteDao;
import xyz.xcye.admin.po.Site;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table site <br/>
 * @description site 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 */

@Service
public class AuroraSiteService extends BaseService<Site> {
    @SuppressWarnings("unused")
    private AuroraSiteDao siteDao;

    @Autowired
    public void setInfoDao(AuroraSiteDao siteDao) {
        super.setBaseDao(siteDao);
        this.siteDao = siteDao;
    }
}