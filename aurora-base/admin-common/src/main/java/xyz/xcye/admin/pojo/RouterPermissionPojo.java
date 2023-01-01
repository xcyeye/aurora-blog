package xyz.xcye.admin.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description router_permission数据表的POJO <br/>
 * @date 2022-12-30 22:23:42 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterPermissionPojo {

    /**
     * 
     */
    @Schema(title = "")
    private Long uid;

    /**
     * 
     */
    @Schema(title = "")
    private Long adminRouterUid;

    /**
     * 
     */
    @Schema(title = "")
    private Long permissionUid;

    private List<Long> permissionUidList;

}