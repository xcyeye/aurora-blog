package xyz.xcye.admin.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * @description navigation数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationPojo {

    /**
     * 唯一uid
     */
    @NotNull(groups = {Update.class, Delete.class})
    @Schema(title = "唯一uid")
    private Long uid;

    /**
     * 前台导航的展示等级 比如0就是一级导航
     */
    @Schema(title = "前台导航的展示等级 比如0就是一级导航")
    private Integer level;

    /**
     * 当前导航的父导航uid，也就是该导航显示在哪个导航下面
     */
    @Schema(title = "当前导航的父导航uid，也就是该导航显示在哪个导航下面")
    private Long parentNavUid;

    /**
     * 导航的标题
     */
    @Schema(title = "导航的标题")
    @Length(max = FieldLengthConstant.NAVIGATION_TITLE)
    @ValidateString(value = "导航的标题", max = FieldLengthConstant.NAVIGATION_TITLE, groups = {Insert.class})
    private String title;

    /**
     * 导航的对应地址
     */
    @Schema(title = "导航的对应地址")
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "导航的链接", max = FieldLengthConstant.URL, groups = Insert.class)
    private String path;

    /**
     * 1：链接到外部地址 0：链接就是此站点的，不在新标签也打开
     */
    @Schema(title = "1：链接到外部地址 0：链接就是此站点的，不在新标签也打开")
    private Boolean external;

    /**
     * 此导航的类名，用户icon
     */
    @Schema(title = "此导航的类名，用户icon")
    @Length(max = FieldLengthConstant.CLASS_NAME)
    private String iconClassName;

    /**
     * 此导航的顺序编号
     */
    @Schema(title = "此导航的顺序编号")
    private Integer sort;

    /**
     * 该导航属于哪个用户
     */
    @Schema(title = "该导航属于哪个用户")
    private Long userUid;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private String createTime;

    /**
     * 最后更新时间
     */
    @Schema(title = "最后更新时间")
    private String updateTime;

    /**
     * 1: 删除 0：不删除
     */
    @Schema(title = "1: 删除 0：不删除")
    private Boolean delete;

    /**
     * 1：显示 0：不显示
     */
    @Schema(title = "1: 显示 0：不显示")
    private Boolean show;

    /**
     * 该导航的子导航uid集合
     */
    @Schema(title = "该导航的子导航uid集合")
    private String sonNavUids;

}