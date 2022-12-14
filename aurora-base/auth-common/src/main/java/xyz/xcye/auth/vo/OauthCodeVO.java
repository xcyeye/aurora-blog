package xyz.xcye.auth.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.xcye.auth.po.OauthCode;

/**
 * @description TODO <br/>
 * @date 2022-12-14 23:53:19 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "oauth_code数据表的VO")
public class OauthCodeVO extends OauthCode {

}