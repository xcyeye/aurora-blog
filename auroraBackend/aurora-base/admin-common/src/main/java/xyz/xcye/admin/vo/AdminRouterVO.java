package xyz.xcye.admin.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.xcye.admin.po.AdminRouter;

/**
 * @description TODO <br/>
 * @date 2022-12-30 22:47:42 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "admin_router数据表的VO")
public class AdminRouterVO extends AdminRouter {

}