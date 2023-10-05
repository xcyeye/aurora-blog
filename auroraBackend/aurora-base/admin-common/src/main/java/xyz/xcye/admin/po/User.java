package xyz.xcye.admin.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.admin.enums.GenderEnum;

import java.io.Serializable;

/**
 * @author xcye <br/>
 * @table user <br/>
 * @description TODO <br/>
 * @date 2022-12-13 21:00:16 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "user数据表的实体类")
public class User implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 唯一uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "唯一uid")
    private Long uid;

    /**
     * 用户简介
     */
    @Schema(title = "用户简介")
    private String userSummary;

    /**
     * 用户昵称
     */
    @Schema(title = "用户昵称")
    private String nickname;

    /**
     * 用户性别
     */
    @Schema(title = "用户性别")
    private GenderEnum gender;

    /**
     * 用户登录记录的uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "用户登录记录的uid")
    private Long loginUid;

    /**
     * 用户的站点配置uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "用户的站点配置uid")
    private Long siteUid;

    /**
     * 用户的头像地址
     */
    @Schema(title = "用户的头像地址")
    private String avatar;

    /**
     * 用户的密码
     */
    @Schema(title = "用户的密码")
    private String password;

    /**
     * 用户名
     */
    @Schema(title = "用户名")
    private String username;

    /**
     * 用户的工作集合
     */
    @Schema(title = "用户的工作集合")
    private String profession;

    /**
     * 此用户对应的邮箱uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "此用户对应的邮箱uid")
    private Long emailUid;

    /**
     *
     */
    @Schema(title = "")
    private String createTime;

    /**
     *
     */
    @Schema(title = "")
    private String updateTime;

    /**
     * 是否删除 1：删除
     */
    @Schema(title = "是否删除 1：删除 ")
    private Boolean delete;

    /**
     * 1: 账户被锁住，0：未被锁住
     */
    @Schema(title = "1: 账户被锁住，0：未被锁住")
    private Boolean accountLock;

    /**
     * 1: 邮箱已验证，0：邮箱未验证
     */
    @Schema(title = "1: 邮箱已验证，0：邮箱未验证")
    private Boolean verifyEmail;

}