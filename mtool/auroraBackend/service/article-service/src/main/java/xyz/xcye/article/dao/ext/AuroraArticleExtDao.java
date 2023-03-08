package xyz.xcye.article.dao.ext;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.article.po.Article;
import xyz.xcye.article.pojo.ArticlePojo;

import java.util.List;

@Mapper
public interface AuroraArticleExtDao {
    public List<Article> queryListArticleByTagOrCategory(ArticlePojo pojo);
}
