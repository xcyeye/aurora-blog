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
 * @description site数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SitePojo {

    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    @NotNull(groups = {Update.class, Delete.class})
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
    @Length(max = FieldLengthConstant.TITLE)
    @ValidateString(value = "站点的标题，浏览器顶部", max = FieldLengthConstant.TITLE, groups = Insert.class)
    private String title;

    /**
     * 站点的前台logo文字
     */
    @Schema(title = "站点的前台logo文字")
    @Length(max = FieldLengthConstant.TITLE)
    @ValidateString(value = "站点的前台logo文字", max = FieldLengthConstant.TITLE, groups = Insert.class)
    private String logoTitle;

    /**
     * 站点的logo地址
     */
    @Schema(title = "站点的logo地址")
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "站点的logo地址", max = FieldLengthConstant.URL, groups = Insert.class)
    private String siteLogo;

    /**
     * 站点前台中间部分logo
     */
    @Schema(title = "站点前台中间部分logo")
    @Length(max = FieldLengthConstant.URL)
    private String siteCenterLogo;

    /**
     * 此站点信息属于哪个用户
     */
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

    /**
     * 站点额外的head信息，直接传入<script/>这种
     */
    @Schema(title = "站点额外的head信息，直接传入<script/>这种")
    private String additionalHead;

}