package xyz.xcye.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据库中的aurora_user表的映射
 * @author qsyyke
 */

@Data
public class User {
    /**
     * 用户唯一id
     */
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

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
    private char gender;

    /**
     * 用户多少天免登录
     */
    private int noLoginDay;

    /**
     * 用户的删除状态 1：已删除 0：未删除
     */
    @Status("用户-删除")
    private int deleteStatus;

    /**
     * 该用户对应的网站设置的uid
     */
    private BigInteger siteUid;

    /**
     * 用户头像
     */
    @ValidateString(value = "用户-头像地址",max = FieldLengthEnum.URL)
    private String avatar;

    /**
     * 用户级别 0：管理员 1：普通用户
     */
    private int userLevel;

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
     * 用户的邮箱是否验证过 1：已验证 0：未验证
     */
    @Status("用户-邮箱验证")
    private int isVerifyEmail;

    /**
     * 该用户对应的邮箱设置uid
     */
    private BigInteger emailUid;
}
