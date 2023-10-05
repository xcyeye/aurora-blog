package xyz.xcye.admin.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xcye <br/>
 * @table site <br/>
 * @description TODO <br/>
 * @date 2022-12-13 21:00:16 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "site数据表的实体类")
public class Site implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 唯一uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "唯一uid")
    private Long uid;

    /**
     * 站点的icon地址
     */
    @Schema(title = "站点的icon地址")
    private String siteIcon;

    /**
     * 站点的标题 浏览器顶部部分
     */
    @Schema(title = "站点的标题 浏览器顶部部分")
    private String title;

    /**
     * 站点的前台logo文字
     */
    @Schema(title = "站点的前台logo文字")
    private String logoTitle;

    /**
     * 站点的logo地址
     */
    @Schema(title = "站点的logo地址")
    private String siteLogo;

    /**
     * 站点前台中间部分logo
     */
    @Schema(title = "站点前台中间部分logo")
    private String siteCenterLogo;

    /**
     * 站点额外的head信息，直接传入<script/>这种
     */
    @Schema(title = "站点额外的head信息，直接传入<script/>这种")
    private String additionalHead;

    /**
     * 此站点信息属于哪个用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "此站点信息属于哪个用户")
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
     * 0:不删除 1： 删除
     */
    @Schema(title = "0:不删除 1： 删除")
    private Boolean delete;

}