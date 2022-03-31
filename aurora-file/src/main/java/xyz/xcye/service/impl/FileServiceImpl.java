package xyz.xcye.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.common.enums.FileStorageModeEnum;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.common.util.FileUtil;
import xyz.xcye.common.util.id.GenerateInfoUtil;
import xyz.xcye.dao.FileDao;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.exception.DeleteFileException;
import xyz.xcye.exception.UpdateFileException;
import xyz.xcye.exception.UploadFileException;
import xyz.xcye.interfaces.FileStorageService;
import xyz.xcye.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.service.FileService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 此类中不会存在对对象的属性判断
 * @author qsyyke
 */

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;



    /**
     * 当前机器的id
     */
    @Value("${aurora.snow-flake.workerId}")
    private int workerId;

    /**
     * 该台机器对应的数据中心id
     */
    @Value("${aurora.snow-flake.datacenterId}")
    private int datacenterId;

    @Autowired
    private LocalFileStorageServiceImpl localStorageService;

    /**
     * fileEntity对象中必须设置getName和getUserUploadPath，还有getInputStream
     * @param fileEntity
     * @param file
     * @param storageMode
     * @return
     * @throws UploadFileException
     */
    @Override
    public File insertFile(FileEntity fileEntity, File file, int storageMode) throws UploadFileException {

        if (fileEntity.getName() == null || fileEntity.getInputStream() == null) {
            return null;
        }

        //生成一个uid
        BigInteger uid = new BigInteger(GenerateInfoUtil.generateUid(workerId,datacenterId) + "");

        //根据storageMode获取需要使用的文件存储方式
        FileStorageService fileStorageService = getNeedFileStorageService(storageMode);

        FileEntity uploadFileEntity = null;
        try {
            uploadFileEntity = fileStorageService.upload(fileEntity.getInputStream(), fileEntity);
        }catch (IOException e) {
            throw new UploadFileException(e.getMessage(),fileEntity.getStoragePath());
        }

        //对file对象进行赋值
        file = new File(uid,DateUtil.format(new Date()),false,
                uploadFileEntity.getName(),uploadFileEntity.getSize(),
                DateUtil.format(new Date()),file.getSummary(),
                uploadFileEntity.getRemoteUrl(),storageMode,uploadFileEntity.getStoragePath());

        int insertFileNum = fileDao.insertFile(file);
        return insertFileNum == 1 ? file : null;
    }

    @Override
    public File updateFile(FileEntity fileEntity, File file, boolean deleteLocalFile) throws UploadFileException {
        //调用insertFile()方法上传文件
        File insertFileEntity = insertFile(fileEntity, file, file.getStorageMode());

        if (insertFileEntity == null) {
            return null;
        }

        //如果deleteLocalFile为true，则删除原文件
        if (deleteLocalFile) {
            if (FileUtil.deleteFile(file.getStoragePath())) {

            }
        }
        return insertFileEntity;
    }

    @Override
    public File deleteFile(File file, boolean deleteLocalFile) throws DeleteFileException {
        return null;
    }

    @Override
    public List<File> query(File file) {
        return null;
    }

    private FileStorageService getNeedFileStorageService(int storageMode) {

        FileStorageService fileStorageService = null;
        //查看文件的存储方式
        if (storageMode == FileStorageModeEnum.LOCAL_STORAGE) {
            //本地存储
            fileStorageService = localStorageService;
        }

        return fileStorageService;
    }
}
