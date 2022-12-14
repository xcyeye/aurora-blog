package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.auth.po.OauthClientDetails;
import xyz.xcye.auth.vo.OauthClientDetailsVO;
import xyz.xcye.core.exception.oauth.OauthException;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/6/25 11:32
 */

@Service
public class OauthClientDetailsService {

    @Autowired
    private AuroraOauthClientDetailsService auroraOauthClientDetailsService;

    public int deleteByClientId(String clientId) {
        AssertUtils.ifNoLengthThrow(clientId, () -> new OauthException("clientId不能为null"));
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(clientId);
        return auroraOauthClientDetailsService.deleteByWhere(oauthClientDetails);
    }

    public void insertOauthClientDetails(OauthClientDetails record) {
        AssertUtils.ifNullThrow(record, () -> new OauthException("必须存在客户端信息"));
    }

    public PageData<OauthClientDetailsVO> selectByCondition(Condition<Object> condition) {
        return null;
    }

    public OauthClientDetailsVO selectByClientId(String clientId) {
        return null;
    }

    public int updateOauthClientDetails(OauthClientDetails record) {
        return 0;
    }
}
