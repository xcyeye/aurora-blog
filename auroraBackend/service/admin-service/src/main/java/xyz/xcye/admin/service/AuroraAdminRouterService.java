package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.AdminRouterDao;
import xyz.xcye.admin.po.AdminRouter;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table admin_router <br/>
 * @description admin_router 数据表Service层 <br/>
 * @date 2022-12-30 22:47:42 <br/>
 */

@Service
public class AuroraAdminRouterService extends BaseService<AdminRouter> {
    @SuppressWarnings("unused")
    private AdminRouterDao adminRouterDao;

    @Autowired
    public void setInfoDao(AdminRouterDao adminRouterDao) {
        super.setBaseDao(adminRouterDao);
        this.adminRouterDao = adminRouterDao;
    }
}