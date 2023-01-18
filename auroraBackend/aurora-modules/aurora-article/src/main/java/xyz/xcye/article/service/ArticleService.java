package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.xcye.article.dao.ext.AuroraArticleExtDao;
import xyz.xcye.article.po.Article;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.pojo.TagPojo;
import xyz.xcye.article.util.TimeUtils;
import xyz.xcye.article.vo.ArticleVO;
import xyz.xcye.article.vo.CategoryVO;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.article.ArticleException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 17:14
 */

@Service
public class ArticleService {

    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private AuroraArticleService auroraArticleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuroraArticleExtDao auroraArticleExtDao;

    public int logicDeleteArticle(Long uid) {
        Assert.notNull(uid, "uid不能为null");

        // 构建一个Article对象
        Article article = Article.builder()
                .uid(uid)
                .delete(true)
                .build();
        // 逻辑删除
        return auroraArticleService.updateById(article);
    }

    public int physicalDeleteArticle(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return auroraArticleService.deleteById(uid);
    }

    // TODO 这里需要有一个定时任务
    @Transactional
    public Long insertArticle(ArticlePojo record) {
        Assert.notNull(record, "插入的文章数据不能为null");
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()));
        // 创建时间是自动生成的
        record.setDelete(false);
        record.setCommentUids(null);
        setUserUid(record);
        // 查询可用的类别和分类
        setCategory(record);
        setTag(record);
        setTimingPublishTime(record);
        return auroraArticleService.insert(BeanUtils.copyProperties(record, Article.class)).getUid();
    }

    public PageData<ArticleVO> queryListArticleByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraArticleService.queryListByCondition(condition), ArticleVO.class);
    }

    public ArticleVO queryListArticleByTagOrCategory(ArticlePojo pojo) {
        return queryArticleByTagInfo(pojo);
    }

    public ArticleVO queryArticleByUid(Long uid) {
        return BeanUtils.copyProperties(auroraArticleService.queryById(uid), ArticleVO.class);
    }

    @Transactional
    public int updateArticle(ArticlePojo record) {
        Assert.notNull(record, "文章数据不能为null");
        setCategory(record);
        setTag(record);
        record.setUserUid(null);
        setTimingPublishTime(record);
        return auroraArticleService.updateById(BeanUtils.copyProperties(record, Article.class));
    }

    public void updateArticleLikeNum(ArticlePojo pojo) {
        Article article = auroraArticleService.queryById(pojo.getUid());
        AssertUtils.stateThrow(article != null, () -> new ArticleException("此文章不存在"));
        ArticlePojo articlePojo = BeanUtils.copyProperties(article, ArticlePojo.class);
        if (articlePojo.getLikeNumber() == null) {
            articlePojo.setLikeNumber(0);
        }
        articlePojo.setLikeNumber(articlePojo.getLikeNumber() + 1);
        auroraArticleService.updateById(BeanUtils.copyProperties(articlePojo, Article.class));
    }

    public void updateArticleReadNum(ArticlePojo pojo) {
        Article article = auroraArticleService.queryById(pojo.getUid());
        AssertUtils.stateThrow(article != null, () -> new ArticleException("此文章不存在"));
        ArticlePojo articlePojo = BeanUtils.copyProperties(article, ArticlePojo.class);
        if (articlePojo.getReadNumber() == null) {
            articlePojo.setReadNumber(0);
        }
        articlePojo.setReadNumber(articlePojo.getReadNumber() + 1);
        auroraArticleService.updateById(BeanUtils.copyProperties(articlePojo, Article.class));
    }

    private void setCategory(ArticlePojo article) {
        Long currentUserUid = UserUtils.getCurrentUserUid();
        if (currentUserUid == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        // 如果文章中的某个标签或者类别不存在，则添加
        if (!StringUtils.hasLength(article.getCategoryNames())) {
            return;
        }

        String categoryNames = Arrays.stream(article.getCategoryNames().split(","))
                .distinct()
                .filter(categoryName -> {
                    // 如果不存在，则添加
                    CategoryPojo categoryPojo = new CategoryPojo();
                    categoryPojo.setTitle(categoryName);
                    categoryPojo.setUserUid(currentUserUid);
                    if (categoryService.selectByTitle(categoryName) == null) {
                        // 不存在，添加
                        categoryService.insertCategory(categoryPojo);
                    }
                    return true;
                })
                .collect(Collectors.joining(","));
        article.setCategoryNames(categoryNames);

    }

    private void setTag(ArticlePojo article) {
        Long currentUserUid = UserUtils.getCurrentUserUid();
        if (currentUserUid == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        if (!StringUtils.hasLength(article.getTagNames())) {
            return;
        }

        String tagNames = Arrays.stream(article.getTagNames().split(","))
                .distinct()
                .filter(tagName -> {
                    // 如果不存在，则添加
                    TagPojo tagPojo = new TagPojo();
                    tagPojo.setTitle(tagName);
                    tagPojo.setUserUid(currentUserUid);
                    if (tagService.selectByTitle(tagName) == null) {
                        // 不存在，添加
                        tagService.insertTag(tagPojo);
                    }
                    return true;
                })
                .collect(Collectors.joining(","));
        article.setTagNames(tagNames);
    }

    private void setUserUid(ArticlePojo article) {
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        article.setUserUid(jwtUserInfo.getUserUid());
    }

    /**
     * 判断公告对象中的定时发布时间是否规范，如果不规范，则设置为null
     * @param article
     */
    private void setTimingPublishTime(ArticlePojo article) {
        if (article.getTiming() == null || !article.getTiming()) {
            article.setTimingPublishTime(null);
            return;
        }
        if (!TimeUtils.isTimingPublishTime(article.getTimingPublishTime())) {
            article.setTiming(false);
            article.setTimingPublishTime(null);
            return;
        }
        article.setTimingPublishTime(DateUtils.parse(article.getTimingPublishTime()));
    }

    /**
     * 根据标签信息查询对应的文章
     * @param pojo
     * @return
     */
    private ArticleVO queryArticleByTagInfo(ArticlePojo pojo) {
        if (pojo.getTagTitleList() == null) {
            pojo.setTagTitleList(new ArrayList<>());
        }
        if (pojo.getCategoryTitleList() == null) {
            pojo.setCategoryTitleList(new ArrayList<>());
        }
        if (pojo.getTagUidList() != null && !pojo.getTagUidList().isEmpty()) {
            // 查询标签名
            pojo.getTagUidList().forEach(v -> {
                TagVO tagVO = tagService.queryTagByUid(v);
                if (tagVO != null) {
                    pojo.getTagTitleList().add(tagVO.getTitle());
                }
            });
        }

        if (pojo.getCategoryUidList() != null && !pojo.getCategoryUidList().isEmpty()) {
            // 查询标签名
            pojo.getCategoryUidList().forEach(v -> {
                CategoryVO categoryVO = categoryService.queryCategoryByUid(v);
                if (categoryVO != null) {
                    pojo.getCategoryTitleList().add(categoryVO.getTitle());
                }
            });
        }

        ArticleVO articleVO = new ArticleVO();
        List<Map<String, List<ArticleVO>>> tagArticleMapList = new ArrayList<>();
        List<Map<String, List<ArticleVO>>> categoryArticleMapList = new ArrayList<>();
        pojo.getTagTitleList().forEach(title -> {
            List<Article> articleList = auroraArticleExtDao.queryListArticleByTagOrCategory(new ArticlePojo() {{
                setTagTitleList(Collections.singletonList(title));
            }});
            Map<String, List<ArticleVO>> map = new HashMap<>();
            map.put(title, BeanUtils.copyList(articleList, ArticleVO.class));
            tagArticleMapList.add(map);
        });
        pojo.getCategoryTitleList().forEach(title -> {
            List<Article> articleList = auroraArticleExtDao.queryListArticleByTagOrCategory(new ArticlePojo() {{
                setTagTitleList(Collections.singletonList(title));
            }});
            Map<String, List<ArticleVO>> map = new HashMap<>();
            map.put(title, BeanUtils.copyList(articleList, ArticleVO.class));
            categoryArticleMapList.add(map);
        });
        articleVO.setTagArticleList(tagArticleMapList);
        articleVO.setCategoryArticleList(categoryArticleMapList);
        return articleVO;
    }
}
