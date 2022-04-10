package xyz.xcye.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * jwt实体
 * @author qsyyke
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
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

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户权限
     */
    private String[] permissions;

    public JwtEntityDTO(String id, String subject, String issuer, Date issuedAt, Date expirationAt) {
        this.id = id;
        this.subject = subject;
        this.issuer = issuer;
        this.issuedAt = issuedAt;
        this.expirationAt = expirationAt;
    }

    /**
     * 是否过期 true表示过期，false反之
     */
    private boolean isExpiration;
}
