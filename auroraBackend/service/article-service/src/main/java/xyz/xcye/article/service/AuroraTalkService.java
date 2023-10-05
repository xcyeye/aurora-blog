package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.article.dao.AuroraTalkDao;
import xyz.xcye.article.po.Talk;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table talk <br/>
 * @description talk 数据表Service层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Service
public class AuroraTalkService extends BaseService<Talk> {
    @SuppressWarnings("unused")
    private AuroraTalkDao talkDao;

    @Autowired
    public void setInfoDao(AuroraTalkDao talkDao) {
        super.setBaseDao(talkDao);
        this.talkDao = talkDao;
    }
}