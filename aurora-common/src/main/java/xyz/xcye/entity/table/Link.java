package xyz.xcye.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Insert;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;
import javax.validation.constraints.NotNull;
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
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 此友情链接对应的用户uid 可以为null
     */
    private BigInteger userUid;

    /**
     * 删除状态 true：已删除 false：未删除 不能为null
     */
    private boolean deleteStatus;

    /**
     * 此条友情链接属于哪个分类 不能为null
     */
    private BigInteger categoryUid;

    /**
     * logo地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "友情链接-对方的logo地址",max = FieldLengthEnum.URL)
    private String linkLogo;

    /**
     * 链接地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "友情链接-对方的站点地址",max = FieldLengthEnum.URL)
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
    @Length(max = FieldLengthEnum.LINK_DESCRIPTION,message = "友情链接-对方的描述信息不能超过{value}")
    private String linkDescription;

    /**
     * 站点封面图 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthEnum.URL,message = "友情链接-对方的站点封面图地址不能超过{max}")
    private String linkCover;

    /**
     * 是否展示此条友情链接 true：展示 false：不展示 不能为null
     */
    private boolean publishStatus;

    /**
     * 邮箱 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "友情链接-对方的邮箱号",max = FieldLengthEnum.EMAIL_NUMBER)
    private String email;

    /**
     * 对方qq号 可以为null
     */
    private int qqNumber;
}
