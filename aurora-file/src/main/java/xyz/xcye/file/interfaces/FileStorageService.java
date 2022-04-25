package xyz.xcye.file.interfaces;

import xyz.xcye.common.dto.FileEntityDTO;
import xyz.xcye.common.exception.file.FileException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传接口，继承类比如，本地服务器存储，阿里云oss，又拍云等
 * @author qsyyke
 */

public interface FileStorageService {
    /**
     * 将文件上传到指定的存储服务器中
     * @param inputStream 输入流
     * @return 文件实体
     * @throws IOException 异常
     */
    FileEntityDTO upload(InputStream inputStream, FileEntityDTO fileEntity) throws FileException;

    /**
     * 将文件从存储服务上下载下来
     * @param objectName 如果存储服务是本地，那么objectName就是文件的uuid号，如果是对象存储服务，objectName就是文件的路径(参照阿里云oss)
     * @return 输出流
     * @throws IOException 异常
     */
    FileEntityDTO download(String objectName) throws IOException;

    /**
     * 根据objectName查询文件
     * @param objectName 文件的绝对路径或者是oss的objectName
     * @return 文件实体
     * @throws IOException 异常
     */
    FileEntityDTO query(String objectName) throws IOException;

    /**
     * 根据objectName删除对应文件
     * @param objectName objectName
     * @return true表示删除成功，false表示删除失败
     * @throws IOException 异常
     */
    boolean delete(String objectName);
}
