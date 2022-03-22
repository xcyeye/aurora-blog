package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 数据表 au_category
 * @author qsyyke
 */

@Data
public class Category {
    /**
     * 唯一uid 主键 不能为null
     */
    private BigInteger uid;

    /**
     * 此类别的标题 不能为null
     * <p>length < 100</p>
     */
    private String title;

    /**
     * 此类别的简介 可以为null
     * <p>length < 500</p>
     */
    private String summary;

    /**
     * 1：已删除 0：未删除 不能为null
     */
    private int deleteStatus;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String createdAt;

    /**
     * 最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updatedAt;

    /**
     * 此类别的封面图地址 可以为null
     */
    private BigInteger coverUid;
}
