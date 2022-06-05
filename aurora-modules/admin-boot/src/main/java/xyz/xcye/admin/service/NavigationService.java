package xyz.xcye.admin.service;

import xyz.xcye.admin.dto.NavigationDTO;
import xyz.xcye.admin.po.Navigation;
import xyz.xcye.admin.vo.NavigationVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

public interface NavigationService {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(long uid);

    int physicsDeleteNavigation(long uid);

    /**
     * insertWhiteUrl record to table selective
     * @param record the record
     * @return insertWhiteUrl count
     */
    int insertSelective(Navigation record);

    /**
     * select by primary key
     * @param condition 查询参数，keyword->title(不是模糊查询)
     * @return object by primary key
     */
    PageData<NavigationVO> selectByCondition(Condition<Long> condition);

    NavigationVO selectNavigationByUid(long uid);

    /**
     * 根据用户名，获取该用户的所有导航信息
     * @param userUid
     * @return
     */
    NavigationDTO selectAllNavigationByUserUid(long userUid);

    int updateByPrimaryKeySelective(Navigation record);
}