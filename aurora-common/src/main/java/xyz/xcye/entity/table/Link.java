package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 数据表 au_link
 * @author qsyyke
 */

@Data
public class Link {
    /**
     * 唯一uid 主键 不能为null
     */
    private BigInteger uid;

    /**
     * 此友情链接对应的用户uid 可以为null
     */
    private BigInteger userUid;

    /**
     * 删除状态 1：已删除 0：未删除 不能为null
     */
    private int deleteStatus;

    /**
     * 此条友情链接属于哪个分类 不能为null
     */
    private BigInteger categoryUid;

    /**
     * logo地址 不能为null
     * <p>长度<255</p>
     */
    private String linkLogo;

    /**
     * 链接地址 不能为null
     * <p>长度<255</p>
     */
    private String linkUrl;

    /**
     * 名称 不能为null
     * <p>长度<30</p>
     */
    private String linkTitle;

    /**
     * 描述信息 可以为null
     * <p>长度<150</p>
     */
    private String linkDescription;

    /**
     * 站点封面图 可以为null
     * <p>长度<255</p>
     */
    private String linkCover;

    /**
     * 是否展示此条友情链接 1：展示 0：不展示 不能为null
     */
    private int publishStatus;

    /**
     * 邮箱 不能为null
     * <p>长度<255</p>
     */
    private String email;

    /**
     * 对方qq号 可以为null
     */
    private int qqNumber;
}
