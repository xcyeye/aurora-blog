package xyz.xcye.common.vo;

import lombok.Data;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.validator.ValidateString;

@Data
public class RoleVO {
    /**
     * 唯一uid，不需要雪花生成
     */
    private Integer uid;

    /**
     * 角色或者权限
     */
    private String role;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;
}