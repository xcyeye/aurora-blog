package xyz.xcye.article;

import xyz.xcye.article.service.impl.ParseMarkdownArticleFileImpl;

import java.io.File;
import java.io.IOException;

/**
 * @author xcye
 * @description
 * @date 2023-01-20 23:28:59
 */

public class MarkdownTest {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\qsyyk\\Documents\\笔记\\spring\\springboot\\单元测试.md");
        ParseMarkdownArticleFileImpl parseMarkdownArticleFile = new ParseMarkdownArticleFileImpl(file);
        parseMarkdownArticleFile.parseArticle();
        System.out.println();

    }
}
