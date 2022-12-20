package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Article;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.article.service.ArticleService;
import xyz.xcye.article.vo.ArticleVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 20:24
 */

@Tag(name = "和博客数据相关的控制器")
@RequestMapping("/blog/article")
@RestController
@RefreshScope
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "插入新文章")
    @ModifyOperation
    @PostMapping("/insertArticle")
    public void insertArticle(@Validated({Insert.class, Default.class}) @RequestBody ArticlePojo article) {
        articleService.insertArticle(article);
    }

    @ModifyOperation
    @Operation(summary = "逻辑删除文章")
    @PostMapping("/logicDeleteArticle")
    public int logicDeleteArticle(@RequestBody long uid) {
        return articleService.logicDeleteArticle(uid);
    }

    @Operation(summary = "物理删除文章")
    @ModifyOperation
    @PostMapping("/physicalDeleteArticle")
    public int physicalDeleteArticle(@RequestBody long uid) {
        return articleService.physicalDeleteArticle(uid);
    }

    @Operation(summary = "修改文章数据")
    @ModifyOperation
    @PostMapping("/updateArticle")
    public int updateArticle(@Validated({Update.class, Default.class}) @RequestBody ArticlePojo article) {
        return articleService.updateArticle(article);
    }

    @SelectOperation
    @Operation(summary = "根据uid查询文章数据")
    @PostMapping("/queryArticleByUid")
    public ArticleVO queryArticleByUid(@RequestBody long uid) {
        return articleService.queryArticleByUid(uid);
    }

    @Operation(summary = "通过条件查询文章数据")
    @PostMapping("/queryListArticleByCondition")
    @SelectOperation
    public PageData<ArticleVO> queryListArticleByCondition(@RequestBody Condition<Long> condition) {
        return articleService.queryListArticleByCondition(condition);
    }
}
