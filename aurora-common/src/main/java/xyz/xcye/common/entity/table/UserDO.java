package xyz.xcye.common.entity.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据库中的aurora_user表的映射
 * @author qsyyke
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDO implements Serializable {
    public static final long serialVersionUID = 42L;

    /**
     * 用户唯一id
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 用户简介
     */
    @Length(max = FieldLengthConstant.SUMMARY,message = "用户-简介不能超过{max}")
    private String userSummary;

    /**
     * 用户昵称
     */
    @Length(max = FieldLengthConstant.NICKNAME,groups = {Insert.class})
    private String nickname;

    /**
     * 用户性别
     */
    @Length(max = FieldLengthConstant.GENDER)
    private String gender;

    /**
     * 用户多少天免登录
     */
    // private Integer rememberMeDay;

    /**
     * 该用户对应的网站设置的uid
     */
    private Long siteUid;

    /**
     * 用户头像
     */
    @Length(max = FieldLengthConstant.URL)
    private String avatar;

    /**
     * 密码，使用md5加密
     */
    @ValidateString(value = "用户-密码",max = FieldLengthConstant.PASSWORD, groups = Insert.class)
    private String password;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    @ValidateString(value = "用户-用户名",max = FieldLengthConstant.USERNAME, groups = Insert.class)
    private String username;

    /**
     * 专业
     */
    @Length(max = FieldLengthConstant.PROFESSION,message = "用户-专业不能超过{max}")
    private String profession;

    /**
     * 用户的邮箱是否验证过 true：已验证 false：未验证
     */
    private Boolean verifyEmail;

    /**
     * 该用户对应的邮箱设置uid
     */
    private Long emailUid;

    /**
     * 用户登录记录的uid
     */
    private Long loginUid;

    /**
     * 角色 角色的命名必须遵循spring security规范，以ROLE_XXX，一个用户只能有一个角色
     */
    //@NotNull
    //@NotEmpty
    //@Pattern(regexp = "^(ROLE_)[a-zA-Z]{1,10}",message = "角色命名必须ROLE_XXX，并且总长度不能大于15")
    //private String role;

    /**
     * 用户权限集合，可以有多个，使用,分割开
     */
    //@Length(max = FieldLengthConstant.USER_PERMISSION)
    //private String permission;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 此用户对应的账户信息uid
     */
    private Long userAccountUid;
}
