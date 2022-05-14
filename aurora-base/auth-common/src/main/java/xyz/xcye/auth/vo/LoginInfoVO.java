package xyz.xcye.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value="au_login_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoVO implements Serializable {
    @ApiModelProperty(value="")
    private Long uid;

    /**
    * 登录的用户名
    */
    @ApiModelProperty(value="登录的用户名")
    private String username;

    /**
    * 登录地
    */
    @ApiModelProperty(value="登录地")
    private String loginLocation;

    /**
    * 登录ip地址
    */
    @ApiModelProperty(value="登录ip地址")
    private String loginIp;

    /**
    * 登录的操作系统
    */
    @ApiModelProperty(value="登录的操作系统")
    private String operationSystemInfo;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
    * 最后更新时间
    */
    @ApiModelProperty(value="最后更新时间")
    private String updateTime;

    @ApiModelProperty(value = "记录登录信息")
    private String message;

    /**
    * 登录的状态 1：登录成功
    */
    @ApiModelProperty(value="登录的状态 1：登录成功")
    private Boolean status;

    private static final long serialVersionUID = 1L;
}