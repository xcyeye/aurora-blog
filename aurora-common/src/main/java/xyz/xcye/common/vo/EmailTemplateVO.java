package xyz.xcye.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据表 au_email_template
 * 设置邮件发送模板
 * @author qsyyke
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailTemplateVO {
    /**
     * 唯一uid，不能为null，主键
     */
    private Long uid;

    /**
     * 用户uid
     */
    private Long userUid;

    /**
     * 回复评论模板 不能为null 长度没有限制
     */
    private String replyCommentTemplate;

    /**
     * 验证身份模板 不能为null 长度没有限制
     */
    private String verifyAccountTemplate;

    /**
     * 普通的通知模板 不能为null 长度没有限制
     */
    private String noticeTemplate;

    /**
     * 有用户评论的模板 不能为null 长度没有限制
     */
    private String receiveCommentTemplate;

    /**
     * 默认回复评论的标题
     */
    private String replyCommentSubject;

    /**
     * 使用该邮箱发送验证账户时的默认标题
     */
    private String verifyAccountSubject;

    /**
     * 使用该邮箱发送收到评论时的默认标题
     */
    private String receiveCommentSubject;

    /**
     * 使用该邮箱发送普通通知时的默认标题
     */
    private String noticeSubject;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
