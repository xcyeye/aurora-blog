package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Site;
import xyz.xcye.admin.vo.SiteVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

public interface SiteService {

    int deleteByPrimaryKey(long uid);

    int physicsDeleteSite(long uid);


    int insertSelective(Site record);

    /**
     * 通过条件查询
     * @param condition 查询参数，keyword->logo_title(不是模糊查询)
     * @return object by primary key
     */
    PageData<SiteVO> selectByCondition(Condition<Long> condition);

    /**
     * 通过userUid查询
     * @param uid
     * @return
     */
    SiteVO selectByUid(long uid);

    int updateByPrimaryKeySelective(Site record);
}