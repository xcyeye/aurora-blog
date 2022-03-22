package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author qsyyke
 */

@Data
public class Social {
    /**
     * 用户唯一uid 不能为null 主键
     */
    private BigInteger uid;

    /**
     * 社交名称 不能为null
     * <p>长度<10</p>
     */
    private String socialName;

    /**
     * 社交图标的地址 不能为null
     * <p>长度<255</p>
     */
    private String socialIcon;

    /**
     * 社交的链接地址 不能为null
     * <p>长度<255</p>
     */
    private String socialUrl;

    /**
     * 此社交属于哪个用户 因为存在索引关系 可以为null
     */
    private BigInteger userUid;

    /**
     * 1：显示此社交 0：不显示
     */
    private int showStatus;

    /**
     * 删除状态 1：已删除 0：未删除
     */
    private int deleteStatus;
}
