package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.auth.dao.AuroraOauthCodeDao;
import xyz.xcye.auth.po.OauthCode;
import xyz.xcye.service.base.BaseService;

/**
 * @table oauth_client_details <br/>
 * @description oauth_client_details 数据表Service层 <br/>
 * @date 2022-12-14 23:53:19 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraOauthCodeService extends BaseService<OauthCode> {
    @SuppressWarnings("unused")
    private AuroraOauthCodeDao oauthCodeDao;

    @Autowired
    public void setInfoDao(AuroraOauthCodeDao oauthCodeDao) {
        super.setBaseDao(oauthCodeDao);
        this.oauthCodeDao = oauthCodeDao;
    }
}