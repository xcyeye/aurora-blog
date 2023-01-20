package xyz.xcye.article.service;

import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.vo.TagVO;

import java.io.IOException;
import java.util.List;

public interface ParseArticleFile {
    List<CategoryPojo> parseCategory();
    List<TagVO> parseTag();

    ArticlePojo parseArticle() throws IOException;
}
