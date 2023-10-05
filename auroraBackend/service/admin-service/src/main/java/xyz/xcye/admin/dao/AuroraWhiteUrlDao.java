package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table white_url <br/>
 * @description white_url 数据表DAO层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 */

@Mapper
public interface AuroraWhiteUrlDao extends BaseDao<WhiteUrl> {

}