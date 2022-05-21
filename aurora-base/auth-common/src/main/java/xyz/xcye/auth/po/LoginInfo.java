package xyz.xcye.auth.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(title="登录信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo implements Serializable {
    @Schema(title = "")
    private Long uid;

    /**
    * 登录的用户名
    */
    @Schema(title = "登录的用户名")
    private String username;

    /**
    * 登录地
    */
    @Schema(title = "登录地")
    private String loginLocation;

    /**
    * 登录ip地址
    */
    @Schema(title = "登录ip地址")
    private String loginIp;

    /**
    * 登录的操作系统
    */
    @Schema(title = "登录的操作系统")
    private String operationSystemInfo;

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
    * 登录的状态 1：登录成功
    */
    @Schema(title = "登录的状态 1：登录成功")
    private Boolean status;

    @Schema(title = "记录登录信息")
    private String message;

    private static final long serialVersionUID = 1L;
}