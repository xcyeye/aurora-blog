package xyz.xcye.admin.vo;

import lombok.Data;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 路径权限
 * @TableName au_permission
 */
@Data
public class PermissionVO implements Serializable {
    /**
     * 唯一uid，自增
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 此路径的名称
     */
    private String name;

    /**
     * 权限的地址，可以是组件的名称，必须遵守Method:path的约定
     */
    @ValidateString(value = "路径的名称", max = FieldLengthConstant.METHOD_AND_PATH)
    private String path;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}