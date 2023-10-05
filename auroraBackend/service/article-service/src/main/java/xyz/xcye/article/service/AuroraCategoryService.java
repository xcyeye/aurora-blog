package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.article.dao.AuroraCategoryDao;
import xyz.xcye.article.po.Category;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table category <br/>
 * @description category 数据表Service层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Service
public class AuroraCategoryService extends BaseService<Category> {
    @SuppressWarnings("unused")
    private AuroraCategoryDao categoryDao;

    @Autowired
    public void setInfoDao(AuroraCategoryDao categoryDao) {
        super.setBaseDao(categoryDao);
        this.categoryDao = categoryDao;
    }
}