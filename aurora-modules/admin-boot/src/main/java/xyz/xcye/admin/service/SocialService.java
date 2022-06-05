package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Social;
import xyz.xcye.admin.vo.SocialVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

public interface SocialService {
    /**
     * 通过主键删除社交信息
     * @param uid
     * @return
     */
    int deleteByPrimaryKey(long uid);

    /**
     * 物理删除社交信息
     * @param uid
     * @return
     */
    int physicsDeleteSocial(long uid);

    /**
     * 有选择的插入社交信息
     * @param record
     * @return
     */
    int insertSelective(Social record);

    /**
     * 根据自定义条件查询社交信息
     * @param condition 查询参数，keyword->social_name(不是模糊查询)
     * @return object by primary key
     */
    PageData<SocialVO> selectByCondition(Condition<Long> condition);

    /**
     * 根据uid查询社交信息
     * @param uid
     * @return
     */
    SocialVO selectByUid(long uid);

    /**
     * 有选择的修改社交信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Social record);
}