package xyz.xcye.file.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.table.FileDO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface FileDao {
    /**
     * 向数据库中插入文件数据
     * @param file File对象
     * @return 影响行数
     */
    int insertFile(@Param("file") FileDO file);

    /**
     * 向数据库更新文件数据
     * @param file FileEntity对象
     * @return 影响行数
     */
    int updateFile(@Param("file") FileDO file);

    /**
     * 向数据库中删除文件数据
     * @param file FileEntity对象
     * @return 影响行数
     */
    int deleteFile(@Param("file") FileDO file);

    /**
     * 向数据库中查询文件
     * @param condition 查询条件，其中keyword为文件名(file_name)
     * @return FileEntity对象集合
     */
    List<FileDO> queryAll(@Param("condition") ConditionDTO condition);
}
