package xyz.xcye.auth.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xcye <br/>
 * @table oauth_client_details <br/>
 * @description TODO <br/>
 * @date 2022-12-14 23:53:19 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "oauth_client_details数据表的实体类")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 客户端id
     */
    @Schema(title = "客户端id")
    private String clientId;

    /**
     * 资源的id，多个用逗号分隔
     */
    @Schema(title = "资源的id，多个用逗号分隔")
    private String resourceIds;

    /**
     * 客户端的秘钥
     */
    @Schema(title = "客户端的秘钥")
    private String clientSecret;

    /**
     * 客户端的权限，多个用逗号分隔
     */
    @Schema(title = "客户端的权限，多个用逗号分隔")
    private String scope;

    /**
     * 授权类型，五种，多个用逗号分隔
     */
    @Schema(title = "授权类型，五种，多个用逗号分隔")
    private String authorizedGrantTypes;

    /**
     * 授权码模式的跳转uri
     */
    @Schema(title = "授权码模式的跳转uri")
    private String webServerRedirectUri;

    /**
     * 权限，多个用逗号分隔
     */
    @Schema(title = "权限，多个用逗号分隔")
    private String authorities;

    /**
     * access_token的过期时间，单位毫秒，覆盖掉硬编码
     */
    @Schema(title = "access_token的过期时间，单位毫秒，覆盖掉硬编码")
    private Integer accessTokenValidity;

    /**
     * refresh_token的过期时间，单位毫秒，覆盖掉硬编码
     */
    @Schema(title = "refresh_token的过期时间，单位毫秒，覆盖掉硬编码")
    private Integer refreshTokenValidity;

    /**
     * 扩展字段，JSON
     */
    @Schema(title = "扩展字段，JSON")
    private String additionalInformation;

    /**
     * 默认false，是否自动授权
     */
    @Schema(title = "默认false，是否自动授权")
    private String autoapprove;

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

}