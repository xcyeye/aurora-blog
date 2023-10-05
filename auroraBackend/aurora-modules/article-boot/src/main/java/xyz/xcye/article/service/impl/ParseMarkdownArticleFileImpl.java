package xyz.xcye.article.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;
import xyz.xcye.article.po.Tag;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.service.ParseArticleFile;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.file.StringFileUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author xcye
 * @description
 * @date 2023-01-20 23:07:34
 */

@Slf4j
public class ParseMarkdownArticleFileImpl implements ParseArticleFile {

    private MultipartFile articleFile;

    public ParseMarkdownArticleFileImpl(MultipartFile articleFile) {
        this.articleFile = articleFile;
    }

    private List<CategoryPojo> parseCategory(String frontmatterCategoryName, boolean folderAsCategoryName) throws IOException {
        List<CategoryPojo> categoryPojoList = new ArrayList<>();
        if (!StringUtils.hasLength(frontmatterCategoryName)) {
            return categoryPojoList;
        }
        if (folderAsCategoryName) {
            // TODO 将此文件的目录作为类别 目前是使用该文件的上一级目录
            String originalFilename = this.articleFile.getOriginalFilename();
            if (StringUtils.hasLength(originalFilename)) {
                String[] folderArr = originalFilename.split("/");
                if (folderArr.length > 1) {
                    // 如果等于1的话，只有一级
                    List<String> folderList = Arrays.stream(folderArr).collect(Collectors.toList());
                    folderList.remove(folderArr.length - 1);
                    folderList.forEach(v -> {
                        CategoryPojo categoryPojo = new CategoryPojo();
                        categoryPojo.setTitle(v);
                        categoryPojo.setUserUid(UserUtils.getCurrentUserUid());
                        categoryPojo.setDelete(false);
                        categoryPojoList.add(categoryPojo);
                    });
                }
            }
        }
        String markdownArticleContent = StringFileUtils.getFileContent(this.articleFile.getInputStream());
        Map<String, Object> frontMatterInfo = getFrontMatterInfo(markdownArticleContent, true);
        if (frontMatterInfo == null) {
            return categoryPojoList;
        }
        Object categoryInfo = frontMatterInfo.get(frontmatterCategoryName);
        if (categoryInfo instanceof List) {
            ((List<String>) categoryInfo).stream().forEach(v -> {
                CategoryPojo categoryPojo = new CategoryPojo();
                categoryPojo.setTitle(v);
                categoryPojo.setUserUid(UserUtils.getCurrentUserUid());
                categoryPojo.setDelete(false);
                categoryPojoList.add(categoryPojo);
            });
        }
        return categoryPojoList;
    }

