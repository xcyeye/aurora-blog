package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.RouterPermissionDao;
import xyz.xcye.admin.po.RouterPermission;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table router_permission <br/>
 * @description router_permission 数据表Service层 <br/>
 * @date 2022-12-30 22:23:42 <br/>
 */

@Service
public class AuroraRouterPermissionService extends BaseService<RouterPermission> {
    @SuppressWarnings("unused")
    private RouterPermissionDao routerPermissionDao;

    @Autowired
    public void setInfoDao(RouterPermissionDao routerPermissionDao) {
        super.setBaseDao(routerPermissionDao);
        this.routerPermissionDao = routerPermissionDao;
    }
}