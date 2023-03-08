package xyz.xcye.admin.dto;

import lombok.Data;
import xyz.xcye.admin.vo.AdminRouterVO;

import java.util.List;

/**
 * @author xcye
 * @description
 * @date 2022-12-30 23:44:42
 */

@Data
public class AdminRouterDTO extends AdminRouterVO {

    private List<AdminRouterDTO> children;
}
