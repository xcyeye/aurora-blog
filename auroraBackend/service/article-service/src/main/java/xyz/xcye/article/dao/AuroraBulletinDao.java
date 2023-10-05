package xyz.xcye.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.article.po.Bulletin;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table bulletin <br/>
 * @description bulletin 数据表DAO层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Mapper
public interface AuroraBulletinDao extends BaseDao<Bulletin> {

}