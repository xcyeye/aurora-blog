package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.Permission;
import xyz.xcye.admin.dao.AuroraPermissionDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table permission <br/>
 * @description permission 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraPermissionService extends BaseService<Permission> {
	@SuppressWarnings("unused")
	private AuroraPermissionDao permissionDao;
	
	@Autowired
    public void setInfoDao(AuroraPermissionDao permissionDao) {
        super.setBaseDao(permissionDao);
        this.permissionDao = permissionDao;
    }
}