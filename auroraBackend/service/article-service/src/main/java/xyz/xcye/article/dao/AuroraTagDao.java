package xyz.xcye.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.article.po.Tag;
import xyz.xcye.service.base.BaseDao;

/**
 * @table tag <br/>
 * @description tag 数据表DAO层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraTagDao extends BaseDao<Tag> {

}