package xyz.xcye.auth.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(title="客户端秘钥")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthClientDetails implements Serializable {
    /**
    * 客户端id
    */
    @Schema(title="客户端id")
    private String clientId;

    /**
    * 资源的id，多个用逗号分隔
    */
    @Schema(title="资源的id，多个用逗号分隔")
    private String resourceIds;

    /**
    * 客户端的秘钥
    */
    @Schema(title="客户端的秘钥")
    private String clientSecret;

    /**
    * 客户端的权限，多个用逗号分隔
    */
    @Schema(title="客户端的权限，多个用逗号分隔")
    private String scope;

    /**
    * 授权类型，五种，多个用逗号分隔
    */
    @Schema(title="授权类型，五种，多个用逗号分隔")
    private String authorizedGrantTypes;

    /**
    * 授权码模式的跳转uri
    */
    @Schema(title="授权码模式的跳转uri")
    private String webServerRedirectUri;

    /**
    * 权限，多个用逗号分隔
    */
    @Schema(title="权限，多个用逗号分隔")
    private String authorities;

    /**
    * access_token的过期时间，单位毫秒，覆盖掉硬编码
    */
    @Schema(title="access_token的过期时间，单位毫秒，覆盖掉硬编码")
    private Integer accessTokenValidity;

    /**
    * refresh_token的过期时间，单位毫秒，覆盖掉硬编码
    */
    @Schema(title="refresh_token的过期时间，单位毫秒，覆盖掉硬编码")
    private Integer refreshTokenValidity;

    /**
    * 扩展字段，JSON
    */
    @Schema(title="扩展字段，JSON")
    private String additionalInformation;

    /**
    * 默认false，是否自动授权
    */
    @Schema(title="默认false，是否自动授权")
    private String autoApprove;

    private static final long serialVersionUID = 1L;
}