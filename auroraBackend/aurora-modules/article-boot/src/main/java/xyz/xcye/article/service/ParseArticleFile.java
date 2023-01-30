package xyz.xcye.article.service;

import xyz.xcye.article.pojo.ArticlePojo;

import java.io.IOException;

public interface ParseArticleFile {
    // List<CategoryPojo> parseCategory(String frontmatterCategoryName, boolean folderAsCategoryName) throws IOException;
    // List<TagVO> parseTag(String frontmatterTagName) throws IOException;

    ArticlePojo parseArticle(boolean reservedFrontMatter,
                             String frontmatterCategoryName,
                             boolean folderAsCategoryName,
                             String frontmatterTagName,
                             boolean useFileNameAsTitle) throws IOException;
}
