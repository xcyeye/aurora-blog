package xyz.xcye.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.admin.po.WhiteUrl;

/**
 * @author xcye <br/>
 * @description TODO <br/>
 * @date 2022-12-13 20:45:36 <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "white_url数据表的VO")
public class WhiteUrlVO extends WhiteUrl {

}