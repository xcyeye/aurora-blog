package xyz.xcye.admin.vo;

import lombok.Data;

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