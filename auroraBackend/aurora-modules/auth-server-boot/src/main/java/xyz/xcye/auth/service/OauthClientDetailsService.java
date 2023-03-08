package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.auth.constant.OauthClientConfigConstant;
import xyz.xcye.auth.po.OauthClientDetails;
import xyz.xcye.auth.pojo.OauthClientDetailsPojo;
import xyz.xcye.auth.vo.OauthClientDetailsVO;
import xyz.xcye.core.exception.oauth.OauthException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author qsyyke
 * @date Created in 2022/6/25 11:32
 */

@Service
public class OauthClientDetailsService {

    @Autowired
    private AuroraOauthClientDetailsService auroraOauthClientDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public int physicalDeleteOauthClient(OauthClientDetailsPojo pojo) {
        String clientId = pojo.getClientId();
        AssertUtils.ifNoLengthThrow(clientId, () -> new OauthException("clientId不能为null"));
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(clientId);
        return auroraOauthClientDetailsService.deleteByWhere(oauthClientDetails);
    }

    public void insertOauthClient(OauthClientDetailsPojo pojo) {
        AssertUtils.ifNullThrow(pojo, () -> new OauthException("必须存在客户端信息"));
        AssertUtils.ifNullThrow(pojo.getClientId(), () -> new OauthException("必须存在clientId"));
        AssertUtils.ifNullThrow(pojo.getAuthorizedGrantTypes(), () -> new OauthException("必须存在授权类型"));
        AssertUtils.ifNullThrow(pojo.getClientSecret(), () -> new OauthException("必须要有密码"));
        // 判断此clientId是否存在
        OauthClientDetails oauthClientDetails = auroraOauthClientDetailsService.queryOne(new OauthClientDetails() {{
            setClientId(pojo.getClientId());
        }});
        AssertUtils.stateThrow(oauthClientDetails == null, () -> new OauthException("此clientId已经存在"));
        // 判断授权类型
        String authorizedGrantTypes = assertAuthorizedGrantTypes(pojo);
        AssertUtils.stateThrow(StringUtils.hasLength(authorizedGrantTypes), () -> new OauthException("没有授权类型"));
        pojo.setClientSecret(passwordEncoder.encode(pojo.getClientSecret()));
        auroraOauthClientDetailsService.insert(BeanUtils.copyProperties(pojo, OauthClientDetails.class));
    }

    public PageData<OauthClientDetailsVO> queryListOauthClientByCondition(Condition<Object> condition) {
        return PageUtils.copyPageDataResult(auroraOauthClientDetailsService.queryListByCondition(condition), OauthClientDetailsVO.class);
    }

    public OauthClientDetailsVO queryOauthClientByClientId(OauthClientDetailsPojo pojo) {
        AssertUtils.ifNullThrow(pojo, () -> new OauthException("必须存在客户端信息"));
        AssertUtils.ifNullThrow(pojo.getClientId(), () -> new OauthException("必须存在clientId"));
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(pojo.getClientId());
        return BeanUtils.copyProperties(auroraOauthClientDetailsService.queryOne(oauthClientDetails), OauthClientDetailsVO.class);
    }

    public int updateOauthClient(OauthClientDetailsPojo pojo) {
        AssertUtils.ifNullThrow(pojo, () -> new OauthException("必须存在客户端信息"));
        AssertUtils.ifNullThrow(pojo.getClientId(), () -> new OauthException("必须存在clientId"));
        if (StringUtils.hasLength(pojo.getAuthorizedGrantTypes())) {
            String authorizedGrantTypes = assertAuthorizedGrantTypes(pojo);
            if (StringUtils.hasLength(authorizedGrantTypes)) {
                pojo.setAuthorizedGrantTypes(authorizedGrantTypes);
            }else {
                pojo.setAuthorizedGrantTypes(null);
            }
        }
        if (StringUtils.hasLength(pojo.getClientSecret())) {
            pojo.setClientSecret(passwordEncoder.encode(pojo.getClientSecret()));
        }
        return auroraOauthClientDetailsService.updateById(BeanUtils.copyProperties(pojo, OauthClientDetails.class));
    }

    private String assertAuthorizedGrantTypes(OauthClientDetailsPojo pojo) {
        return Arrays.stream(pojo.getAuthorizedGrantTypes().split(","))
                .filter(OauthClientConfigConstant.AUTHORIZED_GRANT_TYPES::contains)
                .collect(Collectors.joining(","));
    }
}
