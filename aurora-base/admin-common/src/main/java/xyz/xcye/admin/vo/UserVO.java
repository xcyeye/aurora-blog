package xyz.xcye.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import xyz.xcye.admin.enums.GenderEnum;


//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserVO {

    /**
     * 用户唯一id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 用户简介
     */
    private String userSummary;

    /**
     * 账户是否被锁住
     */
    private Boolean accountLock;

    /**
     * 是否验证了邮箱
     */
    private Boolean verifyEmail;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private GenderEnum gender;

    /**
     * 该用户对应的网站设置的uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteUid;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户级别 0：管理员 1：普通用户
     */
    private Integer userLevel;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    private String username;

    /**
     * 专业
     */
    private String profession;

    /**
     * 该用户对应的邮箱设置uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long emailUid;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否被删除
     */
    private Boolean delete;
}