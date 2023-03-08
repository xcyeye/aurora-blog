package xyz.xcye.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.article.po.Article;
import xyz.xcye.service.base.BaseDao;

/**
 * @table article <br/>
 * @description article 数据表DAO层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraArticleDao extends BaseDao<Article> {

}