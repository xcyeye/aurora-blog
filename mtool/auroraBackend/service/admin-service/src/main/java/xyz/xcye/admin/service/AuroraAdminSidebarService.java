package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.AdminSidebar;
import xyz.xcye.admin.dao.AuroraAdminSidebarDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table admin_sidebar <br/>
 * @description admin_sidebar 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraAdminSidebarService extends BaseService<AdminSidebar> {
	@SuppressWarnings("unused")
	private AuroraAdminSidebarDao adminSidebarDao;
	
	@Autowired
    public void setInfoDao(AuroraAdminSidebarDao adminSidebarDao) {
        super.setBaseDao(adminSidebarDao);
        this.adminSidebarDao = adminSidebarDao;
    }
}