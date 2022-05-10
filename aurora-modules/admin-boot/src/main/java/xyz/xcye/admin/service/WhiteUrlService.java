package xyz.xcye.admin.service;

import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/10 21:11
 */

public interface WhiteUrlService {
    int deleteByUid(Long uid);

    int insert(WhiteUrl record);

    int insertSelective(WhiteUrl record);

    /**
     * 条件查询，其中keyword为url
     * @param condition
     * @return
     */
    List<WhiteUrl> selectByCondition(@Param("condition") Condition<Integer> condition);

    int updateByPrimaryKeySelective(WhiteUrl record);
}
