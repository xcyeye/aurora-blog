package xyz.xcye.file.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.file.po.File;

import java.util.List;

@Mapper
public interface FileMapper {
    /**
     * delete by primary key
     * @param uid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(File record);

    /**
     * select by primary key
     * @param condition 查询条件，其中keyword为文件名(file_name)
     * @return object by primary key
     */
    List<File> selectByCondition(@Param("condition") Condition<Long> condition);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(File record);
}