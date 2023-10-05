package xyz.xcye.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.admin.po.SiteSetting;

/**
 * @author xcye <br/>
 * @description TODO <br/>
 * @date 2023-01-04 20:42:58 <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "site_setting数据表的VO")
public class SiteSettingVO extends SiteSetting {

}