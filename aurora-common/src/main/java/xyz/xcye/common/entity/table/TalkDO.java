package xyz.xcye.common.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 说说表
 * @author qsyyke
 */

@Data
public class TalkDO {
    /**
     * 唯一id 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 该条说说所包含的图片集合 集合中的就是图片地址 可以为null
     */
    private String pictureArr;

    /**
     * 该条说说是否显示评论 true：显示 false：不显示 不能为null
     */
    private Boolean showComment;

    /**
     * 删除状态 true：已删除 false：未删除 不能为null
     */
    private Boolean delete;

    /**
     * 最后更新时间 数据库中的类型为datetime 可以为null
     */
    private String updateTime;

    /**
     * 创建时间 不能为null
     */
    @ValidateString(value = "说说-创建时间",max = FieldLengthConstant.TIME_FORMAT)
    private String createTime;

    /**
     * 说说内容 数据表中的类型为longtext 不能为null
     */
    @ValidateString(value = "说说-内容",max = FieldLengthConstant.CONTENT)
    private String content;

    /**
     * 说说标题 可以为null
     */
    @Length(max = FieldLengthConstant.TITLE,message = "说说-标题不能超过{max}")
    private String title;

    /**
     * 该条说说显示状态 true：显示 false：不显示 不能为null
     */
    private Boolean show;

    /**
     * 如果有评论 commentArray保存该条说说对应的所有评论的父uid集合
     */
    private String commentArray;

    /**
     * 点赞数 不能为null
     */
    private Integer likeNumber;

    /**
     * 该条说说是由哪个用户创建的 因为存在索引关系，所以该字段可以为null
     */
    private Long userUid;
}
