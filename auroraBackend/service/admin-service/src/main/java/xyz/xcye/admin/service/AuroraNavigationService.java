package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.Navigation;
import xyz.xcye.admin.dao.AuroraNavigationDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table navigation <br/>
 * @description navigation 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraNavigationService extends BaseService<Navigation> {
	@SuppressWarnings("unused")
	private AuroraNavigationDao navigationDao;
	
	@Autowired
    public void setInfoDao(AuroraNavigationDao navigationDao) {
        super.setBaseDao(navigationDao);
        this.navigationDao = navigationDao;
    }
}