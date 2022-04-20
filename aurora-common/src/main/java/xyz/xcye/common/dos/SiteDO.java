package xyz.xcye.common.dos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_site
 * @author qsyyke
 */

@Data
public class SiteDO {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 站点关键词 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "站点-站点关键词",max = FieldLengthConstant.KEYWORD)
    private String keyword;

    /**
     * 站点描述 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "站点-站点描述",max = FieldLengthConstant.DESCRIPTION)
    private String description;

    /**
     * 站点的icon地址 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "站点-站点的icon地址不能超过{max}")
    private String siteIcon;

    /**
     * 站点的标题 浏览器顶部 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthConstant.TITLE,message = "站点-站点的标题，浏览器顶部不能超过{max}")
    private String title;

    /**
     * 站点前台logo地址 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "站点-站点的前台logo地址不能超过{max}")
    private String logoTitle;

    /**
     * 站点的logo地址 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "站点-站点的logo地址不能超过{max}")
    private String siteLogo;

    /**
     * 站点前台中间部分logo 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "站点-站点的中间部分logo地址不能超过{max}")
    private String siteCenterLogo;

    /**
     * 站点的地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "站点-站点的host",max = FieldLengthConstant.URL)
    private String siteHost;

    /**
     * 站点额外的head信息 可以为null
     * <p>长度<500</p>
     */
    @Length(max = FieldLengthConstant.STRING_ARRAY,message = "站点-站点额外的head信息不能超过{max}")
    private String additionalHead;

    /**
     * 该站点信息属于哪个用户 因为存在索引关系，可以为null
     */
    private Long userUid;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
