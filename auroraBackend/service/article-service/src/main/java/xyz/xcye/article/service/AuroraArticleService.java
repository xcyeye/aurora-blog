package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.article.dao.AuroraArticleDao;
import xyz.xcye.article.po.Article;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table article <br/>
 * @description article 数据表Service层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Service
public class AuroraArticleService extends BaseService<Article> {
    @SuppressWarnings("unused")
    private AuroraArticleDao articleDao;

    @Autowired
    public void setInfoDao(AuroraArticleDao articleDao) {
        super.setBaseDao(articleDao);
        this.articleDao = articleDao;
    }
}