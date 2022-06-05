package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
* @author aurora
* @description 针对表【au_white_url】的数据库操作Mapper
* @createDate 2022-05-10 21:03:40
* @Entity xyz.xcye.admin.po.WhiteUrl
*/

@Mapper
public interface WhiteUrlMapper {

    /**
     * 根据uid删除白名单数据
     * @param uid
     * @return
     */
    int deleteWhiteUrlByUid(Long uid);


    int insert(WhiteUrl record);

    /**
     * 插入白名单数据
     * @param record
     * @return
     */
    int insertWhiteUrlSelective(WhiteUrl record);

    /**
     * 根据自定义条件查询满足要求的白名单数据
     * @param condition
     * @return
     */
    List<WhiteUrl> selectByCondition(@Param("condition") Condition<Integer> condition);

    /**
     * 更新白名单数据
     * @param record
     * @return
     */
    int updateWhiteUrlSelective(WhiteUrl record);

}
