package xyz.xcye.entity.table;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Insert;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_bulletin
 * @author qsyyke
 */

@Data
public class Bulletin {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Update.class, Delete.class})
    private BigInteger uid;

    /**
     * 公告标题 可以为null
     * <p>length < 100</p>
     */
    @Length(max = FieldLengthEnum.TITLE,message = "公告-标题最大长度为{max}")
    private String title;

    /**
     * 公告内容 不能为null
     * <p>mysql -> longtext</p>
     */
    @ValidateString(value = "公告-内容",max = FieldLengthEnum.CONTENT)
    private String content;

    /**
     * 公告创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "公告-创建时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 公告最后修改时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String updatedAt;

    /**
     * 此公告是哪个用户发布的 可以为null
     */
    private BigInteger userUid;

    /**
     * 公告的状态 不能为null 1：已删除 0：未删除
     */
    @Status("公告-删除")
    private int deleteStatus;

    /**
     * 公告展示状态 1：显示公告 0：不显示
     */
    @Status("公告-展示")
    private int showStatus;

    /**
     * 是否定时发布 不能为null 1：定时发布 0：不定时发布
     */
    @Status("公告-定时发布")
    private int timingStatus;

    /**
     * 定时发布时间 可以为null
     * <p>mysql -> datetime</p>
     */
    @Length(max = FieldLengthEnum.TIME_FORMAT)
    private String timingPublish;

}
