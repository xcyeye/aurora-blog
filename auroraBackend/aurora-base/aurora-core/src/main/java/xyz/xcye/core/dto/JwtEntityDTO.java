package xyz.xcye.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * jwt实体
 *
 * @author qsyyke
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtEntityDTO {
    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String subject;

    /**
     * 签发者
     */
    private String issuer;

    /**
     * 签发时间
     */
    private Date issuedAt;

    /**
     * 过期时间
     */
    private Date expirationAt;

    private String jti;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否过期 true表示过期，false反之
     */
    private boolean isExpiration;
}
