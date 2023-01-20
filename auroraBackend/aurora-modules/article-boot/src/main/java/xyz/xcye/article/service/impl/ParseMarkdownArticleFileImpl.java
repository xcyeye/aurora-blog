package xyz.xcye.article.service.impl;

import org.springframework.util.StringUtils;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.service.ParseArticleFile;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.core.util.file.StringFileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xcye
 * @description
 * @date 2023-01-20 23:07:34
 */

public class ParseMarkdownArticleFileImpl implements ParseArticleFile {

    private File articleFile;

    public ParseMarkdownArticleFileImpl(File articleFile) {
        this.articleFile = articleFile;
    }

    @Override
    public List<CategoryPojo> parseCategory() {
        return null;
    }

    @Override
    public List<TagVO> parseTag() {
        return null;
    }

    @Override
    public ArticlePojo parseArticle() throws IOException {
        String fileContent = StringFileUtils.getFileContent(this.articleFile);
        getFrontMatterInfo(fileContent);
        return null;
    }

    /**
     * 从markdown内容中获取Frontmatter信息
     * @param markdownContent
     * @return
     */
    private Map<String, List<String>> getFrontMatterInfo(String markdownContent) {
        if (!StringUtils.hasLength(markdownContent)) {
            return new HashMap<>();
        }
        String frontMatterContent = "";
        Pattern pattern = Pattern.compile("^(---)(---)$");
        Matcher matcher = pattern.matcher(markdownContent);
        while (matcher.find()) {
            frontMatterContent = matcher.group(1);
        }
        System.out.println(frontMatterContent);

        return new HashMap<>();
    }
}
