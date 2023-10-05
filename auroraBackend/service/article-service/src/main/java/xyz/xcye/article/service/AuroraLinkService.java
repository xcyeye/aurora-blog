package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.article.dao.AuroraLinkDao;
import xyz.xcye.article.po.Link;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table link <br/>
 * @description link 数据表Service层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Service
public class AuroraLinkService extends BaseService<Link> {
    @SuppressWarnings("unused")
    private AuroraLinkDao linkDao;

    @Autowired
    public void setInfoDao(AuroraLinkDao linkDao) {
        super.setBaseDao(linkDao);
        this.linkDao = linkDao;
    }
}