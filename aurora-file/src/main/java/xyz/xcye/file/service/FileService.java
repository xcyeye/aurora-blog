package xyz.xcye.file.service;

import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.FileEntityDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.FileDO;
import xyz.xcye.common.exception.file.FileException;
import xyz.xcye.common.vo.FileVO;

import java.io.IOException;
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
     */
    FileVO insertFile(FileEntityDTO fileEntity, FileDO file, int storageMode) throws FileException, InstantiationException, IllegalAccessException;

    /**
     * 根据传入uid，修改指定文件的数据库信息（不能修改文件本身的信息，比如文件名，存储路径，path，因为没有意义）
     * @param file 需要修改的信息
     * @return 修改情况
     */
    ModifyResult updateFile (FileDO file);

    /**
     * 从指定存储服务中，删除需删除的uid所对应的在存储服务中的文件以及将数据库中的删除状态修改为ture，没有在这里直接删除记录，是因为想要统一删除
     * @param uid
     * @return 删除之后的情况
     */
    ModifyResult deleteFile(long uid) throws InstantiationException, IllegalAccessException, IOException, FileException;

    /**
     * 向数据库中查询文件
     * @param condition 查询添加，其中keyword为(file_name)
     * @return FileEntity对象
     */
    List<FileVO> queryAllFile(ConditionDTO<Long> condition) throws InstantiationException, IllegalAccessException;

    FileVO queryByUid(long uid) throws InstantiationException, IllegalAccessException;

    /**
     * 下载文件
     * @param uid
     * @return
     */
    FileEntityDTO downloadFile(long uid) throws InstantiationException, IllegalAccessException, FileException, IOException;
}
