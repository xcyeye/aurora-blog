package xyz.xcye.file.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.file.po.AuPeople;
import xyz.xcye.service.base.BaseDao;

/**
 * @table au_people <br/>
 * @description au_people 数据表DAO层 <br/>
 * @date 2022-12-13 18:59:12 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuPeopleDao extends BaseDao<AuPeople> {

}