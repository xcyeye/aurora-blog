package xyz.xcye.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qsyyke
 * @date Created in 2022/5/7 19:26
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO {
    private String roleName;
    private Boolean roleStatus;
    private String permissionName;
    private String path;
}
