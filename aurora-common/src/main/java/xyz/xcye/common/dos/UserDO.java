package xyz.xcye.common.dos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.enums.FieldLengthEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.List;

/**
 * 数据库中的aurora_user表的映射
 * @author qsyyke
 */

@Data
public class UserDO {
    /**
     * 用户唯一id
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 用户简介
     */
    @Length(max = FieldLengthEnum.SUMMARY,message = "用户-简介不能超过{max}")
    private String userSummary;

    /**
     * 用户昵称
     */
    @ValidateString(value = "用户-昵称",max = FieldLengthEnum.NICKNAME)
    private String nickname;

    /**
     * 用户性别
     */
    @ValidateString(value = "用户-性别",max = FieldLengthEnum.GENDER)
    private Character gender;

    /**
     * 用户多少天免登录
     */
    private Integer noLoginDay;

    /**
     * 用户的删除状态 true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 该用户对应的网站设置的uid
     */
    private Long siteUid;

    /**
     * 用户头像
     */
    @ValidateString(value = "用户-头像地址",max = FieldLengthEnum.URL)
    private String avatar;

    /**
     * 用户级别 0：管理员 1：普通用户
     */
    private Integer userLevel;

    /**
     * 密码，使用md5加密
     */
    @ValidateString(value = "用户-密码",max = FieldLengthEnum.PASSWORD)
    private String password;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    @ValidateString(value = "用户-用户名",max = FieldLengthEnum.USERNAME)
    private String username;

    /**
     * 专业
     */
    @Length(max = FieldLengthEnum.PROFESSION,message = "用户-专业不能超过{max}")
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
     * 角色 角色的命名必须遵循spring security规范，以ROLE_XXX，一个用户只能有一个角色
     */
    @Pattern(regexp = "^(ROLE_)[a-zA-Z]{1,10}",message = "角色命名必须ROLE_XXX，并且总长度不能大于15")
    private String role;

    /**
     * 用户权限集合，可以有多个，使用,分割开
     */
    @ValidateString(value = "用户权限集合",max = FieldLengthEnum.USER_PERMISSION)
    private String permission;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
