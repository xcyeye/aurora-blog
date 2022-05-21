package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Article;
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
    @PostMapping
    public int insertArticle(@Validated({Insert.class, Default.class}) Article article) {
        return articleService.insertArticle(article);
    }

    @ModifyOperation
    @Operation(summary = "逻辑删除文章")
    @DeleteMapping("/{uid}")
    public int deleteArticleByUid(@PathVariable("uid") long uid) {
        return articleService.deleteByPrimaryKey(uid);
    }

    @Operation(summary = "物理删除文章")
    @ModifyOperation
    @DeleteMapping("/physics/{uid}")
    public int physicsDeleteArticleByUid(@PathVariable("uid") long uid) {
        return articleService.physicsDeleteByUid(uid);
    }

    @Operation(summary = "修改文章数据")
    @ModifyOperation
    @PutMapping
    public int updateArticle(@Validated({Update.class, Default.class}) Article article) {
        return articleService.updateByPrimaryKeySelective(article);
    }

    @SelectOperation
    @Operation(summary = "根据uid查询文章数据")
    @GetMapping("/{uid}")
    public ArticleVO selectArticleByUid(@PathVariable("uid") long uid) {
        return articleService.selectByUid(uid);
    }

    @Operation(summary = "通过条件查询文章数据")
    @GetMapping
    @SelectOperation
    public PageData<ArticleVO> selectArticleByCondition(Condition<Long> condition) {
        return articleService.selectByCondition(condition);
    }
}
