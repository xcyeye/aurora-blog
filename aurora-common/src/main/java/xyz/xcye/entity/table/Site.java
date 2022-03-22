package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 数据表 au_site
 * @author qsyyke
 */

@Data
public class Site {
    /**
     * 唯一uid 主键 不能为null
     */
    private BigInteger uid;

    /**
     * 站点关键词 不能为null
     * <p>长度<255</p>
     */
    private String keyword;

    /**
     * 站点描述 不能为null
     * <p>长度<255</p>
     */
    private String description;

    /**
     * 站点的icon地址 可以为null
     * <p>长度<255</p>
     */
    private String siteIcon;

    /**
     * 站点的标题 浏览器顶部 可以为null
     * <p>长度<255</p>
     */
    private String title;

    /**
     * 站点前台logo地址 可以为null
     * <p>长度<255</p>
     */
    private String logoTitle;

    /**
     * 站点的logo地址 可以为null
     * <p>长度<255</p>
     */
    private String siteLogo;

    /**
     * 站点前台中间部分logo 可以为null
     * <p>长度<255</p>
     */
    private String siteCenterLogo;

    /**
     * 站点的地址 不能为null
     * <p>长度<255</p>
     */
    private String siteHost;

    /**
     * 站点额外的head信息 可以为null
     * <p>长度<255</p>
     */
    private String additionalHead;

    /**
     * 该站点信息属于哪个用户 因为存在索引关系，可以为null
     */
    private BigInteger userUid;
}
