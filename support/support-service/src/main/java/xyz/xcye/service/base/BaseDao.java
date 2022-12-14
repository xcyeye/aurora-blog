package xyz.xcye.service.base;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.data.entity.Condition;

import java.util.List;

@Mapper
public interface BaseDao<T> {
    /**
     * 插入一条数据
     * @param t 实体
     * @return 影响的函数
     */
    int insert(T t);

    /**
     * 插入多条数据
     * @param list 多条实体
     * @return 影响的行数
     */
    int batchInsert(@Param("list") List<T> list);

    /**
     * 根据主键更新单条数据
     * @param t 待更新的实体
     * @return 影响行数
     */
    int updateById(T t);

    /**
     * 根据主键删除数据
     * @param uid 主键
     * @return 影响行数
     */
    int deleteById(@Param("uid") Long uid);

    /**
     * 根据条件删除数据
     * @param t 条件实体
     */
    void deleteByWhere(T t);

    /**
     * 根据uid查询数据
     * @param uid 主键
     * @return 查询到的数据
     */
    T queryById(@Param("uid") Long uid);

    /**
     * 根据条件查询一条数据
     * @param t 条件
     * @return 查询到的数据
     */
    T queryOne(T t);

    /**
     * 根据条件查询多条数据
     * @param t 条件实体
     * @return 查询到的多条数据
     */
    List<T> queryListByWhere(T t);

    /**
     * 根据条件查询满足要求的数量
     * @param t 条件实体
     * @return 数量
     */
    int countByWhere(T t);

    /**
     * 根据uids查询对应的数据
     * @param uids 根据多个uid查询
     * @return 多行数据
     */
    List<T> queryListByIds(@Param("uids") List<Long> uids);

    /**
     * 这是一个兼容方法
     * @param condition 条件
     * @return 多行数据
     */
    List<T> queryListByCondition(@Param("condition") Condition condition);
}