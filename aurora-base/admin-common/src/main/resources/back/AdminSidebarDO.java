package back;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;


/**
 * 数据表 au_admin_sidebar
 * @author qsyyke
 */

@Data
public class AdminSidebarDO {

    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 前台导航的展示等级 比如0就是一级导航 不能为null
     */
    private Integer level;

    /**
     * 当前导航的父导航uid，也就是该导航显示在哪个导航下面 不能为null
     */
    private Long preSidebarUid;

    /**
     * 导航的标题 不能为null
     * <p>length < 15</p>
     */
    @ValidateString(value = "后台-导航标题",max = FieldLengthConstant.NAVIGATION_TITLE)
    private String title;

    /**
     * 导航的对应地址 不能为null
     * <p>length < 255</p>
     */
    @ValidateString(value = "后台-导航地址",max = FieldLengthConstant.URL)
    private String path;

    /**
     * 是否是外部地址 true：链接到外部 false：本站地址
     */
    private Boolean external;

    /**
     * 此导航的类名 用户设置图标 可以为null
     * <p>length < 70</p>
     */
    @Length(max = FieldLengthConstant.CLASS_NAME,message = "后台-类名最大长度必须小于{max}")
    private String iconClassName;

    /**
     * 此导航的顺序编号 不能为null
     */
    private Integer sort;

    /**
     * 此导航属于哪个用户的 可以为null
     */
    private Long userUid;

    private String createTime;
    private String updateTime;
}
