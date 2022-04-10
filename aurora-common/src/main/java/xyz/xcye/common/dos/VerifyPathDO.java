package xyz.xcye.common.dos;

import lombok.Data;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.Pattern;

/**
 * 数据表au_role
 * @author qsyyke
 */

@Data
public class VerifyPathDO {

    /**
     * 唯一uid，没有使用雪花算法
     */
    private Integer uid;

    /**
     * 路径
     */
    @ValidateString(value = "需要授权的路径",max = 255)
    private String path;

    /**
     * 角色 角色的命名必须遵循spring security规范，以ROLE_XXX，一个用户只能有一个角色
     */
    @Pattern(regexp = "^(ROLE_)[a-zA-Z]{1,10}",message = "角色命名必须ROLE_XXX，并且总长度不能大于15")
    private String role;

    /**
     * 用户权限集合，可以有多个，使用,分割开
     */
    @ValidateString(value = "用户权限集合",max = 15)
    private String permission;

    /**
     * 此path的访问是否必须拥有此role的用户才能访问
     */
    private Boolean onlyRole;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
