package xyz.xcye.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value="au_navigation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationVO implements Serializable {
    /**
    * 唯一uid
    */
    @ApiModelProperty(value="唯一uid")
    private Long uid;

    /**
    * 前台导航的展示等级 比如0就是一级导航
    */
    @ApiModelProperty(value="前台导航的展示等级 比如0就是一级导航")
    private Integer level;

    /**
    * 当前导航的父导航uid，也就是该导航显示在哪个导航下面
    */
    @ApiModelProperty(value="当前导航的父导航uid，也就是该导航显示在哪个导航下面")
    private Long parentNavUid;

    /**
    * 导航的标题
    */
    @ApiModelProperty(value="导航的标题")
    private String title;

    /**
    * 导航的对应地址
    */
    @ApiModelProperty(value="导航的对应地址")
    private String path;

    /**
    * 1：链接到外部地址 0：链接就是此站点的，不在新标签也打开
    */
    @ApiModelProperty(value="1：链接到外部地址 0：链接就是此站点的，不在新标签也打开")
    private Boolean external;

    /**
    * 此导航的类名，用户icon
    */
    @ApiModelProperty(value="此导航的类名，用户icon")
    private String iconClassName;

    /**
    * 此导航的顺序编号
    */
    @ApiModelProperty(value="此导航的顺序编号")
    private Integer sort;

    /**
    * 该导航属于哪个用户
    */
    @ApiModelProperty(value="该导航属于哪个用户")
    private Long userUid;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
    * 最后更新时间
    */
    @ApiModelProperty(value="最后更新时间")
    private String updateTime;

    /**
    * 1: 删除 0：不删除
    */
    @ApiModelProperty(value="1: 删除 0：不删除")
    private Boolean delete;

    /**
    * 该导航的子导航uid集合
    */
    @ApiModelProperty(value="该导航的子导航uid集合")
    private String sonNavUids;

    private static final long serialVersionUID = 1L;
}