package xyz.xcye.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.entity.table.Permission;

import java.util.List;

@Mapper
public interface PermissionDao {
    /**
     * 插入一条新的路径权限信息
     * @param permission 需要插入的permission对象
     * @return 插入成功，返回影响的行数，通过permission.getUid可以获取到插入成功之后，自增的uid
     */
    int insertPermission(@Param("permission") Permission permission);

    /**
     * 根据uid修改某条路径的访问权限
     * @param permission 修改的新permission对象，uid还是原来的
     * @return 修改成功的行数
     */
    int updatePermissionByUid(@Param("permission") Permission permission);

    /**
     * 根据uid删除某条路径的访问权限
     * @param uid uid
     * @return 删除成功的行数
     */
    int deletePermissionByUid(@Param("uid") int uid);

    /**
     * 返回所有的路径访问权限信息
     * @return 路径权限集合
     */
    List<Permission> queryAllRole();

}