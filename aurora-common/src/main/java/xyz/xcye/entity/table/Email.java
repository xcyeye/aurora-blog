package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * 数据表 au_email
 * @author qsyyke
 */

@Data
public class Email {
    /**
     * 唯一uid，不能为null，主键
     */
    private BigInteger uid;

    /**
     * 此邮箱配置对应哪个用户 可以为null
     */
    private BigInteger userUid;

    /**
     * 邮箱主机 不能为null
     * <p>length < 25</p>
     */
    private String emailHost;

    /**
     * 邮箱授权码 不能为null
     * <p>length < 50</p>
     */
    private String emailPassword;

    /**
     * 邮箱协议 不能为null
     * <p>length < 10</p>
     */
    private String protocol;

    /**
     * 发送者的名称 不能为null
     * <p>length < 50</p>
     */
    private String senderName;

    /**
     * 发送者的标题 不能为null
     * <p>length < 50</p>
     */
    private String subjectTitle;

    /**
     * 回复评论模板 不能为null 长度没有限制
     */
    private String replyCommentTemplate;

    /**
     * 验证身份模板 不能为null 长度没有限制
     */
    private String verifyInfoTemplate;

    /**
     * 普通的通知模板 不能为null 长度没有限制
     */
    private String noticeTemplate;

    /**
     * 有用户评论的模板 不能为null 长度没有限制
     */
    private String receiveCommentTemplate;
}
