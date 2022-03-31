package xyz.xcye.service;

import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.exception.DeleteFileException;
import xyz.xcye.exception.UploadFileException;

import java.util.List;

/**
 * 文件service层
 * @author qsyyke
 */


public interface FileService {
    /**
     * 向数据库中插入文件数据
     * <p>如果插入失败的话，会返回一个null，但是可能文件是存储成功的，可能就是存储到数据库的时候出问题</p>
     */
    File insertFile(FileEntity fileEntity,File file,int storageMode) throws UploadFileException;

    /**
     * 向数据库更新文件数据
     * @param fileEntity FileEntity对象
     * @return FileEntity对象
     */
    File updateFile(FileEntity fileEntity, File file, boolean deleteLocalFile) throws UploadFileException;

    /**
     * 向数据库中删除文件数据
     * @param file FileEntity对象
     * @param deleteLocalFile 是否删除本地文件
     * @return FileEntity对象
     */
    File deleteFile(File file, boolean deleteLocalFile) throws DeleteFileException;

    /**
     * 向数据库中查询文件
     * @param file FileEntity对象
     * @return FileEntity对象
     */
    List<File> query(File file);
}
