package xyz.xcye.entity.table;

import lombok.Data;

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
    private BigInteger uid;

    /**
     * 用户简介
     */
    private String userSummary;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private char gender;

    /**
     * 用户多少天免登录
     */
    private int noLoginDay;

    /**
     * 用户的删除状态 1：已删除 0：未删除
     */
    private int deleteStatus;

    /**
     * 该用户对应的网站设置的uid
     */
    private BigInteger siteUid;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户级别 0：管理员 1：普通用户
     */
    private int userLevel;

    /**
     * 密码，使用md5加密
     */
    private String password;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    private String username;

    /**
     * 专业
     */
    private String profession;

    /**
     * 用户的邮箱是否验证过 1：已验证 0：未验证
     */
    private int isVerifyEmail;

    /**
     * 该用户对应的邮箱设置uid
     */
    private BigInteger emailUid;
}
