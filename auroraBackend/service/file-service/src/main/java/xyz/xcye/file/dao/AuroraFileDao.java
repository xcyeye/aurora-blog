package xyz.xcye.file.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.file.po.File;
import xyz.xcye.service.base.BaseDao;

/**
 * @table file <br/>
 * @description file 数据表DAO层 <br/>
 * @date 2022-12-14 22:31:22 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraFileDao extends BaseDao<File> {

}