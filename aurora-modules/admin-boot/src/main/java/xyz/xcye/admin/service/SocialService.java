package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Social;
import xyz.xcye.admin.vo.SocialVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

public interface SocialService {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(long uid);

    int physicsDeleteSocial(long uid);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Social record);

    /**
     * select by primary key
     * @param condition 查询参数，keyword->social_name(不是模糊查询)
     * @return object by primary key
     */
    PageData<SocialVO> selectByCondition(Condition<Long> condition);

    SocialVO selectByUid(long uid);

    int updateByPrimaryKeySelective(Social record);
}