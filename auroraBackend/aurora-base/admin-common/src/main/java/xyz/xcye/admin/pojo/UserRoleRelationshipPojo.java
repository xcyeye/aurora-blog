package xyz.xcye.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;

import javax.validation.constraints.NotNull;

/**
 * @author xcye <br/>
 * @description user_role数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRelationshipPojo {

    /**
     *
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 角色uid
     */
    @NotNull(groups = {Insert.class})
    private Long roleUid;

    /**
     * 用户uid
     */
    @NotNull(groups = {Insert.class})
    private Long userUid;

}