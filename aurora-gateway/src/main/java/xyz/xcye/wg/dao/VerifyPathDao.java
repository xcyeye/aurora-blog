package xyz.xcye.wg.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.common.dos.VerifyPathDO;

import java.util.List;

@Mapper
public interface VerifyPathDao {
    /**
     * 返回所有的路径访问权限信息
     * @return 路径权限集合
     */
    List<VerifyPathDO> queryAllVerifyPath();

}