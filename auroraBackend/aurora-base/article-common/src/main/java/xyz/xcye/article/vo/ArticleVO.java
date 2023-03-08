package xyz.xcye.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.article.po.Article;

import java.util.List;
import java.util.Map;

/**
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "article数据表的VO")
public class ArticleVO extends Article {

    private List<Map<String, List<ArticleVO>>> tagArticleList;
    private List<Map<String, List<ArticleVO>>> categoryArticleList;
}