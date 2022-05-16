package xyz.xcye.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value="au_social")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialVO implements Serializable {
    /**
    * 唯一uid,自增
    */
    @ApiModelProperty(value="唯一uid,自增")
    private Long uid;

    /**
    * 社交名称
    */
    @ApiModelProperty(value="社交名称")
    private String socialName;

    /**
    * 此社交图标的地址
    */
    @ApiModelProperty(value="此社交图标的地址")
    private String socialIcon;

    /**
    * 此社交的链接
    */
    @ApiModelProperty(value="此社交的链接")
    private String socialUrl;

    /**
    * 1： 显示此社交 0： 不显示
    */
    @ApiModelProperty(value="1： 显示此社交 0： 不显示")
    private Boolean show;

    /**
    * 此社交属于哪个用户
    */
    @ApiModelProperty(value="此社交属于哪个用户")
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

    private static final long serialVersionUID = 1L;
}