    private List<TagVO> parseTag(String frontmatterTagName) throws IOException {
        if (!StringUtils.hasLength(frontmatterTagName)) {
            return new ArrayList<>();
        }
        String markdownArticleContent = StringFileUtils.getFileContent(this.articleFile.getInputStream());
        Map<String, Object> frontMatterInfo = getFrontMatterInfo(markdownArticleContent, true);
        if (frontMatterInfo == null) {
            return new ArrayList<>();
        }
        Object tagInfo = frontMatterInfo.get(frontmatterTagName);
        if (tagInfo instanceof List) {
            return ((List<String>) tagInfo).stream().map(v -> {
                TagVO tagVO = new TagVO();
                tagVO.setTitle(v);
                tagVO.setDelete(false);
                tagVO.setUserUid(UserUtils.getCurrentUserUid());
                return tagVO;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public ArticlePojo parseArticle(boolean reservedFrontMatter,
                                    String frontmatterCategoryName,
                                    boolean folderAsCategoryName,
                                    String frontmatterTagName,
                                    boolean useFileNameAsTitle,
                                    boolean useFirstArticlePictureAsCover) throws IOException {
        String markdownArticleContent = StringFileUtils.getFileContent(this.articleFile.getInputStream());
        if (!StringUtils.hasLength(markdownArticleContent)) {
            LogUtils.logCommonInfo("在解析" + this.articleFile.getOriginalFilename() + "文件时，该文件不存在内容");
            return null;
        }
        ArticlePojo articlePojo = new ArticlePojo();
        articlePojo.setUserUid(UserUtils.getCurrentUserUid());
        articlePojo.setPublish(true);
        List<TagVO> tagList = parseTag(frontmatterTagName);
        List<CategoryPojo> categoryList = parseCategory(frontmatterCategoryName, folderAsCategoryName);
        articlePojo.setTagNames(tagList.stream().map(Tag::getTitle).collect(Collectors.joining(",")));
        articlePojo.setCategoryNames(categoryList.stream().map(CategoryPojo::getTitle).collect(Collectors.joining(",")));

        if (useFileNameAsTitle) {
            // 使用文件名作为标题
            articlePojo.setTitle(getTitleFromFileName(this.articleFile, markdownArticleContent));
        } else {
            articlePojo.setTitle(getTitleFromContent(markdownArticleContent));
        }
        Map<String, Object> yamlMap = getFrontMatterInfo(markdownArticleContent, reservedFrontMatter);
        String summary = "";
        if (yamlMap == null || yamlMap.isEmpty()) {
            articlePojo.setContent(markdownArticleContent);
            summary = markdownArticleContent;
        } else {
            String auroraFinalArticleContent = (String) yamlMap.get("auroraFinalArticleContent");
            if (StringUtils.hasLength(auroraFinalArticleContent)) {
                articlePojo.setContent(auroraFinalArticleContent);
                summary = auroraFinalArticleContent;
            } else {
                articlePojo.setContent(markdownArticleContent);
                summary = markdownArticleContent;
            }
        }
        summary = summary.replaceAll("\n", "");
        if (summary.length() > 200) {
            summary = summary.substring(0, 200);
        }
        articlePojo.setSummary(summary);

        // 设置文章封面
        setArticleCoverPicture(articlePojo, useFirstArticlePictureAsCover);
        return articlePojo;
    }

    /**
     * 从markdown内容中获取Frontmatter信息
     *
     * @param markdownContent
     * @return Map or Null
     */
    private Map<String, Object> getFrontMatterInfo(String markdownContent, boolean reservedFrontMatter) throws IOException {
        Map<String, Object> yamlMap = new HashMap<>();
        if (!StringUtils.hasLength(markdownContent)) {
            return yamlMap;
        }
        // frontMatter内容
        String frontMatterContent = "";
        String frontMatterArticleContent = "";
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(markdownContent.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder frontMatterContentBuilder = new StringBuilder();
        StringBuilder frontMatterArticleContentBuilder = new StringBuilder();
        boolean frontMatterStart = false;
        boolean frontMatterEnd = false;
        String readLine = "";
        while ((readLine = reader.readLine()) != null && !frontMatterEnd) {
            if ("---".equals(readLine) && frontMatterStart) {
                frontMatterEnd = true;
            }
            if (frontMatterStart && !frontMatterEnd) {
                frontMatterContentBuilder.append(readLine).append("\n");
            }
            if ("---".equals(readLine)) {
                frontMatterStart = true;
            }
            frontMatterArticleContentBuilder.append(readLine).append("\n");
        }
        frontMatterContent = frontMatterContentBuilder.toString();
        frontMatterArticleContent = frontMatterArticleContentBuilder.toString();
        reader.close();

        Yaml yaml = new Yaml();
        try {
            yamlMap = yaml.loadAs(frontMatterContent, Map.class);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
            // 解析yaml可能出问题
            return yamlMap;
        }
        if (yamlMap != null && !reservedFrontMatter) {
            // 将frontmatter替换掉
            markdownContent = markdownContent.substring(frontMatterArticleContent.length());
            yamlMap.put("auroraFinalArticleContent", markdownContent);
            yamlMap.put("auroraFrontMatterContent", frontMatterContent);
        }
        return yamlMap;
    }

    private String getTitleFromFileName(MultipartFile articleFile, String markdownArticleContent) {
        // 使用文件名作为标题
        String originalFilename = this.articleFile.getOriginalFilename();
        if (StringUtils.hasLength(originalFilename)) {
            String[] titleSplits = originalFilename.split("/");
            String fileName = titleSplits[titleSplits.length - 1];
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return getTitleFromContent(markdownArticleContent);
        }
    }

    private String getTitleFromContent(String markdownArticleContent) {
        // 使用md中的第一个一级标题作为标题
        String titleRegex = "#{1} .+\n";
        Pattern pattern = Pattern.compile(titleRegex);
        Matcher matcher = pattern.matcher(markdownArticleContent);
        boolean findTitle = matcher.find();
        if (findTitle) {
            String title = matcher.group(0);
            title = title.replace("# ", "");
            title = title.replace("\n", "");
            return title;
        } else {
            // 截取字符串的前10个字符
            StringBuilder builder = new StringBuilder();
            String markdownArticleContentTemp = markdownArticleContent;
            markdownArticleContentTemp = markdownArticleContentTemp.replaceAll("\n", "");
            if (markdownArticleContentTemp.length() > 10) {
                builder.append("临时标题 - ").append(markdownArticleContentTemp, 0, 10);
            } else {
                builder.append("临时标题 - ").append(markdownArticleContentTemp);
            }
            return builder.toString();
        }
    }

    private void setArticleCoverPicture(ArticlePojo articlePojo, boolean useFirstArticlePictureAsCover) {
        if (useFirstArticlePictureAsCover) {
            String content = articlePojo.getContent();
            String regStr = "!\\[[0-9a-zA-Z-~!@#$%^&*()._+]*]\\(((https|http)://.*)";
            Pattern pattern = Pattern.compile(regStr);
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                String firstPicture = matcher.group(0);
                firstPicture = firstPicture.replaceAll("!\\[[0-9a-zA-Z-~!@#$%^&*()._+]*]\\(", "");
                firstPicture = firstPicture.substring(0, firstPicture.length() - 1);
                articlePojo.setCoverPictureUrl(firstPicture);
            }
        }
    }
}
