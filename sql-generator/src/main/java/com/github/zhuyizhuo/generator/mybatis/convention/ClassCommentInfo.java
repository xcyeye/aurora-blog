package com.github.zhuyizhuo.generator.mybatis.convention;

import com.github.zhuyizhuo.generator.mybatis.annotation.CoventionClass;
import com.github.zhuyizhuo.generator.mybatis.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类注释
 * @author yizhuo
 * @since  1.0
 * @version 1.4.0
 * time: 2018/7/29 16:10
 */
@CoventionClass
public class ClassCommentInfo {
    /** 文件创建时版本号 */
    @Value("#{generate.java.comment.since-version}")
    private String sinceVersion;
    @Value("#{generate.table.default.comment}")
    public static String tableComment;
    /** 当前版本号 */
    @Value("#{generate.java.comment.current-version}")
    private String version;
    /** 作者 */
    @Value("#{generate.java.comment.author}")
    private String author;
    /** 默认生成时间 */
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSinceVersion() {
        return sinceVersion;
    }

    public void setSinceVersion(String sinceVersion) {
        this.sinceVersion = sinceVersion;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        tableComment = tableComment;
    }

    @Override
    public String toString() {
        return "ClassCommentInfo{" +
                "sinceVersion='" + sinceVersion + '\'' +
                ", version='" + version + '\'' +
                ", author='" + author + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
