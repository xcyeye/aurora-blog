package xyz.xcye.common.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_link
 * @author qsyyke
 */

@Data
public class LinkDO {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 此友情链接对应的用户uid 可以为null
     */
    private Long userUid;

    /**
     * 删除状态 true：已删除 false：未删除 不能为null
     */
    private Boolean delete;

    /**
     * 此条友情链接属于哪个分类 不能为null
     */
    private Long categoryUid;

    /**
     * logo地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "友情链接-对方的logo地址",max = FieldLengthConstant.URL)
    private String linkLogo;

    /**
     * 链接地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "友情链接-对方的站点地址",max = FieldLengthConstant.URL)
    private String linkUrl;

    /**
     * 名称 不能为null
     * <p>长度<30</p>
     */
    @ValidateString(value = "友情链接-对方的站点名称",max = 30)
    private String linkTitle;

    /**
     * 描述信息 可以为null
     * <p>长度<150</p>
     */
    @Length(max = FieldLengthConstant.LINK_DESCRIPTION,message = "友情链接-对方的描述信息不能超过{value}")
    private String linkDescription;

    /**
     * 站点封面图 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "友情链接-对方的站点封面图地址不能超过{max}")
    private String linkCover;

    /**
     * 是否展示此条友情链接 true：展示 false：不展示 不能为null
     */
    private Boolean publish;

    /**
     * 邮箱 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "友情链接-对方的邮箱号",max = FieldLengthConstant.EMAIL_NUMBER)
    private String email;

    /**
     * 对方qq号 可以为null
     */
    private Integer qqNumber;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
