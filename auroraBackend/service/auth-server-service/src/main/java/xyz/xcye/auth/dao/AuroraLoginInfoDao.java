package xyz.xcye.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.service.base.BaseDao;

/**
 * @table login_info <br/>
 * @description login_info 数据表DAO层 <br/>
 * @date 2022-12-14 23:53:19 <br/>
 * @author xcye <br/>
 */

@Mapper
public interface AuroraLoginInfoDao extends BaseDao<LoginInfo> {

}