package xyz.xcye.auth.service;

import xyz.xcye.auth.po.OauthClientDetails;
import xyz.xcye.auth.vo.OauthClientDetailsVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

public interface OauthClientDetailsService {
    /**
     * 通过客户端id删除
     * @param clientId primaryKey
     * @return deleteCount
     */
    int deleteByClientId(String clientId);

    /**
     * 插入客户端秘钥信息
     * @param record the record
     * @return insert count
     */
    int insertOauthClientDetails(OauthClientDetails record);

    /**
     * 通过自定义条件查询满足要求的客户端秘钥信息
     * @param condition
     * @return
     */
    PageData<OauthClientDetailsVO> selectByCondition(Condition<Object> condition);

    OauthClientDetailsVO selectByClientId(String clientId);

    /**
     * 更新客户端秘钥信息
     * @param record the updated record
     * @return update count
     */
    int updateOauthClientDetails(OauthClientDetails record);
}