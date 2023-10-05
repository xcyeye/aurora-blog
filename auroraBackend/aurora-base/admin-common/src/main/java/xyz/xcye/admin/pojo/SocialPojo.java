package xyz.xcye.admin.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * @author xcye <br/>
 * @description social数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialPojo {

    /**
     * 唯一uid,自增
     */
    @Schema(title = "唯一uid,自增")
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 社交名称
     */
    @Schema(title = "社交名称")
    @Length(max = FieldLengthConstant.SOCIAL_NAME)
    @ValidateString(value = "社交名称", max = FieldLengthConstant.SOCIAL_NAME, groups = Insert.class)
    private String socialName;

    /**
     * 此社交图标的地址
     */
    @Schema(title = "此社交图标的地址")
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "社交图标地址", max = FieldLengthConstant.URL, groups = Insert.class)
    private String socialIcon;

    /**
     * 此社交的链接
     */
    @Schema(title = "此社交的链接")
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "社交链接地址", max = FieldLengthConstant.URL, groups = Insert.class)
    private String socialUrl;

    /**
     * 1： 显示此社交 0： 不显示
     */
    @Schema(title = "1： 显示此社交 0： 不显示")
    private Boolean show;

    /**
     * 此社交属于哪个用户
     */
    @Schema(title = "此社交属于哪个用户")
    private Long userUid;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private String createTime;

    /**
     * 最后更新时间
     */
    @Schema(title = "最后更新时间")
    private String updateTime;

    /**
     * 1: 删除 0：不删除
     */
    @Schema(title = "1: 删除 0：不删除")
    private Boolean delete;

}