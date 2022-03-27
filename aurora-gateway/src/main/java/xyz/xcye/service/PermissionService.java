package xyz.xcye.service;

import xyz.xcye.entity.table.Permission;

import java.util.List;

/**
 * 查询路径角色，权限
 * @author qsyyke
 */


public interface PermissionService {
    /**
     * 插入一条新的路径权限信息
     * @param permission 需要插入的permission对象
     * @return 插入成功的permission对象，如果失败，返回null
     */
    Permission insertPermission(Permission permission);

    /**
     * 根据uid修改某条路径的访问权限
     * @param permission 修改的新permission对象，uid还是原来的
     * @return 返回修改之后的permission对象，如果修改失败，返回原对象
     */
    Permission updatePermissionByUid(Permission permission);

    /**
     * 根据uid删除某条路径的访问权限
     * @param uid uid
     * @return true表示删除成功，反之
     */
    boolean deletePermissionByUid(int uid);

    /**
     * 返回所有的路径访问权限信息
     * @return 路径权限集合
     */
    List<Permission> queryAllRole();

}
