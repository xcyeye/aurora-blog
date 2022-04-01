package xyz.xcye.service;

import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.exception.CustomFileException;

import java.util.List;

/**
 * 文件service层
 * @author qsyyke
 */


public interface FileService {
    /**
     * 根据指定存储服务，将文件上传到存储服务中，并且将文件的信息，记录在数据库中，返回文件的上传情况(任何类型的文件都可以上传)
     * @param fileEntity 上传文件数据，需要传入输入流，还有文件名称
     * @param file 此文件的描述信息，包括创建时间，简介等等
     * @param storageMode 文件存储模式，值只能在xyz.xcye.common.enums.FileStorageModeEnum中取
     * @return 返回文件的上传情况
     * @throws CustomFileException
     */
    ModifyResult insertFile(FileEntity fileEntity, File file, int storageMode) throws CustomFileException;

    /**
     * 根据传入uid，修改指定文件的数据库信息（不能修改文件本身的信息，比如文件名，存储路径，path，因为没有意义）
     * @param file 需要修改的信息
     * @return 修改情况
     */
    ModifyResult updateFile (File file);

    /**
     * 从指定存储服务中，删除需删除的uid所对应的在存储服务中的文件以及将数据库中的删除状态修改为ture，没有在这里直接删除记录，是因为想要统一删除
     * @param file 存放文件uid的对象，可以只包含uid
     * @return 删除之后的情况
     * @throws CustomFileException
     */
    ModifyResult deleteFile(File file) throws CustomFileException;

    /**
     * 向数据库中查询文件
     * @param file FileEntity对象
     * @return FileEntity对象
     */
    List<File> queryAllFile(File file, Pagination pagination);
}
