package xyz.xcye.common.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.enums.FieldLengthEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_comment
 * @author qsyyke
 */

@Data
public class Comment {

    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 评论这的用户名 不能为null
     * <p>length < 15</p>
     */
    @ValidateString(value = "评论-用户名",max = FieldLengthEnum.USERNAME)
    private String username;

    /**
     * 头像地址 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthEnum.URL,message = "评论-头像地址长度不能超过{max}")
    private String avatar;

    /**
     * 站点地址 不能为null
     * <p>length < 255</p>
     */
    @ValidateString(value = "评论-网站",max = FieldLengthEnum.URL)
    private String site;

    /**
     * 邮箱地址 不能为null 用于接收邮件
     * <p>length < 32</p>
     */
    @ValidateString(value = "评论-邮箱",max = FieldLengthEnum.EMAIL_NUMBER)
    private String email;

    /**
     * 删除状态 true：已删除 false：未删除
     */
    private boolean deleteStatus;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "评论-创建时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updatedAt;

    /**
     * 评论者的ip 可以为null
     * <p>length < 12</p>
     */
    @Length(max = FieldLengthEnum.IP,message = "评论-ip地址最大长度为{max}")
    private String commentIp;

    /**
     * 评论者的操作系统信息 可以为null
     * <p>length < 200</p>
     */
    @Length(max = FieldLengthEnum.OPERATION_INFO,message = "评论-操作系统信息最大长度为{max}")
    private String operationSystemInfo;

    /**
     * 是否显示此条评论 true：显示 false：不显示
     */
    private boolean showComment;

    /**
     * 此条评论是回复哪条评论的 不能为null
     */
    private BigInteger replyCommentUid;

    /**
     * 如果此评论是回复某条评论 则true：体发送邮件通知对方 false：未发送邮件通知对方 如果不是回复评论 比如新评论
     */
    private boolean replyNoticeStatus;

    /**
     * 如果用户发布评论，true：已邮件通知该用户 false：未发送邮件通知该用户
     */
    private boolean publishNoticeStatus;
}
