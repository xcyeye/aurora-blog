package xyz.xcye.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.xcye.entity.table.VerifyPath;

import java.util.List;

@Mapper
public interface VerifyPathDao {
    /**
     * 返回所有的路径访问权限信息
     * @return 路径权限集合
     */
    List<VerifyPath> queryAllVerifyPath();

}