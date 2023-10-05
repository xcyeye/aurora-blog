package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.admin.po.Setting;
import xyz.xcye.service.base.BaseDao;

/**
 * @author xcye <br/>
 * @table setting <br/>
 * @description setting 数据表DAO层 <br/>
 * @date 2022-12-30 15:46:26 <br/>
 */

@Mapper
public interface AuroraSettingDao extends BaseDao<Setting> {

}