package xyz.xcye.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.auth.po.OauthCode;
import xyz.xcye.service.base.BaseDao;

/**
 * description : oauth_code table dao layer interface <br/>
 * time:    2022-12-14 23:53:19 <br/>
 *
 * @author xcye <br/>
 * @version 1.0 <br/>
 * @since 1.0 <br/>
 */
@Mapper
public interface AuroraOauthCodeDao extends BaseDao<OauthCode> {

}