package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.admin.dao.AuroraWhiteUrlDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table white_url <br/>
 * @description white_url 数据表Service层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraWhiteUrlService extends BaseService<WhiteUrl> {
	@SuppressWarnings("unused")
	private AuroraWhiteUrlDao whiteUrlDao;
	
	@Autowired
    public void setInfoDao(AuroraWhiteUrlDao whiteUrlDao) {
        super.setBaseDao(whiteUrlDao);
        this.whiteUrlDao = whiteUrlDao;
    }
}