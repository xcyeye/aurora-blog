package xyz.xcye.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 邮箱验证账户实体
 * @author qsyyke
 */

@Builder
@Data
public class EmailVerifyAccountDTO {
    /**
     * 验证邮箱的地址
     */
    private String verifyAccountUrl;

    /**
     * 接受者的邮箱
     */
    private String receiverEmail;

    /**
     * 此验证链接失效时间
     */
    private Long expirationTime;

    /**
     * 此userUid需要保证在数据库中存在验证账户的邮件发送模板，推荐填写管理员的userUid
     */
    private Long userUid;

    /**
     * 邮件发送的标题
     */
    private String subject;
}
