package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    public Long insertArticle(@Validated({Insert.class, Default.class}) @RequestBody ArticlePojo article) {
        return articleService.insertArticle(article);
    }

    @ModifyOperation
    @Operation(summary = "逻辑删除文章")
    @PostMapping("/logicDeleteArticle")
    public int logicDeleteArticle(@RequestBody ArticlePojo article) {
        return articleService.logicDeleteArticle(article.getUid());
    }

    @Operation(summary = "物理删除文章")
    @ModifyOperation
    @PostMapping("/physicalDeleteArticle")
    public int physicalDeleteArticle(@RequestBody ArticlePojo article) {
        return articleService.physicalDeleteArticle(article.getUid());
    }

    @Operation(summary = "修改文章数据")
    @ModifyOperation
    @PostMapping("/updateArticle")
    public int updateArticle(@Validated({Update.class, Default.class}) @RequestBody ArticlePojo article) {
        return articleService.updateArticle(article);
    }

    private final AtomicInteger count = new AtomicInteger(0);

    @Operation(summary = "修改文章点赞数")
    @ModifyOperation
    @PostMapping("/updateArticleLikeNum")
    public void updateArticleLikeNum(@Validated({Update.class, Default.class}) @RequestBody ArticlePojo article) {
        Thread.currentThread().setName("点赞线程");
        articleService.updateArticleLikeNum(article);
    }

    @Operation(summary = "修改文章阅读数")
    @ModifyOperation
    @PostMapping("/updateArticleReadNum")
    public void updateArticleReadNum(@Validated({Update.class, Default.class}) @RequestBody ArticlePojo article) {
        articleService.updateArticleReadNum(article);
    }

    @SelectOperation
    @Operation(summary = "根据uid查询文章数据")
    @PostMapping("/queryArticleByUid")
    public ArticleVO queryArticleByUid(@RequestBody ArticlePojo article) {
        return articleService.queryArticleByUid(article.getUid());
    }

    @Operation(summary = "通过条件查询文章数据")
    @PostMapping("/queryListArticleByCondition")
    @SelectOperation
    public PageData<ArticleVO> queryListArticleByCondition(@RequestBody Condition<Long> condition) {
        return articleService.queryListArticleByCondition(condition);
    }

    @Operation(summary = "通过标签或者类别查询对应的文章")
    @PostMapping("/queryListArticleByTagOrCategory")
    @SelectOperation
    public ArticleVO queryListArticleByTagOrCategory(@RequestBody ArticlePojo pojo) {
        return articleService.queryListArticleByTagOrCategory(pojo);
    }

    @PostMapping("/queryTotalArticleCount")
    @ModifyOperation
    @Operation(summary = "查询用户数")
    public Integer queryTotalArticleCount(@RequestBody ArticlePojo articlePojo) {
        return articleService.queryTotalArticleCount(articlePojo);
    }

    @Operation(summary = "导入文章")
    @PostMapping("/importArticle")
    @SelectOperation
    public void importArticle(ArticlePojo pojo, List<MultipartFile> articleDataFileList) throws IOException {
        articleService.importArticle(pojo, articleDataFileList);
    }
}
