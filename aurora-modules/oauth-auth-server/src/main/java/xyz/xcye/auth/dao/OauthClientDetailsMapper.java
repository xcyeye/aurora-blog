package xyz.xcye.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.auth.po.OauthClientDetails;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface OauthClientDetailsMapper {
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
    List<OauthClientDetails> selectByCondition(@Param("condition") Condition<Object> condition);

    /**
     * 更新客户端秘钥信息
     * @param record the updated record
     * @return update count
     */
    int updateOauthClientDetails(OauthClientDetails record);
}