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

    int deleteByUid(Long uid);

    int insert(WhiteUrl record);

    int insertSelective(WhiteUrl record);

    List<WhiteUrl> selectByCondition(@Param("condition") Condition<Integer> condition);

    int updateByPrimaryKeySelective(WhiteUrl record);

}
