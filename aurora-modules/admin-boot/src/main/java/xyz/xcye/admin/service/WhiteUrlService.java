package xyz.xcye.admin.service;

import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/10 21:11
 */

public interface WhiteUrlService {

    /**
     * 根据uid删除白名单
     * @param uid
     * @return
     */
    int deleteWhiteUrlByUid(Long uid);

    /**
     * 插入白名单数据，有选择的
     * @param record
     * @return
     */
    int insertWhiteUrlSelective(WhiteUrl record);

    /**
     * 条件查询，其中keyword为url
     * @param condition
     * @return
     */
    PageData<WhiteUrl> selectWhiteUrlByCondition(@Param("condition") Condition<Integer> condition);

    /**
     * 修改白名单数据，有选择的
     * @param record
     * @return
     */
    int updateWhiteUrlSelective(WhiteUrl record);
}
