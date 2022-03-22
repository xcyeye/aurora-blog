package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 说说表
 * @author qsyyke
 */

@Data
public class Talk {
    /**
     * 唯一id 不能为null 主键
     */
    private BigInteger uid;

    /**
     * 该条说说所包含的图片集合 集合中的就是图片地址 可以为null
     */
    private String pictureArr;

    /**
     * 该条说说是否显示评论 1：显示 0：不显示 不能为null
     */
    private int show_comment;

    /**
     * 删除状态 1：已删除 0：未删除 不能为null
     */
    private int deleteStatus;

    /**
     * 最后更新时间 数据库中的类型为datetime 可以为null
     */
    private String updatedAt;

    /**
     * 创建时间 不能为null
     */
    private String createdAt;

    /**
     * 说说内容 数据表中的类型为longtext 不能为null
     */
    private String content;

    /**
     * 说说标题 可以为null
     */
    private String title;

    /**
     * 该条说说显示状态 1：显示 0：不显示 不能为null
     */
    private int showStatus;

    /**
     * 如果有评论 commentArray保存该条说说对应的所有评论的父uid集合
     */
    private String commentArray;

    /**
     * 点赞数 不能为null
     */
    private int likeNumber;

    /**
     * 该条说说是由哪个用户创建的 因为存在索引关系，所以该字段可以为null
     */
    private BigInteger userUid;
}
