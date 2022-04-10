package xyz.xcye.common.dos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.enums.FieldLengthEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 数据表 au_comment
 * @author qsyyke
 */

@Data
public class CommentDO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 此评论对应注册用户中的哪个用户
     */
    @NotNull(groups = {Insert.class,Update.class})
    private Long userUid;

    /**
     * 评论这的用户名 不能为null
     * <p>length < 15</p>
     */
    @ValidateString(value = "评论-用户名",max = FieldLengthEnum.USERNAME,groups = {Insert.class})
    private String username;

    /**
     * 头像地址 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthEnum.URL,message = "评论-头像地址长度不能超过{max}",groups = {Insert.class})
    private String avatar;

    /**
     * 站点地址 不能为null
     * <p>length < 255</p>
     */
    @ValidateString(value = "评论-网站",max = FieldLengthEnum.URL,groups = {Insert.class})
    private String site;

    /**
     * 邮箱地址 不能为null 用于接收邮件
     * <p>length < 32</p>
     */
    @Length(max = FieldLengthEnum.EMAIL_NUMBER)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
    private String email;

    /**
     * 删除状态 true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @Length(max = FieldLengthEnum.TIME_FORMAT)
    private String createTime;

    /**
     * 最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updateTime;

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
    private Boolean showComment;

    /**
     * 此条评论是回复哪条评论的 不能为null
     */
    private Long replyCommentUid;

    /**
     * 是否发送邮件通知
     */
    private Boolean emailNotice;

    /**
     * 评论内容
     */
    @ValidateString(value = "评论的内容",max = FieldLengthEnum.CONTENT,groups = {Insert.class})
    private String content;

    /**
     * 此评论在哪个页面上的评论
     */
    @ValidateString(value = "评论 评论的地址",max = FieldLengthEnum.URL,groups = {Insert.class})
    private String path;

    /**
     * 此评论所对应的所有子评论集合，使用,分割开的
     */
    private String nextCommentUidArray;
}
