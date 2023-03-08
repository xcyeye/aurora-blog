package xyz.xcye.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author qsyyke
 * @date Created in 2022/5/8 23:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserInfo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 用户的uid
     */
    private Long userUid;

    /**
     * 角色集合
     */
    private List<String> roleList;

    /**
     * 是否已经验证了邮箱
     */
    private Boolean verifyEmail;

    /**
     * jwtToken
     */
    private String jwtToken;

    private Map<String,String> requestHeadMap;
}
