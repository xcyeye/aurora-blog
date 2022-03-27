package xyz.xcye.entity.table;

import lombok.Data;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author qsyyke
 */

@Data
public class Social {
    /**
     * 用户唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 社交名称 不能为null
     * <p>长度<10</p>
     */
    @ValidateString(value = "社交-社交名称",max = FieldLengthEnum.SOCIAL_NAME)
    private String socialName;

    /**
     * 社交图标的地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "社交-社交图标地址",max = FieldLengthEnum.URL)
    private String socialIcon;

    /**
     * 社交的链接地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "社交-社交链接地址",max = FieldLengthEnum.URL)
    private String socialUrl;

    /**
     * 此社交属于哪个用户 因为存在索引关系 可以为null
     */
    private BigInteger userUid;

    /**
     * true：显示此社交 false：不显示
     */
    private boolean showStatus;

    /**
     * 删除状态 true：已删除 false：未删除
     */
    private boolean deleteStatus;
}
