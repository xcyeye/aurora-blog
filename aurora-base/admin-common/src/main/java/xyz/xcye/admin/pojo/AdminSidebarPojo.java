package xyz.xcye.admin.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description admin_sidebar数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSidebarPojo {

    /**
     * 唯一uid
     */
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
    private Long preSidebarUid;

    /**
     * 导航的标题
     */
    @Schema(title = "导航的标题")
    private String title;

    /**
     * 导航的对应地址
     */
    @Schema(title = "导航的对应地址")
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
    private String iconClassName;

    /**
     * 此导航的顺序编号
     */
    @Schema(title = "此导航的顺序编号")
    private Integer sort;

    /**
     * 
     */
    @Schema(title = "")
    private Long userUid;

    /**
     * 
     */
    @Schema(title = "")
    private String createTime;

    /**
     * 
     */
    @Schema(title = "")
    private String updateTime;

}