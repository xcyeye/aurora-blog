package xyz.xcye.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 文件实体
 * @author qsyyke
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileEntity {
    /**
     * 如果使用的是对象存储，那么此值就是文件路径，如果是本地服务器存储，那么此值就是uuid，唯一
     */
    private String objectName;

    /** 文件的名称，包含扩展名 **/
    private String name;

    /** 主文件名，不含扩展名 **/
    private String mainName;

    /** 文件的扩展名 **/
    private String extName;

    /** 文件的大小 byte **/
    private long size;

    /** 文件的创建时间 **/
    private String createTime;

    /** 如果存储在本地，则表示本地文件路径，存储在oss，为"" **/
    private String localPath;

    /** 返回文件的url地址 **/
    private String remoteUrl;

    /** 文件输入流 **/
    private InputStream inputStream;

    /** 文件的所有者 **/
    private String owner;

    public FileEntity(String name, long size, String remoteUrl, InputStream inputStream, String owner) {
        this.name = name;
        this.size = size;
        this.remoteUrl = remoteUrl;
        this.inputStream = inputStream;
        this.owner = owner;
    }

    public FileEntity(String name, long size, String remoteUrl, InputStream inputStream, String owner, String objectName) {
        this.name = name;
        this.size = size;
        this.remoteUrl = remoteUrl;
        this.inputStream = inputStream;
        this.owner = owner;
        this.objectName = objectName;
    }
}
