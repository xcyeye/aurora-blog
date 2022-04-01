package xyz.xcye.interfaces.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.enums.ResultStatusCode;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.exception.CustomFileException;
import xyz.xcye.interfaces.FileStorageService;
import xyz.xcye.common.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 这是本地存储实现类
 * <p>目的是将上传的文件存放在本地(服务器上)</p>
 * @author qsyyke
 */

@Component
public class LocalFileStorageServiceImpl implements FileStorageService {

    /**
     * 从哪个目录获取文件或者是上传到哪个目录
     */
    @Value("${aurora.file.nginx-root-path}")
    private String nginxRootPath;

    /**
     * 域名或者ip地址（访问nginx映射文件）
     */
    @Value("${aurora.file.host}")
    private String host;

    /**
     * 在nginx-root-path目录下创建一个单独的文件夹来存放所有上传的文件的文件夹名称
     */
    @Value("${aurora.file.upload-folder-name}")
    private String uploadFolderName;

    /**
     * 该文件的拥有者
     */
    private String fileOwner;

    public LocalFileStorageServiceImpl(String fileOwner) {
        this.fileOwner = fileOwner;
    }

    public LocalFileStorageServiceImpl() {}

    /**
     * 将一个文件的输入流，在本地创建一个文件，并把输入流写入到这个文件中
     * @param inputStream 输入流
     * @param fileEntity fileEntity对象只会使用到userUploadPath和name这两个属性
     * @return
     * @throws IOException
     */
    @Override
    public FileEntity upload(InputStream inputStream, FileEntity fileEntity) throws CustomFileException {
        //获取上传文件的扩展名
        String extName = FileUtil.getExtName(fileEntity.getName());

        String separator = File.separator;

        //获取当前的年和月
        int currentYear = DateUtil.getYear(new Date());
        int currentMonth = DateUtil.getMonth(new Date());

        if (!nginxRootPath.endsWith(File.separator)) {
            nginxRootPath = nginxRootPath + File.separator;
        }

        //判断文件夹是否存在aurora.file.nginx-root-path/aurora.file.upload-folder-name/extName/currentYear/currentMonth
        String folderPath = nginxRootPath + uploadFolderName + separator + extName + separator + currentYear + separator + currentMonth;
        String filePath = getFilePath(fileEntity,folderPath);

        //1. 创建此path文件路径还有文件夹（如果不存在）
        createFile(filePath, folderPath);

        //2. 将此输入流写入到path中
        File writeFile = null;
        try {
            writeFile = FileUtil.writeByStream(inputStream, filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomFileException(ResultStatusCode.EXCEPTION_FILE_FAIL_UPLOAD.getMessage(),ResultStatusCode.EXCEPTION_FILE_FAIL_UPLOAD.getCode());
        }

        String fileRemoteUrl = host + FileUtil.getFileSplitPath(nginxRootPath,writeFile.getAbsolutePath());

        return new FileEntity(writeFile.getAbsolutePath(),writeFile.getName(),writeFile.length(),fileRemoteUrl);
    }

    @Override
    public FileEntity download(String objectName) throws IOException {
        //FileUtil.getOutputStream()
        return null;
    }

    @Override
    public FileEntity query(String objectName) throws IOException {
        return null;
    }

    /**
     * 从本地删除指定文件
     * @param objectName objectName
     * @return 删除成功 true 失败 false
     */
    @Override
    public boolean delete(String objectName) {
        return FileUtil.deleteFile(objectName);
    }

    /**
     * 为了保证多次上传同一个文件，都能够成功，所以会对文件名进行处理，处理规则，原名字(无后缀)+时间戳
     * @param fileEntity
     * @param folderPath
     * @return
     */
    private String getFilePath(FileEntity fileEntity,String folderPath) {
        String extName = FileUtil.getExtName(fileEntity.getName());
        String mainName = FileUtil.getMainName(fileEntity.getName());
        String filePath = folderPath + File.separator + mainName + "." + extName;

        if (new File(filePath).exists()) {
            filePath = folderPath + File.separator + mainName + System.currentTimeMillis() + "." + extName;
        }

        return filePath;
    }

    /**
     * 在指定目录中，创建指定文件
     * @param filePath 指定文件
     * @param folderPath 指定目录
     * @return true创建成功，反之
     * @throws CustomFileException
     */
    private boolean createFile(String filePath,String folderPath) throws CustomFileException {
        //文件夹是否存在
        boolean folderExists = new File(folderPath).exists();

        if (!folderExists) {
            //创建文件夹
            if (!FileUtil.createFile(folderPath, true)) {
                //创建文件夹失败
                throw new CustomFileException(ResultStatusCode.EXCEPTION_FILE_FAIL_CREATE.getMessage(),ResultStatusCode.EXCEPTION_FILE_FAIL_CREATE.getCode());
            }
        }

        //执行到这里，说明文件夹已经存在或者创建成功

        //创建需要写入的文件
        boolean isCreateFile = FileUtil.createFile(filePath, false);
        if (!isCreateFile) {
            //创建文件夹失败
            throw new CustomFileException(ResultStatusCode.EXCEPTION_FILE_FAIL_CREATE.getMessage(),ResultStatusCode.EXCEPTION_FILE_FAIL_CREATE.getCode());
        }

        return isCreateFile;
    }
}
