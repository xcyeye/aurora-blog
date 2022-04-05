package xyz.xcye.wg.service;

import xyz.xcye.common.entity.table.VerifyPath;

import java.util.List;

/**
 * 查询路径角色，权限
 * @author qsyyke
 */


public interface VerifyPathService {
    /**
     * 返回所有的路径访问权限信息
     * @return 路径权限集合
     */
    List<VerifyPath> queryAllVerifyPath();

}
