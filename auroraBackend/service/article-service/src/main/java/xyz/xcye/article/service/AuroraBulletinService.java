package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.article.dao.AuroraBulletinDao;
import xyz.xcye.article.po.Bulletin;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table bulletin <br/>
 * @description bulletin 数据表Service层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Service
public class AuroraBulletinService extends BaseService<Bulletin> {
    @SuppressWarnings("unused")
    private AuroraBulletinDao bulletinDao;

    @Autowired
    public void setInfoDao(AuroraBulletinDao bulletinDao) {
        super.setBaseDao(bulletinDao);
        this.bulletinDao = bulletinDao;
    }
}