package xyz.xcye.auth.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xcye <br/>
 * @description login_info数据表的POJO <br/>
 * @date 2022-12-14 23:53:19 <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoPojo {

    /**
     *
     */
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

    /**
     * 登录消息，记录登录异常等信息
     */
    @Schema(title = "登录消息，记录登录异常等信息")
    private String message;

    private Long[] uids;

}