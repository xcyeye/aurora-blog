package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.SiteSettingDao;
import xyz.xcye.admin.po.SiteSetting;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table site_setting <br/>
 * @description site_setting 数据表Service层 <br/>
 * @date 2023-01-04 20:42:58 <br/>
 */

@Service
public class AuroraSiteSettingService extends BaseService<SiteSetting> {
    @SuppressWarnings("unused")
    private SiteSettingDao siteSettingDao;

    @Autowired
    public void setInfoDao(SiteSettingDao siteSettingDao) {
        super.setBaseDao(siteSettingDao);
        this.siteSettingDao = siteSettingDao;
    }
}