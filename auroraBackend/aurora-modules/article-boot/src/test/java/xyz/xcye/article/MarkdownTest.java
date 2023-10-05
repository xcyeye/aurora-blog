package xyz.xcye.article;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xcye
 * @description
 * @date 2023-01-20 23:28:59
 */

public class MarkdownTest {
    public static void main(String[] args) throws IOException {
        // File file = new File("E:\\qsyyk\\Documents\\笔记\\spring\\springboot\\单元测试.md");
        // File file = new File("E:\\qsyyk\\Documents\\笔记\\spring\\springboot\\README.md");
        // File file = new File("D:\\aa.txt");
        // File file = new File("E:\\qsyyk\\Documents\\笔记\\spring\\springboot\\issue3.md");
        // ParseMarkdownArticleFileImpl parseMarkdownArticleFile = new ParseMarkdownArticleFileImpl(file);
        // parseMarkdownArticleFile.parseArticle(false);
        // parseMarkdownArticleFile.parseCategory("sdf", true);
        // System.out.println();

        // AntPathMatcher antPathMatcher = new AntPathMatcher();
        // System.out.println(antPathMatcher.match("POST:/**/queryTotal*Count", "POST:/blog/article/queryToftalArticleCount"));

        MarkdownTest markdownTest = new MarkdownTest();
        File file = new File("E:\\qsyyk\\Documents\\笔记\\vue\\vue2\\箭头函数和this.md");
        List<File> list = new ArrayList<>();
        markdownTest.manyFolderToSingleFile(list, file);

        System.out.println();
    }

    private void manyFolderToSingleFile(List<File> fileList, File file) {
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        if (!file.isDirectory()) {
            fileList.add(file);
        } else {
            for (File listFile : file.listFiles()) {
                manyFolderToSingleFile(fileList, listFile);
            }
        }
    }
}
