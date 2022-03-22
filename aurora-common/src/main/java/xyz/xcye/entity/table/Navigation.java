package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 数据表 au_navigation
 * @author qsyyke
 */

@Data
public class Navigation {
    /**
     * 唯一uid 主键 不能为null
     */
    private BigInteger uid;

    /**
     * 前台导航的展示等级，比如0就是一级导航 不能为null
     */
    private int level;

    /**
     * 当前导航的父导航uid，也就是该导航显示在哪个导航下面 不能为null
     */
    private BigInteger preUid;

    /**
     * 导航的标题 不能为null
     * <p>长度<15</p>
     */
    private String title;

    /**
     * 导航对应的地址 不能为null
     * <p>长度<255</p>
     */
    private String path;

    /**
     * 1：链接到外部地址 0：链接地址就是本站地址，不需要新标签中打开 不能为null
     */
    private int isExternal;

    /**
     * 此导航的类名，用户设置图标
     * <p>长度<70</p>
     */
    private String iconClassName;

    /**
     * 此导航的顺序编号 不能为null
     */
    private int sort;

    /**
     * 此导航属于哪个用户
     * <p>因为存在索引关系，所以可以为null</p>
     */
    private BigInteger userUid;
}
