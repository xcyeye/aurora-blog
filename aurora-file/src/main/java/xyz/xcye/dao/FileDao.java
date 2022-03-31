package xyz.xcye.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.entity.table.File;

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
    int insertFile(@Param("file") File file);

    /**
     * 向数据库更新文件数据
     * @param file FileEntity对象
     * @return 影响行数
     */
    int updateFile(@Param("file") File file);

    /**
     * 向数据库中删除文件数据
     * @param file FileEntity对象
     * @return 影响行数
     */
    int deleteFile(@Param("file") File file);

    /**
     * 向数据库中查询文件
     * @param file FileEntity对象
     * @return FileEntity对象集合
     */
    List<File> query(@Param("file") File file);
}
