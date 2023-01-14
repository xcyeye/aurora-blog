package xyz.xcye.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.admin.po.Setting;

/**
 * @description TODO <br/>
 * @date 2022-12-30 15:46:26 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "setting数据表的VO")
public class SettingVO extends Setting {

}