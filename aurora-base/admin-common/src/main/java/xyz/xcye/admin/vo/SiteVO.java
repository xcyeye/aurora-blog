package xyz.xcye.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value="au_site")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteVO implements Serializable {
    /**
    * 唯一uid
    */
    @ApiModelProperty(value="唯一uid")
    private Long uid;

    /**
    * 站点的icon地址
    */
    @ApiModelProperty(value="站点的icon地址")
    private String siteIcon;

    /**
    * 站点的标题 浏览器顶部部分
    */
    @ApiModelProperty(value="站点的标题 浏览器顶部部分")
    private String title;

    /**
    * 站点的前台logo文字
    */
    @ApiModelProperty(value="站点的前台logo文字")
    private String logoTitle;

    /**
    * 站点的logo地址
    */
    @ApiModelProperty(value="站点的logo地址")
    private String siteLogo;

    /**
    * 站点前台中间部分logo
    */
    @ApiModelProperty(value="站点前台中间部分logo")
    private String siteCenterLogo;

    /**
    * 此站点信息属于哪个用户
    */
    @ApiModelProperty(value="此站点信息属于哪个用户")
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
    * 0:不删除 1： 删除
    */
    @ApiModelProperty(value="0:不删除 1： 删除")
    private Boolean delete;

    /**
    * 站点额外的head信息，直接传入<script/>这种
    */
    @ApiModelProperty(value="站点额外的head信息，直接传入<script/>这种")
    private String additionalHead;

    private static final long serialVersionUID = 1L;
}