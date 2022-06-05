package xyz.xcye.admin.service;

import xyz.xcye.admin.po.Site;
import xyz.xcye.admin.vo.SiteVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

public interface SiteService {

    /**
     * 逻辑删除
     * @param uid
     * @return
     */
    int deleteByPrimaryKey(long uid);

    /**
     * 物理删除站点信息
     * @param uid
     * @return
     */
    int physicsDeleteSite(long uid);

    /**
     * 有选择的插入站点信息
     * @param record
     * @return
     */
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

    /**
     * 有选择的修改站点信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Site record);
}