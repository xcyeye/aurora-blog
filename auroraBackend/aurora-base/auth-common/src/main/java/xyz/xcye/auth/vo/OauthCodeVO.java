package xyz.xcye.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.auth.po.OauthCode;

/**
 * @author xcye <br/>
 * @description TODO <br/>
 * @date 2022-12-14 23:53:19 <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "oauth_code数据表的VO")
public class OauthCodeVO extends OauthCode {

}