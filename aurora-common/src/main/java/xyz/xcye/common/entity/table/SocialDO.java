package xyz.xcye.common.entity.table;

import lombok.Data;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * @author qsyyke
 */

@Data
public class SocialDO {
    /**
     * 用户唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 社交名称 不能为null
     * <p>长度<10</p>
     */
    @ValidateString(value = "社交-社交名称",max = FieldLengthConstant.SOCIAL_NAME)
    private String socialName;

    /**
     * 社交图标的地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "社交-社交图标地址",max = FieldLengthConstant.URL)
    private String socialIcon;

    /**
     * 社交的链接地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "社交-社交链接地址",max = FieldLengthConstant.URL)
    private String socialUrl;

    /**
     * 此社交属于哪个用户 因为存在索引关系 可以为null
     */
    private Long userUid;

    /**
     * true：显示此社交 false：不显示
     */
    private Boolean show;

    /**
     * 删除状态 true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
