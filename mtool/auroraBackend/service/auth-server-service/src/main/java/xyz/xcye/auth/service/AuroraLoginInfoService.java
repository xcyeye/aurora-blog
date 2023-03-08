package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.auth.dao.AuroraLoginInfoDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table login_info <br/>
 * @description login_info 数据表Service层 <br/>
 * @date 2022-12-14 23:53:19 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraLoginInfoService extends BaseService<LoginInfo> {
	@SuppressWarnings("unused")
	private AuroraLoginInfoDao loginInfoDao;
	
	@Autowired
    public void setInfoDao(AuroraLoginInfoDao loginInfoDao) {
        super.setBaseDao(loginInfoDao);
        this.loginInfoDao = loginInfoDao;
    }
}