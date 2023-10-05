package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.auth.dao.AuroraOauthClientDetailsDao;
import xyz.xcye.auth.po.OauthClientDetails;
import xyz.xcye.service.base.BaseService;

/**
 * @author xcye <br/>
 * @table oauth_client_details <br/>
 * @description oauth_client_details 数据表Service层 <br/>
 * @date 2022-12-14 23:53:19 <br/>
 */

@Service
public class AuroraOauthClientDetailsService extends BaseService<OauthClientDetails> {
    @SuppressWarnings("unused")
    private AuroraOauthClientDetailsDao oauthClientDetailsDao;

    @Autowired
    public void setInfoDao(AuroraOauthClientDetailsDao oauthClientDetailsDao) {
        super.setBaseDao(oauthClientDetailsDao);
        this.oauthClientDetailsDao = oauthClientDetailsDao;
    }
}