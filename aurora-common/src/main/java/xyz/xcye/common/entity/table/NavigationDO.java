package xyz.xcye.common.dos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 数据表 au_navigation
 * @author qsyyke
 */

@Data
public class NavigationDO {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 前台导航的展示等级，比如0就是一级导航 不能为null
     */
    private Integer level;

    /**
     * 当前导航的父导航uid，也就是该导航显示在哪个导航下面 不能为null
     */
    private Long preUid;

    /**
     * 导航的标题 不能为null
     * <p>长度<15</p>
     */
    @ValidateString(value = "前台导航-导航标题",max = FieldLengthConstant.NAVIGATION_TITLE)
    private String title;

    /**
     * 导航对应的地址 不能为null
     * <p>长度<255</p>
     */
    @ValidateString(value = "前台导航-导航对应地址",max = FieldLengthConstant.URL)
    private String path;

    /**
     * true：链接到外部地址 false：链接地址就是本站地址，不需要新标签中打开 不能为null
     */
    private Boolean external;

    /**
     * 此导航的类名，用户设置图标
     * <p>长度<70</p>
     */
    @Length(max = FieldLengthConstant.CLASS_NAME,message = "前台导航-导航的类名")
    private String iconClassName;

    /**
     * 此导航的顺序编号 不能为null
     */
    private Integer sort;

    /**
     * 此导航属于哪个用户
     * <p>因为存在索引关系，所以可以为null</p>
     */
    private Long userUid;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
