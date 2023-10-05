package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.AuroraSocialDao;
import xyz.xcye.admin.po.Social;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table social <br/>
 * @description social 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 */

@Service
public class AuroraSocialService extends BaseService<Social> {
    @SuppressWarnings("unused")
    private AuroraSocialDao socialDao;

    @Autowired
    public void setInfoDao(AuroraSocialDao socialDao) {
        super.setBaseDao(socialDao);
        this.socialDao = socialDao;
    }
}