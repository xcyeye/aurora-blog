package xyz.xcye.comment.pojo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.constant.RegexConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.LogicDelete;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @description comment数据表的POJO <br/>
 * @date 2022-12-14 21:35:45 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPojo {

    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 此评论对应注册用户中的哪个用户
     */
    @NotNull(groups = Insert.class)
    private Long userUid;

    /**
     * 是否逻辑删除
     */
    @NotNull(groups = LogicDelete.class)
    private Boolean delete;

    /**
     * 评论这的用户名 不能为null
     * <p>length < 15</p>
     */
    @Length(max = FieldLengthConstant.USERNAME)
    @ValidateString(value = "评论-用户名", max = FieldLengthConstant.USERNAME, groups = {Insert.class})
    private String username;

    /**
     * 头像地址 可以为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.URL,message = "评论-头像地址长度不能超过{max}",groups = {Insert.class})
    private String avatar;

    /**
     * 站点地址 不能为null
     * <p>length < 255</p>
     */
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "评论-网站", max = FieldLengthConstant.URL ,groups = {Insert.class})
    private String site;

    /**
     * 邮箱地址 不能为null 用于接收邮件
     * <p>length < 32</p>
     */
    @Length(max = FieldLengthConstant.EMAIL_NUMBER)
    @Pattern(regexp = RegexConstant.EMAIL)
    @NotNull
    @NotEmpty
    private String email;

    /**
     * 创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @Length(max = FieldLengthConstant.TIME_FORMAT)
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
    @Length(max = FieldLengthConstant.IP,message = "评论-ip地址最大长度为{max}", groups = Update.class)
    private String commentIp;

    /**
     * 评论者的操作系统信息 可以为null
     * <p>length < 200</p>
     */
    @Length(max = FieldLengthConstant.OPERATION_INFO,message = "评论-操作系统信息最大长度为{max}")
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
    @Length(max = FieldLengthConstant.COMMENT_CONTENT)
    @ValidateString(value = "评论的内容", max = FieldLengthConstant.COMMENT_CONTENT, groups = {Insert.class})
    private String content;

    /**
     * 此评论在哪个页面上的评论
     */
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "评论 评论的地址", max = FieldLengthConstant.URL, groups = {Insert.class})
    private String path;

    /**
     * 此评论所对应的所有子评论集合，使用,分割开的
     */
    private String nextCommentUidArray;

    /**
     * 此评论是在哪种类型的页面上发布的，可以是说说，文章等
     */
    @ValidateString(value = "页面类型", groups = Insert.class)
    private String pageType;

    /**
     * 如果此评论是在说说发布的，那么此pageUid就表示说说的uid
     */
    @NotNull(groups = {Insert.class})
    private Long pageUid;

    private Long[] commentUidArr;

}