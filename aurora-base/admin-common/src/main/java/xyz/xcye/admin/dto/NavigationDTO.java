package xyz.xcye.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import xyz.xcye.admin.vo.NavigationVO;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/16 16:57
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationDTO {

    @ApiModelProperty("用户uid")
    private Long userUid;

    @ApiModelProperty("父导航数量")
    private Integer ParentNavigationLength;

    @ApiModelProperty("该用户的所有导航节点信息")
    private List<ParentNavigation> navigationList;

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class ParentNavigation extends NavigationVO {
        /**
         * 该父导航行的子导航集合
         */
        private List<NavigationVO> sonNavigationList;
    }
}

