package xyz.xcye.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据表 au_email_log
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailLogVO {

    /**
     * 唯一uid 自增 主键 不使用雪花算法
     */
    private Long uid;

    /**
     * 邮件发送的标题 不能为null
     * <p>length < 50</p>
     */
    private String subject;

    /**
     * 邮件发送的内容 不能为null 长度随意
     */
    private String content;

    /**
     * 接收者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    private String receiver;

    /**
     * 发送者的邮箱号 不能为null
     * <p>length < 32</p>
     */
    private String sender;

    /**
     * 邮件发送的状态 true：已成功发送 false：未发送成功
     */
    private Boolean send;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
