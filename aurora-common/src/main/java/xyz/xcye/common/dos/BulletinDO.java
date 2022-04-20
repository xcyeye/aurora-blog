package xyz.xcye.common.dos;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_bulletin
 * @author qsyyke
 */

@Data
public class BulletinDO {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 公告标题 可以为null
     * <p>length < 100</p>
     */
    @Length(max = FieldLengthConstant.TITLE,message = "公告-标题最大长度为{max}")
    private String title;

    /**
     * 公告内容 不能为null
     * <p>mysql -> longtext</p>
     */
    @ValidateString(value = "公告-内容",max = FieldLengthConstant.CONTENT)
    private String content;

    /**
     * 公告创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "公告-创建时间",max = FieldLengthConstant.TIME_FORMAT)
    private String createTime;

    /**
     * 公告最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updateTime;

    /**
     * 此公告是哪个用户发布的 可以为null
     */
    private Long userUid;

    /**
     * 公告的状态 不能为null true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 公告展示状态 true：显示公告 false：不显示
     */
    private Boolean show;

    /**
     * 是否定时发布 不能为null true：定时发布 false：不定时发布
     */
    private Boolean timing;

    /**
     * 定时发布时间 可以为null
     * <p>mysql -> datetime</p>
     */
    @Length(max = FieldLengthConstant.TIME_FORMAT)
    private String timingPublish;

}
