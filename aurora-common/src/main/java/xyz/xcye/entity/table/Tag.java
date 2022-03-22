package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 数据表au_tag
 * @author qsyyke
 */

@Data
public class Tag {

    /**
     * 唯一uid 不能为null 主键
     */
    private BigInteger uid;

    /**
     * 标签的标题 长度<100 不能为null
     */
    private String title;

    /**
     * 标签的简介 长度<500 可以为null
     */
    private String summary;

    /**
     * 该标签的删除状态 1：已删除 0：未删除 不能为null
     */
    private int deleteStatus;

    /**
     * 标签的创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String createdAt;

    /**
     * 标签最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updatedAt;

    /**
     * 该标签的封面图片uid 因为图片是从文件服务里面获取的，所以可以为null
     */
    private BigInteger coverUid;
}
